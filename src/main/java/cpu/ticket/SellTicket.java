package cpu.ticket;

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

        System.out.println("===使用实现接口方式来售票===");
        SellTicket02 sellTicket02 = new SellTicket02();
        new Thread(sellTicket02).start();//第一个窗口
        new Thread(sellTicket02).start();//第二个窗口
        new Thread(sellTicket02).start();//第三个窗口
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