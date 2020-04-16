package metier.semestre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import metier.Categorie;
import metier.UE;

/**
 * Semestre represente un semestre du parcours de licence. Un semestre contient des categorie qui contiennent des UEs.
 */
public class Semestre implements Serializable {
    /* FIELDS */
    private int number; //le numero du semestre
    private ArrayList<Categorie> listCategorie; // la liste des categorie.
    //private Gson gson;

    // RULE of semestre
    private SemestreRules rules;


    /* CONSTRUCTOR */
    public Semestre(int number, ArrayList<Categorie> listCategorie, SemestreRules rules) {
        this.number = number;

        this.listCategorie = new ArrayList<Categorie>();
        this.setListCategorie(listCategorie); //Pour rattacher le semestre au Ues.
        //For RULE
        this.rules = rules;
    }



    public Semestre() {
        this.number = -1;
        this.listCategorie = new ArrayList<Categorie>();
        //For RULE
        this.rules = new SemestreRules(-1, -1, null);
    }

    /* Methods */



    public SemestreRules getRules() {
        return rules;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public ArrayList<Categorie> getListCategorie() {
        return listCategorie;
    }

    public void setListCategorie(ArrayList<Categorie> listCategorie) {
        if(listCategorie != null) {
            for (Categorie c : listCategorie) {
                for (UE ue : c.getListUE()) {
                    ue.setSemestreNumber(this.number);
                }
            }
        }
        this.listCategorie = listCategorie;
    }

    /**
     * Cherche une UE a l'aide de sont code
     * @param codeUE le code de l'ue rechercher
     * @return l'ue si elle est trouver
     */
    public UE findUE(String codeUE) {
        UE ue;
        for (Categorie categorie : listCategorie) {
            ue = categorie.findUE(codeUE);
            if (ue != null) return ue;
        }
        //not find
        return null;
    }


    /**
     * Permet de chercher une categorie si elle existe
     * @param Category le nom de la categorie que l'on cherche
     * @return la categorie si elle a etait trouver
     */
    public Categorie findCategory(String Category){
        for(Categorie category: listCategorie){
            if(category.getName().equals(Category)){
                return category;
            }
        }
        return null; //not found
    }

    /**
     * permet d'ajouter une ue au semestre
     * @param ue l'ue que lon veut ajouter
     */
    public void addUE(UE ue){
        Categorie categorie = findCategory(ue.getCategorie());
        if(categorie==null){ //la categorie existe pas on la crée
            categorie = new Categorie(ue.getCategorie());
            listCategorie.add(categorie);
        }
        //on ajoute l'ue
        categorie.addUe(ue);

    }



    public String
    getJson ()
    {
    	Gson gson = new GsonBuilder().create();
    	return gson.toJson(this);
    }

    /**
     * Permet de set les regle de smeestre
     * @param rules les regle que l'on veut mettre
     */
    public void setRules(SemestreRules rules){
        this.rules = rules;
    }




}
