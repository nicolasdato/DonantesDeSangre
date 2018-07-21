package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import java.io.File;
import java.io.FileDescriptor;
import java.io.IOException;

import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;
import ar.ndato.donantesdesangre.datos.Datos;
import ar.ndato.donantesdesangre.datos.DatosException;
import ar.ndato.donantesdesangre.datos.DatosJson;

public class MainActivity extends ActividadPersistente {
	private final int CODE_LISTAR = 0;
	private final int CODE_ALTA = 1;
	private final int CODE_EXPORTAR = 2;
	private final int CODE_IMPORTAR = 3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		cargar(getDonantesDeSangre());
	}
	
	public void agregarDonante(View view) {
		Intent intent = new Intent(this, ABMDonanteActivity.class);
		intent.putExtra("tipo", ABMDonanteActivity.ALTA);
		startActivityForResult(intent, CODE_ALTA);
	}
	
	public void buscarDonante(View view) {
		Intent intent = new Intent(this, BuscarDonanteActivity.class);
		
		Intent intentParaLista = new Intent(this, ABMDonanteActivity.class);
		intentParaLista.putExtra("tipo", ABMDonanteActivity.MODIFICACION);
		
		Intent intentParaBusqueda = new Intent(this, ListarDonantesActivity.class);
		intentParaBusqueda.putExtra("intent", intentParaLista);
		
		intent.putExtra("intent", intentParaBusqueda);
		startActivity(intent);
	}
	
	public void misDatos(View view) {
		Intent intent = new Intent(this, ABMDonanteActivity.class);
		if (getDonantesDeSangre().getYo() == null) {
			intent.putExtra("tipo", ABMDonanteActivity.ALTA_YO);
			startActivityForResult(intent, CODE_ALTA);
		} else {
			intent.putExtra("tipo", ABMDonanteActivity.MODIFICACION);
			intent.putExtra("donante", getDonantesDeSangre().getYo());
			startActivityForResult(intent, CODE_ALTA);
		}
	}
	
	public void listarTodos(View view) {
		Intent intent = new Intent(this, ListarDonantesActivity.class);
		Intent intentParaBusqueda = new Intent(this, ABMDonanteActivity.class);
		intent.putExtra("busqueda", new BusquedaBase());
		intentParaBusqueda.putExtra("tipo", ABMDonanteActivity.MODIFICACION);
		intent.putExtra("intent", intentParaBusqueda);
		startActivityForResult(intent, CODE_LISTAR);
	}
	
	public void verDonaciones(View view) {
		Intent intent = new Intent(this, BuscarDonanteActivity.class);
		Intent intentParaLista = new Intent(this, VerDonacionesActivity.class);
		
		Intent intentParaBusqueda = new Intent(this, ListarDonantesActivity.class);
		intentParaBusqueda.putExtra("intent", intentParaLista);
		
		intent.putExtra("intent", intentParaBusqueda);
		startActivity(intent);
	}
	
	public void agregarDonacion(View view) {
		Intent intent = new Intent(this, ABMDonacionActivity.class);
		intent.putExtra("tipo", ABMDonacionActivity.ALTA);
		startActivityForResult(intent, CODE_ALTA);
	}
	
	public void estadisticas(View view) {
		Intent intent = new Intent(this, BuscarDonanteActivity.class);
		
		Intent intentParaEstadistica = new Intent(this, EstadisticaActivity.class);
		
		intent.putExtra("titulo", R.string.filtro_para_estadistica);
		intent.putExtra("intent", intentParaEstadistica);
		startActivity(intent);
	}
	
	public void importarDatos(View view) {
		Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
		intent.setType("application/json");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, CODE_IMPORTAR);
	}
	
	public void exportarDatos(View view) {
		Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
		intent.setType("application/json");
		intent.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent, CODE_EXPORTAR);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			if (requestCode == CODE_EXPORTAR || requestCode == CODE_IMPORTAR) {
				Uri uri = data.getData();
				if (uri != null && uri.getPath().length() > 0) {
					try {
						FileDescriptor fd = getContentResolver().openFileDescriptor(uri, "rw").getFileDescriptor();
						Datos datos = new DatosJson(fd);
						if (requestCode == CODE_EXPORTAR) {
							getDonantesDeSangre().exportar(datos);
							Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), R.string.exportado, Snackbar.LENGTH_SHORT);
							mensaje.show();
						} else {
							getDonantesDeSangre().mezclar(datos);
							Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), R.string.importado, Snackbar.LENGTH_SHORT);
							mensaje.show();
						}
					} catch (IOException | DatosException e) {
						e.printStackTrace();
						if (requestCode == CODE_EXPORTAR) {
							Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), R.string.fallo_exportado, Snackbar.LENGTH_LONG);
							mensaje.show();
						} else {
							Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), R.string.fallo_importado, Snackbar.LENGTH_LONG);
							mensaje.show();
						}
					}
				}
			} else  {
				int resultado;
				resultado = data.getExtras().getInt("texto");
				Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), resultado, Snackbar.LENGTH_SHORT);
				mensaje.show();
			}
		}
	}
}
