package metier;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoginModeleTest {

    LoginModele loginModele;

    @BeforeEach
    public void init(){
        loginModele = new LoginModele();
    }

    @Test
    public void acceptINETest(){
        //Des bons ine.
        ArrayList<String> testTrue = new ArrayList<String>();
        testTrue.add("ab123456");
        testTrue.add("ty896542");
        testTrue.add("my713752");
        testTrue.add("kr713789");
        testTrue.add("jm796248");
        testTrue.add("xy000000");

        for(String s : testTrue){
            assertEquals(true,loginModele.acceptINE(s));
        }

        //Des mauvais ine
        ArrayList<String> testFalse = new ArrayList<String>();
        testFalse.add("Ab123456");//majuscule
        testFalse.add("ab1234567");//trop long
        testFalse.add("aB123456");//Majuscule
        testFalse.add("ab1234a6");//lettre dans les chiffres
        testFalse.add("ab12F456");//Majuscule dans les chiffres
        testFalse.add("abc23456");//Trop court.

        for(String s : testFalse){
            assertEquals(false,loginModele.acceptINE(s));
        }


    }
}
