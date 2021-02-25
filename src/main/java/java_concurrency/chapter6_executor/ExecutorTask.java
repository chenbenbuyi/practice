package java_concurrency.chapter6_executor;

import java.util.concurrent.Executor;

/**
 * @author chen
 * @date 2021/1/20 6:45
 * @Description 利用任务执行的抽象 Executor 来编写程序而不是直接t通过手动创建线程。即使不利用线程池，利用Executor接口来编写代码也更优雅.这要求必须将任务表述为一个Runnable
 */
public class ExecutorTask {

    // 异步执行
    static class AsynTask implements Executor{
        @Override
        public void execute(Runnable task) {
            new Thread(task).start();
        }

    }


    // 同步任务
    class SynTask implements Executor {
        @Override
        public void execute(Runnable task) {
            task.run();
        }
    }

}
