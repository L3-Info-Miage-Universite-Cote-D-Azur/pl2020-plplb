package serveur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemestersSampleTest {

    @Test
    public void s1Test(){
        String res = SemestersSample.s1();
        String expected = "{\"number\":1,\"listUE\":[{\"name\":\"Introduction à l\\u0027informatique par le web\",\"code\":\"SPUF11\",\"checked\":false}]}";
        assertEquals(res,expected);
    }
}
