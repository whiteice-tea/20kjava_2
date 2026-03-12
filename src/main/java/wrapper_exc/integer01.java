package wrapper_exc;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class integer01 {
    public static void main(String[] args) {
        //演示int 和 integer的装箱和拆箱
        //jdk5以前
        //手动

        int n1 =1000;
//        Integer integer = new Integer(n1);9以后就移除了
        Integer integer1 = Integer.valueOf(n1);
        //手动拆箱
        int i = integer1.intValue();

        //jdk5以后
        //自动装箱
        int n2 =90000;
        Integer integer2 = n2;//底层使用的还是Integer integer2 = Integer.valueOf(n2);
        //自动拆箱
        int n3 = integer2;//底层integer2.intValue();



        Integer q =100;
        //1
        String str1= q+"";
        //2
        String str2 =q.toString();
        //3
        String str3 = String.valueOf(q);

        String str4 = "12345";
        Integer integer3 = Integer.valueOf(str4);
//        Integer i3 = new Integer(str4);9以后就移除了

    }
}
