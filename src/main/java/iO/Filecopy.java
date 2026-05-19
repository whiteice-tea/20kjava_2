package iO;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Filecopy {
    public static void main(String[] args) {
        String srcfilepath = "e:\\java_exp\\output\\xie_ying.png";
        String destFilepath = "e:\\java_exp\\enter\\xie_ying.png";
        FileInputStream fis = null;
        FileOutputStream fos = null;

        try {
            fis = new FileInputStream(srcfilepath);
            fos = new FileOutputStream(destFilepath);
            //定义一个字节数组，提高读取效果
            byte[] buffer = new byte[1024];
            int readlen = 0;
            while((readlen  = fis.read(buffer)) != -1){
                //读取到后，就写入文件，通过，fileOutputStream，边读边写
                fos.write(buffer,0,readlen);
            }
            System.out.println("complete");

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            try{
                if(fis != null){
                    fis.close();
                }
                if(fos != null){
                    fos.close();
                }
            } catch(IOException e){
                e.printStackTrace();
            }

        }
    }

}
