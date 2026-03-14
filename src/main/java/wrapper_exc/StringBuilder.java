package wrapper_exc;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class StringBuilder {
    public static void main(String[] args) {
        //1.StringBuilder 继承了，AbstractStringBuilder类
        //2.实现了Serializable，说明可以串行化（该对象可以网络传输，可以保存到文件）
        //是final类，不能被继承
        //它的对象字符序列，仍然是存放在其父类AbstractStringBuilder的char[] value；
        //因此，字符序列是在堆中
        //它没有做互斥的处理，即没有synchronized关键字

        StringBuilder StringBuilder = new StringBuilder();

    }
}
