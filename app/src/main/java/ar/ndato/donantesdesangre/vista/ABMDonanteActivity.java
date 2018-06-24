package ar.ndato.donantesdesangre.vista;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.busqueda.Busqueda;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeDonarA;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeRecibirDe;
import ar.ndato.donantesdesangre.factory.AbstractSangreFactory;
import ar.ndato.donantesdesangre.factory.SangreStringFactory;

public class ABMDonanteActivity extends ActividadPersistente implements AdapterView.OnItemSelectedListener, DialogInterface.OnClickListener {
	
	private Boolean agregarYo = false;
	public enum Tipo {
		ALTA,
		MODIFICACION
	}
	private Tipo tipo;
	private Persona donante;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abm_donante);
		tipo = Tipo.values()[getIntent().getExtras().getInt("tipo", Tipo.ALTA.ordinal())];
		
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
		
		if (getDonantesDeSangre().getYo() == null) {
			agregarYo = true;
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage(R.string.crear_yo);
			dialog.setTitle(R.string.crear_yo_title);
			dialog.setCancelable(false);
			dialog.setPositiveButton(R.string.ok, this);
			dialog.create().show();
			Switch favorito = findViewById(R.id.switch_es_favorito);
			favorito.setChecked(true);
		}
		
		switch (tipo) {
			case ALTA:
				enAlta();
				break;
			case MODIFICACION:
				donante = (Persona)getIntent().getSerializableExtra("donante");
				enVista();
				setDatos(donante);
				break;
		}
	}
	
	private void setDatos(Persona donante) {
		if (donante == null) {
			return;
		}
		
		EditText nombre = findViewById(R.id.nombre);
		EditText localidad = findViewById(R.id.localidad);
		EditText provincia = findViewById(R.id.provincia);
		EditText direccion = findViewById(R.id.direccion);
		EditText telefono = findViewById(R.id.telefono);
		EditText email = findViewById(R.id.email);
		Spinner dia = findViewById(R.id.dia);
		Spinner mes = findViewById(R.id.mes);
		Spinner anio = findViewById(R.id.anio);
		Switch es_favorito = findViewById(R.id.switch_es_favorito);
		Spinner sangre = findViewById(R.id.sangre);
		
		nombre.setText(donante.getNombre());
		localidad.setText(donante.getLocalidad());
		provincia.setText(donante.getProvincia());
		direccion.setText(donante.getDireccion());
		telefono.setText(donante.getTelefono());
		email.setText(donante.getMail());
		es_favorito.setChecked(donante.isFavorito());
		for(int i = 0; i < sangre.getCount(); i++) {
			AbstractSangreFactory asf = new SangreStringFactory((String)sangre.getItemAtPosition(i));
			if(donante.getSangre().equals(asf.crearSangre())) {
				sangre.setSelection(i);
				break;
			}
		}
		int dof = donante.getNacimiento().get(Calendar.DAY_OF_MONTH);
		dia.setSelection(dof - 1);
		int month = donante.getNacimiento().get(Calendar.MONTH);
		mes.setSelection(month);
		int year = donante.getNacimiento().get(Calendar.YEAR);
		int yearidx = year - (Integer)anio.getItemAtPosition(0);
		if (yearidx >= 0 && yearidx < anio.getCount()) {
			anio.setSelection(yearidx);
		}
	}
	
	
	private void enAlta() {
		habilitarTodo();
		findViewById(R.id.boton_agregar_persona).setVisibility(View.VISIBLE);
		findViewById(R.id.boton_guardar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_modify_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_eliminar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_buscar_donante).setVisibility(View.INVISIBLE);
	}
	
	private void enVista() {
		deshabilitarTodo();
		findViewById(R.id.boton_agregar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_guardar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_modify_persona).setVisibility(View.VISIBLE);
		if (donante.equals(getDonantesDeSangre().getYo())) {
			findViewById(R.id.boton_eliminar_persona).setVisibility(View.INVISIBLE);
		} else {
			findViewById(R.id.boton_eliminar_persona).setVisibility(View.VISIBLE);
		}
		findViewById(R.id.boton_buscar_donante).setVisibility(View.VISIBLE);
	}
	
	private void enEdicion() {
		habilitarTodo();
		findViewById(R.id.boton_agregar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_guardar_persona).setVisibility(View.VISIBLE);
		findViewById(R.id.boton_modify_persona).setVisibility(View.INVISIBLE);
		if (donante.equals(getDonantesDeSangre().getYo())) {
			findViewById(R.id.boton_eliminar_persona).setVisibility(View.INVISIBLE);
		} else {
			findViewById(R.id.boton_eliminar_persona).setVisibility(View.VISIBLE);
		}
		findViewById(R.id.boton_buscar_donante).setVisibility(View.VISIBLE);
	}
	
	private void cambiarEditable(boolean estado) {
		int ids[] = {R.id.nombre, R.id.localidad, R.id.provincia, R.id.direccion, R.id.telefono, R.id.email, R.id.mes, R.id.anio, R.id.dia, R.id.sangre, R.id.switch_es_favorito};
		for(int id : ids) {
			findViewById(id).setEnabled(estado);
		}
	}
	
	private void habilitarTodo() {
		cambiarEditable(true);
	}
	
	private void deshabilitarTodo() {
		cambiarEditable(false);
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
		bundle.putBoolean("agregarYo", agregarYo);
		bundle.putSerializable("donante", donante);
		bundle.putInt("tipo", tipo.ordinal());
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		agregarYo = bundle.getBoolean("agregarYo");
		donante = (Persona)bundle.getSerializable("donante");
		tipo = Tipo.values()[bundle.getInt("tipo")];
	}
	
	public void agregarDonante(View view) {
		Persona persona = crearDonante(view);
		if (persona == null) {
			Snackbar mensaje = Snackbar.make(view, R.string.error_crear_donante, Snackbar.LENGTH_LONG);
			mensaje.show();
		} else if (getDonantesDeSangre().getDonantes().contains(persona)) {
			Snackbar mensaje = Snackbar.make(view, R.string.persona_existente, Snackbar.LENGTH_LONG);
			mensaje.show();
		} else {
			getDonantesDeSangre().agregarDonante(persona);
			if (agregarYo) {
				persona.setFavorito(true);
				getDonantesDeSangre().setYo(persona);
			}
			Intent intent = new Intent();
			intent.putExtra("texto", R.string.agregado_correcto);
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	
	public void modificarDonante(View view) {
		enEdicion();
	}
	
	public void guardarDonante(View view) {
		Persona persona = crearDonante(view);
		if (persona == null) {
			Snackbar mensaje = Snackbar.make(view, R.string.error_crear_donante, Snackbar.LENGTH_LONG);
			mensaje.show();
		} else {
			getDonantesDeSangre().agregarDonante(persona);
			if (getDonantesDeSangre().getYo().equals(donante)) {
				getDonantesDeSangre().setYo(persona);
			}
			getDonantesDeSangre().quitarDonante(donante);
			donante = persona;
			Snackbar mensaje = Snackbar.make(view, R.string.persona_guardada, Snackbar.LENGTH_LONG);
			mensaje.show();
		}
		enVista();
	}
	
	public void eliminarDonante(View view) {
		DialogInterface.OnClickListener listenerEliminar = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
					case DialogInterface.BUTTON_POSITIVE:
						getDonantesDeSangre().quitarDonante(donante);
						finish();
						break;
		
					case DialogInterface.BUTTON_NEGATIVE:
						break;
				}
			}
		};
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage(R.string.pergunta_eliminar_donante);
		dialog.setTitle(R.string.pergunta_eliminar_donante_title);
		dialog.setCancelable(false);
		dialog.setPositiveButton(R.string.ok, listenerEliminar);
		dialog.setNegativeButton(R.string.no, listenerEliminar);
		dialog.create().show();
	}
	
	public void buscarDonante(View view) {
		DialogInterface.OnClickListener listenerBuscar = new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which){
					case DialogInterface.BUTTON_POSITIVE: {
						Intent intent = new Intent(ABMDonanteActivity.this, BuscarDonanteActivity.class);
						Busqueda busqueda = new BusquedaPuedeRecibirDe(donante.getSangre(), new BusquedaBase());
						intent.putExtra("busqueda", busqueda);
						startActivity(intent);
						break;
					}
					
					case DialogInterface.BUTTON_NEGATIVE: {
						Intent intent = new Intent(ABMDonanteActivity.this, BuscarDonanteActivity.class);
						Busqueda busqueda = new BusquedaPuedeDonarA(donante.getSangre(), new BusquedaBase());
						intent.putExtra("busqueda", busqueda);
						startActivity(intent);
						break;
					}
				}
			}
		};
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setMessage(R.string.pergunta_que_buscar);
		dialog.setTitle(R.string.pergunta_que_buscar_title);
		dialog.setCancelable(true);
		dialog.setPositiveButton(R.string.done, listenerBuscar);
		dialog.setNegativeButton(R.string.reciba, listenerBuscar);
		dialog.create().show();
	}
	
	public Persona crearDonante(View view) {
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
			Spinner dia = findViewById(R.id.dia);
			Spinner sangre = findViewById(R.id.sangre);
			Switch sw = findViewById(R.id.switch_es_favorito);
			AbstractSangreFactory sangreFactory = new SangreStringFactory((String)sangre.getSelectedItem());
			Calendar calendario = new GregorianCalendar((Integer)anio.getSelectedItem(), mes.getSelectedItemPosition(), dia.getSelectedItemPosition() + 1);
			Persona persona = new Persona(nombre.getText().toString(), localidad.getText().toString(), provincia.getText().toString(), direccion.getText().toString(),
					telefono.getText().toString(), email.getText().toString(), sw.isChecked(), calendario, sangreFactory.crearSangre());
			return persona;
		}
		return null;
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
	
	@Override
	public void onClick(DialogInterface dialog, int which) {
		dialog.dismiss();
	}
}
