package serveur.listener;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.listener.DataListener;

public class ShareListener implements DataListener<String> {
    @Override
    public void onData(SocketIOClient client, String data, AckRequest ackSender) throws Exception {

    }
}
