package com.example.plplbproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

import java.net.MalformedURLException;
import java.net.URISyntaxException;


import org.json.JSONException;
import org.json.JSONObject;


public class MessageCreation extends AppCompatActivity {

    public static final String AUTOCONNECT = "AUTOCONNECT";

    private EditText textView;
    private Button validate;

    private Socket mSocket;

    /**
     * Set a listener on the server to receive messages
     */

    Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            MessageCreation.this.runOnUiThread(new Runnable() {
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
        setContentView(R.layout.activity_message_creation);

        textView = findViewById(R.id.plain_text_input);
        validate = findViewById(R.id.Validate);
        validate.setText("Send");

        /**
         * Send a message to the server
         */
        validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = textView.getText().toString().trim();
                if (TextUtils.isEmpty(message)) {
                    return;
                }
                textView.setText("");
                //mSocket.emit("new message", message);
                Toast.makeText(getApplicationContext(),"Message sent",Toast.LENGTH_LONG).show();
            }
        });


        try {
            mSocket = IO.socket("http://10.0.2.2:10111");
            System.out.println("MSOCKET: " + mSocket);
            System.out.println("########### SOCKET CONNECTED ############");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.on(Socket.EVENT_CONNECT, new Emitter.Listener() {
            @Override
            public void call(Object... args) {
                mSocket.emit("clientMessage", "TEST");
            }})
        .on("serverMessage",onNewMessage);


        mSocket.connect();

    }

    @Override
    protected void onPause() {
        super.onPause();

        //if (connexion != null) connexion.disconnect();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
