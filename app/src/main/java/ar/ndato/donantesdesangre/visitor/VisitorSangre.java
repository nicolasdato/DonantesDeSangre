package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.sangre.*;

public interface VisitorSangre {

	void visit(ABRhN sangre);

	void visit(ABRhP sangre);

	void visit(ARhN sangre);

	void visit(ARhP sangre);

	void visit(BRhN sangre);

	void visit(BRhP sangre);

	void visit(ORhN sangre);

	void visit(ORhP sangre);
}
