package jihe;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class exce01 {
    public static void main(String[] args) {

        Collection col = new ArrayList();
        Dog dog1 = new Dog("qqq",100);
        Dog dog2 = new Dog("ttt",999);
        Dog dog3 = new Dog("ggg",500);

        col.add(dog1);
        col.add(dog2);
        col.add(dog3);

        Iterator iter = col.iterator();
        while(iter.hasNext()){
            Dog d = (Dog)iter.next();
            System.out.println(d);
        }

        for(Object o : col){
            System.out.println(o);
        }
    }

}

class Dog{
    String name;
    int age;

    public Dog(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString(){
        return "Dog [name=" + name + ", age=" + age + "]";
    }
}