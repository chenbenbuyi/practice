package thinckingInJava.part21_juc.p8.p2;

import thinckingInJava.part19_enum.p7.Course;
import thinckingInJava.part19_enum.p7.Food;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

/**
 * @author chen
 * @date 2020/8/16 9:39
 * @Description 饭店仿真
 */

public class RestaurantWithOrders {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newCachedThreadPool();
        // 5 个服务员，2个厨师
        Restaurant restaurant = new Restaurant(5, 2, exec);
        exec.execute(restaurant);
        System.out.println(" 请按回车键退出 ");
        TimeUnit.SECONDS.sleep(5);
        // 如果线程池关闭，会停止接收外部提交的任务
        exec.shutdown();
    }
}


class Order {
    private static int counter = 0;
    private final int id = counter++;
    private final Customer customer;
    private final WaitPerson waitPerson;
    private final Food food;

    public Order(Customer customer, WaitPerson waitPerson, Food food) {
        this.customer = customer;
        this.waitPerson = waitPerson;
        this.food = food;
    }

    public Food item() {
        return food;
    }

    public Customer getCustomer() {
        return customer;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }

    @Override
    public String toString() {
        return "Order：" +
                "订单编号：" + id +
                " 餐食：" + food +
                " 顾客：" + customer +
                " 被 " + waitPerson +
                " 服务";
    }
}

class Plate {
    private final Order order;
    private final Food food;

    public Plate(Order order, Food food) {
        this.order = order;
        this.food = food;
    }

    public Order getOrder() {
        return order;
    }

    public Food getFood() {
        return food;
    }

    @Override
    public String toString() {
        return food.toString();
    }
}

class Customer implements Runnable {
    private static int counter;
    private final int id = counter++;
    private final WaitPerson waitPerson;
    private SynchronousQueue<Plate> plateSetting = new SynchronousQueue<>();

    public Customer(WaitPerson waitPerson) {
        this.waitPerson = waitPerson;
    }

    public void deliver(Plate p) throws InterruptedException {
        plateSetting.put(p);
    }

    @Override
    public void run() {
        for (Course course : Course.values()) {
            Food food = course.randomSelection();
            try {
                waitPerson.placeOrder(this, food);
                System.out.println(this + "正在享用 " + plateSetting.take());
            } catch (InterruptedException e) {
                System.out.println(this + " 等待被中断");
                break;
            }
        }
        System.out.println(this + " 用餐完毕，准备离开。。。");
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                '}';
    }
}


class WaitPerson implements Runnable {

    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    BlockingQueue<Plate> filledOrders = new LinkedBlockingQueue<>();

    public WaitPerson(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    // 客户下订单方法
    public void placeOrder(Customer cust, Food food) {
        try {
            restaurant.orders.put(new Order(cust, this, food));
        } catch (InterruptedException e) {
            System.out.println("WaitPerson placeOrder 发生中断");
        }
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Plate plate = filledOrders.take();
                System.out.println(this + "received" + plate + " delivering to " + plate.getOrder().getCustomer());
                plate.getOrder().getCustomer().deliver(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " 任务中断");
        }
    }

    @Override
    public String toString() {
        return "WaitPerson{" +
                "id=" + id +
                '}';
    }
}

class Chef implements Runnable {
    private static int counter = 0;
    private final int id = counter++;
    private final Restaurant restaurant;
    private static Random rand = new Random(47);

    public Chef(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                Order order = restaurant.orders.take();
                Food food = order.item();
                TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
                Plate plate = new Plate(order, food);
                order.getWaitPerson().filledOrders.put(plate);
            }
        } catch (InterruptedException e) {
            System.out.println(this + " 任务中断");
        }

        System.out.println(this + " off duty");
    }

    @Override
    public String toString() {
        return "Chef{" +
                "id=" + id +
                '}';
    }
}

// Restaurant 餐馆
class Restaurant implements Runnable {
    private List<WaitPerson> waitPersons = new ArrayList<>();
    private List<Chef> chefs = new ArrayList<>();
    private ExecutorService exec;
    private static Random rand = new Random(47);
    /**LinkedBlockingQueue 只能从head取元素，从tail添加元素
     * LinkedBlockingQueue是读写分离的，读写操作可以并行执行。LinkedBlockingQueue采用可重入锁(ReentrantLock)来保证在并发情况下的线程安全。
     * 这里创建了一个 表示订单基于单链表的阻塞队列
     */
    BlockingQueue<Order> orders = new LinkedBlockingQueue<>();

    public Restaurant(int nWaitPersons, int nChefs, ExecutorService exec) {
        for (int i = 0; i < nWaitPersons; i++) {
            WaitPerson waitPerson = new WaitPerson(this);
            waitPersons.add(waitPerson);
        }
        for (int i = 0; i < nChefs; i++) {
            Chef chef = new Chef(this);
            chefs.add(chef);
            exec.execute(chef);
        }
        this.exec = exec;
    }

    @Override
    public void run() {
        try {
            while (!Thread.interrupted()) {
                WaitPerson waitPerson = waitPersons.get(rand.nextInt(waitPersons.size()));
                Customer customer = new Customer(waitPerson);
                exec.execute(customer);
                TimeUnit.MILLISECONDS.sleep(100);
            }
        } catch (InterruptedException e) {
            System.out.println("Restaurant 任务中断");
        }

        System.out.println("Restaurant 任务完成");
    }
}
