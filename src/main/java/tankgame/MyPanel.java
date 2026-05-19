package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class MyPanel  extends JPanel implements KeyListener,Runnable {
    // 游戏区域大小
    public static final int GAME_WIDTH = 1000;
    public static final int GAME_HEIGHT = 700;
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (Exception e) {
                e.printStackTrace();
            }
            this.repaint();
        }
    }
    //定义我的坦克
    Hero hero=null;

    Vector<EnemyTank> enemyTanks = new Vector<>();
    Vector<Bomb> bombs = new Vector<>();

    int enemytankssize=3;

    //坦克坐标
    int x=300;
    int y=300;

    //
    int direction=0;

    public MyPanel() {
        hero = new Hero(x,y);

        for(int i=0;i<enemytankssize;i++){
            EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
            enemyTank.setDirection(2);
            //加入子弹
//            Shot shot=new Shot(enemyTank.getX()+20,enemyTank.getY()+60,2);
            //加入到vector
//            enemyTank.shots.add(shot);
            //start shot
//            new Thread(shot).start();

            enemyTanks.add(enemyTank);

            new Thread(enemyTank).start();
        }
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);//填充矩形，默认黑色//填充矩形，默认黑色

        // 检测我方子弹是否击中敌方坦克
        hitEnemyTank();
        hitHeroTank();
        //画出的坦克-封装方法
        if(hero!=null&&hero.isLive){
            drawTank(hero.getX(), hero.getY(), g, hero.getDirection(), 0);
            for(int i=0;i<hero.shots.size();i++){
                Shot shot=hero.shots.get(i);
                if (shot != null && shot.islive) {
                    g.setColor(Color.RED);
                    g.fillOval(shot.x, shot.y, 4, 4);
                } else {
                    hero.shots.remove(shot);
                    i--;
                }
            }
        }else{

        }



        //画出敌人坦克
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank et =enemyTanks.get(i);
            if(et!=null&&et.isLive){
                drawTank(et.getX(),et.getY(),g,et.getDirection(),1);
            }else{
                enemyTanks.remove(et);
                i--;
            }


            for(int j=0;j<et.shots.size();j++){
                Shot shot =et.shots.get(j);
                if(shot.islive){
                    g.setColor(Color.YELLOW);
                    g.fillOval(shot.x,shot.y, 4, 4);
                }else{
                    et.shots.remove(shot);
                    j--;
                }
            }
        }

        //paint tank bomb
        for(int i=0;i<bombs.size();i++){
            Bomb bomb = bombs.get(i);

            if (bomb.life > 6) {
                g.setColor(Color.ORANGE);
                g.fillOval(bomb.x, bomb.y, 40, 40);
            } else if (bomb.life > 3) {
                g.setColor(Color.YELLOW);
                g.fillOval(bomb.x + 5, bomb.y + 5, 30, 30);
            } else {
                g.setColor(Color.RED);
                g.fillOval(bomb.x + 10, bomb.y + 10, 20, 20);
            }

            bomb.lifeDown();

            if (!bomb.islive) {
                bombs.remove(bomb);
                i--;
            }
        }



    }

    public void  drawTank(int x,int y,Graphics g,int direction,int type){
        switch(type){
            case 0:
                //自己
                g.setColor(Color.cyan);
                break;

            case 1:
                //敌人
                g.setColor(Color.yellow);
                break;

        }

        switch(direction){
            case 0: // 上
                g.fill3DRect(x, y, 10, 60, false);       // 左履带
                g.fill3DRect(x + 30, y, 10, 60, false);  // 右履带
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // 车身
                g.fillOval(x + 10, y + 20, 20, 20);      // 炮塔
                g.drawLine(x + 20, y + 30, x + 20, y);   // 炮管
                break;

            case 1: // 右
                g.fill3DRect(x, y, 60, 10, false);       // 上履带
                g.fill3DRect(x, y + 30, 60, 10, false);  // 下履带
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // 车身
                g.fillOval(x + 20, y + 10, 20, 20);      // 炮塔
                g.drawLine(x + 30, y + 20, x + 60, y + 20); // 炮管
                break;

            case 2: // 下
                g.fill3DRect(x, y, 10, 60, false);       // 左履带
                g.fill3DRect(x + 30, y, 10, 60, false);  // 右履带
                g.fill3DRect(x + 10, y + 10, 20, 40, false); // 车身
                g.fillOval(x + 10, y + 20, 20, 20);      // 炮塔
                g.drawLine(x + 20, y + 30, x + 20, y + 60); // 炮管
                break;

            case 3: // 左
                g.fill3DRect(x, y, 60, 10, false);       // 上履带
                g.fill3DRect(x, y + 30, 60, 10, false);  // 下履带
                g.fill3DRect(x + 10, y + 10, 40, 20, false); // 车身
                g.fillOval(x + 20, y + 10, 20, 20);      // 炮塔
                g.drawLine(x + 30, y + 20, x, y + 20);   // 炮管
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_S){
            hero.setDirection(2);
            hero.moveDOWN();

        } else if(e.getKeyCode() == KeyEvent.VK_W){
            hero.setDirection(0);
            hero.moveUP();

        } else if(e.getKeyCode() == KeyEvent.VK_A){
            hero.setDirection(3);
            hero.moveLeft();

        } else if(e.getKeyCode() == KeyEvent.VK_D){
            hero.setDirection(1);
            hero.moveRight();

        } else if (e.getKeyCode() == KeyEvent.VK_J) {
            if(hero.shots.size()<5){
                hero.shotEnemyTank();
            }
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void hitenemyTank(Shot s,EnemyTank enemyTank){
        switch(enemyTank.getDirection()){
            case 0:
            case 2://up or down width:40 height:60
                if(s.x>enemyTank.getX()
                        &&s.x<enemyTank.getX()+40
                        &&s.y>enemyTank.getY()
                        &&s.y<enemyTank.getY()+60
                ){
                    s.islive = false;
                    enemyTank.isLive = false;
                    //creat bomb
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3://right or left width:40 height:60
                if(s.x>enemyTank.getX()
                        &&s.x<enemyTank.getX()+60
                        &&s.y>enemyTank.getY()
                        &&s.y<enemyTank.getY()+40
                ){
                    s.islive = false;
                    enemyTank.isLive = false;

                    //creat bomb
                    Bomb bomb = new Bomb(enemyTank.getX(),enemyTank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    public void hitEnemyTank() {
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);

            if (shot != null && shot.islive) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);

                    if (enemyTank.isLive) {
                        hitenemyTank(shot, enemyTank);
                    }
                }
            }
        }
    }

    public void hitheroTank(Shot s,Hero hero){
        switch(hero.getDirection()){
            case 0:
            case 2://up or down width:40 height:60
                if(s.x>hero.getX()
                        &&s.x<hero.getX()+40
                        &&s.y>hero.getY()
                        &&s.y<hero.getY()+60
                ){
                    s.islive = false;
                    hero.isLive = false;
                    //create bomb
                    Bomb bomb = new Bomb(hero.getX(),hero.getY());
                    bombs.add(bomb);
                }
                break;
            case 1:
            case 3://right or left width:40 height:60
                if(s.x>hero.getX()
                        &&s.x<hero.getX()+60
                        &&s.y>hero.getY()
                        &&s.y<hero.getY()+40
                ){
                    s.islive = false;
                    hero.isLive = false;
                    //create bomb
                    Bomb bomb = new Bomb(hero.getX(),hero.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    public void hitHeroTank(){
        for(int i=0;i<enemyTanks.size();i++){
            EnemyTank ET = enemyTanks.get(i);
            for(int j=0;j<ET.shots.size();j++){
                Shot shot = ET.shots.get(j);
                if (shot != null && shot.islive) {
                    if(hero.isLive){
                        hitheroTank(shot,hero);
                    }
                }
            }
        }
    }
}
