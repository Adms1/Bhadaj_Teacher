package anandniketan.com.anbcteacher.Utility;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import org.jetbrains.annotations.Nullable;

import anandniketan.com.anbcteacher.R;

public class DialogUtils {


    private static Dialog dialog;


    public static void showGIFDialog(Context context, @NonNull String username){
        dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_birthday_wish);
        GifView gifView = (GifView)dialog.findViewById(R.id.gifview);

        Button btnClose = (Button)dialog.findViewById(R.id.btn_close);
        gifView.setImageResource(R.drawable.gif_brthday);

        TextView userName = (TextView)dialog.findViewById(R.id.tv_username);

        userName.setText(username);

        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

}
