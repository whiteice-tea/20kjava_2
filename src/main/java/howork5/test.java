package howork5;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
class A{
    private String name="whiteicetea";

    public void m1(){
        class B{
            private String name="blackicetea";
            public void show(){
                System.out.println(name);
                System.out.println(A.this.name);
            }
        }

        B b = new B();
        b.show();

    }
}


public class test {
    public static void main(String[] args) {
        new A().m1();//只用一次
        A a = new A();//可以多次使用
        a.m1();

    }
}
