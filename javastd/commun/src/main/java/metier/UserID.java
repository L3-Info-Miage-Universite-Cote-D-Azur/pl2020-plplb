package metier;

import org.json.JSONException;
import org.json.JSONObject;

public class UserID implements ToJSON {

    private  String nom;

    public UserID() {
        this("nom par défaut");
    }

    public UserID(String nom) {
        this.nom = nom;
    }



    /*GETTERS/SETTERS*/
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }


    public String toString() {
        return this.getNom();
    }



    /*JSON*/
    @Override
    public JSONObject toJSON() {
        JSONObject id = new JSONObject();
        try {
            id.put("nom", getNom());
        } catch (JSONException e) {
            e.printStackTrace();}
        return id;
    }
}

