package cpu.threaduse;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Thread03 {
    public static void main(String[] args) {
        T1 t1 = new T1();
        T2 t2 = new T2();
        Thread thread = new Thread(t1);
        Thread thread2 = new Thread(t2);
        thread.start();
        thread2.start();
    }
}

class T1 implements Runnable {
    int count = 0;
    @Override
    public void run() {
        while (true) {
            System.out.println("hello,world"+(++count));
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            if (count == 10) {
                break;
            }
        }
    }
}

class T2 implements Runnable {
    int count = 0;
    @Override
    public void run() {
        while (true) {
            System.out.println("hi"+(++count));
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            if (count == 5) {
                break;
            }
        }
    }
}