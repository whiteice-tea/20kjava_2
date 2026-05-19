package iO.reader;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class BuffererReader_ {
    public static void main(String[] args) throws Exception{
        String filepath = "e:\\java_idea\\20kjava_2\\src\\main\\java\\vector\\LinkedListCRUD.java";

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filepath));

        //read
        String line;
//        line = bufferedReader.readLine(); 
        while((line = bufferedReader.readLine()) != null){
            System.out.println(line);
        }
        //只需要关闭BufferedReader，因为底层会自动关闭节点流
        bufferedReader.close();
    }
}
