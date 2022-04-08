package it.univaq.veloxapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import it.univaq.veloxapp.R;
import it.univaq.veloxapp.database.DB;
import it.univaq.veloxapp.model.Autovelox;
import it.univaq.veloxapp.utility.OnRequestListener;
import it.univaq.veloxapp.utility.Pref;
import it.univaq.veloxapp.utility.Request;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private List<Autovelox> data = new ArrayList<>();
    private Adapter adapter = new Adapter(data);


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);

        if (Pref.load(requireContext(),"firstStart", true)){
            download();
        } else {
            load();
        }

    }

    //per recuperare i dati negli avvii successivi al primo download
    private void load(){
        new Thread(()->{
            data.addAll(DB.getInstance(requireContext()).autoveloxDao().findAll());
            recyclerView.post(()->adapter.notifyDataSetChanged());
        }).start();
    }

    private void download(){
        Request.asyncRequest(new OnRequestListener() {
            @Override

            public void onRequestCompleted(byte[] data) {

                List<Autovelox> autoveloxList = new ArrayList<>() ;
                if (data == null) return;

                try {
                    JSONArray autoveloxArray = new JSONArray(data);
                    for (int i = 0; i < autoveloxArray.length() ; i++) {
                        Autovelox autovelox = Autovelox.parseData(getContext(),autoveloxArray.getJSONObject(i));
                        if (autovelox != null) autoveloxList.add(autovelox);
                    }
                }
                catch (JSONException e) {
                    e.printStackTrace();
                }

                ListFragment.this.data.addAll(autoveloxList);
                recyclerView.post(()->adapter.notifyDataSetChanged()); // per eseguire la notifica dell'aggiornamento dati sul thread principale

                DB.getInstance(requireContext()).autoveloxDao().save(autoveloxList);

                Pref.save(requireContext(), "firstStart", false);
            }

            @Override
            public void onRequestUpdate(int progress) {

            }
        });
    }



}
