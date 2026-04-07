package chapter16;

import java.awt.Graphics;
import javax.swing.*;
/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class DrawCircle extends JFrame{//JFrame对应窗口，可以理解成是一个画框
    //定义一个面板
    private Mypanle mp = null;
    public static void main(String[] args) {

        new DrawCircle();
        System.out.println("程序结束了");
    }

    public DrawCircle() {
        //初始化面板
        mp = new Mypanle();
        //把面板放入到窗口（画框）
        this.add(mp);
        //设置窗口的大小
        this.setSize(400,300);
        //
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);//可以显示

    }
}

class Mypanle extends JPanel {
    //1.Mypanel就是一个画板
    //2.Graphics g 把g理解成一个画笔
    //3.Graphics 提供了很多绘图的方法


    @Override
    public void paint(Graphics g) {
        super.paint(g);//调用父类的方法完成初始化
//        System.out.println("paint 方法被调用了");
        //画出一个圆形
        g.drawOval(10,10,100,100);
//        g.drawLine(10,10,100,100);

    }

}
