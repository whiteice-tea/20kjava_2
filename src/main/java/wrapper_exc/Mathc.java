package wrapper_exc;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Mathc {
    public static void main(String[] args) {
        //都是静态方法
        //绝对值
        int abs = Math.abs(-10);
        System.out.println(abs);
        //求幂
        double pow = Math.pow(2, 4);
        System.out.println(pow);
        //向上取整
        double ceil = Math.ceil(10.0002);
        System.out.println(ceil);
        //向下取整
        double floor = Math.floor(10.0002);
        System.out.println(floor);
        //四舍五入
        long round = Math.round(10.0002);
        System.out.println(round);
        //求开方
        double Sqrt = Math.sqrt(9);
        System.out.println(Sqrt);

        //random返回的是0<=x<1的一个随即小数
        //获取a到b的一个随机成熟
        //int num = (int)(Math,random()*(b-a+1)+a);
    }
}
