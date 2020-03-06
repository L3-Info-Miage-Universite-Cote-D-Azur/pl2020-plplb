package serveur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemestersSampleTest {

    @Test
    public void s1Test(){
        String res = SemestersSample.S1Jsoned;
        String expected = "{\"number\":1,\"listCategorie\":[{\"name\":\"GEOGRAPHIE\",\"listUE\":" +
                "[{\"name\":\"Decouverte 1\",\"code\":\"SPUGDE10\",\"categorie\":\"GEOGRAPHIE\"}," +
                "{\"name\":\"Decouverte 2\",\"code\":\"SPUGDC10\",\"categorie\":\"GEOGRAPHIE\"}," +
                "{\"name\":\"Decouverte 1\",\"code\":\"SPUGDI10\",\"categorie\":\"GEOGRAPHIE\"}]}," +
                "{\"name\":\"INFORMATIQUE\",\"listUE\":[{\"name\":\"Bases de l\\u0027informatique\"," +
                "\"code\":\"SPUF10\",\"categorie\":\"INFORMATIQUE\"},{\"name\":\"Introduction a" +
                " l\\u0027informatique par le web\",\"code\":\"SPUF11\",\"categorie\":\"INFORMATIQUE\"}]}," +
                "{\"name\":\"MATHEMATIQUES\",\"listUE\":[{\"name\":\"Fondements 1\",\"code\":" +
                "\"SPUM11\",\"categorie\":\"MATHEMATIQUES\"},{\"name\":\"Complements 1\",\"code\":\"SPUM13\"," +
                "\"categorie\":\"MATHEMATIQUES\"},{\"name\":\"Methodes - approche continue\",\"code\":\"SPUM12\"," +
                "\"categorie\":\"MATHEMATIQUES\"}]},{\"name\":\"SCIENCES DE LA VIE\",\"listUE\":[{\"name\":" +
                "\"Genetique. evolution. origine vie \\u0026 biodiversite\",\"code\":\"SPUV101\",\"categorie\":" +
                "\"SCIENCES DE LA VIE\"},{\"name\":\"Org et mecan. moleculaires - cellules eucaryotes\",\"code\":" +
                "\"SPUV100\",\"categorie\":\"SCIENCES DE LA VIE\"},{\"name\":\"Structure microscopique de la matiere\"," +
                "\"code\":\"SPUC10\",\"categorie\":\"SCIENCES DE LA VIE\"}]},{\"name\":\"ELECTRONIQUE\",\"listUE\":" +
                "[{\"name\":\"Electronique numerique - Bases\",\"code\":\"SPUE10\",\"categorie\":\"ELECTRONIQUE\"}]}," +
                "{\"name\":\"ECONOMIE - GESTION\",\"listUE\":[{\"name\":\"Economie-gestion\",\"code\":\"SPUA10\",\"" +
                "categorie\":\"ECONOMIE - GESTION\"}]},{\"name\":\"PHYSIQUE\",\"listUE\":[{\"name\":\"Mecanique 1\"," +
                "\"code\":\"SPUP10\",\"categorie\":\"PHYSIQUE\"}]},{\"name\":\"SCIENCES DE LA TERRE\",\"listUE\":[{" +
                "\"name\":\"Decouverte des sciences de la terre\",\"code\":\"SPUT10\",\"categorie\":\"SCIENCES DE LA " +
                "TERRE\"}]},{\"name\":\"MATH ENJEUX\",\"listUE\":[{\"name\":\"Math enjeux 1\",\"code\":\"SPUS10\",\"" +
                "categorie\":\"MATH ENJEUX\"}]},{\"name\":\"COMPETENCES TRANSVERSALES\",\"listUE\":[{\"name\":\"" +
                "Competences transversales\",\"code\":\"KCTTS1\",\"categorie\":\"COMPETENCES TRANSVERSALES\"}]}," +
                "{\"name\":\"FABLAB\",\"listUE\":[{\"name\":\"Fablab S1\",\"code\":\"SPUSF100\",\"categorie\":" +
                "\"FABLAB\"}]}],\"numberUENeedChoose\":5,\"maxNumberByCategorie\":1,\"listObligatory\":[\"SPUM11\"," +
                "\"SPUM12\"],\"ueAutomaticCheck\":[\"KCTTS1\",\"SPUS10\"]}";


        assertEquals(expected,res);
    }
}
