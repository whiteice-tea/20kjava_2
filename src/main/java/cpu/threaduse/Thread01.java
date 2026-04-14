package cpu.threaduse;

/**
 * @author whiteicetea
 * 演示通过继承Thread类创建线程
 * @version 1.0.0
 */
public class Thread01 {
    public static void main(String[] args) throws InterruptedException {
        Cat cat = new Cat();

        /*
        （1）
          public void start() {
                synchronized (this) {
                    // zero status corresponds to state "NEW".
                    if (holder.threadStatus != 0)
                        throw new IllegalThreadStateException();
                    start0();
                }
            }
            （2）
            //start0()是本地方法，是JVM调用，底层是c/c++实现
            //真正实现多线程的效果，是start0()，而不是run
            private native void start0();
            （3）

         */
        cat.start();
        //cat.run();//run方法就是一个普通的方法，没有真正启动一个线程，就会把run方法执行完毕，才向下执行
        //当main线程启动一个子线程 Thread-0，主线程不会继续阻塞，会继续执行
        System.out.println("主线程继续执行+"+ Thread.currentThread().getName());//main
        for (int i = 0; i < 50; i++) {
            System.out.println("主线程 i="+i);
            Thread.sleep(1000);
        }
    }
}
//1. 当一个类继承了Thread类，该类就可以当作线程使用
//2. 我们会重写run方法，写上自己的业务代码
//3. run Thread 类实现了 Runnable 接口的run 方法
class Cat extends Thread {
    int time = 0;

    @Override
    public void run() {//重写run，写上自己的业务逻辑
        while (true) {
            System.out.println("miaomiao,i am cat "+(++time)+"线程名= "+Thread.currentThread().getName());
            //让线程休眠1秒  ctrl+alt+t
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}