package cpu.SYN;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class DeadLock {
    public static void main(String[] args) {
        //模拟死锁
        DeadLockThread A = new DeadLockThread(true);
        DeadLockThread B = new DeadLockThread(false);
        A.setName("A-Thread");
        B.setName("B-Thread");
        A.start();
        B.start();

    }
}

class DeadLockThread extends Thread {
    static Object lock1 = new Object();//保证多线程，共享一个对象，这里使用static
    static Object lock2 = new Object();
    boolean flag;

    public DeadLockThread(boolean flag) {//构造器
        this.flag =flag;
    }
    //如果flag维T，线程A就会先得到o1对象锁，然后去尝试获取o2对象锁
    //如果线程A得不到o2对象锁，就会Blocked
    //如果flag为F，线程B就会得到o2对象锁，然后去尝试获取o1对象锁
    //如果线程B得不到o1对象锁，就会Blocked
    public void run() {
        if (flag) {
            synchronized (lock1) {//对象互斥锁，下面就是同步代码
                System.out.println(Thread.currentThread().getName() + "1");
                synchronized (lock2) {//这里获得
                    System.out.println(Thread.currentThread().getName() + "2");
                }
            }
        }else{
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() + "3");
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "4");
                }
            }
        }
    }
}