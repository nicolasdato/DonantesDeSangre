package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void agregarDonante(View view) {
		Intent intent = new Intent(this, AgegarDonanteActivity.class);
		startActivity(intent);
	}

	public void buscarDonante(View view) {
		/*Intent intent = new Intent(this, BuscarDonanteActivity.class);
		startActivity(intent);*/
	}

	public void agregarDonacion(View view) {
		/*Intent intent = new Intent(this, AgregarDonacionActivity.class);
		startActivity(intent);*/
	}

	public void estadisticas(View view) {
		/*Intent intent = new Intent(this, EstadisticasActivity.class);
		startActivity(intent);*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
}
