package src.src.com.fausaku.palne;

import java.awt.*;

/**
 * 爆炸类
 */
public class Explode {
    //位置
    double x,y;

    static Image[] imgs = new Image[16];

    int count;

    static {
        for (int i = 0; i < 16; i++) {
            imgs[i] = GameUtil.getImage("images/explode/e" + (i + 1) + ".gif");
//            imgs[i].getWidth(null);     //解决懒加载问题。目前没有问题，加不加都行
        }
    }

    public void drawMySelf(Graphics g) {
        if (count < 15) {
            g.drawImage(imgs[count], (int) x, (int) y, null);
            count++;
        }
    }

    public Explode() {
    }

    public Explode(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
