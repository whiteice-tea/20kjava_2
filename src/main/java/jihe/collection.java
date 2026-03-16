package jihe;

/**
 * @author whiteicetea
 * @version 1.0.0
 */

import java.util.List;
import java.util.ArrayList;
/**
==================== Java 集合框架 ====================

一、单列集合 Collection
Collection
|-- List
|   |-- ArrayList
|   |-- LinkedList
|   `-- Vector
`-- Set
    |-- HashSet
    `-- TreeSet


二、双列集合 Map
Map
|-- HashMap
|-- TreeMap
|-- Hashtable
`-- Properties

======================================================
*/


public class collection {
    @SuppressWarnings({"all"})
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("a");
        list.add(10);
        list.add(true);
        System.out.println(list);
        list.remove(0);//删除第一个元素
        list.remove("a");
        System.out.println(list);
        System.out.println(list.size());
        System.out.println(list.contains("jack"));
        System.out.println(list.isEmpty());
        list.clear();
        System.out.println(list);
        ArrayList list2 = new ArrayList();
        list2.add("b");
        list2.add(101);
        list2.add(false);
        System.out.println(list2);
        list.addAll(list2);
        System.out.println(list);
        System.out.println(list.containsAll(list2));
        list.removeAll(list2);
        System.out.println(list2);
        //iterator成为迭代器，主要有意遍历collection集合中的元素
        //所有实现了collection接口的集合类都有一个iterator方法，所以反悔哦一个实现了iterator接口的对象，即可以返回一个迭代器
        //iterator仅用于集合遍历，iterator本身不存放变量

    }
}
