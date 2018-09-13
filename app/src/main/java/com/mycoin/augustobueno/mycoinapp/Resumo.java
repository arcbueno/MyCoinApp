package com.mycoin.augustobueno.mycoinapp;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.util.JsonToken;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;



public class Resumo extends AppCompatActivity {



    private String baseURL = "https://www.mercadobitcoin.net/api/";
    private String tick = "/ticker/";
    public String finalURL;

    public TextView view;

    StringBuilder sb = new StringBuilder();


    private static Resumo instancia;

    public Resumo() {

    }

    public static synchronized Resumo getInstance(){
        if (instancia == null){
            instancia = new Resumo();
        }
        return instancia;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resumo);





        view = (TextView) findViewById(R.id.texmex_view);



        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            String getName = (String) bd.get("moeda");
            finalURL = baseURL+getName+tick;
            view.setText(finalURL);

        }


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

                try {


                    URL githubEndpoint = new URL("https://www.mercadobitcoin.net/api/BTC/ticker/");
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

                        sb.append("In the last 24 hours: \n");

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

                                sb.append(name + "  -  " + number + " \n");


                                Log.v("teoricamente", sb.toString());

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
        }

        final MyAsyncTask at = new MyAsyncTask();

        at.execute();







        //view.setText(sb.toString());



    }




}

