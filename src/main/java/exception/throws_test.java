package exception;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class throws_test {
    public static void main(String[] args) {

    }
    //使用throws，抛出异常，让调用f1方法的调用者处理
    public void f1() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("d://aa.txt");
    }

    public static void f2(){
        //为什么不会报错，是应为f3抛出的时运行异常
        //而java中，并不要要求程序员显示处理
        f3();
    }

    public static void f3() throws ArithmeticException {
        int n = 1 / 0;
    }

    public static void f4() throws FileNotFoundException {
        //处理这个异常，这个异常是编译异常
        //要么try-catch-finally，或者继续throws这个编译异常
        f5();//抛出异常
    }

    public static void f5() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("d://aa.txt");
    }
}

class Father{
    public void method1() throws RuntimeException {

    }


}
//子类重写父类的方法时，对抛出异常的规定：子类的重写方法
//所抛出的异常类型要么和父类抛出的异常一致，要么为父类抛出的异常类型的子类型
class Son extends Father{
    @Override
    public void method1() throws NullPointerException {
        System.out.println("method2");
    }
}


//异常树
//Throwable
//├── Error
//│   ├── StackOverflowError
//│   └── OutOfMemoryError
//│
//└── Exception
//    ├── IOException
//    │   └── FileNotFoundException
//    ├── SQLException
//    ├── ClassNotFoundException
//    ├── InterruptedException
//    │
//    └── RuntimeException
//        ├── NullPointerException
//        ├── ArithmeticException
//        ├── NumberFormatException
//        ├── IndexOutOfBoundsException
//        ├── ClassCastException
//        └── IllegalArgumentException
//        | 类型      | 代表异常                                         | 编译器是否强制处理 | 常见处理方式                 |
//        | ------- | --------------------------------------------     | ---------       | ---------------------- |
//        | `Error` | `OutOfMemoryError`                               | 不谈强制处理      | 一般不主动处理            |
//        | 编译时异常   | `IOException`、`FileNotFoundException`        | 是               | `try-catch` 或 `throws` |
//        | 运行时异常   | `NullPointerException`、`ArithmeticException` | 否               | 可处理可不处理            |
