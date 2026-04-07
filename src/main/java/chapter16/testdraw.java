package chapter16;

import java.awt.*;
import javax.swing.*;
/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class testdraw extends JFrame {
    private MyPanel MP = null;

    public static void main(String[] args) {
        new testdraw();

    }

    public testdraw() {
        MP = new MyPanel();
        this.add(MP);
        this.setSize(1000, 800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}


class MyPanel extends JPanel {
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //演示绘制不同的图形
        //画直线
        //g.drawLine(10,10,100,100);
        //画出矩形边框
        //g.drawRect(10,10,100,100);
        //画出椭圆边框
        //g.drawOval(10,10,100,100);
        //填充矩形
        //设置画笔颜色
        //g.setColor(Color.CYAN);
        //g.fillRect(10,10,100,100);
        //填充椭圆
        //g.setColor(Color.red);
        //g.fillOval(10,10,100,100);
        //画图片
        //1.加载图片资源
        //Image image = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/DuLin.png"));
        //这个MyPanel要是自己写的类
        //2.展示图片
        //g.drawImage(image, 10, 10 , 822,729 ,this);
//        java.net.URL url = MyPanel.class.getResource("/DuLin.png");
//        System.out.println(url);
//        Image image = Toolkit.getDefaultToolkit().getImage(url);
//        g.drawImage(image, 10, 10, 822, 729, this);
        //画字符串
        //g.setColor(Color.red);
        //g.setFont(new Font("Serif", Font.BOLD, 20));//BOLD粗体
        //g.drawString("Hello World", 50, 50);



    }
}
