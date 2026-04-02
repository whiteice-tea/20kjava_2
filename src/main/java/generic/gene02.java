package generic;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class gene02 {
    public static void main(String[] args) {

    }
}

class Person<E> {
    E s;//定义Person对象的时候指定

    public Person(E s) {
        this.s = s;
    }

    public E f(){
        return s;
    }
}