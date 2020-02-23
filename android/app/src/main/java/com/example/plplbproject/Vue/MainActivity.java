package com.example.plplbproject.Vue;


import android.os.Bundle;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.example.plplbproject.R;
import com.example.plplbproject.model.MainModele;
import com.example.plplbproject.reseau.Connexion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;


import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import metier.UE;


public class MainActivity extends AppCompatActivity implements Vue {

    private ArrayList<UE> ueList;

    private ListView ueListView;
    private MainModele modele;

    private Connexion socket;
    private Socket mSocket;
    private final Gson gson = new GsonBuilder().create();

    /**
     * Set a listener on the server to receive messages
     *
    */
    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String receivedMessage = (String) args[0];

                    // add the message to view todo
                    //just show the message in a toast
                    Toast.makeText(getApplicationContext(),"Server sent you a message: "+ receivedMessage,Toast.LENGTH_LONG).show();
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        this.modele = new MainModele();
        socket= new Connexion(this,modele);
        socket.setup("192.168.1.46","10101");
        socket.connect();


        //###################### Adapt the list of Ue to display #####################

        //TODO: for now we only have one Ue to display, instancied here. In reality we need to get it from the server

        ueList = new ArrayList<UE>();
        ueList.add(new UE("Projet", "(6666)"));
        ueList.add(new UE("Algo", "(1234)"));

        ueListView = (ListView) findViewById(R.id.ueListView);

        UeDisplayAdapter ueDisplayAdapter = new UeDisplayAdapter(this, ueList);
        ueListView.setAdapter(ueDisplayAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void toastMessage(final String msg) {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String receivedMessage = msg;

                // add the message to view todo
                //just show the message in a toast
                Toast.makeText(getApplicationContext(),"Server sent you a message: "+ receivedMessage,Toast.LENGTH_LONG).show();
            }
        });

    }
}
