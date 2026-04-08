package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class MyPanel  extends JPanel implements KeyListener {
    //定义我的坦克
    Hero hero=null;
    //坦克坐标
    int x=100;
    int y=100;
    int direction=0;

    public MyPanel() {
        hero = new Hero(x,y);
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750);//填充矩形，默认黑色
        //画出的坦克-封装方法
        drawTank(x, y, g,direction,0);
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
            y+=3;
            direction = 2;
        } else if(e.getKeyCode() == KeyEvent.VK_W){
            y-=3;
            direction = 0;
        } else if(e.getKeyCode() == KeyEvent.VK_A){
            x-=3;
            direction = 3;
        } else if(e.getKeyCode() == KeyEvent.VK_D){
            x+=3;
            direction = 1;
        }

        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
