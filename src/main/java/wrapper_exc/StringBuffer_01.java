package wrapper_exc;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class StringBuffer_01 {
    public static void main(String[] args) {
        String price = "1234556.59";
        StringBuffer sb = new StringBuffer(price);

        for(int i=sb.indexOf(".")-3;i>0;i-=3){
            sb=sb.insert(i,',');
        }
        System.out.println(sb);

    }
}
