package iO;

import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Directory {
    public static void main(String[] args) {

    }
    @Test
    public void m1(){
        String parentPath = "e:\\java_exp\\new1.txt\\a\\b\\c\\d";
        File file = new File(parentPath);
        if(file.exists()){

            System.out.println("存在");
        }else{
           if(file.mkdirs()){
               System.out.println("创建成功");
           }else{
               System.out.println("创建失败");
           }
        }

    }
}
