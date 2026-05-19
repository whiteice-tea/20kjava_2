package cpu.test;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class test02 {
    public static void main(String[] args) {
        user user1 = new user();
        new Thread(user1).start();
        new Thread(user1).start();

    }
}

class user implements Runnable {
    private static int money=10000;
    private boolean loop=true;
    Object object = new Object();
    public  void usemoney(){
        synchronized (object){
            if(money<=0){
                System.out.println("钱用完了");
                loop=false;
                return;
            }

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("窗口" + Thread.currentThread().getName() + "使用1000元" + ",剩余钱数" + (money=money-1000) + "元");
        }
    }
    @Override
    public void run() {
        while(loop){
            usemoney();
        }
    }
}
