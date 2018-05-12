package ar.ndato.donantesdesangre.visitor;

import ar.ndato.donantesdesangre.sangre.ABRhN;
import ar.ndato.donantesdesangre.sangre.ABRhP;
import ar.ndato.donantesdesangre.sangre.ARhN;
import ar.ndato.donantesdesangre.sangre.ARhP;
import ar.ndato.donantesdesangre.sangre.BRhN;
import ar.ndato.donantesdesangre.sangre.BRhP;
import ar.ndato.donantesdesangre.sangre.ORhN;
import ar.ndato.donantesdesangre.sangre.ORhP;
import ar.ndato.donantesdesangre.Estadistica;

/**
 * Obtiene estadisticas. Para usarlo crear un nuevo {@link VisitorEstadistica} y visitar todas las sangres con el mismo visitor. Al terminar obtener las estadisticas con {@link VisitorEstadistica#getEstadistica()}
 */
public class VisitorEstadistica implements VisitorSangre {
	private Integer total;
	private Integer ap;
	private Integer an;
	private Integer abp;
	private Integer abn;
	private Integer bp;
	private Integer bn;
	private Integer op;
	private Integer on;


	public VisitorEstadistica() {
		total = 0;
		ap = 0;
		an = 0;
		abp = 0;
		abn = 0;
		bp = 0;
		bn = 0;
		op = 0;
		on = 0;
	}

	/**
	 * @return la {@link Estadistica} de las sangres visitadas hasta el momento
	 * @note No se resetean los valores al llamar a esta funcion por lo que se puede usar a medida que se va visitando las sangres.
	 */
	public Estadistica getEstadistica() {
		return new Estadistica(total, ap, bp, abp, op, an, bn, abn, on);
	}

	@Override
	public void visit(ABRhN sangre) {
		total++;
		abn++;
	}

	@Override
	public void visit(ABRhP sangre) {
		total++;
		abp++;
	}

	@Override
	public void visit(ARhN sangre) {
		total++;
		an++;
	}

	@Override
	public void visit(ARhP sangre) {
		total++;
		ap++;
	}

	@Override
	public void visit(BRhN sangre) {
		total++;
		bn++;
	}

	@Override
	public void visit(BRhP sangre) {
		total++;
		bp++;
	}

	@Override
	public void visit(ORhN sangre) {
		total++;
		on++;
	}

	@Override
	public void visit(ORhP sangre) {
		total++;
		op++;
	}
}
