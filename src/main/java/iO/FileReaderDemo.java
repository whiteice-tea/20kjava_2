package iO;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileReaderDemo {
    public static void main(String[] args) {
        String filePath = "e:\\java_exp\\store.txt";

        FileReader fr = null;
        int readlen = 0;
        char[] buf = new char[8];

        try {
            fr = new FileReader(filePath);

            while((readlen = fr.read(buf)) != -1){
                System.out.print(new  String(buf,0,readlen));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(fr != null){
                    fr.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}