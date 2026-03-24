package hashsetexc;

import java.util.HashSet;
import java.util.Objects;
/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class hashset {
    public static void main(String[] args) {
        Employee employee1 = new Employee("jack",19);
        Employee employee2 = new Employee("jane",20);
        Employee employee3 = new Employee("john",21);
        Employee employee4 = new Employee("john",21);

        HashSet<Employee> set = new HashSet<Employee>();
        set.add(employee1);
        set.add(employee2);
        set.add(employee3);
        set.add(employee4);
        System.out.println(set);

    }
}

class Employee {
    String name;
    int age;

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

    ///每写一个hashset就要重写它的判断相同标准，不然无法判断
    @Override
    public boolean equals(Object o){
        if(this == o)return true;
        if(o==null||getClass()!=o.getClass())return false;
        Employee employee = (Employee)o;
        return age == employee.age && Objects.equals(name, employee.name);
    }
    /// 还要重写它的哈希值代码
    @Override
    public int hashCode(){
        return Objects.hash(name, age);
    }

    @Override
    public String toString() {
        return "hashsetexc.Employee{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }


}
