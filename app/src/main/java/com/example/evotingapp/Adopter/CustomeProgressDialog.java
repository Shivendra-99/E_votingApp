package com.example.evotingapp.Adopter;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import com.example.evotingapp.R;


public class CustomeProgressDialog extends Dialog {
    public CustomeProgressDialog(@NonNull Context context) {
        super(context);
        WindowManager.LayoutParams params=getWindow().getAttributes();
        params.gravity= Gravity.CENTER_HORIZONTAL;
        getWindow().setAttributes(params);
        setTitle(null);
        setCancelable(false);
        setOnCancelListener(null);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        View view= LayoutInflater.from(context).inflate(R.layout.progressdialog,null);
        setContentView(view);
    }
}
