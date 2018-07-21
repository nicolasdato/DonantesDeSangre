package ar.ndato.donantesdesangre.vista;

import android.os.Bundle;
import android.widget.TextView;

import ar.ndato.donantesdesangre.Estadistica;
import ar.ndato.donantesdesangre.busqueda.Busqueda;

public class EstadisticaActivity extends ActividadPersistente {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_estadistica);
		Busqueda busqueda = (Busqueda)getIntent().getSerializableExtra("busqueda");
		
		Estadistica estadistica = getDonantesDeSangre().getEstadistica(busqueda);
		Integer total = estadistica.getTotal();
		if (total > 0) {
			TextView text;
			text = findViewById(R.id.cantidadOn);
			text.setText("" + estadistica.getOn() + " (" + ((estadistica.getOn() * 100) / total) + "%)");
			text = findViewById(R.id.cantidadOp);
			text.setText("" + estadistica.getOp() + " (" + ((estadistica.getOp() * 100) / total) + "%)");
			text = findViewById(R.id.cantidadAn);
			text.setText("" + estadistica.getAn() + " (" + ((estadistica.getAn() * 100) / total) + "%)");
			text = findViewById(R.id.cantidadAp);
			text.setText("" + estadistica.getAp() + " (" + ((estadistica.getAp() * 100) / total) + "%)");
			text = findViewById(R.id.cantidadBn);
			text.setText("" + estadistica.getBn() + " (" + ((estadistica.getBn() * 100) / total) + "%)");
			text = findViewById(R.id.cantidadBp);
			text.setText("" + estadistica.getBp() + " (" + ((estadistica.getBp() * 100) / total) + "%)");
			text = findViewById(R.id.cantidadABn);
			text.setText("" + estadistica.getAbn() + " (" + ((estadistica.getAbn() * 100) / total) + "%)");
			text = findViewById(R.id.cantidadABp);
			text.setText("" + estadistica.getAbp() + " (" + ((estadistica.getAbp() * 100) / total) + "%)");
		}
		
	}
}
