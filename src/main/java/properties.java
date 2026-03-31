import java.util.Properties;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class properties {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("john",100);
        prop.put("jane",200);
        prop.put("mary",300);
//        prop.put(null,400);
//        prop.put("lic",null);
        System.out.println(prop);
    }

}
