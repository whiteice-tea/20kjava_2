package hashsetexc;

import java.util.LinkedHashSet;
import java.util.Objects;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class linkhash {
    public static void main(String[] args) {
        LinkedHashSet<Car> set = new LinkedHashSet<Car>();
        Car car1 = new Car("A",10000);
        Car car2 = new Car("B",20000);
        Car car3 = new Car("C",30000);
        Car car4 = new Car("C",30000);
        set.add(car1);
        set.add(car2);
        set.add(car3);
        set.add(car4);
        System.out.println(set);
    }
}

class Car{
    private String name;
    private double price;

    public Car(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public int hashCode(){
        return Objects.hash(name,price);
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        Car car = (Car) o;
////        浮点数可能不精确：
//        return price == car.price;
        return Double.compare(price, car.price) == 0 && Objects.equals(name, car.name);
    }

    @Override
    public String toString() {
        return "Car{" +
                "name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}