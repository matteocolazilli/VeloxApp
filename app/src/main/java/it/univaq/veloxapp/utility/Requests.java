package it.univaq.veloxapp.utility;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

//Classe per richieste http
public class Requests {

    //funzione pubblica: richieste internet in modo asincrono->serve un thread
    public static void asyncRequest(OnRequestListener listener){  //non ritorna nulla perchè dobbiamo aspettare la fine dell'azione dello scaricamento dei dati ->callback onRequest

        new Thread(() -> {

            byte[] data = doRequest("GET", "http://www.datiopen.it/export/json/Mappa-degli-autovelox-in-italia.json", listener);
            if(listener != null){
                listener.onRequestCompleted(data);
            }
        }).start();
    }

    //Metodo per realizzare la connessione
    private static byte[] doRequest(String method, String address, OnRequestListener listener) {
        HttpURLConnection connection = null; // inizializzata a null per poter essere utilizzata nel finally
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //verifichiamo la dimensione del download contenuta nell'header della risposta HTTP
                InputStream in = connection.getInputStream();
                int length = Integer.parseInt(connection.getHeaderField("Content-Length"));


                byte[] data = new byte[length]; // dati scaricati
                byte[] buffer = new byte[1024]; // buffer temporaneo per elaborare i dati un po' alla volta
                int read, counter = 0;

                while ((read = in.read(buffer)) != -1) {
                    System.arraycopy(buffer, 0, data, counter, read);
                    counter += read;

                    int percentage = counter * 100 / length;
                    if (listener != null) {
                        listener.onRequestUpdate(percentage);
                    }
                }
                return data;


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }
        return null; //se qualcosa va male, non restituisce nulla
    }

}
