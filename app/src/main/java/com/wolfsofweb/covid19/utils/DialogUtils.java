package com.wolfsofweb.covid19.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Window;

import com.wolfsofweb.covid19.R;

public class DialogUtils {
    public static Dialog getLoadingDialog(Context context) {

        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_layout_loading);

       /* AppCompatImageView imgLoading = dialog.findViewById(R.id.img_loading);
        Glide.with(context)
                .load(R.drawable.ic_launcher_foreground)
                .into(imgLoading);*/
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        return dialog;
    }

}
