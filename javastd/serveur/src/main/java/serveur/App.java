package serveur;

/**
 * Classe principale du serveur
 */
public class App {


    public static final void main(String[] args) {

        String ip = "0.0.0.0";
        int port = 10101;


        Serveur server = new Serveur(ip, port);
        server.startServer();

    }
}