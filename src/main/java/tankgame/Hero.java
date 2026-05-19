package tankgame;

import java.util.Vector;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class Hero extends Tank{
    Vector<Shot> shots=new Vector<>();
    public Hero(int x,int y){
        super(x,y);
    }

    public void shotEnemyTank(){
        Shot shot = null;
        switch (getDirection()){
            case 0: // 上
                shot = new Shot(getX() + 20, getY(), 0);
                break;
            case 1: // 右
                shot = new Shot(getX() + 60, getY() + 20, 1);
                break;
            case 2: // 下
                shot = new Shot(getX() + 20, getY() + 60, 2);
                break;
            case 3: // 左
                shot = new Shot(getX(), getY() + 20, 3);
                break;
        }
        shots.add(shot);
        new Thread(shot).start();
    }
}
