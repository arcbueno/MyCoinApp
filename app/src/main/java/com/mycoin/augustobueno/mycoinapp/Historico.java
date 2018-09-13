package com.mycoin.augustobueno.mycoinapp;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Historico extends AppCompatActivity {

    private TextView view;
    private String baseURL = "https://www.mercadobitcoin.net/api/";
    private String hist = "/trades/";
    private String finalURL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        view = (TextView) findViewById(R.id.textView);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            String getName = (String) bd.get("moeda");
            finalURL = baseURL+getName+hist;
            view.setText(finalURL);
        }

        final StringBuilder sb = new StringBuilder();




        class MyAsyncTask extends AsyncTask<String, Void, String> {



            @Override
            protected String doInBackground(String... strings) {
                Log.v("Aqui", "Background Execute");

                executar();

                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                Log.v("Aqui", "Post execute");

                view.setText(sb.toString());

                super.onPostExecute(s);
            }

            public void executar() {

                HttpsURLConnection myConnection = null;
                try {


                    URL githubEndpoint = new URL(finalURL);
                    myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
                    myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");

                    if (myConnection.getResponseCode() == 200) {

                        InputStream responseBody = myConnection.getInputStream();
                        InputStreamReader responseBodyReader =
                                new InputStreamReader(responseBody, "UTF-8");

                        JsonReader jsonReader = new JsonReader(responseBodyReader);

                        jsonReader.beginArray();// Start processing the JSON object

                        JsonToken ke;
                        String key;
                        String name;
                        String type;

                        sb.append("LAST 10 TRADES \n\n");

                        for (int i = 0; i <= 10; i++){
                            jsonReader.beginObject();
                            for(int j = 0; j<=3; j++){
                                try{
                                    name = jsonReader.nextString();
                                }catch (IllegalStateException e){
                                    name = jsonReader.nextName();
                                }
                                sb.append(name + " - "+ jsonReader.nextDouble() + "\n");
                            }
                            try{
                                name = jsonReader.nextString();
                                type = jsonReader.nextName();
                            }catch (IllegalStateException e){
                                name = jsonReader.nextName();
                                type = jsonReader.nextString();
                            }
                            sb.append(name +" - " +type + "\n");

                            jsonReader.endObject();

                            sb.append("\n");

                        }

                        jsonReader.close();




//                        for (int j = 0; j <= 1; j++){
//
//                            String key;
//                            try {
//                                key = jsonReader.nextName();
//                            } catch (IllegalStateException e) {
//                                key = jsonReader.nextString();
//                            }
//
//
//                            JsonToken ke;
//
//                            if (key.equals("asks")) {
//                                Log.v("asks", key.toString());
//                                sb.append("OFERTA DE VENDA, CRESCENTE \n");
//                            } else if(key.equals("bids")){
//                                Log.v("bids", key.toString());
//                                sb.append("OFERTA DE COMPRA, CRESCENTE \n");
//                            }
//                            ke = jsonReader.peek();
//
//                            if (ke.toString().equals("BEGIN_ARRAY")) {
//                                jsonReader.beginArray();
//                                //sb.append("Ofertas, do menor pra maior");
//                                for (int i = 0; i <= 999; i++) {
//
//                                    if(i<=10){
//                                        jsonReader.beginArray();
//                                        sb.append("Preço da oferta: "+jsonReader.nextDouble() + "\n");
//                                        sb.append("Quantidade da oferta: "+jsonReader.nextDouble() + " \n");
//                                        jsonReader.endArray();
//
//                                    } else{
//                                        try{
//                                            jsonReader.beginArray();
//                                        } catch (IllegalStateException e){
//                                            break;
//                                        }
//                                        jsonReader.nextDouble();
//                                        jsonReader.nextDouble();
//                                        jsonReader.endArray();
//                                        Log.v("LOOP", String.valueOf(i));
//                                    }
//
//                                }
//                                Log.v("LOOP23", String.valueOf(j));
//                                jsonReader.endArray();
//
//
//                                Log.v("AQUI", "UM   FUCKING   ARRAY");
//
//                            }
//
//                        }

                    }




                    myConnection.disconnect();

                    Log.v("Prox", "BBBBBBBBBBBBBBBBBBBBBBBB");


                    return;


                } catch(IOException e) {
                    e.printStackTrace();
                }


            }
        }

        final MyAsyncTask at = new MyAsyncTask();

        at.execute();



    }
}
