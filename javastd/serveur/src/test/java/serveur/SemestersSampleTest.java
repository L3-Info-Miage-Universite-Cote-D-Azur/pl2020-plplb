package serveur;

import org.junit.jupiter.api.Test;

import semester_manager.SemestersSample;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemestersSampleTest {

    @Test
    public void s1Test(){
        /*
        String res = SemestersSample.S1Jsoned;
        String expected = "{\"number\":1,\"listCategorie\":[{\"name\":\"GEOGRAPHIE\",\"listUE\":[{\"name\":" +
                "\"Decouverte 1\",\"code\":\"SPUGDE10\",\"semestreNumber\":1,\"categorie\":\"GEOGRAPHIE\"},{" +
                "\"name\":\"Decouverte 2\",\"code\":\"SPUGDC10\",\"semestreNumber\":1,\"categorie\":\"GEOGRAPHIE" +
                "\"},{\"name\":\"Disciplinaire 1\",\"code\":\"SPUGDI10\",\"semestreNumber\":1,\"categorie\":" +
                "\"GEOGRAPHIE\"}]},{\"name\":\"INFORMATIQUE\",\"listUE\":[{\"name\":\"Bases de l\\u0027informatique" +
                "\",\"code\":\"SPUF10\",\"semestreNumber\":1,\"categorie\":\"INFORMATIQUE\"},{\"name\":" +
                "\"Introduction a l\\u0027informatique par le web\",\"code\":\"SPUF11\",\"semestreNumber" +
                "\":1,\"categorie\":\"INFORMATIQUE\"}]},{\"name\":\"MATHEMATIQUES\",\"listUE\":[{\"name\":" +
                "\"Fondements 1\",\"code\":\"SPUM11\",\"semestreNumber\":1,\"categorie\":\"MATHEMATIQUES\"},{" +
                "\"name\":\"Complements 1\",\"code\":\"SPUM13\",\"semestreNumber\":1,\"categorie\":\"MATHEMATIQUES" +
                "\"},{\"name\":\"Methodes - approche continue\",\"code\":\"SPUM12\",\"semestreNumber\":1," +
                "\"categorie\":\"MATHEMATIQUES\"}]},{\"name\":\"SCIENCES DE LA VIE\",\"listUE\":[{\"name\":" +
                "\"Genetique. evolution. origine vie \\u0026 biodiversite\",\"code\":\"SPUV101\",\"semestreNumber" +
                "\":1,\"categorie\":\"SCIENCES DE LA VIE\"},{\"name\":" +
                "\"Org et mecan. moleculaires - cellules eucaryotes\",\"code\":\"SPUV100\",\"semestreNumber" +
                "\":1,\"categorie\":\"SCIENCES DE LA VIE\"}]},{\"name\":\"CHIMIE\",\"listUE\":[{\"name\":" +
                "\"Structure microscopique de la matiere\",\"code\":\"SPUC10\",\"semestreNumber\":1," +
                "\"categorie\":\"CHIMIE\"}]},{\"name\":\"ELECTRONIQUE\",\"listUE\":[{\"name\":" +
                "\"Electronique numerique - Bases\",\"code\":\"SPUE10\",\"semestreNumber\":1,\"categorie\":" +
                "\"ELECTRONIQUE\"}]},{\"name\":\"ECONOMIE - GESTION\",\"listUE\":[{\"name\":\"Economie-gestion" +
                "\",\"code\":\"SPUA10\",\"semestreNumber\":1,\"categorie\":\"ECONOMIE - GESTION\"}]},{" +
                "\"name\":\"PHYSIQUE\",\"listUE\":[{\"name\":\"Mecanique 1\",\"code\":\"SPUP10\"," +
                "\"semestreNumber\":1,\"categorie\":\"PHYSIQUE\"}]},{\"name\":\"SCIENCES DE LA TERRE" +
                "\",\"listUE\":[{\"name\":\"Decouverte des sciences de la terre\",\"code\":\"SPUT10\"," +
                "\"semestreNumber\":1,\"categorie\":\"SCIENCES DE LA TERRE\"}]},{\"name\":\"MATH ENJEUX" +
                "\",\"listUE\":[{\"name\":\"Math enjeux 1\",\"code\":\"SPUS10\",\"semestreNumber\":1," +
                "\"categorie\":\"MATH ENJEUX\"}]},{\"name\":\"COMPETENCES TRANSVERSALES\",\"listUE\":[{" +
                "\"name\":\"Competences transversales\",\"code\":\"KCTTS1\",\"semestreNumber\":1,\"categorie" +
                "\":\"COMPETENCES TRANSVERSALES\"}]},{\"name\":\"FABLAB\",\"listUE\":[{\"name\":\"Fablab S1\"," +
                "\"code\":\"SPUSF100\",\"semestreNumber\":1,\"categorie\":\"FABLAB\"}]}],\"rules\":{" +
                "\"maxUELibre\":3,\"maxByCategory\":1,\"obligatoryUEList\":[\"KCTTS1\",\"SPUS10\"]," +
                "\"chooseUEList\":[\"SPUM11\",\"SPUM12\"],\"numberChooseUE\":1}}";

        assertEquals(expected,res);
         */
    }


    @Test
    public void s2Test(){
        /*
        String res = SemestersSample.S2Jsoned;
        String expected = "{\"number\":2,\"listCategorie\":[{\"name\":\"GEOGRAPHIE\"," +
                "\"listUE\":[{\"name\":\"Decouverte 3\",\"code\":\"SPUGDE20\",\"semestreNumber" +
                "\":2,\"categorie\":\"GEOGRAPHIE\"},{\"name\":\"Decouverte 4\",\"code\":\"SPUGDC20" +
                "\",\"semestreNumber\":2,\"categorie\":\"GEOGRAPHIE\"},{\"name\":\"Decouverte 2\"," +
                "\"code\":\"SPUGDI20\",\"semestreNumber\":2,\"categorie\":\"GEOGRAPHIE\"}]},{\"name" +
                "\":\"INFORMATIQUE\",\"listUE\":[{\"name\":\"Programmation imperative\",\"code\":" +
                "\"SPUF21\",\"semestreNumber\":2,\"categorie\":\"INFORMATIQUE\"},{\"name\":" +
                "\"Systeme 1 unix et programmation shell\",\"code\":\"SPUF20\",\"semestreNumber\":2," +
                "\"categorie\":\"INFORMATIQUE\"}]},{\"name\":\"MATHEMATIQUES\",\"listUE\":[{\"name\":" +
                "\"Fondements 2\",\"code\":\"SPUM21\",\"semestreNumber\":2,\"categorie\":\"MATHEMATIQUES" +
                "\"},{\"name\":\"Complements 2\",\"code\":\"SPUM23\",\"semestreNumber\":2,\"categorie\":" +
                "\"MATHEMATIQUES\"},{\"name\":\"Methodes Maths-Approche discrete\",\"code\":\"SPUM22\"," +
                "\"semestreNumber\":2,\"categorie\":\"MATHEMATIQUES\"}]},{\"name\":\"SCIENCES DE LA VIE" +
                "\",\"listUE\":[{\"name\":\"Diversite du vivant\",\"code\":\"SPUV201\",\"semestreNumber" +
                "\":2,\"categorie\":\"SCIENCES DE LA VIE\"},{\"name\":\"Physiologie - neurologie - enzymologie" +
                "\",\"code\":\"SPUV200\",\"semestreNumber\":2,\"categorie\":\"SCIENCES DE LA VIE\"}]},{" +
                "\"name\":\"CHIMIE\",\"listUE\":[{\"name\":\"Reactions et reactivites chimiques\",\"code" +
                "\":\"SPUC20\",\"semestreNumber\":2,\"categorie\":\"CHIMIE\"},{\"name\":" +
                "\"Thermodynamique chimique / Options\",\"code\":\"SPUC21\",\"semestreNumber\":2,\"categorie" +
                "\":\"CHIMIE\"}]},{\"name\":\"ELECTRONIQUE\",\"listUE\":[{\"name\":\"Communication sans fil" +
                "\",\"code\":\"SPUE21\",\"semestreNumber\":2,\"categorie\":\"ELECTRONIQUE\"},{\"name\":" +
                "\"Electronique analogique\",\"code\":\"SPUE20\",\"semestreNumber\":2,\"categorie\":" +
                "\"ELECTRONIQUE\"}]},{\"name\":\"ECONOMIE - GESTION\",\"listUE\":[{\"name\":\"Economie-gestion S2" +
                "\",\"code\":\"SPUA20\",\"semestreNumber\":2,\"categorie\":\"ECONOMIE - GESTION\"}]},{" +
                "\"name\":\"PHYSIQUE\",\"listUE\":[{\"name\":\"Mecanique - complements\",\"code\":" +
                "\"SPUP21\",\"semestreNumber\":2,\"categorie\":\"PHYSIQUE\"},{\"name\":\"Optique\"," +
                "\"code\":\"SPUP20\",\"semestreNumber\":2,\"categorie\":\"PHYSIQUE\"}]},{\"name\":" +
                "\"SCIENCES DE LA TERRE\",\"listUE\":[{\"name\":\"Atmosphere. ocean. climats\",\"code" +
                "\":\"SPUT22\",\"semestreNumber\":2,\"categorie\":\"SCIENCES DE LA TERRE\"},{\"name" +
                "\":\"Structure et dynamique de la terre\",\"code\":\"SPUT20\",\"semestreNumber\":2," +
                "\"categorie\":\"SCIENCES DE LA TERRE\"}]},{\"name\":\"MATH ENJEUX\",\"listUE\":[{" +
                "\"name\":\"Math enjeux 2\",\"code\":\"SPUS20\",\"semestreNumber\":2,\"categorie\":" +
                "\"MATH ENJEUX\"}]},{\"name\":\"COMPETENCES TRANSVERSALES\",\"listUE\":[{\"name\":" +
                "\"Competences transversales - S2\",\"code\":\"KCTTS2\",\"semestreNumber\":2,\"categorie" +
                "\":\"COMPETENCES TRANSVERSALES\"}]},{\"name\":\"FABLAB\",\"listUE\":[{\"name\":" +
                "\"Fablab S2\",\"code\":\"SPOSF200\",\"semestreNumber\":2,\"categorie\":\"FABLAB" +
                "\"}]}],\"rules\":{\"maxUELibre\":4,\"maxByCategory\":2,\"obligatoryUEList\":[" +
                "\"KCTTS2\",\"SPUS20\"],\"chooseUEList\":[\"SPUM21\",\"SPUM22\"],\"numberChooseUE\":1}}";

        assertEquals(expected,res);
         */
    }
}
