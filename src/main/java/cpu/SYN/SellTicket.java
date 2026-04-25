package cpu.SYN;

/**
 * @author whiteicetea
 * @version 1.0.0
 * 使用多线程，模拟三个窗口同时售票
 */
public class SellTicket {
    public static void main(String[] args) {
//        SellTicket01 sellTicket01 = new SellTicket01();
//        SellTicket01 sellTicket02 = new SellTicket01();
//        SellTicket01 sellTicket03 = new SellTicket01();
//
//        sellTicket01.start();
//        sellTicket02.start();
//        sellTicket03.start();

//        System.out.println("===使用实现接口方式来售票===");
//        SellTicket02 sellTicket02 = new SellTicket02();
//        new Thread(sellTicket02).start();//第一个窗口
//        new Thread(sellTicket02).start();//第二个窗口
//        new Thread(sellTicket02).start();//第三个窗口
        SellTicket03 sellTicket03 = new SellTicket03();
        new Thread(sellTicket03).start();
        new Thread(sellTicket03).start();
        new Thread(sellTicket03).start();
    }
}


class SellTicket01 extends Thread{
    private static int Num_Ticket=100;
    @Override
    public void run() {
        while(true){
            if(Num_Ticket<=0){
                System.out.println("售票结束...");
                break;
            }

            //休眠50ms

            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("窗口"+Thread.currentThread().getName()+"售出一张票"+",剩余票数"+(--Num_Ticket)+"张");
        }
    }
}

//使用Thread方法
class SellTicket02 extends Thread{
    private static int Num_Ticket=100;
    @Override
    public void run() {
        while(true){
            if(Num_Ticket<=0){
                System.out.println("售票结束...");
                break;
            }

            //休眠50ms

            try{
                Thread.sleep(50);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

            System.out.println("窗口"+Thread.currentThread().getName()+"售出一张票"+",剩余票数"+(--Num_Ticket)+"张");
        }
    }
}

//实现接口方式，使用synchronized实现线程同步

// public synchronized void sellTicket()是一个同步方法
//这是锁再this对象
//也可以再代码块上写synchronize，同步代码块，互斥锁还是在this对象
class SellTicket03 implements Runnable{
    private static int Num_Ticket=100;
    private boolean loop = true;
    Object object = new Object();

    public /*synchronized*/ void sellTicket(){

        synchronized (/*this*/ object ) {
            if (Num_Ticket <= 0) {
                System.out.println("售票结束...");
                loop = false;
                return;
            }

            //休眠50ms

            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("窗口" + Thread.currentThread().getName() + "售出一张票" + ",剩余票数" + (--Num_Ticket) + "张");
        }
    }
    @Override
    public void run() {//同步方法，再同一时刻，只能有一个线程来执行这个方法
        while(loop){
            sellTicket();
        }

    }
}