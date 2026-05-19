package cpu.test;

import java.util.Scanner;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class testo1 {
    public static void main(String[] args) {
        A a = new A();
        a.start();
        B b = new B(a);
        b.start();

    }
}

class A extends Thread {
    private boolean loop = true;

    @Override
    public void run() {
        while(loop) {
            System.out.println((int)(Math.random()*100+1));
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    public void setLoop(boolean loop) {
        this.loop = loop;
    }
}

class B extends Thread {
    private A a;
    private Scanner scanner = new Scanner(System.in);
    public B(A a) {//在构造器中，直接传入A类对象
        this.a = a;
    }

    @Override
    public void run() {

        while(true) {
            //接收到用户输入
            System.out.println("enter your choice(Q or q  stop)");
            char c = scanner.next().charAt(0);
            if(c == 'Q'||c=='q') {
                a.setLoop(false);
                System.out.println("exit");
                break;
            }
        }
    }
}