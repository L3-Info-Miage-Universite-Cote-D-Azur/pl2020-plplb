package file;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import metier.UE;
import metier.parcours.ParcoursType;
import metier.semestre.SemestreRules;

import java.util.ArrayList;
import java.util.List;

/**
 * Class qui permet la conversion simplifiee
 * de types
 */
public class
Converter {
    /**
     * Objet qui traite la conversion
     */
    private Gson gson;

    /**
     * Constructeur de Converter
     */
    public Converter() {
        this.gson = new GsonBuilder().create();
    }


    /**
     * Convertie le fichier csv en list d'ue
     *
     * @param allUeCsv le tableau de csv
     * @return l'array list d'ue des ue du semestre
     */
    public ArrayList<UE> csvToUe(List<List<String>> allUeCsv) {

        if (allUeCsv.size() <= 0) return null;

        ArrayList<UE> ueList = new ArrayList<UE>();
        for (int i = 1; i < allUeCsv.size(); i++) { //premiere ligne est reserver au titre des colonne
            List<String> stringUe = allUeCsv.get(i);
            //si la ligne est bien complete
            if (stringUe.size() >= 4) {
                ueList.add(new UE(stringUe));
            }
        }
        return ueList;
    }


    /**
     * Permet de renvoyer un semestreRules a partir
     * d'une String sous format JSON
     *
     * @param s la String sous format JSON
     * @return le semestreRules lie a la String
     */
    public SemestreRules stringToSemestreRule(String s) {
        return gson.fromJson(s, SemestreRules.class);
    }
}
