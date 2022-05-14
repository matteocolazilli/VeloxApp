package it.univaq.veloxapp.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import it.univaq.veloxapp.R;

public class NoGpsFragment extends DialogFragment {
    public static String TAG = "NoGpsFragment";
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        Dialog dialog =  new  AlertDialog.Builder(requireActivity()).setMessage(R.string.no_gps_alert_message)
                .setCancelable(false)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        requireContext().startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        requireActivity().onBackPressed();
                        Toast.makeText(requireContext(), R.string.disabled_position_toast, Toast.LENGTH_SHORT).show();
                    }
                }).create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }
}
