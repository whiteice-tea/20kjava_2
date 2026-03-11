package exception;

import java.util.*;
/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class EcmDef {
    public static void main(String[] args) {

        //这个是命令行输入
        try {
            if(args.length!=2){
                throw new ArrayIndexOutOfBoundsException("参数个数不对");
            }
            int a = Integer.parseInt(args[0]);
            int b = Integer.parseInt(args[1]);
            numbers c1 = new numbers();
            int t = c1.cal(a, b);
            System.out.println(t);
        }catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("请输入两个整数参数");
        } catch (NumberFormatException e) {
            System.out.println("请输入整数");
        }catch (ArithmeticException e) {
            System.out.println("除数不能为0");
        }

    }
}

class numbers{
    public int cal(int num1,int num2){
        return num1/num2;
    }
}
