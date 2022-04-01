package it.univaq.veloxapp.utility;

public interface OnRequestListener { //callback implementata all'interno thread della classe request

    //metodo per i dati completi
    void onRequestCompleted(byte[]data);

    //metodo feedback per download se file troppo grande
    void onRequestUpdate(int progress);
}
