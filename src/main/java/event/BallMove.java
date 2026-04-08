package event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author whiteicetea
 * @version 1.0.0
 */
public class BallMove extends JFrame {

    MyPanel myPanel=null;


    public static void main(String[] args) {
        BallMove ballMove = new BallMove();
    }

    public BallMove() {
        myPanel = new MyPanel();
        this.add(myPanel);
        this.setSize(400, 300);
        //窗口JFrame对象可以可以监听键盘事件，即可以监听到面板发生事件
        this.addKeyListener(myPanel);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}
//KeyListeners1监听器，可以监听键盘事件


class MyPanel extends JPanel implements KeyListener {

    int x=10;
    int y=10;
    @Override
    public void paint(Graphics g){
        super.paint(g);
        g.fillOval(x,y,20,20);
    }
    //监听有字符输出时，该方法就会触发
    @Override
    public void keyTyped(KeyEvent e) {

    }
    //当某个按键按下，就会触发
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode()==KeyEvent.VK_DOWN){
            y++;
        }else if(e.getKeyCode()==KeyEvent.VK_UP){
            y--;
        }else if(e.getKeyCode()==KeyEvent.VK_LEFT){
            x--;
        }else if(e.getKeyCode()==KeyEvent.VK_RIGHT){
            x++;
        }
        //重绘面板
        this.repaint();
//        System.out.println((char)e.getKeyChar()+"被按下");
    }
    //当某个键松开，就会触发
    @Override
    public void keyReleased(KeyEvent e) {

    }
}
