package tankgame;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Shot implements Runnable{
    int x;
    int y;
    int direction;
    int speed=10;
    boolean islive=true;

    public Shot(int x, int y, int direction){
        this.x = x;
        this.y = y;
        this.direction = direction;

    }

    @Override
    public void run(){
        while(islive){
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            switch(direction){
                case 0: // 上
                    y -= speed;
                    break;
                case 1: // 右
                    x += speed;
                    break;
                case 2: // 下
                    y += speed;
                    break;
                case 3: // 左
                    x -= speed;
                    break;
            }

            if (x < 0 || x > 1000 || y < 0 || y > 750) {
                islive = false;
            }
        }
    }
}
