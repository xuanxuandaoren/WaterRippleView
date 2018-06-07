package mingda.com.waterripple.view;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;




import java.util.ArrayList;
import java.util.List;

import mingda.com.waterripple.R;
import mingda.com.waterripple.bean.Circle;
import mingda.com.waterripple.utils.DisplayUtils;

/**
 * 助眠背景的图片
 * Created by 玉光 on 2017-12-1.
 */

public class WaterRippleView extends View {
    /**
     * 更新界面
     */
    private static final int UPDATE_VIEW = 100;
    /**
     * 控件的宽
     */
    private int mWidth;
    /**
     * 控件的高
     */
    private int mHeight;
    /**
     * 画圆的画笔
     */
    private Paint mPaint = new Paint();
    /**
     * 画进度的画笔
     */
    private Paint mProgressPaint = new Paint();
    /**
     * 圆的集合
     */
    private List<Circle> circles = new ArrayList<>();
    /**
     * 动画间隔时间
     */
    private int intervalTime = 25;
    /**
     * 每次增加的距离
     */
    private int distance = 2;
    /**
     * 最小的半径
     */
    private int minRadius = 38;
    /**
     * 间隔的距离
     */
    private int intervalDistance = 38;
    /**
     * 画圆角的半径
     */
    private int angle = 0;
    /**
     * 最大的半径
     */
    private int maxRadius;

    /**
     *
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case UPDATE_VIEW:
                    //更新界面
                    if (isMoving) {
                        updateCircles();
                        sendEmptyMessageDelayed(UPDATE_VIEW, intervalTime);
                    }
                    break;
            }

        }
    };


    /**
     * 是否需要动画
     */
    private boolean isMoving;
    private float interRadius = 30f;
    private int mBackgroundColor = getResources().getColor(R.color.white_50_transparent);
    private RectF destRect;

    public WaterRippleView(Context context) {
        this(context, null);
    }

    public WaterRippleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterRippleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化控件
     */
    private void init() {
        mPaint.setAntiAlias(true);
        mPaint.setColor(mBackgroundColor);

        mProgressPaint.setColor(Color.WHITE);
        mProgressPaint.setAntiAlias(true);
        mProgressPaint.setStyle(Paint.Style.STROKE);
        mProgressPaint.setStrokeWidth(DisplayUtils.dip2px(getContext(), 5f));

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();

        maxRadius = Math.min(mWidth / 2, mHeight / 2);

        destRect = new RectF(mWidth / 2-DisplayUtils.dip2px(getContext(),minRadius), mHeight / 2-DisplayUtils.dip2px(getContext(),minRadius), mWidth / 2+DisplayUtils.dip2px(getContext(),minRadius), mHeight / 2+DisplayUtils.dip2px(getContext(),minRadius));


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Circle circle = null;
        mPaint.setColor(Color.WHITE);
        for (int i = 0; i < circles.size(); i++) {
            circle = circles.get(i);


            mPaint.setAlpha(circle.alpha);
            canvas.drawCircle(circle.centeX, circle.centerY, circle.radius, mPaint);

        }

        mPaint.setColor(0xFF1B77FF);//0xFF1B77FF
        canvas.drawCircle(mWidth / 2, mHeight / 2, DisplayUtils.dip2px(getContext(), minRadius), mPaint);

//        canvas.drawArc(destRect, -90, angle % 360, false, mProgressPaint);

        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.music_interact_center),null,destRect,mPaint);

//
        canvas.save();

//        canvas.translate(mWidth/2,mHeight/2);
        canvas.rotate(angle % 360,mWidth/2,mHeight/2);
        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.rotated_edge), null,destRect, mPaint);
        canvas.restore();
//



//        matrix.mapRect(destRect);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.music_interact_center),new RectF(0,0,mWidth,mHeight),destRect,mPaint);
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.music_interact_center),matrix,mPaint);
    }

    /**
     * 开始动画
     */
    public void startMoving() {
        isMoving = true;
        mHandler.sendEmptyMessage(UPDATE_VIEW);
    }

    /**
     * 结束动画
     */
    public void stopMoving() {
        isMoving = false;
        mHandler.removeMessages(UPDATE_VIEW);
    }

    /**
     * 更新界面
     */
    private void updateCircles() {
        if (mWidth == 0 && mHeight == 0) {
            return;
        }

        angle += distance;
        //更新圆
        Circle circle = null;
        for (int i = circles.size() - 1; i >= 0; i--) {
            circle = circles.get(i);
            circle.radius += distance;
            circle.alpha = updataCircleAlpha(circle.radius);
//            if (Math.max(Math.max(Math.pow(circle.centeX, 2) + Math.pow(circle.centerY, 2), Math.pow(circle.centeX - mWidth, 2) + Math.pow(circle.centerY, 2)), Math.max(Math.pow(circle.centeX, 2) + Math.pow(circle.centerY - mHeight, 2), Math.pow(circle.centeX - mWidth, 2) + Math.pow(circle.centerY - mHeight, 2))) < Math.pow(circle.radius - 4 * DisplayUtils.dip2px(getContext(), interRadius), 2)) {
//                circles.remove(circle);
//            }
            if (circle.radius >= maxRadius) {
                circles.remove(circle);
            }

        }
//        if (circles.size() == 0 || circles.get(circles.size() - 1).radius > DisplayUtils.dip2px(getContext(), interRadius)) {
//            circles.add(new Circle(mWidth / 2, mHeight / 2, distance, updataCircleAlpha(0)));
//        }
        if (circles.size() == 0 || (circles.get(circles.size() - 1).radius - DisplayUtils.dip2px(getContext(), minRadius) >= DisplayUtils.dip2px(getContext(), intervalDistance))) {
            circles.add(new Circle(mWidth / 2, mHeight / 2, DisplayUtils.dip2px(getContext(), minRadius), updataCircleAlpha(minRadius)));
        }
        invalidate();

    }

    /**
     * 设置波形背景的颜色
     *
     * @param mBackgroundColor
     */
    public void setmBackgroundColor(int mBackgroundColor) {
        this.mBackgroundColor = mBackgroundColor;
        mPaint.setColor(mBackgroundColor);
    }


    /**
     * 更新圆的颜色
     *
     * @param radius
     * @return
     */
    private int updataCircleAlpha(int radius) {
        int alpha = (int) ((maxRadius - radius) * 0.1 * 255 / maxRadius);
        if (alpha < 0) {
            alpha = 0;
        }
        return alpha;
    }
}
