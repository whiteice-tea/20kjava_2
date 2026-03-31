import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Collection;

/**
 * @author whiteicetea
 * @version 1.0.0
 */

public class collections {
    public static void main(String[] args) {
        List list = new ArrayList();
        list.add("tom");
        list.add("john");
        list.add("norman");
        list.add("jerry");
        list.add("smith");
        Collections.reverse(list);//反转list
        System.out.println(list);
        Collections.shuffle(list);
        System.out.println(list);
        Collections.sort(list);
        System.out.println(list);

    }
}
