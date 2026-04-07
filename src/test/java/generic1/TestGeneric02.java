package generic1;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class TestGeneric02 {


    @Test
    public void testList(){
        Map<String, User> map = new HashMap<>();
        DAO<User> dao = new DAO<>(map);

        dao.save("1", new User("Tom", 20, 1));
        dao.save("2", new User("Jack", 22, 2));

        List<User> list = dao.list();

        assertEquals(2, list.size());
    }

    @Test
    public void testSave(){
        Map<String, User> map = new HashMap<>();
        DAO<User> dao = new DAO<>(map);

        User user = new User("Tom", 20, 1);
        dao.save("1", user);

        assertEquals(user, dao.get("1"));
    }

    @Test
    public void testGet(){
        Map<String, User> map = new HashMap<>();
        DAO<User> dao = new DAO<>(map);

        User user = new User("Jack", 22, 2);
        dao.save("2", user);

        assertEquals(user, dao.get("2"));
    }

    @Test
    public void testUpdate(){
        Map<String, User> map = new HashMap<>();
        DAO<User> dao = new DAO<>(map);

        User user1 = new User("Tom", 20, 1);
        User user2 = new User("Mike", 25, 1);

        dao.save("1", user1);
        dao.update("1", user2);

        assertEquals(user2, dao.get("1"));
    }

    @Test
    public void testDelete(){
        Map<String, User> map = new HashMap<>();
        DAO<User> dao = new DAO<>(map);

        User user = new User("Tom", 20, 1);
        dao.save("1", user);
        dao.delete("1");

        assertNull(dao.get("1"));
    }
}

class DAO<T> {
    Map<String, T> map = new HashMap<>();

    public DAO(Map<String, T> map) {
        this.map = map;
    }

    public void save(String id,T entify){
        map.put(id, entify);
    }

    public T get(String id){
        return map.get(id);
    }

    public void update(String id,T entify){
        map.put(id, entify);
    }

    public List<T> list() {
        return new ArrayList<>(map.values());
    }

    public void delete(String id){
        map.remove(id);
    }

}

class User{
    private String name;
    private int age;
    private int id;

    public User(String name, int age, int id) {
        this.name = name;
        this.age = age;
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", id=" + id +
                '}';
    }
}