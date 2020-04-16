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

    //le client envoie un nom de parcour, le serveur lui envoie le parcours
    public static final String LOADCOURSE = "Load/Send course";

    //le client envoie sa sauvegarde
    public static final String SENDCLIENTSAVE = "send client save";

    //le client envoie une mise a jour
    public static final String CLIENTUPDATE = "client update";

    //Le client demande/recoit les parcours predefinis.
    public static final String PREDEFINEDCOURSE = "predefined course";

    //Le client creer un parcours partage ou recoit un code pour le partage.
    public static final String ASKCODE = "ask code";

    //Le client veut charger un parcours partage depuis un code.
    public static final String COURSECODE = "share course code";

    //Le client veut supprimer un parcours.
    public static final String DELETECOURSE = "delete course";

    // Le client veut renommer un parcours.
    public static final String RENAMECOURSE = "rename course";


}