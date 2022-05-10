package it.univaq.veloxapp.fragments;


import android.content.Context;
import android.view.LayoutInflater;

import android.app.Activity;
import android.app.AlertDialog;

import it.univaq.veloxapp.R;

public class DialogProgress extends AlertDialog {

    private AlertDialog dialog;
    private Context context;
    private Activity activity;
    private AlertDialog.Builder builder;

    protected DialogProgress(Context context, Activity activity) {
        super(context);
        this.context = context;
        this.activity = activity;
    }

    public void showDialog(){
        this.builder = new Builder(this.context);
        builder.setCancelable(false);
        LayoutInflater inflater = this.activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_progress, null));
        this.dialog = builder.create();
        dialog.show();
    }

    void dismissBar(){
        dialog.dismiss();
    }
}
