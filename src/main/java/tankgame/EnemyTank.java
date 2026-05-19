package tankgame;

import java.util.Vector;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class EnemyTank extends Tank implements Runnable{
    Vector<Shot> shots = new Vector<>();
    public EnemyTank(int x, int y) {
        super(x, y);
    }

    @Override
    public void run() {

        while (true) {
            if(isLive&&shots.size()<3){
                shotEnemy();
            }

            switch(getDirection()){
                case 0://up
                    for(int i=0;i<20;i++){
                        moveUP();
                        try{
                            Thread.sleep(50);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }

                    }
                    break;
                case 1://right
                    for(int i=0;i<20;i++){
                        moveRight();
                        try{
                            Thread.sleep(50);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }

                    }
                    break;
                case 2://down
                    for(int i=0;i<20;i++){
                        moveDOWN();
                        try{
                            Thread.sleep(50);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }

                    }
                    break;
                case 3://left
                    for(int i=0;i<20;i++){
                        moveLeft();
                        try{
                            Thread.sleep(50);
                        }catch(InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                    break;
            }

            setDirection((int)(Math.random()*4));
        }
    }

    public void shotEnemy(){
        if(!isLive){
            return;
        }

        Shot shot = null;
        switch(getDirection()){
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
