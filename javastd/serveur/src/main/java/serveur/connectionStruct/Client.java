package serveur.connectionStruct;

import com.corundumstudio.socketio.SocketIOClient;
import metier.Student;

/**
 * Classe-association pour le client
 */
public class Client {

    /** L'etudiant represente */
    private Student student;
    /** La socket du client */
    private SocketIOClient sock;


    /**
     * Constructeur de Client
     * @param student L'etudiant represente
     * @param sock La socket du client
     */
    public Client (Student student, SocketIOClient sock)
    {
        this.student = student;
        this.sock = sock;
    }


    @Override
    public String toString ()
    {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        sb.append(this.sock.getRemoteAddress().toString());
        sb.append("|");
        sb.append(this.student.getNom());
        sb.append("]");
        return sb.toString();
    }

    /** Getter de Etudiant */
    public Student getStudent ()
    {return this.student;}

    /** Getter de SocketIOCLient */
    public SocketIOClient getSock ()
    {return this.sock;}

}
