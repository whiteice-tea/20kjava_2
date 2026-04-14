package cpu;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class CpuNum {
    public static void main(String[] args) {
        Runtime rt = Runtime.getRuntime();
        int cpus = rt.availableProcessors();
        System.out.println(cpus);


    }
}
