package ar.ndato.donantesdesangre.busqueda;

import java.util.Calendar;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.Persona;

public class BusquedaDonoHaceMasDeMeses extends BusquedaCondicion {

	private Integer meses;

	public BusquedaDonoHaceMasDeMeses(Integer meses, Busqueda busqueda) {
		super(busqueda);
		this.meses = meses;
	}

	@Override
	protected Boolean condicion(Persona persona) {
		Calendar hoy = Calendar.getInstance();
		Calendar ultimaDonacion = null;

		if(persona.getDonaciones() == null || persona.getDonaciones().size() == 0)
			return true;

		for(Donacion donacion : persona.getDonaciones()){
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
