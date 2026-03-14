package wrapper_exc;

import java.util.Arrays;
import java.util.List;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class ARRAY02 {
    public static void main(String[] args) {
        Integer[] arr = {1,2,56,76,53465,5664544};
        int index = Arrays.binarySearch(arr, 76);
        System.out.println(index);

        Integer[] num = new Integer[]{9,8,4,3};
        Arrays.fill(num,99);
        System.out.println(Arrays.toString(num));

        Integer[] arr1 = {1,2,56,76,53465,56645442};
        boolean equals = Arrays.equals(arr1, arr);
        System.out.println(equals);
        //alist方法，将数据转换成一个List集合
        List asList = Arrays.asList(arr);
        System.out.println(asList);
    }
}
