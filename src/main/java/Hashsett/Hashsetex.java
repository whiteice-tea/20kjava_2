package Hashsett;

import java.util.HashSet;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Hashsetex {
    public static void main(String[] args) {
        Employee jack = new Employee("jack", 19);
        Employee john = new Employee("john", 21);
        Employee jane = new Employee("jane", 22);

        HashSet hashSet = new HashSet();
        hashSet.add(jack);
        hashSet.add(john);
        hashSet.add(jane);

        System.out.println(hashSet);
    }
}

class Employee {
    private String name;
    private int age;

    public Employee(String name, int age) {
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
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

}
