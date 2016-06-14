package me.khrystal.swipedismissdialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialog;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.Random;

import me.khrystal.swipedissmissdialog.SwipeDismissDialog;

/**
 * usage:
 * author: kHRYSTAL
 * create time: 16/6/14
 * update time:
 * email: 723526676@qq.com
 */
public class ExampleDialogFragment extends SwipeDismissDialog {

    private static final Random sRandom = new Random();

    private interface DialogBuilder {
        @NonNull
        Dialog create(Context context, ExampleDialogFragment fragment);
    }

    public enum Type implements DialogBuilder {
        APPCOMPAT() {
            @Override
            public
            @NonNull
            Dialog create(Context context, ExampleDialogFragment fragment) {
                return new android.support.v7.app.AlertDialog.Builder(context)
                        .setTitle("Title")
                        .setMessage("Message")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel", null)
                        .create();
            }
        },
        APPCOMPAT_LIST() {
            @Override
            public
            @NonNull
            Dialog create(Context context, ExampleDialogFragment fragment) {
                return new android.support.v7.app.AlertDialog.Builder(context)
                        .setTitle("Title")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(new String[]{
                                "Item 1",
                                "Item 2",
                                "Item 3",
                                "Item 4",
                                "Item 5",
                                "Item 6",
                                "Item 7",
                                "Item 8",
                                "Item 9",
                                "Item 10",
                        }, null)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel", null)
                        .create();
            }
        },
        DEFAULT() {
            @Override
            public
            @NonNull
            Dialog create(Context context, ExampleDialogFragment fragment) {
                return new AlertDialog.Builder(context)
                        .setTitle("Title")
                        .setMessage("Message")
                        .setIcon(R.mipmap.ic_launcher)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel", null)
                        .create();
            }
        },
        DEFAULT_LIST() {
            @Override
            public
            @NonNull
            Dialog create(Context context, ExampleDialogFragment fragment) {
                return new AlertDialog.Builder(context)
                        .setTitle("Title")
                        .setIcon(R.mipmap.ic_launcher)
                        .setItems(new String[]{
                                "Item 1",
                                "Item 2",
                                "Item 3",
                                "Item 4",
                                "Item 5",
                                "Item 6",
                                "Item 7",
                                "Item 8",
                                "Item 9",
                                "Item 10",
                        }, null)
                        .setPositiveButton("OK", null)
                        .setNegativeButton("Cancel", null)
                        .create();
            }
        },
        PROGRESS() {
            @Override
            public
            @NonNull
            Dialog create(Context context, ExampleDialogFragment fragment) {
                ProgressDialog dialog = new ProgressDialog(context);
                dialog.setTitle("Title");
                dialog.setMessage("Message");
                return dialog;
            }
        },
        DATE() {
            @Override
            public
            @NonNull
            Dialog create(Context context, ExampleDialogFragment fragment) {
                final Calendar c = Calendar.getInstance();
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);
                return new DatePickerDialog(context, null, year, month, day);
            }
        },
        TIME() {
            @Override
            public
            @NonNull
            Dialog create(Context context, ExampleDialogFragment fragment) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);
                return new TimePickerDialog(context, null, hour, minute, true);
            }
        },
    }

    public static ExampleDialogFragment newInstance(Type type) {
        ExampleDialogFragment f = new ExampleDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable("type", type);
        f.setArguments(args);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Type type = (Type) getArguments().getSerializable("type");
        return type.create(getActivity(), this);
    }
}
