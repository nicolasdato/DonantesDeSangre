package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.sangre.ABRhN;
import ar.ndato.donantesdesangre.sangre.ABRhP;
import ar.ndato.donantesdesangre.sangre.ARhN;
import ar.ndato.donantesdesangre.sangre.ARhP;
import ar.ndato.donantesdesangre.sangre.BRhN;
import ar.ndato.donantesdesangre.sangre.BRhP;
import ar.ndato.donantesdesangre.sangre.ORhN;
import ar.ndato.donantesdesangre.sangre.ORhP;

public abstract class VisitorDonacion implements VisitorSangre {
	protected Boolean puede;
	
	protected static final int ON = 0;
	protected static final int OP = 1;
	protected static final int AN = 2;
	protected static final int AP = 3;
	protected static final int BN = 4;
	protected static final int BP = 5;
	protected static final int ABN = 6;
	protected static final int ABP = 7;
	
	private Boolean []compatibilidad = null;
	
	//[donador][receptor]
	protected final Boolean [][]donarGlobulos = {
			//ON    OP     AN     AP     BN     BP     ABN    ABP
			{true , true , true , true , true , true , true , true }, //ON
			{false, true , false, true , false, true , false, true }, //OP
			{false, false, true , true , false, false, true , true }, //AN
			{false, false, false, true , false, false, false, true }, //AP
			{false, false, false, false, true , true , true , true }, //BN
			{false, false, false, false, false, true , false, true }, //BP
			{false, false, false, false, false, false, true , true }, //ABN
			{false, false, false, false, false, false, false, true }  //ABP
	};
	
	//[donador][receptor]
	protected final Boolean [][]donarPlasma = {
			//ON    OP     AN     AP     BN     BP     ABN    ABP
			{true , true , false, false, false, false, false, false}, //ON
			{true , true , false, false, false, false, false, false}, //OP
			{true , true , true , true , false, false, false, false}, //AN
			{true , true , true , true , false, false, false, false}, //AP
			{true , true , false, false, true , true , false, false}, //BN
			{true , true , false, false, true , true , false, false}, //BP
			{true , true , true , true , true , true , true , true }, //ABN
			{true , true , true , true , true , true , true , true }  //ABP
	};
	
	//[donador][receptor]
	protected final Boolean [][]donarSangreCompleta = {
			//ON    OP     AN     AP     BN     BP     ABN    ABP
			{true , true , false, false, false, false, false, false}, //ON
			{false, true , false, false, false, false, false, false}, //OP
			{false, false, true , true , false, false, false, false}, //AN
			{false, false, false, true , false, false, false, false}, //AP
			{false, false, false, false, true , true , false, false}, //BN
			{false, false, false, false, false, true , false, false}, //BP
			{false, false, false, false, false, false, true , true }, //ABN
			{false, false, false, false, false, false, false, true }  //ABP
	};

	public VisitorDonacion() {
		puede = false;
	}
	
	public VisitorDonacion(Donacion.Accion accion, Donacion.TipoDonacion tipoDonacion, int tipoSangre) {
		Boolean donar[][];
		if (tipoDonacion == Donacion.TipoDonacion.GLOBULOS_ROJOS) {
			donar = donarGlobulos;
		} else if (tipoDonacion == Donacion.TipoDonacion.PLASMA) {
			donar = donarPlasma;
		} else {
			donar = donarSangreCompleta;
		}
		if (accion == Donacion.Accion.DONAR) {
			compatibilidad = donar[tipoSangre];
		}
		else {
			compatibilidad = new Boolean[8];
			for (int i = 0; i < 8; i++) {
				compatibilidad[i] = donar[i][tipoSangre];
			}
		}
	}

	public Boolean puede() {
		return puede;
	}
	
	@Override
	public void visit(ABRhN sangre) {
		puede = compatibilidad[ABN];
	}
	
	@Override
	public void visit(ABRhP sangre) {
		puede = compatibilidad[ABP];
	}
	
	@Override
	public void visit(ARhN sangre) {
		puede = compatibilidad[AN];
	}
	
	@Override
	public void visit(ARhP sangre) {
		puede = compatibilidad[AP];
	}
	
	@Override
	public void visit(BRhN sangre) {
		puede = compatibilidad[BN];
	}
	
	@Override
	public void visit(BRhP sangre) {
		puede = compatibilidad[BP];
	}
	
	@Override
	public void visit(ORhN sangre) {
		puede = compatibilidad[ON];
	}
	
	@Override
	public void visit(ORhP sangre) {
		puede = compatibilidad[OP];
	}
}
