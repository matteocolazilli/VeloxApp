package it.univaq.veloxapp.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import it.univaq.veloxapp.R;

public class DialogProgress extends DialogFragment {

    private TextView textView;
    private ProgressBar progressBar;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        setCancelable(false);

        Dialog dialog = new Dialog(requireActivity());
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_progress);

        progressBar = dialog.findViewById(R.id.progressBar);
        textView = dialog.findViewById(R.id.textText);
        return dialog;
    }

    public void updateProgress (int progress) {

        if (progressBar != null) {
            progressBar.setProgress(progress);
            String text = progress + "%" ;
            textView.setText(text);
        }
    }
}
