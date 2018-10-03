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

public class Livro extends AppCompatActivity {

    private TextView Textv;
    private String baseURL = "https://www.mercadobitcoin.net/api/";
    private String order = "/orderbook/";
    private String finalURL;
    private ProgressBar bar;

    public TextView view;

    StringBuilder sb = new StringBuilder();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livro);

        view = (TextView) findViewById(R.id.livro_view_id);

        view.setVisibility(View.GONE);

        bar = findViewById(R.id.bar_id);
        bar.setVisibility(View.VISIBLE);


        Intent intent = getIntent();
        Bundle bd = intent.getExtras();
        if (bd != null) {
            String getName = (String) bd.get("moeda");
            finalURL = baseURL+getName+order;
            view.setText(finalURL);


        }


        final StringBuilder sb = new StringBuilder();


        Names names = new Names.Builder()
                .finalURL(finalURL)
                .method("book")
                .sb(sb)
                .build();

        Views views = new Views.Builder()
                .bar(bar)
                .view(view)
                .build();


        final MyAsyncTask at = MyAsyncTask.getInstance(names, views);


        at.execute();






    }
}
