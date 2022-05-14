package it.univaq.veloxapp.fragments;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import it.univaq.veloxapp.R;

public class DialogProgress extends DialogFragment {

    private final AlertDialog dialog;


    public DialogProgress(Context context, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_progress, null));
        this.dialog = builder.create();
        this.dialog.setCancelable(false);
        this.dialog.setCanceledOnTouchOutside(false);
    }

    public void showDialog(){
        dialog.show();
    }

    public void dismissBar(){
        dialog.dismiss();
    }
}
