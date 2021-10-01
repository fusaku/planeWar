package src.src.com.fausaku.palne;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 游戏主窗口
 */
public class MyGameFrame extends Frame {


    Image planeImg = GameUtil.getImage("images/plane.png");
    Image bg = GameUtil.getImage("images/bg.jpg");

    Plane p1 = new Plane(planeImg, 100, 100, 7);

    Shell[] shells = new Shell[3];

    @Override
    public void paint(Graphics g) {     //g当做是一直画笔

        g.drawImage(bg, 0, 0, 500, 500, null);

         p1.drawMyself(g);

         //画炮弹
        for (int i = 0; i < shells.length; i++) {
            shells[i].drawMyself(g);

            //碰撞检测。将所有飞行的炮弹和飞机进行检测，看有没有碰撞。
            boolean peng = p1.getRec().intersects(shells[i].getRec());

            if (peng) {
                System.out.println("飞机被击中了！！");
            }
        }
    }

    //初始化窗口
    public void launchFrame() {
        this.setTitle("飞机大战.Fusaku");
        setVisible(true);   //窗口是否可见

        setSize(Constant.GAME_WIDTH,Constant.GAME_HEIGHT);  //窗口大小

        setLocation(400, 100);      //窗口打开的位置

        //增加关闭窗口的动作
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);     //正常退出程序
            }
        });

        new PaintThread().start();      //启动重画窗口的线程。
        this.addKeyListener(new KeyMonitor());      //启动键盘监听

        //初始化创建50个炮弹对象
        for (int i = 0; i < shells.length; i++) {
            shells[i] = new Shell();
        }

    }

    /**
     * 定义了一个重画窗口的线程类。
     * 定义成内部类是为了方便直接使用窗口类相关方法。
     */
    class PaintThread extends Thread {
        @Override
        public void run() {
            while (true) {
                repaint();      //内部类可以直接使用外部类的成员！

                try {
                    Thread.sleep(50);       //1s=1000ms。    1秒画20次(20*50=1000)
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //内部类，实现键盘的监听处理
    class KeyMonitor extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
//            System.out.println("按下:"+e.getKeyCode());
/*            if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                left = true;
            }
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                right = true;
            }*/

            p1.addDirection(e);
        }

        @Override
        public void keyReleased(KeyEvent e) {
//            System.out.println("抬起:"+e.getKeyCode());
            p1.minusDirection(e);
        }
    }

    private Image offScreenImage = null;

    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(Constant.GAME_WIDTH, Constant.GAME_HEIGHT);//这是游戏窗口的宽度和高度

        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public static void main(String[] args) {
        MyGameFrame gameFrame = new MyGameFrame();
        gameFrame.launchFrame();

    }
}
