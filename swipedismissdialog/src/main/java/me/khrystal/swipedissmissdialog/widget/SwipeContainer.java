package me.khrystal.swipedissmissdialog.widget;

import android.content.Context;
import android.support.v4.BuildConfig;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/13
 * update time:
 * email: 723526676@qq.com
 */
public class SwipeContainer extends FrameLayout{

    private static final int X_VEL_THRESHOLD = 900;
    public static final int X_DISTANCE_THRESHOLD = 300;
    public static final int VANISH_TYPE_LEFT = 0;
    public static final int VANISH_TYPE_RIGHT = 1;

    private final ViewDragHelper mDragHelper;
    private GestureDetectorCompat moveDetector;
    private int mWidth;
    private int mHeight;

    private int initCenterViewX, initCenterViewY, childWidth;
    private DismissListener mDismissListener;


    public SwipeContainer(Context context) {
        this(context, null);
    }

    public SwipeContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SwipeContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mDragHelper = ViewDragHelper.create(this,1.0f,new DragHelperCallback());
        moveDetector = new GestureDetectorCompat(context, new MoveDetector());
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        initCenterViewX = getChildAt(0).getLeft();
        initCenterViewY = getChildAt(0).getTop();
        childWidth = getChildAt(0).getWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean shouldIntercept = mDragHelper.shouldInterceptTouchEvent(ev);
        boolean moveFlag = moveDetector.onTouchEvent(ev);
        return shouldIntercept && moveFlag;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            mDragHelper.processTouchEvent(event);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return true;
        }

        @Override
        public int getViewHorizontalDragRange(View child) {
            // speed when auto drag
            return 256;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            animToDismiss(releasedChild, xvel, yvel);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return top;
        }
    }

    private void animToDismiss(View releasedChild, float xvel, float yvel) {
        int finalX = initCenterViewX;
        int finalY = initCenterViewY;
        int flyType = -1;

        int dx = releasedChild.getLeft() - initCenterViewX;
        int dy = releasedChild.getTop() - initCenterViewY;
        if (dx == 0) {
            dx = 1;
        }
        if (xvel > X_VEL_THRESHOLD || dx > X_DISTANCE_THRESHOLD) {
            finalX = mWidth;
            finalY = dy * (childWidth + initCenterViewX) / dx + initCenterViewY;
            flyType = VANISH_TYPE_RIGHT;
        } else if (xvel < -X_VEL_THRESHOLD || dx < -X_DISTANCE_THRESHOLD) {
            finalX = -childWidth;
            finalY = dy * (childWidth + initCenterViewX) / (-dx) + dy
                    + initCenterViewY;
            flyType = VANISH_TYPE_LEFT;
        }
        if (BuildConfig.DEBUG)
            Log.e("SwipeDialogIfDebug", "flyType="+flyType);
        if (finalY > mHeight) {
            finalY = mHeight;
        } else if (finalY < -mHeight / 2) {
            finalY = -mHeight / 2;
        }

        if (mDragHelper.smoothSlideViewTo(releasedChild, finalX, finalY)) {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    @Override
    public void computeScroll() {
        if (mDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(this);
        } else {
            synchronized (this) {
                if (mDragHelper.getViewDragState() == ViewDragHelper.STATE_IDLE) {
                    if (BuildConfig.DEBUG)
                        Log.e("SwipeDialogIfDebug","dismiss");
                    if (getChildAt(0).getLeft() != initCenterViewX
                            && getChildAt(0).getTop() != initCenterViewY && null != mDismissListener) {
                        mDismissListener.onDismiss();
                    }
                    if (getChildAt(0).getLeft() == initCenterViewX
                            && getChildAt(0).getTop() == initCenterViewY && null != mDismissListener) {
                        mDismissListener.onResume();
                    }
                }
            }
        }
    }

    public void setOnDismissListener(DismissListener listener){
        mDismissListener = listener;
    }

    class MoveDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float dx, float dy) {
            return Math.abs(dy) + Math.abs(dx) > 5;
        }
    }

    public interface DismissListener {
        void onDismiss();
        void onResume();
    }
}
