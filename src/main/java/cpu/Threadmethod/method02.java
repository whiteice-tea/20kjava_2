package cpu.Threadmethod;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class method02 {
    public static void main(String[] args) {
        T t1 = new T();
        t1.start();

        for (int i = 0; i <= 20; i++) {
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("主线程吃了--- "+i);
            if(i==5){
                try {
                    t1.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class T extends Thread {
    @Override
    public void run() {
        for (int i = 0; i <= 20; i++) {
            try{
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("子线程 吃了  "+i);
        }
    }
}
