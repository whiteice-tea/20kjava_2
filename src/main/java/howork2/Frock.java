package howork2;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Frock {
    private static int currentNum=100000;
    private int serialNumber=0;
    public Frock() {
        serialNumber=getNextNum();

    }

    public static int getNextNum() {
        currentNum+=100;
        return currentNum;
    }

    public int getSerialNumber() {
        return serialNumber;
    }
}
