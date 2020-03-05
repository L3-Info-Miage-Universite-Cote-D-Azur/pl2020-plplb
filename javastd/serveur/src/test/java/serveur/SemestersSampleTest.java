package serveur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemestersSampleTest {

    @Test
    public void s1Test(){
        String res = SemestersSample.s1();
        String expected = "{\"number\":1,\"listCategorie\":[{\"name\":\"GEOGRAPHIE\"," +
                "\"listUE\":[{\"name\":\"Decouverte 1\",\"code\":\"SPUGDE10\",\"checked\":false}," +
                "{\"name\":\"Decouverte 2\",\"code\":\"SPUGDC10\",\"checked\":false}," +
                "{\"name\":\"Decouverte 1\",\"code\":\"SPUGDI10\",\"checked\":false}]}," +
                "{\"name\":\"INFORMATIQUE\",\"listUE\":[{\"name\":\"Bases de l\\u0027informatique\"," +
                "\"code\":\"SPUF10\",\"checked\":false},{\"name\":\"Introduction a l\\u0027informatique par le web\"," +
                "\"code\":\"SPUF11\",\"checked\":false}]},{\"name\":\"MATHEMATIQUES\",\"listUE\":[{\"name\":\"Fondements 1\"," +
                "\"code\":\"SPUM11\",\"checked\":false},{\"name\":\"Complements 1\",\"code\":\"SPUM13\",\"checked\":false}," +
                "{\"name\":\"Methodes - approche continue\",\"code\":\"SPUM12\",\"checked\":false}]}," +
                "{\"name\":\"SCIENCES DE LA VIE\",\"listUE\":[{\"name\":\"Genetique. evolution. origine vie \\u0026 biodiversite\"," +
                "\"code\":\"SPUV101\",\"checked\":false},{\"name\":\"Org et mecan. moleculaires - cellules eucaryotes\"," +
                "\"code\":\"SPUV100\",\"checked\":false},{\"name\":\"Structure microscopique de la Categorie\"," +
                "\"code\":\"SPUC10\",\"checked\":false}]},{\"name\":\"ELECTRONIQUE\"," +
                "\"listUE\":[{\"name\":\"Electronique numerique - Bases\",\"code\":\"SPUE10\",\"checked\":false}]}," +
                "{\"name\":\"ECONOMIE - GESTION\",\"listUE\":[{\"name\":\"Economie-gestion\",\"code\":\"SPUA10\",\"checked\":false}]}," +
                "{\"name\":\"PHYSIQUE\",\"listUE\":[{\"name\":\"Mecanique 1\",\"code\":\"SPUP10\",\"checked\":false}]}," +
                "{\"name\":\"SCIENCES DE LA TERRE\",\"listUE\":[{\"name\":\"Decouverte des sciences de la terre\"," +
                "\"code\":\"SPUT10\",\"checked\":false}]},{\"name\":\"MATH ENJEUX\",\"listUE\":[{\"name\":\"Math enjeux 1\"," +
                "\"code\":\"SPUS10\",\"checked\":false}]},{\"name\":\"COMPETENCES TRANSVERSALE\"," +
                "\"listUE\":[{\"name\":\"Competences transversales\",\"code\":\"KCTTS1\",\"checked\":false}]}," +
                "{\"name\":\"FABLAB\",\"listUE\":[{\"name\":\"Fablab S1\",\"code\":\"SPUSF100\",\"checked\":false}]}]}";


        assertEquals(res,expected);
    }
}
