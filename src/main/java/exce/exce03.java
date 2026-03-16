package exce;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class exce03 {
    public static void main(String[] args) {
        String name = "Han Shun Ping";
        printf(name);

    }
    public static void printf(String str){
        if(str==null){
            System.out.println("null");
        }

        String[] parts = str.split(" ");
        if(parts.length!=3){
            System.out.println("wrong length");
        }

        String.format("%s,%s.%c",parts[2],parts[0],parts[1].toUpperCase().charAt(0));
        System.out.printf( String.format("%s,%s.%c",parts[2],parts[0],parts[1].toUpperCase().charAt(0)));

    }
}
