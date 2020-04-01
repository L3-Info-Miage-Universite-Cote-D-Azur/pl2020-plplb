import metier.parcours.Parcours;
import metiermanager.courses.ParcoursSample;
import metiermanager.semesters.*;
import serveur.Serveur;

/**
 * Classe principale du serveur
 */
public class App {


    public static final void main(String[] args) {

        String ip = "127.0.0.1";
        int port = 10101;
                
        Serveur serv = new Serveur(ip, port);
        SemestersSample.init();
        ParcoursSample.init();
        serv.startServer();
    }
}