package java_concurrency.chapter10_liveness;

import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2021/2/28 20:46
 * @Description 动态的锁顺序死锁
 *  这种情况下，通常可以通过对象的某种排序来指定加锁顺序防止循环加速的问题
 */
public class DynamicOrderDeadlock {

    /**
     * 模拟转账操作
     * 这里看似没有顺序性问题，但由于锁的是对象，而对象的顺序取决于外部的输入
     * 所以，如果两个线程同时调用转账操作相互装置，你猜会发生什么？
     */
    public static void transfer(Account fromAccount, Account toAccount, int amount) {
        synchronized (fromAccount) {
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (toAccount) {
                fromAccount.debit(amount);
                toAccount.credit(amount);
            }
        }
    }

    public static void main(String[] args) {
        Account account1 = new Account(100);
        Account account2 = new Account(100);
        new Thread(() -> transfer(account1, account2, 50)).start();
        new Thread(() -> transfer(account2, account1, 60)).start();
    }

}


class Account {
    private int balance; // 账户余额

    public Account(int balance) {
        this.balance = balance;
    }

    public void debit(int amount) {

        balance = balance - amount;
    }

    public void credit(int amount) {
        balance = balance + amount;
    }
}
