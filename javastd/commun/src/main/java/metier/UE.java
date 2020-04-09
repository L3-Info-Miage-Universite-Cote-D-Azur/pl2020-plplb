package metier;

import java.io.Serializable;
import java.util.List;

/**
 * Representation des UE
 */
public class UE implements Serializable {

    /*FIELDS*/
    private String name;
    private String code;
    private int semestreNumber; // Numero du semestre auquel appartient l'ue.
    private String categorie; //categorie de l'ue
    private String description;

    /*CONSTRUCTOR*/
    public UE(String name, String code) {
        this.name = name;
        this.code = code;
        this.setSemestreNumber(-1);//Pas encore de semestre affecte.
    }

    /**
     * Constructeur utile pour convertir un tableau issue d'un tableau csv
     * @param stringUe
     */
    public UE(List<String> stringUe){
        this.code = stringUe.get(0);
        this.name = stringUe.get(1);
        this.semestreNumber = Integer.parseInt(stringUe.get(2));
        this.categorie = stringUe.get(3);
        if(stringUe.size()==5){
            this.description = stringUe.get(4);
        }

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

    public String getUeDescription() {
        return description;
    }

    /**
     * Permet de set la description de l'ue
     * @param description la description de l'ue
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
