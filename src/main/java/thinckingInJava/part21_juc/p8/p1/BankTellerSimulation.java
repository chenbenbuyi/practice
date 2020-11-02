package thinckingInJava.part21_juc.p8.p1;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/8/15 11:50
 * @Description
 */

public class BankTellerSimulation {
    private static final int MAX_LINE_SIZE = 50;
    private static final int ADJUSTMENT_PERIOD = 1000;

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
//        ExecutorService executor = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>());
        CustomerQueue customers = new CustomerQueue(MAX_LINE_SIZE);
        executor.execute(new CustomerGenerator(customers));
        executor.execute(new TellerManger(executor, customers, ADJUSTMENT_PERIOD));
        System.out.println(" 请按回车键退出 ");
        TimeUnit.SECONDS.sleep(5);
        // 如果线程池关闭，会停止接收外部提交的任务
        executor.shutdown();
    }
}


class Customer {
    private final int serviceTime;

    Customer(int serviceTime) {
        this.serviceTime = serviceTime;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "[" + serviceTime + "]";
    }
}

// 等待被服务的顾客构成的队列
class CustomerQueue extends ArrayBlockingQueue<Customer> {
    CustomerQueue(int maxlSize) {
        super(maxlSize);
    }

    @Override
    public String toString() {
        if (this.size() == 0) {
            return "[空队列]";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Customer customer : this) {
            stringBuilder.append(customer);
        }
        return stringBuilder.toString();
    }
}

// 模拟产生顾客
class CustomerGenerator implements Runnable {

    private CustomerQueue customers;
    private static Random rand = new Random(47);

    CustomerGenerator(CustomerQueue customers) {
        this.customers = customers;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 间隔随机时间向队列中添加顾客
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(300));
                // 顾客的用餐时间随机
                customers.put(new Customer(rand.nextInt(1000)));
            }
        } catch (InterruptedException e) {
            System.out.println("CustomerGenerator 中断执行");
        }
        System.out.println("CustomerGenerator 执行结束");
    }
}

// 顾客在队列中等待被某个 teller服务
class Teller implements Runnable, Comparable<Teller> {
    private static int counter = 0;
    private final int id = counter++;
    private int customersServed = 0;
    private CustomerQueue customers;
    // 用于判定队列中是否有需要服务的顾客
    private boolean servingCustomerLine = true;

    Teller(CustomerQueue customers) {
        this.customers = customers;
    }

    // 让优先级队列可以自动的将工作量最小的teller安排去服务顾客
    @Override
    public int compareTo(Teller o) {
        return Integer.compare(customersServed, o.customersServed);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Customer customer = customers.take();
                // 模拟顾客用餐
                TimeUnit.MILLISECONDS.sleep(customer.getServiceTime());
                synchronized (this) {
                    // teller没服务过一个顾客，自己已经服务调用用户数进行递增，以便任务分配时，能优先服务顾客少的teller上
                    customersServed++;
                    while (!servingCustomerLine) {
                        wait();
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(this + " 任务中断");
        }
        System.out.println(this + " 任务结束");
    }

    // 当没有足够多的顾客资源时，teller会被安排去执行一些其他的事情
    public synchronized void doSomethingElse() {
        customersServed = 0;
        servingCustomerLine = false;
    }

    public synchronized void serverCustomerLine() {
        assert !servingCustomerLine : "已经服务过：" + this;
        servingCustomerLine = true;
        notifyAll();
    }

    @Override
    public String toString() {
        return "Teller{" +
                "id=" + id +
                '}';
    }

    public String shortString() {
        return "T" + id;
    }
}

class TellerManger implements Runnable {

    private ExecutorService exec;
    private CustomerQueue customers;
    // 用以存放teller的优先级队列 根据服务员的customersServed 判定优先级
    private PriorityQueue<Teller> workingTellers = new PriorityQueue<>();
    private Queue<Teller> tellersDoingOtherThings = new LinkedList<>();
    private int adjsutmentPeriod;

    TellerManger(ExecutorService exec, CustomerQueue customers, int adjsutmentPeriod) {
        this.exec = exec;
        this.customers = customers;
        this.adjsutmentPeriod = adjsutmentPeriod;
        Teller teller = new Teller(customers);
        exec.execute(teller);
        workingTellers.add(teller);
    }

    // 服务员数量业务判断
    private void adjustTellerNumber() {
        // 如果顾客数量比服务员数量的2倍还多
        if (customers.size() / workingTellers.size() > 2) {
            // 被安排做其它事情的队列中有人，这从队列中拉出队头的teller进行付过
            if (tellersDoingOtherThings.size() > 0) {
                // 检索并删除此队列的头。 此方法与poll不同之处在于，如果此队列为空，它将抛出异常。
                Teller teller = tellersDoingOtherThings.remove();
                // 通知所有挂起的 teller 开始执行任务——挂起的线程任务将继续执行，循环的从顾客队列中获取顾客提供服务
                teller.serverCustomerLine();
                // 加入工作对列
                workingTellers.offer(teller);
                return;
            }
            // 没有空闲的teller,就叫新人，并将新人加入工作对列
            Teller teller = new Teller(customers);
            exec.execute(teller);
            workingTellers.add(teller);
            return;
        }

        // 在有服务人员且顾客数量较少的情况下，抽掉一名 teller 去做一些其它事情
        if (workingTellers.size() > 1 && customers.size() / workingTellers.size() < 2) {
            reassignOneTeller();
        }
        // 如果没有顾客，那就全部去干其它事情
        if (customers.size() == 0) {
            while (workingTellers.size() > 1) {
                reassignOneTeller();
            }
        }
    }

    private void reassignOneTeller() {
        Teller teller = workingTellers.poll();
        teller.doSomethingElse();
        // offer 插入元素到队列
        tellersDoingOtherThings.offer(teller);
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                // 判断时间间隔
                TimeUnit.MILLISECONDS.sleep(adjsutmentPeriod);
                adjustTellerNumber();
                System.out.println(customers + " { ");
                for (Teller workingTeller : workingTellers) {
                    System.out.println(workingTeller.shortString() + " ");
                }
                System.out.println("}");
            }
        } catch (InterruptedException e) {
            System.out.println(this + " 任务中断");
        }
        System.out.println(this + " 任务结束");
    }

    @Override
    public String toString() {
        return "TellerManger ";
    }
}

