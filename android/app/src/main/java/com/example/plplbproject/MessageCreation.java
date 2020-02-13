package com.example.plplbproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

import com.github.nkzawa.emitter.Emitter;

import org.json.JSONException;
import org.json.JSONObject;


public class MessageCreation extends AppCompatActivity {

    public static final String AUTOCONNECT = "AUTOCONNECT";

    private EditText textView;
    private Button validate;

    private Socket mSocket;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_creation);

        textView = findViewById(R.id.plain_text_input);
        validate = findViewById(R.id.Validate);
        validate.setText("Send");


        try {
            mSocket = IO.socket("http://10.0.2.2:10101");
            System.out.println("########### SOCKET CONNECTED ############");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        mSocket.connect();

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
                mSocket.emit("new message", message);
                Toast.makeText(getApplicationContext(),"Message sent",Toast.LENGTH_LONG).show();
            }
        });


        /**
         * Set a listener on the server to receive messages
         */

        Emitter.Listener onNewMessage = new Emitter.Listener() {
            @Override
            public void call(final Object... args) {
                MessageCreation.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        JSONObject data = (JSONObject) args[0];
                        String username;
                        String message;
                        try {
                            username = data.getString("username");
                            message = data.getString("message");
                        } catch (JSONException e) {
                            return;
                        }

                        // add the message to view
                        Toast.makeText(getApplicationContext(),username +" sent you a message: "+ message,Toast.LENGTH_LONG).show();
                    }
                });
            }
        };

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
