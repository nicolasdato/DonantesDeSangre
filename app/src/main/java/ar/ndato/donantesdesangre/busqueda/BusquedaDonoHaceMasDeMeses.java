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
		if (ultimaDonacion == null) {
			return true;
		}

		long hoyms = hoy.getTimeInMillis();
		long ultimams = ultimaDonacion.getTimeInMillis();

		return (hoyms - ultimams) >= meses * 30l * 24l * 60l * 60l * 1000l;
	}
}
