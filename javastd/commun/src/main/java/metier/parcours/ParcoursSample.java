package metier.parcours;

import java.util.ArrayList;
import java.util.HashMap;

public class ParcoursSample {

    //La liste des parcours types.
    public static ArrayList<ParcoursType> parcoursTypes;
    public static ArrayList<String> parcoursTypesName;

    /**
     * Initialise la liste des parcours Types
     */
     public static void init(){
         ArrayList<ParcoursType> listParcoursType = new ArrayList<ParcoursType>();
         ArrayList<String> parcoursTypesName = new ArrayList<String>();

         //PARCOURS INFO
         HashMap<String,Integer> numberUeInfo = new HashMap<String,Integer>();
         numberUeInfo.put("INFORMATIQUE",6);
         numberUeInfo.put("MATHEMATIQUES",2);

         listParcoursType.add(new ParcoursType("Parcours Informatique",numberUeInfo,null));
         parcoursTypesName.add("Parcours Informatique");

         //PARCOURS MATHEMATIQUES
         ArrayList<String> obligatoryUeMath = new ArrayList<String>();
         obligatoryUeMath.add("SPUM11");
         obligatoryUeMath.add("SPUM13");

         obligatoryUeMath.add("SPUM21");
         obligatoryUeMath.add("SPUM23");

         obligatoryUeMath.add("SPUM30");
         obligatoryUeMath.add("SPUM32");
         obligatoryUeMath.add("SPUM31");

         obligatoryUeMath.add("SPUM40");
         obligatoryUeMath.add("SPUM42");
         obligatoryUeMath.add("SPUM41");
         obligatoryUeMath.add("SPUM43");

         listParcoursType.add(new ParcoursType("Parcours Mathématiques",null,obligatoryUeMath));
         parcoursTypesName.add("Parcours Mathématiques");

         //PARCOURS MAJEUR MATHEMATIQUES
         ArrayList<String> obligatoryUeMathMaj = new ArrayList<String>();
         obligatoryUeMathMaj.add("SPUM11");
         obligatoryUeMathMaj.add("SPUM13");

         obligatoryUeMathMaj.add("SPUM21");
         obligatoryUeMathMaj.add("SPUM23");

         obligatoryUeMathMaj.add("SPUM30");
         obligatoryUeMathMaj.add("SPUM32");
         obligatoryUeMathMaj.add("SPUM31");

         //TODO : Deux cours à choisir parmi les trois cours (on ne met que deux cours parmis les 3)
         obligatoryUeMathMaj.add("SPUM40");
         obligatoryUeMathMaj.add("SPUM42");
         obligatoryUeMathMaj.add("SPUM41");
         //obligatoryUeMathMaj.add("SPUM43");

         listParcoursType.add(new ParcoursType("Parcours Majeur Mathématiques",null,obligatoryUeMathMaj));
         parcoursTypesName.add("Parcours Majeur Mathématiques");

         //PARCOURS ELECTRONIQUE
         ArrayList<String> obligatoryUeElect = new ArrayList<String>();
         obligatoryUeElect.add("SPUE10");
         obligatoryUeElect.add("SPUM12");

         obligatoryUeElect.add("SPUE21");
         obligatoryUeElect.add("SPUM22");

         obligatoryUeElect.add("SPUM33");
         obligatoryUeElect.add("SPUE31");
         obligatoryUeElect.add("SPUE32");
         obligatoryUeElect.add("SPUE30");

         obligatoryUeElect.add("SPUM44");
         obligatoryUeElect.add("SPUE42");
         obligatoryUeElect.add("SPUE41");
         obligatoryUeElect.add("SPUE40");

         listParcoursType.add(new ParcoursType("Parcours Electronique",null,obligatoryUeElect));
         parcoursTypesName.add("Parcours Electronique");

         //PARCOURS LIBRE
         listParcoursType.add(new ParcoursType("Parcours Libre",null,null));
         parcoursTypesName.add("Parcours Libre");

         //TODO : ajouter les autres parcours types quand on les aura.
         ParcoursSample.parcoursTypes = listParcoursType;
         ParcoursSample.parcoursTypesName = parcoursTypesName;
     }

    /**
     * Class non instanciable, c'est une class utilitaire
     */
    private ParcoursSample(){};
}
