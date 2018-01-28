package com.ely.lastchoice.Utilities;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ely.lastchoice.R;

/**
 * Created by lior on 1/3/18.
 */

@SuppressWarnings("DefaultFileTemplate")
public class ToastUtil extends View{


    public ToastUtil(Context context) {
        super(context);
    }

    public static void createToast(String Message, Context context){


        LayoutInflater layoutInflater = ((Activity) context).getLayoutInflater();
        View layout = layoutInflater.inflate(R.layout.toast_layout,
                (ViewGroup) ((Activity) context).findViewById(R.id.toast_layout_root));
        TextView toastText = layout.findViewById(R.id.text);
        toastText.setText(Message);
        int duration = Toast.LENGTH_SHORT;
        Toast toast =new Toast(context);
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.setView(layout);

        toast.show();


    }
}
