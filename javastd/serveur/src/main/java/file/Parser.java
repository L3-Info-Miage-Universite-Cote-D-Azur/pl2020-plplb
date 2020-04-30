package file;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Parser est une class qui permet le parsage
 * de differentes configurations
 */
public class Parser {

    /**
     * Permet a l'aide d'un texte brut d'un fichier de renvoyer
     * un tableau qui represente chaque liste
     * @param text le text que l'on veut separer par ligne
     * @return une list qui continent toutes les lignes
     */
    public List<String> parseLine(String text){
        List<String> splitText;
        //on split les text a chaque ligne
        splitText = Arrays.asList(text.split("\n"));

        return trimList(splitText);
    }

    /**
     * Permet a l'aide d'un texte brut d'un fichier de csv
     * de creer une matrice avec tous les elements du fichier csv
     * @param text le texte csv que l'on veut parser
     * @return la matrice du fichier csv
     */
    public List<List<String>> parseCsv(String text){
        List<String> allLines = parseLine(text);
        List<List<String>> result = new ArrayList<List<String>>();
        for(String line : allLines){
            result.add(trimList(Arrays.asList(line.split(";"))));
        }
        return result;
    }

    /**
     * Permet d'enlever les espaces en trop au debut et en fin de string
     * pour l'integralité d'un tableau de string
     * @param listText la list de string que l'on veut trim
     * @return une list de string sans les espaces a la fin
     */
    private List<String> trimList(List<String> listText){
        List<String> result = new ArrayList<String>();
        for(String string : listText){
            result.add(string.trim());
        }
        return result;
    }
}
