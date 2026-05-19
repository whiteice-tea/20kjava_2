package iO;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Filecreate {
    public static void main(String[] args) {

    }

    //obj 1
    @Test
    public  void create01(){
        String filePath = "e:\\java_exp\\new1.txt";
        File file = new File(filePath);
        try{
            file.createNewFile();
            System.out.println("文件创建成功");
        } catch (IOException e){
            e.printStackTrace();
        }
    }


    //obj 2 new File(File parent,String child)根据父目录文件+子路径构建
    @Test
    public void create02(){
        File parentFile = new File("e:\\java_exp");
        String fileName = "new2.txt";
        File file = new File(parentFile, fileName);

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    //obj 3  new File(String parent,String child)//根据父目录+子路径构建
    @Test
    public void create03(){
        String parentPath = "e:\\java_exp";
        String fileName = "new3.txt";
        File file = new File(parentPath, fileName);

        try {
            file.createNewFile();
            System.out.println("complete");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
