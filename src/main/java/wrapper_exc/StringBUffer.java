package wrapper_exc;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class StringBUffer {
    public static void main(String[] args) {
        //StringBuffer的父类是AbstractStringBuilder
        //实现了Serializable，它的对象可以串化
        //父类中有属性 char[] value，不是final
        //该value数组存放字符串内容，引用存放在堆中的
        //他是个final，不能继承
        StringBuffer sb = new StringBuffer("Hello World");
    }
}
