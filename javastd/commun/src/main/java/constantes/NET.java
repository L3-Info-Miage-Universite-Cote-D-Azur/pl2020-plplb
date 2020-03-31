package constantes;

/**
 * constante pour définir les messages entre le client et le serveur
 */
public class NET {

    //demande/envoie la liste des ue
    public static final String SEMSTERDATA = "Semestre data";

    //envoie le login de l'etudiant pour l'identifier au pres du serveur
    public static final String STUDENT = "Student";

    //requete/envoie de la liste des nom de parcours
    public static final String COURSESNAMES = "Courses names";

    //le client envoie un nom de parcour, le serveur lui envoie le parcour
    public static final String LOADCOURSE = "Load/Send course";

    //le client envoie sa sauvegarde
    public static final String SENDCLIENTSAVE = "send client save";


}