package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ar.ndato.donantesdesangre.DonantesDeSangre;
import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.factory.AbstractSangreFactory;
import ar.ndato.donantesdesangre.factory.SangreStringFactory;

public class AgegarDonanteActivity extends ActividadPersistente implements AdapterView.OnItemSelectedListener {

	private final int CALENDARIO_RESULTADO = 0;
	
	private void actualizarCantidadDeDias(Spinner dia, Integer anio, Integer mes) {
		Integer maxDias = 0;
		Calendar calendar = new GregorianCalendar(mes, anio, 1);
		maxDias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Integer> dias = new ArrayList<>();
		for (Integer d = 1; d <= maxDias; d++) {
			dias.add(d);
		}
		ArrayAdapter<Integer> diasAdapter = new ArrayAdapter<>(this, R.layout.activity_agegar_donante, dias);
		dia.setAdapter(diasAdapter);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agegar_donante);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		findViewById(R.id.boton_agregar_persona).requestFocus();
		Spinner anio = findViewById(R.id.anio);
		List<Integer> anios = new ArrayList<>();
		for (Integer a = Calendar.getInstance().get(Calendar.YEAR); a >= 1900; a--) {
			anios.add(a);
		}
		ArrayAdapter<Integer> aniosAdapter = new ArrayAdapter<>(this, R.layout.activity_agegar_donante, anios);
		anio.setAdapter(aniosAdapter);
		anio.setSelection(0);
		Spinner mes = findViewById(R.id.mes);
		mes.setSelection(Calendar.getInstance().get(Calendar.MONTH));
		Spinner dia = findViewById(R.id.dia);
		actualizarCantidadDeDias(dia, (Integer)anio.getSelectedItem(), mes.getSelectedItemPosition());
		dia.setSelection(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1);
		
		anio.setOnItemSelectedListener(this);
		mes.setOnItemSelectedListener(this);
	}
	
	public void agregarDonante(View view) {
		EditText nombre = findViewById(R.id.nombre);
		EditText localidad = findViewById(R.id.localidad);
		EditText provincia = findViewById(R.id.provincia);
		EditText direccion = findViewById(R.id.direccion);
		EditText email = findViewById(R.id.email);
		EditText telefono = findViewById(R.id.telefono);
		
		if (nombre.getText().toString().equals("")) {
			Snackbar mensaje = Snackbar.make(view, R.string.falta_nombre, Snackbar.LENGTH_LONG);
			mensaje.show();
		}
		else {
			Spinner mes = findViewById(R.id.mes);
			Spinner anio = findViewById(R.id.anio);
			Spinner dia = findViewById(R.id.anio);
			Spinner sangre = findViewById(R.id.sangre);
			AbstractSangreFactory sangreFactory = new SangreStringFactory((String)sangre.getSelectedItem());
			Calendar calendario = new GregorianCalendar((Integer)anio.getSelectedItem(), mes.getSelectedItemPosition(), dia.getSelectedItemPosition() + 1);
			Persona persona = new Persona(nombre.getText().toString(), localidad.getText().toString(), provincia.getText().toString(), direccion.getText().toString(),
					telefono.getText().toString(), email.getText().toString(), false, calendario, sangreFactory.crearSangre());
			DonantesDeSangre donantesDeSangre = DonantesDeSangre.getInstance();
			if (donantesDeSangre.getDonantes().contains(persona)) {
				Snackbar mensaje = Snackbar.make(view, R.string.persona_existente, Snackbar.LENGTH_LONG);
				mensaje.show();
			}
			else {
				donantesDeSangre.agregarDonante(persona);
				Intent intent = new Intent();
				intent.putExtra("texto", R.string.agregado_correcto);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (view.getId() == R.id.mes || view.getId() == R.id.anio) {
			Spinner mes = findViewById(R.id.mes);
			Spinner anio = findViewById(R.id.anio);
			Spinner dia = findViewById(R.id.anio);
			Integer seleccionado = dia.getSelectedItemPosition();
			actualizarCantidadDeDias(dia, (Integer)anio.getSelectedItem(), mes.getSelectedItemPosition());
			if(dia.getAdapter().getCount() > seleccionado) {
				dia.setSelection(seleccionado);
			} else {
				dia.setSelection(0);
			}
		}
	}
	
	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	
	}
}
