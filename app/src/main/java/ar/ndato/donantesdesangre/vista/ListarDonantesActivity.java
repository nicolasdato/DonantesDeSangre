package ar.ndato.donantesdesangre.vista;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import ar.ndato.donantesdesangre.DonantesDeSangre;
import ar.ndato.donantesdesangre.busqueda.Busqueda;

public class ListarDonantesActivity extends ActividadBase {
	Busqueda busqueda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_donantes);
		busqueda = (Busqueda)getIntent().getSerializableExtra("busqueda");
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putSerializable("busqueda", busqueda);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		busqueda = (Busqueda)bundle.getSerializable("busqueda");
	}
}
