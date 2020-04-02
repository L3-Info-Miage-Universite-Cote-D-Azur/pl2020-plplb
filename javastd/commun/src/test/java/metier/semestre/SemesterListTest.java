package metier.semestre;

import metier.UE;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockingDetails;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SemesterListTest {
    SemesterList semesterList = new SemesterList();

    @Mock
    UE ue = Mockito.mock(UE.class);
    @Mock
    UE ue2 = Mockito.mock(UE.class);

    @Mock
    Semestre s1 = Mockito.mock(Semestre.class);
    @Mock
    Semestre s2 = Mockito.mock(Semestre.class);

    @Test
    public void findUETest(){
        //Mise en place des mocks
        Mockito.when(s1.findUE("code1")).thenReturn(ue);
        Mockito.when(s1.findUE("code2")).thenReturn(null);
        Mockito.when(s1.findUE("code3")).thenReturn(null);

        Mockito.when(s2.findUE("code2")).thenReturn(ue2);
        Mockito.when(s2.findUE("code1")).thenReturn(null);
        Mockito.when(s2.findUE("code3")).thenReturn(null);

        //Ajout des mocks a la semesterlist
        semesterList.add(s1);
        semesterList.add(s2);

        UE result = semesterList.findUE("code1");
        assertEquals(result,ue);

        result = semesterList.findUE("code2");
        assertEquals(result,ue2);

        //Aucune ue n'a été trouvé
        result = semesterList.findUE("code3");
        assertEquals(result,null);
    }
}
