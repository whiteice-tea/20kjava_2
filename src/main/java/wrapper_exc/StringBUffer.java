package wrapper_exc;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class StringBUffer {
    public static void main(String[] args) {
        //StringBuffer的父类是AbstractStringBuilder
        //实现了Serializable，它的对象可以串行化
        //父类中有属性 char[] value，不是final
        //该value数组存放字符串内容，引用存放在堆中的
        //他是个final，不能继承
        //不用每次都更换地址（不是每次创建新的对象）
        StringBuffer sb = new StringBuffer("Hello World");

        //创建一个大小为16的char[]，用于存放字符内容
        StringBuffer sb1 = new StringBuffer();

        //通过构造器指定char[]大小
        StringBuffer sb2 = new StringBuffer(100);

        //通过给一个String创建StringBuffer
        //char[]大小就是str.length()+16
         StringBuffer sb3 = new StringBuffer("Hello World");


         //String 和StringBuffer相互转换

        String str="hello world";
        //1使用构造器
        //返回的才是StringBuffer，对str，没有影响
        StringBuffer sb4 = new StringBuffer(str);

        //2使用append
        StringBuffer sb5 = new StringBuffer();
        sb5=sb5.append(str);

        //StringBuffer->String
        StringBuffer sb6 = new StringBuffer("Hello World");
        //1使用StringBuffer提供的toString方法
        String s = sb6.toString();

        //使用构造器
        String s1 = new String (sb6);

        StringBuffer st = new StringBuffer("this_is_test_file");
        //增加
        st.append('s');//''单引号只能写一个字符
        st.append(1000).append(true);
        System.out.println(st.toString());

        //删除
        st.delete(0,3);//这个是左闭右开区间
        System.out.println(st.toString());

        //改
         st.replace(0,1,"L");
         System.out.println(st.toString());
         //找
        //返回第一次出现的索引，找不到返回-1
        int indexOf = st.indexOf("L_is");
        System.out.println(indexOf);

        //插入
        st.insert(4,"insert");
        System.out.println(st.toString());

        //长度
        System.out.println(st.length());

    }
}
