package mingda.com.waterripple.bean;

/**
 * 圆的对象
 * Created by 玉光 on 2017-12-1.
 */

public class Circle {
    /**
     * 圆心点的x坐标
     */
    public int centeX;
    /**
     * 圆心点的Y坐标
     */
    public int centerY;
    /**
     * 圆的半径
     */
    public int radius;
    /**
     * 透明度
     */
    public int alpha = (int) (0.006 * 255);

    public Circle() {

    }

    public Circle(int centeX, int centerY, int radius, int alpha) {
        this.centeX = centeX;
        this.centerY = centerY;
        this.radius = radius;
        this.alpha = alpha;
    }
}
