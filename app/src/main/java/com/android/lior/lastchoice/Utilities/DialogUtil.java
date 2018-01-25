package com.android.lior.lastchoice.Utilities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Lior on 1/12/2018.
 */

@SuppressWarnings("DefaultFileTemplate")
public class DialogUtil {

   public static void createDialog(Context context, String alertMessage, String confirmMessage) {
       {
           final AlertDialog alertDialog = new AlertDialog.Builder(context).create();


           alertDialog.setMessage(alertMessage);

           alertDialog.setButton(confirmMessage, new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {
                   alertDialog.dismiss();
               }
           });
           alertDialog.show();
       }
   }








}
