package hashmap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class mapp {
    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap();
        map.put("jack", 20);
        map.put("tom", 18);
        map.put("jone", 30); // 会覆盖

        //使用增强for遍历数组
        for(Map.Entry<String,Integer> entry :map.entrySet()){
            System.out.println(entry.getKey()+" "+entry.getValue());
        }

        for(Integer value : map.values()){
            System.out.println(value);
        }

        for(String key : map.keySet()){
            System.out.println(key+" "+map.get(key));
        }

        //迭代器遍历
        Iterator<Entry<String, Integer>> it = map.entrySet().iterator();

        while(it.hasNext()){
            Map.Entry<String,Integer> entry = it.next();
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
        Iterator it2 = map.values().iterator();
        while(it2.hasNext()){
            System.out.println(it2.next());
        }
    }
}
