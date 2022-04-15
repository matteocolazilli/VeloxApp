package it.univaq.veloxapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.univaq.veloxapp.model.Autovelox;

public class DetailActivity extends AppCompatActivity {

    private Autovelox autovelox;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        try {
            autovelox = (Autovelox) getIntent().getSerializableExtra("autovelox");
            setupTextView();
        }catch (Exception e) {
            e.printStackTrace();
            onBackPressed(); //se c'è un errore torna alla pagina precedente
        }
    }

    //set up textView che richiamiamo nel try di OnCreate se non ci sono stati problemi
    private void setupTextView() {

        ((TextView) findViewById(R.id.textTitle)).setText(autovelox.getAddress());
        ((TextView) findViewById(R.id.textMunicipality)).setText(autovelox.getMunicipality());
        ((TextView) findViewById(R.id.textProvince)).setText(autovelox.getProvince());
        ((TextView) findViewById(R.id.textRegion)).setText(autovelox.getRegion());
        ((TextView) findViewById(R.id.textInsertionData)).setText(getString(R.string.insertion_date).concat(": ").concat(convertTimestampToDate(autovelox.getInsertionDate())));
    }

    private String convertTimestampToDate (long timestamp) {

        if (timestamp == -1) return "No date";

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        return format.format(new Date(timestamp));
    }
}
