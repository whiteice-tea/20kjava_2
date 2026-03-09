package howork2;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class TestFrock {
    public static void main(String[] args) {
        // 1. 分两次调用 getNextNum()
        System.out.println(Frock.getNextNum());
        System.out.println(Frock.getNextNum());

        // 2. 创建三个对象
        Frock frock1 = new Frock();
        Frock frock2 = new Frock();
        Frock frock3 = new Frock();

        // 3. 打印三个对象的序列号
        System.out.println(frock1.getSerialNumber());
        System.out.println(frock2.getSerialNumber());
        System.out.println(frock3.getSerialNumber());
    }
}
