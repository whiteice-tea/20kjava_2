package cpu.threaduse;



/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Thread02 {
    public static void main(String[] args) {
//        Dog dog = new Dog();
//        //dog.start();,这里不能调用start
//        //创建Thread对象，把dog对象（实现Runnable），放入Thread
//        Thread thread = new Thread(dog);
//        thread.start();

        Dog dog = new Dog();
        Proxy proxy = new Proxy(dog);
        proxy.start();

    }
}
//模拟量一个极简的Thread
class Proxy implements Runnable {//把Proxy类当作ThreadProxy

    private Runnable target=null;

    @Override
    public void run() {
        if(target!=null){
            target.run();
        }
    }

    public Proxy(Runnable target) {
        this.target = target;
    }

    public void start(){
        start0();
    }

    public void start0(){
        run();
    }
}




class Dog implements Runnable {
    int count=0;
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            System.out.println("Dog run"+(++count)+Thread.currentThread().getName());
            try{
                Thread.sleep(1000);
            }catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }
}