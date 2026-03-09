package howork2;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public abstract class Animal {
    public abstract void shout();

}

class Dog extends Animal {
    @Override
    public void shout(){
        System.out.println("Dog");
    }
}

class Cat extends Animal {
    @Override
    public void shout(){
        System.out.println("Cat");
    }
}