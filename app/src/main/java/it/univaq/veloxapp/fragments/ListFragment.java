package it.univaq.veloxapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import it.univaq.veloxapp.R;
import it.univaq.veloxapp.utility.OnRequestListener;
import it.univaq.veloxapp.utility.Requests;

public class ListFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    private void download(){
        Requests.asyncRequest(new OnRequestListener() {
            @Override

            public void onRequestCompleted(byte[] data) {

                if (data == null)return;
                String result = new String(data);
                System.out.println(result);

            }

            @Override
            public void onRequestUpdate(int progress) {

            }
        });
    }


}
