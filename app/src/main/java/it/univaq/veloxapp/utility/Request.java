package it.univaq.veloxapp.utility;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

//Classe per richieste http
public class Request {

    //funzione pubblica: richieste internet in modo asincrono->serve un thread
    public static void asyncRequest(OnRequestListener listener){  //non ritorna nulla perchè dobbiamo aspettare la fine dell'azione dello scaricamento dei dati ->callback onRequest

        new Thread(() -> {

            String data = doRequest("GET", "http://www.datiopen.it/export/json/Mappa-degli-autovelox-in-italia.json", listener);
            if(listener != null){
                listener.onRequestCompleted(data);
            }
        }).start();
    }

    //Metodo per realizzare la connessione
    private static String doRequest(String method, String address, OnRequestListener listener) {
        HttpURLConnection connection = null; // inizializzata a null per poter essere utilizzata nel finally
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);

            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                //verifichiamo la dimensione del download contenuta nell'header della risposta HTTP
                InputStream in = connection.getInputStream();

                StringBuilder sb  =new StringBuilder();
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                String line = "";
                while((line = reader.readLine()) != null) {
                    sb.append(line);
                }
                return sb.toString();


            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }
        return null; //se qualcosa va male, non restituisce nulla
    }

}
