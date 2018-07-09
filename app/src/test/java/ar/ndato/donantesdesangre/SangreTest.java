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
import ar.ndato.donantesdesangre.visitor.VisitorDonacion;

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
        VisitorDonacion visitorDonaA;
        VisitorDonacion visitorRecibeDe;

        Boolean [][]compatibilidadesGlobulos =
		            //[dador][receptor]
		        {
		        	//AB+   A+     B+     O+     AB-    A-     B-     O-
		        	{true , false, false, false, false, false, false, false}, //ABP
				    {true , true , false, false, false, false, false, false}, //AP
				    {true , false, true , false, false, false, false, false}, //BP
				    {true , true , true , true , false, false, false, false}, //OP
				    {true , false, false, false, true , false, false, false}, //ABN
				    {true , true , false, false, true , true , false, false}, //AN
				    {true , false, true , false, true , false, true , false}, //BN
				    {true , true , true , true , true , true , true , true }, //ON
		        };
	
	    Boolean [][]compatibilidadesSangreTotal =
			    //[dador][receptor]
			    {
				    //AB+   A+     B+     O+     AB-    A-     B-     O-
				    {true , false, false, false, false, false, false, false}, //ABP
				    {false, true , false, false, false, false, false, false}, //AP
				    {false, false, true , false, false, false, false, false}, //BP
				    {false, false, false, true , false, false, false, false}, //OP
				    {true , false, false, false, true , false, false, false}, //ABN
				    {false, true , false, false, false, true , false, false}, //AN
				    {false, false, true , false, false, false, true , false}, //BN
				    {false, false, false, true , false, false, false, true }  //ON
			    };
	
	    Boolean [][]compatibilidadesPlasma =
			    //[dador][receptor]
			    {
				    //AB+   A+     B+     O+     AB-    A-     B-     O-
				    {true , true , true , true , true , true , true , true }, //ABP
				    {false, true , false, true , false, true , false, true }, //AP
				    {false, false, true , true , false, false, true , true }, //BP
				    {false, false, false, true , false, false, false, true }, //OP
				    {true , true , true , true , true , true , true , true }, //ABN
				    {false, true , false, true , false, true , false, true }, //AN
				    {false, false, true , true , false, false, true , true }, //BN
				    {false, false, false, true , false, false, false, true }  //ON
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
        	for(j = 0; j < sangres.length; j++) {
				visitorDonaA = sangres[i].getVisitorDonacion(Donacion.Accion.DONAR, Donacion.TipoDonacion.GLOBULOS_ROJOS);
				sangres[j].accept(visitorDonaA);
				assertEquals(compatibilidadesGlobulos[i][j], visitorDonaA.puede());
		        visitorRecibeDe = sangres[i].getVisitorDonacion(Donacion.Accion.RECIBIR, Donacion.TipoDonacion.GLOBULOS_ROJOS);
		        sangres[j].accept(visitorRecibeDe);
		        assertEquals(compatibilidadesGlobulos[j][i], visitorRecibeDe.puede());
		        visitorDonaA = sangres[i].getVisitorDonacion(Donacion.Accion.DONAR, Donacion.TipoDonacion.PLASMA);
		        sangres[j].accept(visitorDonaA);
		        assertEquals(compatibilidadesPlasma[i][j], visitorDonaA.puede());
		        visitorRecibeDe = sangres[i].getVisitorDonacion(Donacion.Accion.RECIBIR, Donacion.TipoDonacion.PLASMA);
		        sangres[j].accept(visitorRecibeDe);
		        assertEquals(compatibilidadesPlasma[j][i], visitorRecibeDe.puede());
		        visitorDonaA = sangres[i].getVisitorDonacion(Donacion.Accion.DONAR, Donacion.TipoDonacion.SANGRE_COMPLETA);
		        sangres[j].accept(visitorDonaA);
		        assertEquals(compatibilidadesSangreTotal[i][j], visitorDonaA.puede());
		        visitorRecibeDe = sangres[i].getVisitorDonacion(Donacion.Accion.RECIBIR, Donacion.TipoDonacion.SANGRE_COMPLETA);
		        sangres[j].accept(visitorRecibeDe);
		        assertEquals(compatibilidadesSangreTotal[j][i], visitorRecibeDe.puede());
	        }
        }
    }
}
