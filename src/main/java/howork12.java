/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class howork12 {
    public static void main(String[] args) {
        color c=color.BLACK;
        switch(c){
            case RED:
                System.out.println("匹配到红色");
                c.show();
                break;
            case BLUE:
                System.out.println("匹配到蓝色");
                c.show();
                break;
            case BLACK:
                System.out.println("匹配到黑色");
                c.show();
                break;
            case YELLOW:
                System.out.println("匹配到黄色");
                c.show();
                break;
            case GREEN:
                System.out.println("匹配到绿色");
                c.show();
                break;
        }
    }
}

enum color implements showcolor{
    RED(255,0,0),
    BLUE(0,0,255),
    GREEN(0,0,0),
    YELLOW(255,255,0),
    BLACK(0,0,0);

    private int redValue;
    private int greenValue;
    private int blueValue;

    color(int redValue, int greenValue, int blueValue) {
        this.redValue = redValue;
        this.blueValue = blueValue;
        this.greenValue = greenValue;
    }

    @Override
    public void show() {
        System.out.println("redValue=" + redValue +
                ", greenValue=" + greenValue +
                ", blueValue=" + blueValue);
    }

}


interface showcolor{
    void show();
}
