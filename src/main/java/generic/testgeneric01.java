package generic;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class testgeneric01 {
    public static void main(String[] args) {
        MyDate birthDate1 = new MyDate(1990, 5, 15);

        Employee employee1 = new Employee("John Doe", 50000.0, birthDate1);

        MyDate birthDate2 = new MyDate(2000, 3, 31);

        Employee employee2 = new Employee("whiteicetea", 30000.0, birthDate2);

        MyDate birthDate3 = new MyDate(2000, 3, 30);

        Employee employee3 = new Employee("whiteicetea", 80000.0, birthDate3);

        ArrayList<Employee> employees = new ArrayList<Employee>();

        employees.add(employee1);
        employees.add(employee2);
        employees.add(employee3);

        employees.sort(new Comparator<Employee>() {
            @Override
            public int compare(Employee emp1,Employee emp2){
                if(!(emp1 instanceof Employee&&emp2 instanceof Employee)){
                    System.out.println("Employee format is not an Employee");
                    return 0;
                }
                int i=emp1.getName().compareToIgnoreCase(emp2.getName());
                if(i!=0){
                    return i;
                }

                return emp1.getBirthday().compareTo(emp2.getBirthday());
            }


        });

        System.out.println(employees);
    }
}


class Employee{
    private String name;
    private Double salary;
    public MyDate birthday;

    public Employee(String name, Double salary, MyDate birthday) {
        this.name = name;
        this.salary = salary;
        this.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public MyDate getBirthday() {
        return birthday;
    }

    public void setBirthday(MyDate birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "name='" + name + '\'' +
                ", salary=" + salary +
                ", birthday=" + birthday +
                '}'+"\n";
    }
}


class MyDate implements Comparable<MyDate>{
    private int year;
    private int month;
    private int day;

    public MyDate(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "MyDate{" +
                "year=" + year +
                ", month=" + month +
                ", day=" + day +
                '}';
    }

    @Override
    public int compareTo(MyDate o){
        int yearResult=this.getYear()-o.getYear();
        if(yearResult!=0){
            return yearResult;
        }

        int mouthResult=this.getMonth()-o.getMonth();
        if(mouthResult!=0){
            return mouthResult;
        }

        return this.getDay()-o.getDay();
    }
}