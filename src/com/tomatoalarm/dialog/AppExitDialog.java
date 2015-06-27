package com.tomatoalarm.dialog;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.tomatoalarm.R;

public class AppExitDialog extends AlertDialog {
    private Context context;

    public AppExitDialog(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_app_exit);

        this.findViewById(R.id.b_close_no).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AppExitDialog.this.dismiss();
                    }
                });

        this.findViewById(R.id.b_close_ok).setOnClickListener(
                new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        AppExitDialog.this.dismiss();
                        ((Activity) context).finish();
                    }
                });
    }

}