package ar.ndato.donantesdesangre;

import org.junit.Test;
import static org.junit.Assert.*;

public class SangreTest {
    @Test
    public void sangreTest() {
        Sangre sangreABP;
        Sangre sangreABN;
        Sangre sangreON;
        VisitorDonaA visitorDonaA;
        VisitorRecibeDe visitorRecibeDe;

        sangreABP = new ABRhPFactory().crearSangre();
        sangreABN = new ABRhNFactory().crearSangre();
        sangreON = new ORhNFactory().crearSangre();

        assertEquals(sangreABP, new ABRhPFactory().crearSangre());
        assertNotEquals(sangreON, new ORhPFactory().crearSangre());

        visitorDonaA = sangreABP.getVisitorDonaA();
        sangreABN.accept(visitorDonaA);
        assertFalse(visitorDonaA.puedeDonar());
        visitorDonaA = sangreABP.getVisitorDonaA();
        sangreON.accept(visitorDonaA);
        assertFalse(visitorDonaA.puedeDonar());
        visitorRecibeDe = sangreABP.getVisitorRecibeDe();
        sangreABN.accept(visitorRecibeDe);
        assertTrue(visitorRecibeDe.puedeRecibir());
        visitorRecibeDe = sangreABP.getVisitorRecibeDe();
        sangreON.accept(visitorRecibeDe);
        assertTrue(visitorRecibeDe.puedeRecibir());
        visitorDonaA = sangreON.getVisitorDonaA();
        sangreABP.accept(visitorDonaA);
        assertTrue(visitorDonaA.puedeDonar());
        visitorRecibeDe = sangreON.getVisitorRecibeDe();
        sangreABN.accept(visitorRecibeDe);
        assertFalse(visitorRecibeDe.puedeDonar());
    }
}
