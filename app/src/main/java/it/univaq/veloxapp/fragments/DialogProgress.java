package it.univaq.veloxapp.fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import it.univaq.veloxapp.R;

public class DialogProgress extends DialogFragment {
    public static String TAG = "DialogProgressFragment";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog dialog = new AlertDialog.Builder(requireContext())
                .setView(requireActivity().getLayoutInflater()
                        .inflate(R.layout.dialog_progress, null))
                .create();
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

}
