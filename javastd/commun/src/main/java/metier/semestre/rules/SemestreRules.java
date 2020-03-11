package metier.semestre.rules;

import metier.UE;
import metier.semestre.manager.ParcoursSemestreManager;

import java.util.List;

public interface SemestreRules {

    /**
     * Regarde si il est possible de cocher l'ue
     * @param ue l'ue que l'on veut cocher
     * @return tru:e il est possible de cocher; false: il n'est pas possible
     */
    public boolean canBeCheck(UE ue, ParcoursSemestreManager parcoursManager);

    /**
     * Regarde si il est possible de decocher l'ue
     * @param ue l'ue que l'on veut decocher
     * @return tru:e il est possible de decocher; false: il n'est pas possible
     */
    public boolean canBeUncheck(UE ue, ParcoursSemestreManager parcoursManager);

    /**
     * Verification si une UE fait partie des ue obligatoire du semestre
     * @param codeUE L'ue que l'on verifie
     * @return true: est une ue obligatoire;
     */
    public boolean isObligatoryUE(String codeUE);

    /**
     * Liste des codeUE des ue obligatoire
     * @return liste des codeUE
     */
    public List<String> obligatoryUEList();

    /**
     * Verification si une UE fait partie des ue a choix du semestre
     * @param codeUE L'ue que l'on verifie
     * @return true: est une ue obligatoire;
     */
    public boolean isChooseUE(String codeUE);

    /**
     * Cree le gestionnaire de semestre adaptée
     * @return la manager adaptée pour controller les regle de semestre
     */
    public ParcoursSemestreManager createManager();

    /**
     * Verifie si les donner semble correcte
     * @param semestreManager le parcours qui doit etre verifier
     * @return true si correcte sinon false
     */
    public boolean verifCorrectSemestre(ParcoursSemestreManager semestreManager);



}
