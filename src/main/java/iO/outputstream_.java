package iO;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class outputstream_ {
    public static void main(String[] args) {

    }
    //演示使用FileOutputStream 将数据写到文件中，
    //如果文件不存在，就创建文件
    @Test
    public void writeFile(){
        String filePath = "e:\\java_exp\\new9.txt";
        FileOutputStream fileOutputStream = null;
        try {
            //1.new FileOutputStream(filePath);,创建方式，当写入内容是，会覆盖原来的内容
            //2.new FileOutputStream(filePath,true) 创建方式，当写入内容是 ，是追加到文件后面
            fileOutputStream = new FileOutputStream(filePath,true);
//            fileOutputStream.write('h');
            //写入字符串
            String str = "hello,world";
            fileOutputStream.write(str.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                fileOutputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }
}
