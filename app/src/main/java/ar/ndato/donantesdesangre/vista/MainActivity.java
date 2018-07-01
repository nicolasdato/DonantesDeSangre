package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;

public class MainActivity extends ActividadPersistente {
	private final int CODE_LISTAR = 0;
	private final int CODE_ALTA = 1;
	private final int CODE_REGRESA_ABM = 2;

	
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
		Intent intentParaBusqueda = new Intent(this, ABMDonanteActivity.class);
		intentParaBusqueda.putExtra("tipo", ABMDonanteActivity.MODIFICACION);
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
		intent.putExtra("busqueda", new BusquedaBase());
		startActivityForResult(intent, CODE_LISTAR);
	}
	
	public void verDonaciones(View view) {
		Intent intent = new Intent(this, BuscarDonanteActivity.class);
		Intent intentParaBusqueda = new Intent(this, VerDonacionesActivity.class);
		intent.putExtra("intent", intentParaBusqueda);
		startActivity(intent);
	}
	
	public void agregarDonacion(View view) {
		Intent intent = new Intent(this, ABMDonacionActivity.class);
		intent.putExtra("tipo", ABMDonacionActivity.ALTA);
		startActivityForResult(intent, CODE_ALTA);
	}
	
	public void estadisticas(View view) {
		/*Intent intent = new Intent(this, EstadisticasActivity.class);
		intent.putExtra("donantesDeSangre", getDonantesDeSangre());
		startActivity(intent);*/
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			if (requestCode == CODE_REGRESA_ABM) {
				listarTodos(findViewById(R.id.listar_todos));
			}
			if (data != null) {
				if (requestCode == CODE_LISTAR) {
					Persona donante = (Persona) data.getSerializableExtra("donante");
					if (donante != null) {
						Intent intent = new Intent(this, ABMDonanteActivity.class);
						intent.putExtra("donante", donante);
						intent.putExtra("tipo", ABMDonanteActivity.MODIFICACION);
						startActivityForResult(intent, CODE_REGRESA_ABM);
					}
				} else if (requestCode == CODE_ALTA) {
					int resultado;
					resultado = data.getExtras().getInt("texto");
					Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), resultado, Snackbar.LENGTH_SHORT);
					mensaje.show();
				}
			}
		}
	}
}
