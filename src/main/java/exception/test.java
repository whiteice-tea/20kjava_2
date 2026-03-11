package exception;

import java.util.*;
/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class test {
    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int a;
//
//        while(true){
//            System.out.println("enter a number");
////            sc.hasNextInt()是检查是不是输入的是一个整数/只是检查
//            if(sc.hasNextInt()){
//                a=sc.nextInt();
//                break;
//            }else{
//                System.out.println("enter a number");
//                sc.next();
//            }
//        }
//        System.out.println("你输入的整数是：" + a);
//        sc.close();

        Scanner sc = new Scanner(System.in);
        int num;
        while (true) {
            System.out.println("Enter a number: ");
            String str=sc.nextLine();

            try{
                num = Integer.parseInt(str);
                break;
            }catch(NumberFormatException e){
                System.out.println("Invalid number");

            }
        }
        System.out.println(num);
        sc.close();



    }
}
