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
class Ageexception extends RuntimeException {
    public Ageexception(String message) {
        super(message);
    }
}