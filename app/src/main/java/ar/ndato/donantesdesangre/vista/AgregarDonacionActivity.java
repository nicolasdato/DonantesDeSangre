package ar.ndato.donantesdesangre.vista;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.visitor.VisitorDonaA;

public class AgregarDonacionActivity extends ActividadPersistente implements AdapterView.OnItemSelectedListener {
	
	private final int CODE_DONADOR = 0;
	private final int CODE_RECEPTOR = 0;
	
	private Persona donador = null;
	private Persona receptor = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_agregar_donacion);
		
		Spinner anio = findViewById(R.id.anio);
		List<Integer> anios = new ArrayList<>();
		for (Integer a = Calendar.getInstance().get(Calendar.YEAR); a >= 1900; a--) {
			anios.add(a);
		}
		ArrayAdapter<Integer> aniosAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, anios);
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
	
	private void actualizarCantidadDeDias(Spinner dia, Integer anio, Integer mes) {
		Integer maxDias;
		Calendar calendar = new GregorianCalendar(anio, mes, 1);
		maxDias = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		List<Integer> dias = new ArrayList<>();
		for (Integer d = 1; d <= maxDias; d++) {
			dias.add(d);
		}
		ArrayAdapter<Integer> diasAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dias);
		dia.setAdapter(diasAdapter);
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putSerializable("donador", donador);
		bundle.putSerializable("receptor", receptor);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		donador = (Persona)bundle.getSerializable("donador");
		receptor = (Persona)bundle.getSerializable("receptor");
	}
	
	public void buscarDonador(View view) {
		Intent intent = new Intent(this, BuscarDonanteActivity.class);
		if (receptor != null) {
			intent.putExtra("receptor", receptor);
		}
		startActivityForResult(intent, CODE_DONADOR);
	}
	
	public void buscarReceptor(View view) {
		Intent intent = new Intent(this, BuscarDonanteActivity.class);
		if (donador != null) {
			intent.putExtra("donador", donador);
		}
		startActivityForResult(intent, CODE_RECEPTOR);
	}
	
	public void agregarDonacion(View view) {
		Switch receptorSwitch = findViewById(R.id.switch_receptor);
		if (donador == null) {
			Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), getText(R.string.falta_donador), Snackbar.LENGTH_SHORT);
			mensaje.show();
		} else if (receptor == null && receptorSwitch.isChecked()) {
			Snackbar mensaje = Snackbar.make(findViewById(R.id.main_activity), getText(R.string.falta_receptor), Snackbar.LENGTH_SHORT);
			mensaje.show();
		} else {
			Spinner mes = findViewById(R.id.mes);
			Spinner anio = findViewById(R.id.anio);
			Spinner dia = findViewById(R.id.dia);
			Calendar calendario = new GregorianCalendar((Integer)anio.getSelectedItem(), mes.getSelectedItemPosition(), dia.getSelectedItemPosition() + 1);
			final Donacion donacion = new Donacion(receptor, calendario);
			
			if (receptorSwitch.isChecked()) {
				VisitorDonaA visitor = donador.getSangre().getVisitorDonaA();
				receptor.getSangre().accept(visitor);
				if (!visitor.puedeDonar()) {
					
					DialogInterface.OnClickListener listenerEliminar = new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							switch (which){
								case DialogInterface.BUTTON_POSITIVE:
									nuevaDonacion(donador, donacion);
									break;
								
								case DialogInterface.BUTTON_NEGATIVE:
									break;
							}
						}
					};
					AlertDialog.Builder dialog = new AlertDialog.Builder(this);
					dialog.setMessage(R.string.pergunta_donacion_no_compatible);
					dialog.setTitle(R.string.pergunta_donacion_no_compatible_title);
					dialog.setCancelable(false);
					dialog.setPositiveButton(R.string.ok, listenerEliminar);
					dialog.setNegativeButton(R.string.no, listenerEliminar);
					dialog.create().show();
					return;
				}
			}

			nuevaDonacion(donador, donacion);
		}
	}
	
	private void nuevaDonacion(Persona donador, Donacion donacion) {
		getDonantesDeSangre().agregarDonacion(donador, donacion);
		Intent intentResult = new Intent();
		intentResult.putExtra("texto", getText(R.string.donacion_agregada));
		setResult(RESULT_OK, intentResult);
		finish();
	}
	
	public void clickSwitchReceptor(View view) {
		Switch receptorSwitch = (Switch)view;
		TextView receptorText = findViewById(R.id.receptor);
		if (!receptorSwitch.isChecked()) {
			receptor = null;
			receptorText.setText(getText(R.string.sin_receptor_especifico));
		} else {
			receptorText.setText("");
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		Persona persona;
		if (resultCode == RESULT_OK) {
			if (requestCode == CODE_DONADOR) {
				persona = (Persona)data.getSerializableExtra("donante");
				if (persona != null) {
					donador = persona;
					TextView donadorText = findViewById(R.id.donador);
					donadorText.setText(persona.getNombre());
				}
			} else if (requestCode == CODE_RECEPTOR) {
				persona = (Persona)data.getSerializableExtra("donante");
				if (persona != null) {
					receptor = persona;
					TextView receptorText = findViewById(R.id.receptor);
					receptorText.setText(persona.getNombre());
				}
			}
		}
	}
	
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		if (parent.getId() == R.id.mes || parent.getId() == R.id.anio) {
			Spinner mes = findViewById(R.id.mes);
			Spinner anio = findViewById(R.id.anio);
			Spinner dia = findViewById(R.id.dia);
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
