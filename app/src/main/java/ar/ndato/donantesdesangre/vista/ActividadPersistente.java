package ar.ndato.donantesdesangre.vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import java.io.File;

import ar.ndato.donantesdesangre.DonantesDeSangre;
import ar.ndato.donantesdesangre.datos.Datos;
import ar.ndato.donantesdesangre.datos.DatosException;
import ar.ndato.donantesdesangre.datos.DatosJson;

public abstract class ActividadPersistente extends AppCompatActivity {
	private DonantesDeSangre donantesDeSangre;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		donantesDeSangre = DonantesDeSangre.getInstance();
		if (donantesDeSangre.getDonantes().size() == 0) { //no hay datos, quisas hay datos guardados
			cargar(donantesDeSangre);
		}
	}
	
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
				ex.printStackTrace();
			}
		}
	}
	
	public void guardar(DonantesDeSangre donantesDeSangre) {
		File archivo = new File(getApplicationContext().getFilesDir(), getText(R.string.archivo_interno).toString());
		try {
			Datos datos = new DatosJson(archivo);
			donantesDeSangre.exportar(datos);
		} catch (DatosException ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	public DonantesDeSangre getDonantesDeSangre() {
		return donantesDeSangre;
	}
}
