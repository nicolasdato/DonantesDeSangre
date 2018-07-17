package ar.ndato.donantesdesangre.vista;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

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
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.menu_informacion:
            	mostrarInformacion();
            	return true;
            case R.id.menu_tabla_compatibilidad:
            	Intent intent = new Intent(this, TablaCompatibilidadActivity.class);
            	startActivity(intent);
            	return true;
			default:
                return super.onOptionsItemSelected(item);
        }
	}
	
	
	private void mostrarInformacion() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(R.string.informacion_mensage).setTitle(R.string.informacion);
		builder.setPositiveButton(R.string.ok, null);
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	protected void cargar(DonantesDeSangre donantesDeSangre) {
		File archivo = new File(getApplicationContext().getFilesDir(), getText(R.string.archivo_interno).toString());
		if (archivo.exists() && archivo.canRead()) {
			try {
				FileDescriptor archivoDescriptor = getContentResolver().openFileDescriptor(Uri.parse(archivo.toURI().toString()), "rw").getFileDescriptor();
				Datos datos = new DatosJson(archivoDescriptor);
				donantesDeSangre.importar(datos);
			} catch (IOException | DatosException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	protected void guardar(DonantesDeSangre donantesDeSangre) {
		File archivo = new File(getApplicationContext().getFilesDir(), getText(R.string.archivo_interno).toString());
		try {
			FileDescriptor archivoDescriptor = getContentResolver().openFileDescriptor(Uri.parse(archivo.toURI().toString()), "rw").getFileDescriptor();
			Datos datos = new DatosJson(archivoDescriptor);
			donantesDeSangre.exportar(datos);
		} catch (IOException | DatosException ex) {
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
