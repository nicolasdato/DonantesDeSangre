package ar.ndato.donantesdesangre;

import org.junit.Test;

import ar.ndato.donantesdesangre.factory.ABRhNFactory;
import ar.ndato.donantesdesangre.factory.ABRhPFactory;
import ar.ndato.donantesdesangre.factory.ARhNFactory;
import ar.ndato.donantesdesangre.factory.ARhPFactory;
import ar.ndato.donantesdesangre.factory.BRhNFactory;
import ar.ndato.donantesdesangre.factory.BRhPFactory;
import ar.ndato.donantesdesangre.factory.ORhNFactory;
import ar.ndato.donantesdesangre.factory.ORhPFactory;
import ar.ndato.donantesdesangre.sangre.Sangre;
import ar.ndato.donantesdesangre.visitor.VisitorDonaA;
import ar.ndato.donantesdesangre.visitor.VisitorRecibeDe;

import static org.junit.Assert.assertEquals;

public class SangreTest {
    @Test
    public void sangreTest() {
        Sangre sangreAP;
        Sangre sangreBP;
        Sangre sangreABP;
        Sangre sangreOP;
        Sangre sangreAN;
        Sangre sangreBN;
        Sangre sangreABN;
        Sangre sangreON;
        VisitorDonaA visitorDonaA;
        VisitorRecibeDe visitorRecibeDe;

        Boolean [][]compatibilidades =
		            //[dador][receptor]
		            //ABP AP BP OP ABN AN BN ON
		        {
		        	{true , false, false, false, false, false, false, false}, //ABP
				    {true , true , false, false, false, false, false, false}, //AP
				    {true , false, true , false, false, false, false, false}, //BP
				    {true , true , true , true , false, false, false, false}, //OP
				    {true , false, false, false, true , false, false, false}, //ABN
				    {true , true , false, false, true , true , false, false}, //AN
				    {true , false, true , false, true , false, true , false }, //BN
				    {true , true , true , true , true , true , true , true }, //ON
		        };

        sangreAP = new ARhPFactory().crearSangre();
        sangreBP = new BRhPFactory().crearSangre();
        sangreABP = new ABRhPFactory().crearSangre();
        sangreOP = new ORhPFactory().crearSangre();
        sangreAN = new ARhNFactory().crearSangre();
        sangreBN = new BRhNFactory().crearSangre();
        sangreABN = new ABRhNFactory().crearSangre();
        sangreON = new ORhNFactory().crearSangre();

        Sangre []sangres = {sangreABP, sangreAP, sangreBP, sangreOP, sangreABN, sangreAN, sangreBN, sangreON};

        int i, j;
        for(i = 0; i < sangres.length; i++){
        	for(j = 0; j < sangres.length; i++) {
				visitorDonaA = sangres[i].getVisitorDonaA();
				sangres[j].accept(visitorDonaA);
				assertEquals(compatibilidades[i][j], visitorDonaA.puedeDonar());
		        visitorRecibeDe = sangres[i].getVisitorRecibeDe();
		        sangres[j].accept(visitorRecibeDe);
		        assertEquals(compatibilidades[j][i], visitorRecibeDe.puedeRecibir());
	        }
        }
    }
}
