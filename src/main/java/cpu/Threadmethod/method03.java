package cpu.Threadmethod;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class method03 {
    public static void main(String[] args) {
        MyDeamonThread myDeamonThread = new MyDeamonThread();
        myDeamonThread.setDaemon(true);
        myDeamonThread.start();

        for(int i=1;i<=10;i++){
            System.out.println("mimimimi");
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}

class MyDeamonThread extends Thread {
    public void run(){
        for(;;){
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            System.out.println("hahahahahaha");
        }
    }
}