package collection_exc;

import java.util.ArrayList;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class homework2 {
    public static void main(String[] args) {
        Car car1=new Car(" 宝马","400000.0");
        Car car2=new Car("benzi","600000.0");
        ArrayList<Car> Cars = new ArrayList<Car>();
        Cars.add(car1);
        Cars.add(car2);
        System.out.println(Cars);
        Cars.remove(car1);
        System.out.println(Cars);
        System.out.println(Cars.contains(car2));
        System.out.println(Cars.contains(car1));
        System.out.println(Cars.isEmpty());


    }
}
class Car{
    String name;
    String price;

    public Car(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString(){
        return "Car [name=" + name + ", price=" + price + "]";
    }
}
