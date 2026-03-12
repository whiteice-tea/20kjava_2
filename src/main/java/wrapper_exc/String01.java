package wrapper_exc;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class String01 {
    public static void main(String[] args) {
        String str = "Hello World";
        str="ttf" + "sd" + "sd" ;
        final char[] chars = {'a','b','c'};
        chars[0]='t';
        char[] chars2 = {'o','p','q'};
        //chars=chars2;不可以更改chars地址
        //final 修饰数组名，锁的是指向，不是锁数组内容。

    }
}
