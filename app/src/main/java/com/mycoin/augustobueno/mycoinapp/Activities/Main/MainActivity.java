package com.mycoin.augustobueno.mycoinapp.Activities.Main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.lang.UCharacter;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.mycoin.augustobueno.mycoinapp.Activities.ListActivities.Historico;
import com.mycoin.augustobueno.mycoinapp.Activities.ListActivities.Livro;
import com.mycoin.augustobueno.mycoinapp.Activities.ListActivities.Resumo;
import com.mycoin.augustobueno.mycoinapp.R;
import com.mycoin.augustobueno.mycoinapp.Activities.Webviews.brazillex;
import com.mycoin.augustobueno.mycoinapp.Activities.Webviews.mercbiticoin;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String escolha = "LTC";
    private Intent tent;
    private FloatingActionButton fab;
    private FloatingActionButton item_um;
    private FloatingActionButton item_dois;
    private boolean fabExpanded = false;
    private WifiManager manager;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    escolha = "LTC";
                    Toast.makeText(MainActivity.this, "You selected litecoin",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_dashboard:
//                    Intent tent = new Intent(MainActivity.this, bitcash_activity.class);
//                    startActivity(tent);
                    escolha = "BCH";
                    Toast.makeText(MainActivity.this, "You selected bitcoin cash",
                            Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.navigation_notifications:
//                    tent = new Intent(MainActivity.this, bitcoin.class);
//                    startActivity(tent);
                    escolha = "BTC";
                    Toast.makeText(MainActivity.this, "You selected bitcoin",
                            Toast.LENGTH_SHORT).show();
                    return true;
            }
            return false;
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);


        fab = findViewById(R.id.fab);
        item_dois = findViewById(R.id.item_dois);
        item_um = findViewById(R.id.item_um);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(fabExpanded == true){
                   closeSubMenusFab();
                   item_dois.hide();
                   item_um.hide();

                }
                else{
                   openSubMenusFab();
                   item_um.show();
                   item_dois.show();

                }

            }
        });

        item_um.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(MainActivity.this, mercbiticoin.class);
                startActivity(browserIntent);
                //mandar pra webview mercado bitcoin
            }
        });
        item_dois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(MainActivity.this, brazillex.class);
                startActivity(browserIntent);
                //mandar pro outro site
            }
        });



        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);









        String[] array = {"Operations Summary", "Orderbook","Last Trades"};

        lv = (ListView) findViewById(R.id.lista_id);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, array);

        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (manager.isWifiEnabled()){

                    Intent in;

                    switch (position){
                        case 0:
                            //Enviar pra Resumo
                            in = new Intent(MainActivity.this, Resumo.class);
                            in.putExtra("moeda", escolha);
                            startActivity(in);
                            break;
                        case 1:
                            in = new Intent(MainActivity.this, Livro.class);
                            in.putExtra("moeda", escolha);
                            startActivity(in);
                            //Mandar pra Livro
                            break;
                        case 2:
                            in = new Intent(MainActivity.this, Historico.class);
                            in.putExtra("moeda", escolha);
                            startActivity(in);
                            //Mandar pra Historico
                            break;
                    }
                }
                else{
                    Toast.makeText(MainActivity.this,"Liga a net pfv",
                            Toast.LENGTH_LONG).show();

                }
            }
        });





    }

    private Intent onClickNavView(int posicao){



        switch (posicao){
            case 0:

                tent = new Intent(MainActivity.this, Resumo.class);
                return tent;

            case 1:

                tent = new Intent(MainActivity.this, Livro.class);
                return tent;

            case 2:

                tent = new Intent(MainActivity.this, Historico.class);
                return tent;

        }


        tent = new Intent(MainActivity.this, MainActivity.class);
        return tent;
    }


    private void closeSubMenusFab(){

        fab.setImageResource(R.drawable.ic_monetization_on_black_24dp);
        fabExpanded = false;
    }

    private void openSubMenusFab(){

        fab.setImageResource(R.drawable.ic_close_black_24dp);
        fabExpanded = true;
    }


    private BroadcastReceiver wifiState = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,
                    WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    Toast.makeText(MainActivity.this, "Wifi is on",
                            Toast.LENGTH_SHORT).show();
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(MainActivity.this, "Wifi is off",
                            Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiState, filter);

    }

    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(wifiState);
    }
}