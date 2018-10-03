package com.mycoin.augustobueno.mycoinapp.Activities.ListActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mycoin.augustobueno.mycoinapp.SuportClasses.Builder.Names;
import com.mycoin.augustobueno.mycoinapp.SuportClasses.Builder.Views;
import com.mycoin.augustobueno.mycoinapp.SuportClasses.MyAsyncTask;
import com.mycoin.augustobueno.mycoinapp.R;

public class Historico extends AppCompatActivity {

    private TextView view;
    private String baseURL = "https://www.mercadobitcoin.net/api/";
    private String hist = "/trades/";
    private String finalURL;
    StringBuilder sb ;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);

        view = (TextView) findViewById(R.id.textView);

        view.setVisibility(View.GONE);

        bar = findViewById(R.id.progressBar);
        bar.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            String getName = (String) bd.get("moeda");
            finalURL = baseURL+getName+hist;
            view.setText(finalURL);
        }


        Names names = new Names.Builder().finalURL(finalURL).method("trades").sb(sb).build();
        Views views = new Views.Builder().bar(bar).view(view).build();


        final MyAsyncTask at = MyAsyncTask.getInstance(names, views);


        at.execute();





    }


}
