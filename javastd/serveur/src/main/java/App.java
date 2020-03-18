import serveur.Serveur;

/**
 * Classe principale du serveur
 */
public class App {


    public static final void main(String[] args) {

        String ip = "0.0.0.0";
        int port = 10101;
                
        Serveur serv = new Serveur(ip, port);
        serv.startServer();
    }
}