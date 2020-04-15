package serveur.connectionStruct;

import com.corundumstudio.socketio.SocketIOClient;
import metier.Student;

/**
 * Interface qui permet de faire le lien entre client et socket
 * la structure interne n'a pas d'importance et peut donc etre changer
 */
public interface LinkClientSocket {

    /**
     * Permet de recuperer un Client
     * @param student La student que l'on veut trouver
     * @return le client voulu
     */
    public Client getClient(Student student);

    /**
     * Permet de recuperer un Client
     * @param sock La socket du client
     * @return Le client voulu
     */
    public Client getClient(SocketIOClient sock);

    /**
     * Permet de supprimer un Client
     * @param student La student que l'on veut supprimer
     * @return le client voulu
     */
    public void removeClient(Student student);

    /**
     * Permet de supprimer un Client
     * @param sock La sock que l'on veut supprimer
     * @return le client voulu
     */
    public void removeClient(SocketIOClient sock);

    /**
     * Permet d'ajouter un client
     * @param client le client a ajouter
     */
    public void addClient(Client client);

    /**
     * Permet de savoir le nombre de client connecter
     * @return le nombre de client connecter
     */
    public int numberClient();


}
