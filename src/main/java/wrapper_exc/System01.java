package wrapper_exc;

import java.util.Arrays;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class System01 {
    public static void main(String[] args) {
//        System.out.println("ok1");
//        System.exit(0);
//        System.out.println("ok2");


        int[] src={1,2,3};
        int[] dest= new int[3];
        //srcpos从原数组的哪个索引位置开始拷贝
        //dest目标数组
        //destpos，拷贝到目标数组的哪个索引
        //length拷贝多少个数据到目标数组
        System.arraycopy(src,0,dest,0,3);
        System.out.println(Arrays.toString(dest));
    }
}
