import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class mapexercise {
    public static void main(String[] args) {
        Map<String,employee> employees = new HashMap<String,employee>();
        employee john = new employee("john", "0001", 18000);
        employee at = new employee("at", "0002", 18000);
        employee bt = new employee("bt", "0003", 18000);

        employees.put(john.id, john);
        employees.put(at.id, at);
        employees.put(bt.id, bt);

        for(Map.Entry<String,employee> entry : employees.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue());
        }

        Iterator<Map.Entry<String,employee>> iterator = employees.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry<String,employee> entry = iterator.next();
        }

    }
}

class employee {
    String name;
    String id;
    int salary;

    public employee(String name, String id, int salary) {
        this.name = name;
        this.id = id;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public int hashCode(){
        return id.hashCode();
    }

    @Override
    public boolean equals(Object o){
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;
        employee employee = (employee) o;
        return id.equals(employee.id);
    }

    @Override
    public String toString() {
        return "employee{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", salary=" + salary +
                '}';
    }
}