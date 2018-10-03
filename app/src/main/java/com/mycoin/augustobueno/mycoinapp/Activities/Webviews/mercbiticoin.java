package com.mycoin.augustobueno.mycoinapp.Activities.Webviews;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

import com.mycoin.augustobueno.mycoinapp.R;

public class mercbiticoin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mercbiticoin);

        WebView myWebView = (WebView) findViewById(R.id.webview_id);
        myWebView.loadUrl("https://www.mercadobitcoin.com.br/");
    }
}
