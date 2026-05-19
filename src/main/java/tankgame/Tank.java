package tankgame;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Tank {
    private int x;
    private int y;
    private int direction;
    private int speed =5;

    boolean isLive = true;
    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void moveUP(){
        if(y - speed >= 0){
            y -= speed;
        }
    }

    public void moveDOWN(){
        if(y + speed + getTankHeight() <= MyPanel.GAME_HEIGHT){
            y += speed;
        }
    }

    public void moveLeft(){
        if(x - speed >= 0){
            x -= speed;
        }
    }

    public void moveRight(){
        if(x + speed + getTankWidth() <= MyPanel.GAME_WIDTH){
            x += speed;
        }
    }
    public int getTankWidth() {
        if (direction == 0 || direction == 2) {
            return 40;
        } else {
            return 60;
        }
    }

    public int getTankHeight() {
        if (direction == 0 || direction == 2) {
            return 60;
        } else {
            return 40;
        }
    }

}
