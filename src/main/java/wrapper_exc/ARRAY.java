package wrapper_exc;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class ARRAY {
    public static void main(String[] args) {
        Integer[] arr = {1, 2, 3, 4, 5,67,53,4,234,4235};

        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));

        Arrays.sort(arr,new Comparator() {
           @Override
           public int compare(Object o1,Object o2){
               Integer i1 = (Integer) o1;
               Integer i2 = (Integer) o2;
               return i2-i1;
           }
        });

        bul(arr,new Comparator() {
            @Override
            public int compare(Object o1,Object o2){
                Integer i1 = (Integer) o1;
                Integer i2 = (Integer) o2;
                return i2-i1;
            }
        });
        System.out.println(Arrays.toString(arr));

    }

    public static void bul(Integer[] arr,Comparator c){
        int temp = 0;
        for (int i = 0; i < arr.length-1; i++) {
            for (int j = 0; j < arr.length-1-i; j++) {
                if(c.compare(arr[i],arr[j+1])>0){
                    temp = arr[i];
                    arr[i] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }
    }
}
