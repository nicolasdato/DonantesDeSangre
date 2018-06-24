package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;

public class MainActivity extends ActividadPersistente {
	private final int CODE_LISTAR = 0;
	private final int CODE_ABM = 1;
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
		startActivityForResult(intent, CODE_ABM);
	}
	
	public void buscarDonante(View view) {
		Intent intent = new Intent(this, BuscarDonanteActivity.class);
		startActivity(intent);
	}
	
	public void misDatos(View view) {
		Intent intent = new Intent(this, ABMDonanteActivity.class);
		if (getDonantesDeSangre().getYo() == null) {
			intent.putExtra("tipo", ABMDonanteActivity.ALTA_YO);
			startActivityForResult(intent, CODE_ABM);
		} else {
			intent.putExtra("tipo", ABMDonanteActivity.MODIFICACION);
			intent.putExtra("donante", getDonantesDeSangre().getYo());
			startActivity(intent);
		}
	}
	
	public void listarTodos(View view) {
		Intent intent = new Intent(this, ListarDonantesActivity.class);
		intent.putExtra("busqueda", new BusquedaBase());
		startActivityForResult(intent, CODE_LISTAR);
	}
	
	public void agregarDonacion(View view) {
		/*Intent intent = new Intent(this, AgregarDonacionActivity.class);
		intent.putExtra("donantesDeSangre", getDonantesDeSangre());
		startActivity(intent);*/
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
				} else if (requestCode == CODE_ABM) {
					int resultado;
					resultado = data.getExtras().getInt("texto");
					Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), resultado, Snackbar.LENGTH_SHORT);
					mensaje.show();
				}
			}
		}
	}
}
