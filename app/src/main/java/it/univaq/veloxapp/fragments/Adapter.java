package it.univaq.veloxapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import it.univaq.veloxapp.R;
import it.univaq.veloxapp.model.Autovelox;

public class Adapter extends  RecyclerView.Adapter<Adapter.ViewHolder>{

    private final List<Autovelox> data;

    public Adapter ( List<Autovelox> data){
        this.data = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_autovelox,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView title;

        private final TextView subtitle;

        public ViewHolder (View view) {
            super(view);

            title = view.findViewById(R.id.textTitle);
            subtitle = view.findViewById(R.id.textSubtitle);
            view.findViewById(R.id.layoutRoot).setOnClickListener(this);
        }

        public void onBind(Autovelox autovelox) {

            title.setText(autovelox.getAddress());
            String info = String.format("%s ($s), %s",
                    autovelox.getMunicipality(),
                    autovelox.getProvince(),
                    autovelox.getRegion());
            subtitle.setText(info);
        }

        @Override
        public void onClick(View v) { //autovelox deve essere serializabile->dentro Autovelox implementa interfaccia serializeble

            Autovelox autovelox = data.get(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable("autovelox", autovelox);
            Navigation.findNavController(v).navigate(R.id.action_navList_to_detailActivity, bundle);
        }
    }
}
