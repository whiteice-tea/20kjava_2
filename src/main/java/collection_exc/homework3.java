package collection_exc;

import java.util.HashMap;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class homework3 {
    public static void main(String[] args) {
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        map.put("jack",650);
        map.put("tom",1200);
        map.put("smith",2900);
        System.out.println(map);
        map.put("jack",2600);
        System.out.println(map);
        for(HashMap.Entry<String,Integer> entry : map.entrySet()) {
            int value = entry.getValue();
            map.put(entry.getKey(),value+100);
        }
        System.out.println(map);
        for(String key : map.keySet()) {
            System.out.println(map.get(key));
        }

        for(Integer value : map.values()) {
            System.out.println(value);
        }
    }
}


