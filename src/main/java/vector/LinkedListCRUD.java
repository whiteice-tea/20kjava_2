package vector;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Set;


/**
 * @author whiteicetea
 * @version 1.0.0
 */

public class LinkedListCRUD {
    public static void main(String[] args) {
//        LinkedList linkedList = new LinkedList();
//        linkedList.addFirst(1);
        Set set = new HashSet();
        set.add(1);
        set.add(2);
        set.add(3);
        set.add(null);
        for (Object o : set) {
            System.out.println(o);
        }

        Iterator iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }
}
