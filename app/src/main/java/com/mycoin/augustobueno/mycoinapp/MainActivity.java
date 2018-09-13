package com.mycoin.augustobueno.mycoinapp;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private String escolha = "LTC";
    private Intent tent;
    private FloatingActionButton fab;
    private FloatingActionButton item_um;
    private FloatingActionButton item_dois;
    private boolean fabExpanded = false;

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

        fab = findViewById(R.id.fab);
        item_dois = findViewById(R.id.item_dois);
        item_um = findViewById(R.id.item_um);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

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
                //mandar pra webview mercado bitcoin
            }
        });
        item_dois.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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


}
//
//public class YourFragment extends Fragment  {
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//    }
//
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        View v = inflater.inflate(R.layout.fragment_home, null, false);
//        getActivity().setTitle(getResources().getString(R.string.app_name));
//
//        v.findViewById(R.id.pink_icon).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//
//        FloatingActionButton button = (FloatingActionButton) v.findViewById(R.id.setter);
//        button.setSize(FloatingActionButton.SIZE_MINI);
//        button.setColorNormalResId(R.color.pink);
//        button.setColorPressedResId(R.color.pink_pressed);
//        button.setIcon(R.drawable.bubble_in);
//        button.setStrokeVisible(false);
//
//        final View actionB = v.findViewById(R.id.action_b);
//
//        FloatingActionButton actionC = new FloatingActionButton(baseActivity);
//        actionC.setTitle("Hide/Show Action above");
//        actionC.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                actionB.setVisibility(actionB.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
//            }
//        });
//
//        final FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) v.findViewById(R.id.multiple_actions);
//        menuMultipleActions.addButton(actionC);
//
//        final FloatingActionButton removeAction = (FloatingActionButton) v.findViewById(R.id.button_remove);
//        removeAction.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                ((FloatingActionsMenu) v.findViewById(R.id.multiple_actions_down)).removeButton(removeAction);
//            }
//        });
//
//        ShapeDrawable drawable = new ShapeDrawable(new OvalShape());
//        drawable.getPaint().setColor(getResources().getColor(R.color.white));
//        ((FloatingActionButton) v.findViewById(R.id.setter_drawable)).setIconDrawable(drawable);
//
//        final FloatingActionButton actionA = (FloatingActionButton) v.findViewById(R.id.action_a);
//        actionA.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                actionA.setTitle("Action A clicked");
//            }
//        });
//
//        // Test that FAMs containing FABs with visibility GONE do not cause crashes
//        v.findViewById(R.id.button_gone).setVisibility(View.GONE);
//
//        final FloatingActionButton actionEnable = (FloatingActionButton) v.findViewById(R.id.action_enable);
//        actionEnable.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                menuMultipleActions.setEnabled(!menuMultipleActions.isEnabled());
//            }
//        });
//
//        FloatingActionsMenu rightLabels = (FloatingActionsMenu) v.findViewById(R.id.right_labels);
//        FloatingActionButton addedOnce = new FloatingActionButton(baseActivity);
//        addedOnce.setTitle("Added once");
//        rightLabels.addButton(addedOnce);
//
//        FloatingActionButton addedTwice = new FloatingActionButton(baseActivity);
//        addedTwice.setTitle("Added twice");
//        rightLabels.addButton(addedTwice);
//        rightLabels.removeButton(addedTwice);
//        rightLabels.addButton(addedTwice);
//
//
//        return v;
//
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//    }
//
//}
//
