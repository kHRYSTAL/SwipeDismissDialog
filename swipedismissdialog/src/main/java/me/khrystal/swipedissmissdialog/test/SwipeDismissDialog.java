package me.khrystal.swipedissmissdialog.test;

import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import me.khrystal.swipedissmissdialog.widget.SwipeContainer;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/13
 * update time:
 * email: 723526676@qq.com
 */
public class SwipeDismissDialog extends DialogFragment {

    private boolean mSwipeable = true;
    private boolean mSwipeLayoutGenerated = false;

    public void setSwipeable(boolean swipeable) {
        mSwipeable = swipeable;
    }

    public boolean isSwipeable() {
        return mSwipeable;
    }

    @Override
    public void onStart() {
        super.onStart();

        if (!mSwipeLayoutGenerated && getShowsDialog()) {
            Window window = getDialog().getWindow();
            ViewGroup decorView = (ViewGroup)window.getDecorView();

            View content = decorView.getChildAt(0);
            decorView.removeView(content);


            final SwipeContainer container = new SwipeContainer(getActivity());
            container.addView(content);
            decorView.addView(container);

            container.setOnDismissListener(new SwipeContainer.DismissListener() {
                @Override
                public void onDismiss() {
                    container.post(new Runnable() {
                        @Override
                        public void run() {
                            SwipeDismissDialog.this.dismiss();
                        }
                    });
                }

                @Override
                public void onResume() {

                }
            });

            mSwipeLayoutGenerated = true;
        }
    }
}
