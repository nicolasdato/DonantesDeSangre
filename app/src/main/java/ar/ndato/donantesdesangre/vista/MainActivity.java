package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;

import ar.ndato.donantesdesangre.DonantesDeSangre;

public class MainActivity extends ActividadPersistente {
	
	private DonantesDeSangre donantesDeSangre;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		donantesDeSangre = DonantesDeSangre.getInstance();
		super.setDonantesDeSangre(donantesDeSangre);
		cargar(donantesDeSangre);
	}
	
	public void agregarDonante(View view) {
		Intent intent = new Intent(this, AgregarDonanteActivity.class);
		intent.putExtra("donantesDeSangre", donantesDeSangre);
		startActivityForResult(intent, 0);
	}
	
	public void buscarDonante(View view) {
		Intent intent = new Intent(this, BuscarDonanteActivity.class);
		intent.putExtra("donantesDeSangre", donantesDeSangre);
		startActivity(intent);
	}
	
	public void agregarDonacion(View view) {
		/*Intent intent = new Intent(this, AgregarDonacionActivity.class);
		intent.putExtra("donantesDeSangre", donantesDeSangre);
		startActivity(intent);*/
	}
	
	public void estadisticas(View view) {
		/*Intent intent = new Intent(this, EstadisticasActivity.class);
		intent.putExtra("donantesDeSangre", donantesDeSangre);
		startActivity(intent);*/
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			int resultado;
			resultado = data.getExtras().getInt("texto");
			Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), resultado, Snackbar.LENGTH_SHORT);
			mensaje.show();
		}
	}
}
