package BIGinteger;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class bigmath {
    public static void main(String[] args) {

//        long l = 2378888889999999999999999l;
//        System.out.println(l);

        BigInteger bigInteger = new BigInteger("2378888889999999999999999");
        System.out.println(bigInteger);

        //在对biginteger，要使用对应的方法，不能直接+—*\
        BigInteger bigInteger1 = new BigInteger("1");
        BigInteger add = bigInteger.add(bigInteger1);
        System.out.println(add);

        //大精度

//        double d=1.888888888888888888888888888888888888888;
        BigDecimal bigDecimal = new BigDecimal("1.888888888888888888888888877777777888888888");
        System.out.println(bigDecimal);

        Date d1 = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String date = sdf.format(d1);
        System.out.println(date);

        Date d2 = new Date(9234567);
        System.out.println(d2);


        String s="01/11/2008 10:48:01";
        try {
            Date parsedDate = sdf.parse(s);
            System.out.println(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
