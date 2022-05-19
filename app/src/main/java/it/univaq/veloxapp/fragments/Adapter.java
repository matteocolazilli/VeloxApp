package it.univaq.veloxapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import it.univaq.veloxapp.R;
import it.univaq.veloxapp.model.Autovelox;

public class Adapter extends  RecyclerView.Adapter<Adapter.ViewHolder> implements Filterable{

    private final List<Autovelox> data;
    private List<Autovelox> autoveloxListFiltered;

    public Adapter ( List<Autovelox> data){
        this.data = data;
        this.autoveloxListFiltered = data;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    autoveloxListFiltered = data;
                } else {
                    List<Autovelox> filteredList = new ArrayList<>();
                    for (Autovelox autovelox : data) {
                        if (autovelox.getMunicipality().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(autovelox);
                        }
                    }
                    autoveloxListFiltered = filteredList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = autoveloxListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                autoveloxListFiltered = (ArrayList<Autovelox>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_autovelox,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBind(autoveloxListFiltered.get(position));
    }

    @Override
    public int getItemCount() {
        return autoveloxListFiltered != null ? autoveloxListFiltered.size() : 0;
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
            String address = (autovelox.getAddress()).split(",")[0];
            title.setText(address);

            String info = String.format("%s (%s), %s",
                    autovelox.getMunicipality(),
                    autovelox.getProvince(),
                    autovelox.getRegion());
            subtitle.setText(info);
        }

        @Override
        public void onClick(View v) { //autovelox deve essere serializabile->dentro Autovelox implementa interfaccia serializeble

            Autovelox autovelox = autoveloxListFiltered.get(getAdapterPosition());
            Bundle bundle = new Bundle();
            bundle.putSerializable("autovelox", autovelox);
            Navigation.findNavController(v).navigate(R.id.action_navList_to_detailActivity, bundle);
        }
    }

}
