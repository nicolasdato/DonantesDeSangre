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
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		File archivo = new File(getApplicationContext().getFilesDir(), getText(R.string.archivo_interno).toString());
		DonantesDeSangre donantesDeSangre = DonantesDeSangre.getInstance();
		try {
			Datos datos = new DatosJson(archivo);
			donantesDeSangre.exportar(datos);
		} catch (DatosException ex) {
		
		}
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		DonantesDeSangre donantesDeSangre = DonantesDeSangre.getInstance();
		File archivo = new File(getApplicationContext().getFilesDir(), getText(R.string.archivo_interno).toString());
		if (archivo.exists() && archivo.canRead()) {
			try {
				Datos datos = new DatosJson(archivo);
				donantesDeSangre.importar(datos);
			} catch (DatosException ex) {
			
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
}
