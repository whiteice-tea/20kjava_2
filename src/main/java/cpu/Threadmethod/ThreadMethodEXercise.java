package cpu.Threadmethod;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class ThreadMethodEXercise {
    public static void main(String[] args) {
        Thread thread = new Thread(new sayhello());

        for (int i = 1; i <= 10; i++) {
            System.out.println("hi"+i);
            if(i==5){
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }

    }
}

class sayhello implements Runnable {
    int counter = 0;
    @Override
    public void run() {
        while(true){
            System.out.println("hello"+(++counter));
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            if(counter==10){
                break;
            }
        }
    }
}