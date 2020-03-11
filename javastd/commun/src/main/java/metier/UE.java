package metier;

/**
 * Representation des UE
 */
public class UE{

    /*FIELDS*/
    private String name;
    private String code;
    private int semestreNumber; // Numero du semestre auquel appartient l'ue.
    private String categorie; //categorie de l'ue

    /*CONSTRUCTOR*/
    public UE(String name, String code) {
        this.name = name;
        this.code = code;
        this.setSemestreNumber(-1);//Pas encore de semestre affecte.
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


    public int getSemestreNumber() {
        return semestreNumber;
    }
    public void setSemestreNumber(int semestreNumber) {
        this.semestreNumber = semestreNumber;
    }
}
