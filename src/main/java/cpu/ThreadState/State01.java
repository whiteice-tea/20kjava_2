package cpu.ThreadState;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class State01 {
    public static void main(String[] args) {
        T t = new T();
        System.out.println(t.getName()+" "+t.getState());
        t.start();
        while(Thread.State.TERMINATED != t.getState()){
            System.out.println(t.getName()+" "+t.getState());
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }

        System.out.println(t.getName()+" "+t.getState());
    }
}

class T extends Thread {
    @Override
    public void run() {
        while (true) {
            for (int i = 0; i < 10; i++) {
                System.out.println("hi"+ i);
                try{
                    Thread.sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }

            }
            break;
        }
    }
}