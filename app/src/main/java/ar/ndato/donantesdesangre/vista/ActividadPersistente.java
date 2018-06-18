package ar.ndato.donantesdesangre.vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.io.File;

import ar.ndato.donantesdesangre.DonantesDeSangre;
import ar.ndato.donantesdesangre.datos.Datos;
import ar.ndato.donantesdesangre.datos.DatosException;
import ar.ndato.donantesdesangre.datos.DatosJson;

public abstract class ActividadPersistente extends ActividadBase {
	
	
	@Override
	public void onStop() {
		super.onStop();
		guardar(getDonantesDeSangre());
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		guardar(getDonantesDeSangre());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		cargar(getDonantesDeSangre());
	}
	
	public void cargar(DonantesDeSangre donantesDeSangre) {
		File archivo = new File(getApplicationContext().getFilesDir(), getText(R.string.archivo_interno).toString());
		if (archivo.exists() && archivo.canRead()) {
			try {
				Datos datos = new DatosJson(archivo);
				donantesDeSangre.importar(datos);
			} catch (DatosException ex) {
			
			}
		}
	}
	
	public void guardar(DonantesDeSangre donantesDeSangre) {
		File archivo = new File(getApplicationContext().getFilesDir(), getText(R.string.archivo_interno).toString());
		try {
			Datos datos = new DatosJson(archivo);
			donantesDeSangre.exportar(datos);
		} catch (DatosException ex) {
		
		}
	}
}
