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
import java.util.Set;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.busqueda.Busqueda;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeDonarA;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeRecibirDe;
import ar.ndato.donantesdesangre.factory.AbstractSangreFactory;
import ar.ndato.donantesdesangre.factory.SangreStringFactory;

public class ABMDonanteActivity extends ActividadPersistente implements AdapterView.OnItemSelectedListener, DialogInterface.OnClickListener {
	
	public static final int ALTA = 0;
	public static final int MODIFICACION = 1;
	public static final int ALTA_YO = 2;
	
	private final int CODE_BUSQUEDA = 0;
	
	private Boolean agregarYo = false;
	private int tipo;
	private Persona donante;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_abm_donante);
		tipo = getIntent().getExtras().getInt("tipo", ALTA);
		
		Spinner anio = findViewById(R.id.anio);
		List<Integer> anios = new ArrayList<>();
		for (Integer a = Calendar.getInstance().get(Calendar.YEAR); a >= 1900; a--) {
			anios.add(a);
		}
		ArrayAdapter<Integer> aniosAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, anios);
		anio.setAdapter(aniosAdapter);
		anio.setSelection(0);
		Spinner mes = findViewById(R.id.mes);
		ArrayAdapter<String> mesAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, getResources().getStringArray(R.array.meses));
		mes.setAdapter(mesAdapter);
		mes.setSelection(Calendar.getInstance().get(Calendar.MONTH));
		Spinner dia = findViewById(R.id.dia);
		actualizarCantidadDeDias(dia, (Integer)anio.getSelectedItem(), mes.getSelectedItemPosition());
		dia.setSelection(Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - 1);
		Spinner sangre = findViewById(R.id.sangre);
		ArrayAdapter<String> sangreAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, getResources().getStringArray(R.array.sangres));
		sangre.setAdapter(sangreAdapter);
		sangre.setSelection(0);
		
		anio.setOnItemSelectedListener(this);
		mes.setOnItemSelectedListener(this);
		
		if (getDonantesDeSangre().getYo() == null || tipo == ALTA_YO) {
			agregarYo = true;
			if (tipo != ALTA_YO) {
				AlertDialog.Builder dialog = new AlertDialog.Builder(this);
				dialog.setMessage(R.string.crear_yo);
				dialog.setTitle(R.string.crear_yo_title);
				dialog.setCancelable(false);
				dialog.setPositiveButton(R.string.ok, this);
				dialog.create().show();
			}
			Switch favorito = findViewById(R.id.switch_es_favorito);
			favorito.setChecked(true);
		}
		
		switch (tipo) {
			case ALTA_YO:
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
	
	public void switchFavorito(View view) {
		Switch sw = (Switch)view;
		if (!sw.isChecked() && (agregarYo || (tipo == MODIFICACION && donante != null && donante.equals(getDonantesDeSangre().getYo())))) {
			sw.setChecked(true);
			Snackbar mensaje = Snackbar.make(view, R.string.editando_yo, Snackbar.LENGTH_SHORT);
			mensaje.show();
		}
	}
	
	private void setDatos(Persona donante) {
		if (donante == null) {
			return;
		}
		this.donante = donante;
		
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
		int yearidx = (Integer)anio.getItemAtPosition(0) - year;
		if (yearidx >= 0 && yearidx < anio.getCount()) {
			anio.setSelection(yearidx);
		}
		Integer seleccionado = dia.getSelectedItemPosition();
		actualizarCantidadDeDias(dia, (Integer)anio.getSelectedItem(), mes.getSelectedItemPosition());
		if(dia.getAdapter().getCount() > seleccionado) {
			dia.setSelection(seleccionado);
		} else {
			dia.setSelection(0);
		}
	}
	
	
	private void enAlta() {
		habilitarTodo();
		findViewById(R.id.boton_agregar_persona).setVisibility(View.VISIBLE);
		findViewById(R.id.boton_guardar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_modify_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_eliminar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_buscar_donante).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_buscar_donaciones).setVisibility(View.INVISIBLE);
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
		findViewById(R.id.boton_buscar_donaciones).setVisibility(View.VISIBLE);
	}
	
	private void enEdicion() {
		habilitarTodo();
		findViewById(R.id.boton_agregar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_guardar_persona).setVisibility(View.VISIBLE);
		findViewById(R.id.boton_modify_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_eliminar_persona).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_buscar_donante).setVisibility(View.INVISIBLE);
		findViewById(R.id.boton_buscar_donaciones).setVisibility(View.INVISIBLE);
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
		ArrayAdapter<Integer> diasAdapter = new ArrayAdapter<>(this, R.layout.spinner_item, dias);
		dia.setAdapter(diasAdapter);
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putBoolean("agregarYo", agregarYo);
		bundle.putSerializable("donante", donante);
		bundle.putInt("tipo", tipo);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		agregarYo = bundle.getBoolean("agregarYo");
		donante = (Persona)bundle.getSerializable("donante");
		tipo = bundle.getInt("tipo", ALTA);
	}
	
	public void agregarDonante(View view) {
		Persona persona = crearDonante(view);
		if (persona != null && getDonantesDeSangre().getDonantes().contains(persona)) {
			Snackbar mensaje = Snackbar.make(view, R.string.persona_existente, Snackbar.LENGTH_LONG);
			mensaje.show();
		} else if (persona != null){
			getDonantesDeSangre().agregarDonante(persona);
			if (agregarYo) {
				persona.setFavorito(true);
				if (getDonantesDeSangre().getYo() == null) {
					getDonantesDeSangre().setYo(persona);
				} else {
					getDonantesDeSangre().modificarDonante(getDonantesDeSangre().getYo(), persona);
				}
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
			getDonantesDeSangre().modificarDonante(donante, persona);
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
						setResult(RESULT_OK);
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
				Busqueda busqueda = null;
				Intent intent;
				String intentStr = "donador";
				switch (which){
					case DialogInterface.BUTTON_POSITIVE: {
						intentStr = "donador";
						break;
					}
					
					case DialogInterface.BUTTON_NEGATIVE: {
						intentStr = "receptor";
						break;
					}
				}
				if (busqueda != null) {
					intent = new Intent(ABMDonanteActivity.this, BuscarDonanteActivity.class);
					intent.putExtra(intentStr, donante);
					startActivityForResult(intent, CODE_BUSQUEDA);
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
	
	public void buscarDonaciones(View view) {
		Intent intent = new Intent(this, VerDonacionesActivity.class);
		intent.putExtra("donante", donante);
		startActivity(intent);
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK) {
			if (data != null && requestCode == CODE_BUSQUEDA) {
				Persona donante = (Persona) data.getSerializableExtra("donante");
				if (donante != null) {
					this.donante = donante;
					setDatos(donante);
					enVista();
				}
			}
		}
	}
}
