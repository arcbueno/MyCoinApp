package com.mycoin.augustobueno.mycoinapp.SuportClasses;

import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mycoin.augustobueno.mycoinapp.SuportClasses.Builder.Names;
import com.mycoin.augustobueno.mycoinapp.SuportClasses.Builder.Views;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MyAsyncTask extends AsyncTask {


//    private String finalURL;
//    private StringBuilder sb;
//    private String method;
//
//
//    private TextView view;
//    private ProgressBar bar;


    private Names names;
    private Views views;

    private static MyAsyncTask instance;


    public MyAsyncTask(Names names, Views views) {
        this.names = names;
        this.views = views;

    }

    public static synchronized MyAsyncTask getInstance(Names names, Views views){
        if(instance == null){
            instance = new MyAsyncTask(names, views);
        }
        return instance;
    }
    @Override
    protected void onPostExecute(Object o) {
        Log.v("Aqui", "Post execute");

        views.getBar().setVisibility(View.GONE);
        views.getView().setVisibility(View.VISIBLE);
        views.getView().setText(names.getSb().toString());


        super.onPostExecute(o);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        if (names.getMethod().equals("trades")){
            executarHist();
        }
        else if(names.getMethod().equals("book")){
            executarLivro();

        } if(names.getMethod().equals("ticker")){
            executarTick();
        }
        else{

        }

        return null;
    }

    public void executarHist() {

        StringBuilder sb = names.getSb();

        HttpsURLConnection myConnection = null;
        try {


            URL githubEndpoint = new URL(names.getFinalURL());
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

            }

            myConnection.disconnect();

            Log.v("Prox", "BBBBBBBBBBBBBBBBBBBBBBBB");


            return;


        } catch(IOException e) {
            e.printStackTrace();
        }


    }

    public void executarTick() {

        try {


            URL githubEndpoint = new URL(names.getFinalURL());
            HttpsURLConnection myConnection =
                    (HttpsURLConnection) githubEndpoint.openConnection();
            myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");

            if (myConnection.getResponseCode() == 200) {

                InputStream responseBody = myConnection.getInputStream();
                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, "UTF-8");

                JsonReader jsonReader = new JsonReader(responseBodyReader);

                jsonReader.beginObject(); // Start processing the JSON object


                String key;
                try {
                    key = jsonReader.nextName();
                } catch (IllegalStateException e) {
                    key = jsonReader.nextString();
                }

                names.getSb().append("In the last 24 hours: \n");

                if (key.equals("ticker")) {

                    while (jsonReader.hasNext()) { // Loop through all keys

                        JsonToken ke = jsonReader.peek();
                        Log.v("ticker", ke.toString());

                        ke = jsonReader.peek();
                        if (ke.toString().equals("BEGIN_OBJECT")) {
                            jsonReader.beginObject();
                        }
//                        }else if(ke.toString().equals("END_DOCUMENT")){
//                            jsonReader.close();
//                            result = false;
//                            continue;
////                                            ke = jsonReader.peek();
////                                            Log.v("Prox", ke.toString()+"dtree");
//                        }

                        String name;
                        String number;
                        try {
                            name = jsonReader.nextName();
                            number = jsonReader.nextString();
                        } catch (IllegalStateException e) {
                            name = jsonReader.nextString();
                            number = jsonReader.nextName();
                        }

//                                if(name == "high"){
//                                    name = "Maior preco de negociacao";
//                                }else if(name == "low"){
//                                    name = "Menor preco de negociacao";
//                                }else if(name == "vol"){
//                                    name = "Qtd de negociacoes em 24h";
//                                }else if(name == "last"){
//                                    name = "Preco da ultima negociacao";
//                                }else if(name == "buy"){
//                                    name = "Maior preco de oferta de compra";
//                                }else if(name == "sell"){
//                                    name = "Menor preco oferta de venda";
//                                }else if(name == "date") {
//                                    name = "Data e horario em Era Unix";
//                                }

                        names.getSb().append(name + "  -  " + number + " \n");


                        Log.v("teoricamente", names.getSb().toString());

                        ke = jsonReader.peek();
                        Log.v("Prox", ke.toString());
                        if (ke.toString().equals("END_OBJECT")) {
                            jsonReader.close();

                            Log.v("Prox", "AAAAAAAAAAAAAAAAAAAA");
                            break;


//                            jsonReader.endObject();
//                            ke = jsonReader.peek();
//                            Log.v("Prox", ke.toString()+"umd");
//                            if(ke.toString().equals("END_OBJECT")){
//                                jsonReader.endObject();
//                                ke = jsonReader.peek();
//                                Log.v("Prox", ke.toString()+"doois");
//                                ke = jsonReader.peek();
//                                Log.v("Prox", ke.toString()+"umd");
//
//                            }
                        }


                    }
                }

            }


            myConnection.disconnect();

            Log.v("Prox", "BBBBBBBBBBBBBBBBBBBBBBBB");


            return;


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void executarLivro() {

        HttpsURLConnection myConnection = null;
        try {


            URL githubEndpoint = new URL(names.getFinalURL());
            myConnection = (HttpsURLConnection) githubEndpoint.openConnection();
            myConnection.setRequestProperty("User-Agent", "my-rest-app-v0.1");

            if (myConnection.getResponseCode() == 200) {

                InputStream responseBody = myConnection.getInputStream();
                InputStreamReader responseBodyReader =
                        new InputStreamReader(responseBody, "UTF-8");

                JsonReader jsonReader = new JsonReader(responseBodyReader);

                jsonReader.beginObject(); // Start processing the JSON object



                for (int j = 0; j <= 1; j++){

                    String key;
                    try {
                        key = jsonReader.nextName();
                    } catch (IllegalStateException e) {
                        key = jsonReader.nextString();
                    }


                    JsonToken ke;

                    if (key.equals("asks")) {
                        Log.v("asks", key.toString());
                        names.getSb().append("OFERTA DE VENDA, CRESCENTE \n");
                    } else if(key.equals("bids")){
                        Log.v("bids", key.toString());
                        names.getSb().append("OFERTA DE COMPRA, CRESCENTE \n");
                    }
                    ke = jsonReader.peek();

                    if (ke.toString().equals("BEGIN_ARRAY")) {
                        jsonReader.beginArray();
                        //sb.append("Ofertas, do menor pra maior");
                        for (int i = 0; i <= 999; i++) {

                            if(i<=10){
                                jsonReader.beginArray();
                                names.getSb().append("Offer price: "+jsonReader.nextDouble() + "\n");
                                names.getSb().append("Total offer: "+jsonReader.nextDouble() + " \n");
                                jsonReader.endArray();

                            } else{
                                try{
                                    jsonReader.beginArray();
                                } catch (IllegalStateException e){
                                    break;
                                }
                                jsonReader.nextDouble();
                                jsonReader.nextDouble();
                                jsonReader.endArray();
                                Log.v("LOOP", String.valueOf(i));
                            }

                        }
                        Log.v("LOOP23", String.valueOf(j));
                        jsonReader.endArray();


                        Log.v("AQUI", "UM   FUCKING   ARRAY");

                        names.getSb().append("\n \n");

                    }

                }
                jsonReader.endObject();
                jsonReader.close();
            }




            myConnection.disconnect();

            Log.v("Prox", "BBBBBBBBBBBBBBBBBBBBBBBB");


            return;


        } catch(IOException e) {
            e.printStackTrace();
        }


    }









}
