package generic;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;
import java.util.Objects;
/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class gene03 {
    public static void main(String[] args) {
        Set<Student> set = new HashSet<Student>();
        set.add(new Student("john",18));
        set.add(new Student("jane",17));
        set.add(new Student("jane",17));
        System.out.println(set);

        HashMap<String,Student> map = new HashMap<String,Student>();
        map.put("john",new Student("john",18));
        map.put("jane",new Student("jane",17));
        for(String s:map.keySet()){
            System.out.println(s);
        }
    }
}


class Student {
    String name;
    int age;

    public Student(String name, int age) {
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
    public int hashCode(){
        return Objects.hash(name,age);
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj)return true;
        if(obj==null||getClass()!=obj.getClass())return false;
        Student student = (Student) obj;
        return name.equals(student.name) && age == student.age;
    }


    @Override
    public String toString(){
        return name + " " + age;
    }
}