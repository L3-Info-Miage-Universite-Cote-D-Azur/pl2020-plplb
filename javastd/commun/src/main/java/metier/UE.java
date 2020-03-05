package metier;

/**
 * Representation des UE
 */
public class UE{

    /*FIELDS*/
    private String name;
    private String code;
    private String categorie; //categorie de l'ue

    /*CONSTRUCTOR*/
    public UE(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getUeName() {
        return name;
    }

    public String getUeCode() {
        return code;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
    public String getCategorie(){ return categorie;}
}
