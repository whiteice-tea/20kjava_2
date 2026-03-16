package exce;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class exce01 {
    public static void main(String[] args) {
        String a = "abcdefg";
        System.out.println(reverse(a,0,a.length()-1));
    }
    public static String reverse(String str,int start,int end){

        char[] chars = str.toCharArray();
        if(start>chars.length-1){
            return "false";

        }else if(end>chars.length-1){
            return "false";
        }else if(start==end){
            return "false";
        }
        char temp;
        for(int i=start,j=end;i<j;i++,j--){
            temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }

}
