package treemap;

import java.util.Comparator;
import java.util.TreeMap;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class treemap_ {
    public static void main(String[] args) {


        TreeMap tm = new TreeMap(new Comparator(){
            @Override
            public int compare(Object o1,Object o2){
                return ((String) o1).compareTo((String) o2);
            }
        });
        tm.put("A", 1);
        tm.put("B", 2);
        tm.put("C", 3);
        tm.put("D", 4);
        tm.put("E", 5);
        tm.put("F", 6);

        System.out.println(tm);
    }
}
