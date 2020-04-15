package serveur.connectionStruct;

import com.corundumstudio.socketio.SocketIOClient;
import metier.Student;

import java.util.ArrayList;

/**
 * Une structure de donner permetant de stocker les different
 * client qui sont connecter au serveur
 */
public class ClientSocketList extends ArrayList<Client> implements LinkClientSocket{



    /**
     * Permet de recuperer un Client
     * @param student La student que l'on veut trouver
     * @return le client voulu
     */
    @Override
    public Client getClient(Student student) {
        for (Client client : this)
            if (student.getNom().equals(client.getStudent().getNom()))
                return client;
        return null;
    }

    /**
     * Permet de recuperer un Client
     * @param sock La socket du client
     * @return Le client voulu
     */
    @Override
    public Client getClient(SocketIOClient sock) {
        for (Client client : this)
            if (sock.equals(client.getSock()))
                return client;
        return null;
    }

    /**
     * Permet de supprimer un Client
     * @param student La student que l'on veut supprimer
     * @return le client voulu
     */
    @Override
    public void removeClient(Student student) {
        for (int i =0; i<this.size(); i++)
            if (student.getNom().equals(this.get(i).getStudent().getNom())){
                this.remove(i);
            }
    }

    /**
     * Permet de supprimer un Client
     * @param sock La sock que l'on veut supprimer
     * @return le client voulu
     */
    @Override
    public void removeClient(SocketIOClient sock) {
        for (int i =0; i<this.size(); i++)
            if (sock.equals(this.get(i).getSock())){
                this.remove(i);
            }
    }


    /**
     * Permet d'ajouter un client
     * @param client le client a ajouter
     */
    @Override
    public void addClient(Client client){
        if(getClient(client.getSock())!=null){
            removeClient(client.getSock());
        }
        this.add(client);
    }

    /**
     * Permet de savoir le nombre de client connecter
     * @return le nombre de client connecter
     */
    @Override
    public int numberClient() {
        return this.size();
    }


}
