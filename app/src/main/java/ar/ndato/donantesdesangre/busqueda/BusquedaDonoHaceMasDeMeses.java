package ar.ndato.donantesdesangre.busqueda;

import java.util.Calendar;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.DonantesDeSangre;
import ar.ndato.donantesdesangre.Persona;

public class BusquedaDonoHaceMasDeMeses extends BusquedaCondicion {

	private Integer meses;
	private DonantesDeSangre donantesDeSangre;

	public BusquedaDonoHaceMasDeMeses(Integer meses, DonantesDeSangre donantesDeSangre, Busqueda busqueda) {
		super(busqueda);
		this.meses = meses;
		this.donantesDeSangre = donantesDeSangre;
	}

	@Override
	protected Boolean condicion(Persona persona) {
		Calendar hoy = Calendar.getInstance();
		Calendar ultimaDonacion = null;

		for(Donacion donacion : donantesDeSangre.getDonaciones(persona)){
			if(ultimaDonacion == null) {
				ultimaDonacion = donacion.getFecha();
			}
			else if(donacion.getFecha().after(ultimaDonacion)){
				ultimaDonacion = donacion.getFecha();
			}
		}

		hoy.add(Calendar.DAY_OF_MONTH, -ultimaDonacion.get(Calendar.DAY_OF_MONTH));
		hoy.add(Calendar.MONTH, -ultimaDonacion.get(Calendar.MONTH));
		hoy.add(Calendar.YEAR, -ultimaDonacion.get(Calendar.YEAR));

		return hoy.get(Calendar.YEAR) * 12 + hoy.get(Calendar.MONTH) >= meses;
	}
}
