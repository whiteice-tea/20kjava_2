package tankgame;

import javax.swing.*;
import java.awt.*;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class MyPanel  extends JPanel {
    //定义我的坦克
    Hero hero=null;

    public MyPanel() {
        hero = new Hero(100,100);
    }


    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillRect(0,0,1000,750);//填充矩形，默认黑色
        //画出的坦克-封装方法
        drawTank(hero.getX(), hero.getY(), g,0,0);
    }

    public void  drawTank(int x,int y,Graphics g,int direction,int type){
        switch(type){
            case 0:
                //自己
                g.setColor(Color.cyan);
                break;

            case 1:
                g.setColor(Color.yellow);
                break;

        }

        switch(direction){
            case 0:
                g.fill3DRect(x, y,10,60,false);
                g.fill3DRect(x+30, y,10,60,false);
                g.fill3DRect(x+10, y+10,20,40,false);
                g.fillOval(x+10,y+20,20,20);
                g.drawLine(x+20,y+30,x+20,y);
                break;
        }
    }
}
