package generic;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class gene04 {
    public static void main(String[] args) {

        Pig<A> aPig = new Pig<A>(new A());
    }
}

class A {

}

class B extends A {

}

class Pig<E>{
    E e;

    public Pig(E e) {
        this.e = e;
    }
}