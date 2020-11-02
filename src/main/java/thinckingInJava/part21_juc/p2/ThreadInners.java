package thinckingInJava.part21_juc.p2;


import java.util.concurrent.TimeUnit;

/**
 * @author chen
 * @date 2020/4/6 10:31
 * @Description 在一个java文件中，可以有多个同级类，但public修饰的只能有一个，并且必须和文件名相同；若没有public，则可以和文件名不同
 */
public class ThreadInners {

    // 这里，如果是用Junit 进行测试，是看不到打印的。不同于主方法，被@Test 注解标注的方法在执行完成之后，内部开启的线程也会强制退出
//    @Test
//    public void test() throws InterruptedException {
//        new InnerThread1("陈线程");
//        new InnerThread2("陈线程2");
//        new InnerRunnable1("陈线程3");
//        new InnerRunnable2("陈线程4");
//        new ThreadMethod("陈线程5").runTask();
//    }


    public static void main(String[] args) {
        new InnerThread1("陈线程");
        new InnerThread2("陈线程2");
        new InnerRunnable1("陈线程3");
        new InnerRunnable2("陈线程4");
        new ThreadMethod("陈线程5").runTask();
//        System.exit(0);
    }



    static class InnerThread1 {
        private int countDown = 5;

        private Inner inner;

        private class Inner extends Thread {
            public Inner(String name) {
                super(name);
                start();
            }

            @Override
            public void run() {
                System.out.println("进入线程任务方法");
                try {
                    while (true) {
                        System.out.println(this);
                        if (--countDown == 0)
                            return;
                        sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String toString() {
                return getName() + ":" + countDown;
            }
        }

        public InnerThread1(String name) {
            inner = new Inner(name);
        }
    }

    static class InnerThread2 {
        private int countDown = 5;

        private Thread thread;

        public InnerThread2(String name) {
            // new 之后直接大括号加对象
            thread = new Thread(name) {
                public void run() {
                    try {
                        while (true) {
                            System.out.println(this);
                            if (--countDown == 0)
                                return;
                            sleep(10);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public String toString() {
                    return getName() + ":" + countDown;
                }
            };
            thread.start();
        }
    }

    static class InnerRunnable1 {
        private int countDown = 5;
        private Inner inner;

        private class Inner implements Runnable {
            Thread t;
            // new对象后面直接大括号，是为匿名类，通常用匿名类作为方法重写的一种手段
            public Inner(String name) {
                t = new Thread(this, name);
                t.start();
            }

            @Override
            public void run() {
                try {
                    while (true) {
                        System.out.println(this);
                        if (--countDown == 0)
                            return;
                        TimeUnit.SECONDS.sleep(10);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public String toString() {
                return t.getName() + ":" + countDown;
            }
        }

        public InnerRunnable1(String name) {
            this.inner = new Inner(name);
        }
    }

    static class InnerRunnable2 {
        private int countDown = 5;
        private Thread t;

        public InnerRunnable2(String name) {
            t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            System.out.println(this);
                            if (--countDown == 0)
                                return;
                            TimeUnit.SECONDS.sleep(10);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, name);
            t.start();
        }
    }

    static class ThreadMethod {
        private int countDown = 5;
        private Thread t;
        private String name;

        public ThreadMethod(String name) {
            this.name = name;
        }

        public void runTask() {
            if (t == null) {
                t = new Thread(name) {
                    @Override
                    public void run() {
                        try {
                            while (true) {
                                System.out.println(this);
                                if (--countDown == 0)
                                    return;
                                sleep(10);
                            }
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public String toString() {
                        return getName() + ":" + countDown;
                    }
                };
                t.start();
            }
        }
    }


}
