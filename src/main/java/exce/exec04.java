package exce;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class exec04 {
    public static void main(String[] args) {
        String str = "abcasdufbasASDGEBAJKFONAdg  iasgn,ladf,ags;g";
        int lowercchar=0;
        int upperchar=0;
        int digit=0;
        for(int i=0; i<str.length(); i++){
            if(str.charAt(i)>='a'&&str.charAt(i)<='z'){
                lowercchar++;
            }
            if(str.charAt(i)>='A'&&str.charAt(i)<='Z'){
                upperchar++;
            }
            if(str.charAt(i)>='0'&&str.charAt(i)<='9'){
                digit++;
            }
        }
        System.out.println(lowercchar);
        System.out.println(upperchar);
        System.out.println(digit);

    }
}
