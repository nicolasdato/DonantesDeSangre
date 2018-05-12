package ar.ndato.donantesdesangre;

/**
 * Clase estadistica que retorna el {@link VisitorEstadistica}
 */
public class Estadistica {
	private Integer total;
	private Integer ap;
	private Integer bp;
	private Integer abp;
	private Integer op;
	private Integer an;
	private Integer bn;
	private Integer abn;
	private Integer on;

	public Estadistica(Integer total, Integer ap, Integer bp, Integer abp, Integer op, Integer an, Integer bn, Integer abn, Integer on){
		this.total = total;
		this.ap = ap;
		this.bp = bp;
		this.abp = abp;
		this.op = op;
		this.an = an;
		this.bn = bn;
		this.abn = abn;
		this.on = on;
	}

	public Integer getTotal() {
		return total;
	}

	public Integer getAp() {
		return ap;
	}

	public Integer getBp() {
		return bp;
	}

	public Integer getAbp() {
		return abp;
	}

	public Integer getOp() {
		return op;
	}

	public Integer getAn() {
		return an;
	}

	public Integer getBn() {
		return bn;
	}

	public Integer getAbn() {
		return abn;
	}

	public Integer getOn() {
		return on;
	}
}
