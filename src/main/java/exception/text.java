package exception;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class text {
    public static void main(String[] args) {

        int n1=0;
        int n2=10;
        //选中然后 crtl+alt+t
        try {
            int res =n2/n1;
        } catch (Exception e) {
            System.out.println(e.getMessage());//输出异常信息
        }
        System.out.println("continue");

    }
}
//error 无法解决的严重问题
//exception 分为运行时异常，和编译时异常