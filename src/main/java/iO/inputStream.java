package iO;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class inputStream {
    public static void main(String[] args) {

    }

//    @Test
//    public void readFile01(){//单个字节的读取
//        String path = "e:\\java_exp\\hello.txt";
//        int read =0;
//        FileInputStream fileInputStream = null;
//        try {
//             fileInputStream = new FileInputStream(path);
//            while((read = fileInputStream.read()) != -1){
//                System.out.print((char)read);//转成char显示
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }finally{
//            //关闭文件流，释放资源
//            try{
//                fileInputStream.close();
//            } catch(IOException e){
//                e.printStackTrace();
//            }
//        }
//    }

    @Test
    public void readFile02(){//使用read（byte[]）
        String path = "e:\\java_exp\\hello.txt";
        int read =0;
        FileInputStream fileInputStream = null;
        byte[] b = new byte[8];
        int readlen=0;
        try {
            fileInputStream = new FileInputStream(path);
            while((readlen = fileInputStream.read(b)) != -1){
                System.out.print(new String(b,0,readlen));//转成char显示
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            //关闭文件流，释放资源
            try{
                fileInputStream.close();
            } catch(IOException e){
                e.printStackTrace();
            }
        }

    }

}
