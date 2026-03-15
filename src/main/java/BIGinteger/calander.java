package BIGinteger;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class calander {
    public static void main(String[] args) {
//        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar.get(Calendar.YEAR));
//        //月是从0开始
//        System.out.println(calendar.get(Calendar.MONTH)+1);
//        System.out.println(calendar.get(Calendar.DAY_OF_MONTH));
//        System.out.println(calendar.get(Calendar.HOUR_OF_DAY));
//        System.out.println(calendar.get(Calendar.MINUTE));
//        System.out.println(calendar.get(Calendar.SECOND));



        LocalDateTime localDate = LocalDateTime.now();
        System.out.println(localDate.getYear());
    }
}
