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
        String poem = "E:\\aa\\bb\\cc";
        String[] split=poem.split("\\\\");
        //在对字符串分割时，如果有特殊字符，需要加入转义符\


        //compareTo 比较两个字符串的大小，如果前者大，则返回正数
        //后者大，则返回负数，如果相等，返回0

        //如果长度相同，并且每个字符也相同，就返回0
        //如果长度相同或者不同，但是在进行比较时，可以区分大小
        //就返回
        //if(a!=b){
        //return a-b;
        //}
        //如果前面的部分都相同，就返回str1.len - str2.len

        String a="jcck";
        String b="jack";
        System.out.println(a.compareTo(b));


        //占位符
        String name="liu";
        int age=18;
        double score=3.14;
        char gender='m';

        String formatstr="name %s age %s score %s gender %s";
        String info=String.format(formatstr,name,age,score,gender);
        System.out.println(info);
    }
}
