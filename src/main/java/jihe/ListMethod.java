package jihe;

import java.util.ArrayList;
import java.util.List;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class ListMethod {
    public static void main(String[] args) {
        List list = new ArrayList();
        List list1 = new ArrayList();
        list.add("Hello");
        list.add("World");
        list.add(1,"Java");//再index位置插入元素
        System.out.println(list);
        list1.add("ttt");
        list1.add("qqq");
        list1.addAll(1,list);
        System.out.println(list1);
        list1.remove(1);
        System.out.println(list1);

        list.set(1,"nihao");
        System.out.println(list);

        List returnlist =list.subList(1,3);
        System.out.println(returnlist);
    }
}
