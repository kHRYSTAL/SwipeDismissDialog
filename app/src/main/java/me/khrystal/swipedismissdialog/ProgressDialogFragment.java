package me.khrystal.swipedismissdialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import me.khrystal.swipedissmissdialog.test.SwipeDismissDialog;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/14
 * update time:
 * email: 723526676@qq.com
 */
public class ProgressDialogFragment extends SwipeDismissDialog {
    public static ProgressDialogFragment show(AppCompatActivity activity, String title) {
        ProgressDialogFragment newFragment = ProgressDialogFragment.newInstance(title);
        // newFragment.show(activity.getSupportFragmentManager(), "progressDialog");
        FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
        ft.add(newFragment, "progressDialog");
        ft.commitAllowingStateLoss();
        return newFragment;

    }

    public static ProgressDialogFragment show(Fragment fragment, String title) {
        ProgressDialogFragment newFragment = ProgressDialogFragment.newInstance(title);
        //newFragment.show(fragment.getChildFragmentManager(), "progressDialog");
        FragmentTransaction ft = fragment.getChildFragmentManager().beginTransaction();
        ft.add(newFragment,  "progressDialog");
        ft.commitAllowingStateLoss();
        return newFragment;
    }

    public static ProgressDialogFragment newInstance(String title) {
        ProgressDialogFragment frag = new ProgressDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        String title = getArguments().getString("title");

        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(title);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return dialog;
    }
}
