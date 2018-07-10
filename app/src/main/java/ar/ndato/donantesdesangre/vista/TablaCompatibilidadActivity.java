package ar.ndato.donantesdesangre.vista;

import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.factory.ABRhNFactory;
import ar.ndato.donantesdesangre.factory.ABRhPFactory;
import ar.ndato.donantesdesangre.factory.ARhNFactory;
import ar.ndato.donantesdesangre.factory.ARhPFactory;
import ar.ndato.donantesdesangre.factory.AbstractSangreFactory;
import ar.ndato.donantesdesangre.factory.BRhNFactory;
import ar.ndato.donantesdesangre.factory.BRhPFactory;
import ar.ndato.donantesdesangre.factory.ORhNFactory;
import ar.ndato.donantesdesangre.factory.ORhPFactory;
import ar.ndato.donantesdesangre.factory.SangreStringFactory;
import ar.ndato.donantesdesangre.sangre.Sangre;
import ar.ndato.donantesdesangre.visitor.VisitorDonacion;

public class TablaCompatibilidadActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
	
	private final int SELECCION_DONADOR = 0;
	private final int SELECCION_SANGRE_COMPLETA = 0;
	private final int SELECCION_GLOBULOS_ROJOS = 1;
	private final int SELECCION_PLASMA = 2;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tabla_compatibilidad);
		Spinner donadorReceptor = findViewById(R.id.donadorreceptor);
		Spinner sangreSpinner = findViewById(R.id.sangre);
		Spinner spinnerTipoDonacion = findViewById(R.id.tipodonacion);
		donadorReceptor.setOnItemSelectedListener(this);
		sangreSpinner.setOnItemSelectedListener(this);
		spinnerTipoDonacion.setOnItemSelectedListener(this);
		mostrar();
	}
	
	private void mostrar()
	{
		Donacion.Accion accion;
		Spinner donadorReceptor = findViewById(R.id.donadorreceptor);
		accion = donadorReceptor.getSelectedItemPosition() == SELECCION_DONADOR ? Donacion.Accion.DONAR : Donacion.Accion.RECIBIR;
		TextView textDonadorReceptor = findViewById(R.id.textDonadorReceptor);
		textDonadorReceptor.setText(accion == Donacion.Accion.RECIBIR ? R.string.donador : R.string.receptor);
		Spinner sangerSpinner = findViewById(R.id.sangre);
		AbstractSangreFactory sangreFactory = new SangreStringFactory((String)sangerSpinner.getSelectedItem());
		Sangre sangre = sangreFactory.crearSangre();
		Donacion.TipoDonacion tipoDonacion;
		Spinner spinnerTipoDonacion = findViewById(R.id.tipodonacion);
		if (spinnerTipoDonacion.getSelectedItemPosition() == SELECCION_SANGRE_COMPLETA) {
			tipoDonacion = Donacion.TipoDonacion.SANGRE_COMPLETA;
		} else if(spinnerTipoDonacion.getSelectedItemPosition() == SELECCION_GLOBULOS_ROJOS) {
			tipoDonacion = Donacion.TipoDonacion.GLOBULOS_ROJOS;
		} else {
			tipoDonacion = Donacion.TipoDonacion.PLASMA;
		}
		
		AbstractSangreFactory []sangres = {new ORhNFactory(), new ORhPFactory(), new ARhNFactory(), new ARhPFactory(), new BRhNFactory(), new BRhPFactory(), new ABRhNFactory(), new ABRhPFactory()};
		int []sangresIds = {R.id.compatibleOn, R.id.compatibleOp, R.id.compatibleAn, R.id.compatibleAp, R.id.compatibleBn, R.id.compatibleBp, R.id.compatibleABn, R.id.compatibleABp};
		
		for (int i = 0; i < sangresIds.length; i++) {
			VisitorDonacion visitor = sangre.getVisitorDonacion(accion, tipoDonacion);
			Sangre testeo = sangres[i].crearSangre();
			testeo.accept(visitor);
			ImageView imagen = findViewById(sangresIds[i]);
			if (visitor.puede()) {
				imagen.setImageResource(R.drawable.ic_check);
			} else {
				imagen.setImageResource(R.drawable.ic_close);
			}
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		mostrar();
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	
	}
}
