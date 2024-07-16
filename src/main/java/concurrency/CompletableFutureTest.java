package concurrency;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author csxian
 * @Description 关于CompletableFuture的一些问题探究
 * Q1:多个CompletableFuture对象组成的任务列表，使用allof.get方法和allOf.thenApply方法时，两者的执行时间先后问题
 *  此问题在于对thenApply或get方法的理解不够透彻，实际上，调用CompletableFuture对象的allOf方法或thenApply等方法时，都会创建新的CompletableFuture对象，所以，决定时间先后的关键在于其执行的异步任务本身的执行时序
 *  因为通过调用 supplyAsync 或其他异步方法创建的CompletableFuture对象，其提交的异步任务会直接开始执行，和是否调用get/allOf等方法无关。
 *  所以，使用CompletableFuture来完成异步任务时，虽然通常会采用对allOf或get等阻塞方法来获取结果或判断异步方法是否执行完毕，但allOf或get等方法的结束不能作为其内部异步任务执行时序先后的判断依据。
 *  即可以保证的是 allOf.thenApply内的任务执行肯定是在allOf所涉及的所有的CompletableFuture对象提交的异步执行完成后才执行，但不能保证allOf.get 方法完成和allOf.thenApply内任务的先后顺序，因为 allOf.get方法的调用与否和异步任务无关
 *
 * Q2:多个CompletableFuture对象通过allOf组成的任务列表，allOf.get带超时的方法调用后，为啥allOf.thenApply内部在对单个CompletableFuture对象进行get超时调用，内部的超时异常不会触发？
 * 因为 thenApply 保证了时序关系
 * 如下的代码示例中，allOf为一个所有CompletableFuture对象组成的任务列表，其内部thenApply方法中，对future1和future2进行get超时调用
 * 但实际执行时，只有外部的allOf.get(1,TimeUnit.SECONDS)方法会触发超时异常，内部的get超时调用不会触发超时异常，因为thenApply保证了时序关系，即执行到thenApply所在的异步方法时，其实allOf所涉及的异步任务全部都已经完成了
 * 此时对已经完成态的任务进行get操作，仅仅是有获取结果的作用，不会再有超时的效果
 *
 * @Date 2024/7/16 10:57
 */

@Slf4j
public class CompletableFutureTest {

    public static void main(String[] args) throws InterruptedException {
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(4);
                log.info("任务1完成");
            } catch (InterruptedException e) {
            }
        });
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
                log.info("任务2完成");
            } catch (InterruptedException e) {
            }
        });
        List<CompletableFuture> completableFutures = new ArrayList<>();
        completableFutures.add(future1);
        completableFutures.add(future2);

        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFutures.toArray(new CompletableFuture[0]));


        // 这里将在 future1 和 future2 都完成后执行 因为生成了新的CompletableFuture，所以该步骤不会阻塞主流程，除非对该CompletableFuture进行get操作
        allOf.thenApply(v ->{
            log.info( "All tasks completed");
            for (CompletableFuture completableFuture : completableFutures) {
                try {
                    completableFuture.get(1,TimeUnit.MICROSECONDS);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } catch (ExecutionException e) {
                    throw new RuntimeException(e);
                } catch (TimeoutException e) {
                    // 因为外层allOf.thenApply的时序关系存在，故此处的超时异常永远不会触发
                    throw new RuntimeException(e);
                }
            }
            return null;
        });

        log.info("流程到此2=========");

        try {
            // 这里会阻塞，直到 future1 和 future2 都完成
            allOf.get(1, TimeUnit.SECONDS);
        } catch (Exception  e) {
            e.printStackTrace();
        }

    }
}
