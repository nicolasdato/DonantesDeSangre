package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.sangre.ABRhN;
import ar.ndato.donantesdesangre.sangre.ABRhP;
import ar.ndato.donantesdesangre.sangre.ARhN;
import ar.ndato.donantesdesangre.sangre.ARhP;
import ar.ndato.donantesdesangre.sangre.BRhN;
import ar.ndato.donantesdesangre.sangre.BRhP;
import ar.ndato.donantesdesangre.sangre.ORhN;
import ar.ndato.donantesdesangre.sangre.ORhP;

public class VisitorORhNDonaA extends VisitorDonaA {
	@Override
	public void visit(ABRhN sangre) {
		puede = true;
	}

	@Override
	public void visit(ABRhP sangre) {
		puede = true;
	}

	@Override
	public void visit(ARhN sangre) {
		puede = true;
	}

	@Override
	public void visit(ARhP sangre) {
		puede = true;
	}

	@Override
	public void visit(BRhN sangre) {
		puede = true;
	}

	@Override
	public void visit(BRhP sangre) {
		puede = true;
	}

	@Override
	public void visit(ORhN sangre) {
		puede = true;
	}

	@Override
	public void visit(ORhP sangre) {
		puede = true;
	}
}
