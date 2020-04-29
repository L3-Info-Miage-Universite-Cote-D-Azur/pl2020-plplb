package dataBase;
import file.FileUtility;
import log.Logger;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import org.mockito.internal.verification.Times;
import serveur.Serveur;

import java.io.File;

public class
UpdaterTest
{
    @Mock
    private Updater updater;
    private long lastModified;

    @Mock
    private Serveur server;
    @Mock
    private File dir;

    @BeforeEach
    public void
    testConstructor ()
    {
        this.server = Mockito.mock(Serveur.class);
        this.lastModified = 1L;
        this.dir = Mockito.mock(File.class);
        Mockito.when(this.dir.listFiles()).thenReturn(new File[]{});
        this.updater = new Updater(this.server, this.dir);
        Logger.verbose = false;
    }

    @Test
    public void
    testBody ()
    {
        long expected = 2L;

        Mockito.when(this.dir.lastModified()).thenReturn(expected);
        // != de lastModified
        Mockito.when(FileUtility.getLastModified(this.dir)).thenReturn(expected);
        this.lastModified = this.updater.body(this.lastModified);

        Mockito.verify(this.server, new Times(1)).updateSemestersOfClients();
        assertEquals(this.lastModified, expected);

        /* -------------------------------------------------- */

        expected = 2L;
        // == de lastModified
        Mockito.when(FileUtility.getLastModified(this.dir)).thenReturn(expected);
        this.lastModified = this.updater.body(this.lastModified);

        // 1 car on n'a pas appele une 2e fois
        Mockito.verify(this.server, new Times(1)).updateSemestersOfClients();
        assertEquals(this.lastModified, expected);
    }
}
