package me.khrystal.swipedissmissdialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import me.khrystal.swipedissmissdialog.widget.SwipeContainer;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/14
 * update time:
 * email: 723526676@qq.com
 */
public class SwipeDismissPop extends PopupWindow {

    private Activity mContext;
    private int mWidth;
    private int mHeight;
    private SwipeContainer rootView;
    private View contentView;

    public SwipeDismissPop(Activity context) {

        mContext = context;

        Rect frame = new Rect();
        mContext.getWindow().getDecorView()
                .getWindowVisibleDisplayFrame(frame);
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);
        rootView = (SwipeContainer) LayoutInflater.from(mContext).inflate(R.layout.dialog_root, null);
        rootView.setOnDismissListener(new SwipeContainer.DismissListener() {
            @Override
            public void onDismiss() {
                dismiss();
            }

            @Override
            public void onResume() {

            }
        });
    }

    public View inflateContent(int resId) {
        contentView = LayoutInflater.from(mContext).inflate(resId,rootView,true);
        return contentView;
    }

    public void show(View anchor) {
        if (null == rootView)
            throw new NullPointerException("contentView must be not null!");
        showAtLocation(anchor, Gravity.BOTTOM, 0, 0);
    }


}
