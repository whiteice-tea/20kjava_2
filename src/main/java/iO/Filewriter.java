package iO;

import java.io.FileWriter;
import java.io.IOException;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Filewriter {
    public static void main(String[] args) {

        String filepath = "e:\\java_exp\\note.txt";
        FileWriter fw = null;
        char[] note = "absdnsaind".toCharArray();
        String sb = "qpqpqallalzmxnff";
        try {
            fw = new FileWriter(filepath,true);
//            fw.write('H');
            fw.write(note);
            fw.write(note,1,note.length-2);
            fw.write(sb);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("complete");
    }
}
