/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class howork12 {
    public static void main(String[] args) {

    }
}

enum color{
    RED(255,0,0),
    BLUE(0,0,255),
    GREEN(0,0,0),
    YELLOW(255,255,0),
    BLACK(0,0,0);

    private int redValue;
    private int blueValue;
    private int greenValue;

    color(int redValue, int blueValue, int greenValue) {
        this.redValue = redValue;
        this.blueValue = blueValue;
        this.greenValue = greenValue;
    }
}
