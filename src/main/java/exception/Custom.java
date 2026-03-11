package exception;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Custom {
    public static void main(String[] args) {
        int age =80;
        if(!(age>=18&&age<=120)){
            throw new Ageexception("80-120");
        }
        System.out.println(age+" years old");
    }
}

//自定义异常
//一般情况下，我们自定义异常是继承RuntimeException
//即把自定义异常做成 运行时异常，好处时，我们可以使用默认的处理机制
//比较方便
class Ageexception extends RuntimeException {
    public Ageexception(String message) {
        super(message);
    }
}