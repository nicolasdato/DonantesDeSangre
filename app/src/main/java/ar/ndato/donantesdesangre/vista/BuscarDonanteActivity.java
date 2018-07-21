package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.busqueda.Busqueda;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;
import ar.ndato.donantesdesangre.busqueda.BusquedaDonoHaceMasDeMeses;
import ar.ndato.donantesdesangre.busqueda.BusquedaEdadMayorA;
import ar.ndato.donantesdesangre.busqueda.BusquedaEdadMenorA;
import ar.ndato.donantesdesangre.busqueda.BusquedaEsFavorito;
import ar.ndato.donantesdesangre.busqueda.BusquedaLocalidad;
import ar.ndato.donantesdesangre.busqueda.BusquedaNombre;
import ar.ndato.donantesdesangre.busqueda.BusquedaProvincia;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeDonarA;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeRecibirDe;
import ar.ndato.donantesdesangre.busqueda.BusquedaTieneSangre;
import ar.ndato.donantesdesangre.factory.AbstractSangreFactory;
import ar.ndato.donantesdesangre.factory.SangreStringFactory;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class BuscarDonanteActivity extends ActividadPersistente {
	private final int CODE_BUSQUEDA = 0;
	private Intent intentParaBusqueda = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_donante);
		intentParaBusqueda = (Intent)getIntent().getParcelableExtra("intent");
		Persona donador = (Persona)getIntent().getSerializableExtra("donador");
		Persona receptor = (Persona)getIntent().getSerializableExtra("receptor");
		int titulo_id = getIntent().getIntExtra("titulo", R.string.buscar_donante);
		TextView titulo = findViewById(R.id.titulo);
		titulo.setText(titulo_id);
		CheckBox swDonar = findViewById(R.id.switch_donar_a);
		CheckBox swRecibir = findViewById(R.id.switch_recibir_de);
		CheckBox swTs = findViewById(R.id.switch_tiene_sangre);
		Spinner sangre = findViewById(R.id.sangre);
		Spinner ultimaDonacion = findViewById(R.id.ultima_donacion);
		Spinner tipoDonacion = findViewById(R.id.tipodonacion);
		sangre.setEnabled(false);
		tipoDonacion.setEnabled(false);
		ultimaDonacion.setEnabled(false);
		if (receptor != null) {
			swRecibir.setChecked(false);
			swTs.setChecked(false);
			swDonar.setChecked(true);
			clickSwitch(swDonar);
		} else if (donador != null) {
			swDonar.setChecked(false);
			swTs.setChecked(false);
			swRecibir.setChecked(true);
			clickSwitch(swRecibir);
		}
		if (receptor != null || donador != null) {
			Persona persona = receptor != null ? receptor : donador;
			for(int i = 0; i < sangre.getCount(); i++) {
				AbstractSangreFactory asf = new SangreStringFactory((String)sangre.getItemAtPosition(i));
				if(persona.getSangre().equals(asf.crearSangre())) {
					sangre.setSelection(i);
					break;
				}
			}
			if (persona.getLocalidad() != null && persona.getLocalidad().length() > 0) {
				TextView localidad = findViewById(R.id.localidad);
				localidad.setText(persona.getLocalidad());
				CheckBox swLocalidad = findViewById(R.id.switch_localidad);
				swLocalidad.setChecked(true);
				clickSwitch(swLocalidad);
			}
			if (persona.getProvincia() != null && persona.getProvincia().length() > 0) {
				TextView provincia = findViewById(R.id.provincia);
				provincia.setText(persona.getProvincia());
				CheckBox swProvincia = findViewById(R.id.switch_provincia);
				swProvincia.setChecked(true);
				clickSwitch(swProvincia);
			}
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putParcelable("intentParaBusqueda", intentParaBusqueda);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		intentParaBusqueda = (Intent)bundle.getParcelable("intentParaBusqueda");
	}
	
	public void clickSwitch(View view) {
		CheckBox sw = (CheckBox)view;
		CheckBox swDonar = findViewById(R.id.switch_donar_a);
		CheckBox swRecibir = findViewById(R.id.switch_recibir_de);
		CheckBox swTs = findViewById(R.id.switch_tiene_sangre);
		switch (view.getId()) {
			case R.id.switch_nombre:
				findViewById(R.id.nombre).setEnabled(sw.isChecked());
				break;
				
			case R.id.switch_localidad:
				findViewById(R.id.localidad).setEnabled(sw.isChecked());
				break;
				
			case R.id.switch_provincia:
				findViewById(R.id.provincia).setEnabled(sw.isChecked());
				break;
				
			case R.id.switch_tiene_sangre:
			case R.id.switch_donar_a:
			case R.id.switch_recibir_de:
				 if (swTs.isChecked()) {
					if (view.getId() == R.id.switch_donar_a || view.getId() == R.id.switch_recibir_de) {
						swTs.setChecked(false);
					}
					else {
						swRecibir.setChecked(false);
						swDonar.setChecked(false);
					}
				}
				findViewById(R.id.sangre).setEnabled(swDonar.isChecked() || swRecibir.isChecked() || swTs.isChecked());
				findViewById(R.id.tipodonacion).setEnabled(swDonar.isChecked() || swRecibir.isChecked());
				break;
				
			case R.id.switch_edad_mayor:
			case R.id.switch_edad_menor: {
				CheckBox sw1 = findViewById(R.id.switch_edad_mayor);
				CheckBox sw2 = findViewById(R.id.switch_edad_menor);
				findViewById(R.id.edad).setEnabled(sw1.isChecked() || sw2.isChecked());
				break;
			}
			
			case R.id.switch_ultima_donacion:
				findViewById(R.id.ultima_donacion).setEnabled(sw.isChecked());
				break;
		}
	}
	
	public void buscarDonante(View view) {
		Busqueda busqueda = new BusquedaBase();
		int spinner_ultima_donacion_to_int[] = {1, 2, 6, 12, 24};
		
		CheckBox swNombre = findViewById(R.id.switch_nombre);
		CheckBox swProvincia = findViewById(R.id.switch_provincia);
		CheckBox swLocalidad = findViewById(R.id.switch_localidad);
		CheckBox swMayorA = findViewById(R.id.switch_edad_mayor);
		CheckBox swMenorA = findViewById(R.id.switch_edad_menor);
		CheckBox swDonaA = findViewById(R.id.switch_donar_a);
		CheckBox swTieneSangre = findViewById(R.id.switch_tiene_sangre);
		CheckBox swRecibeDe = findViewById(R.id.switch_recibir_de);
		CheckBox swEsFavorito = findViewById(R.id.switch_es_favorito);
		CheckBox swUltimaDonacion = findViewById(R.id.switch_ultima_donacion);
		TextView nombre = findViewById(R.id.nombre);
		TextView provincia = findViewById(R.id.provincia);
		TextView localidad = findViewById(R.id.localidad);
		TextView edad = findViewById(R.id.edad);
		Spinner sangre = findViewById(R.id.sangre);
		Spinner tipoDonacion = findViewById(R.id.tipodonacion);
		Spinner ultima_donacion = findViewById(R.id.ultima_donacion);
		if (swMayorA.isChecked() || swMenorA.isChecked()) {
			if (edad.getText().toString().isEmpty()) {
				Snackbar mensaje = Snackbar.make(view, R.string.edad_sin_completar, Snackbar.LENGTH_SHORT);
				mensaje.show();
				return;
			}
		}
		if (swNombre.isChecked()) {
			busqueda = new BusquedaNombre(nombre.getText().toString(), busqueda);
		}
		if (swProvincia.isChecked()) {
			busqueda = new BusquedaProvincia(provincia.getText().toString(), busqueda);
		}
		if (swLocalidad.isChecked()) {
			busqueda = new BusquedaLocalidad(localidad.getText().toString(), busqueda);
		}
		try {
			if (swMayorA.isChecked()) {
				busqueda = new BusquedaEdadMayorA(Integer.parseInt(edad.getText().toString()), busqueda);
			}
			if (swMenorA.isChecked()) {
				busqueda = new BusquedaEdadMenorA(Integer.parseInt(edad.getText().toString()), busqueda);
			}
		} catch (NumberFormatException ex) {
			Snackbar mensaje = Snackbar.make(view, R.string.edad_mal_formada, Snackbar.LENGTH_SHORT);
			mensaje.show();
			return;
		}
		if (swDonaA.isChecked() || swRecibeDe.isChecked()) {
			Donacion.TipoDonacion tipo;
			if (tipoDonacion.getSelectedItemPosition() == 0) {
				tipo = Donacion.TipoDonacion.SANGRE_COMPLETA;
			} else if (tipoDonacion.getSelectedItemPosition() == 1) {
				tipo = Donacion.TipoDonacion.GLOBULOS_ROJOS;
			} else {
				tipo = Donacion.TipoDonacion.PLASMA;
			}
			if (swDonaA.isChecked()) {
				AbstractSangreFactory sf = new SangreStringFactory((String) sangre.getSelectedItem());
				Sangre s = sf.crearSangre();
				busqueda = new BusquedaPuedeDonarA(s, tipo, busqueda);
			}
			if (swRecibeDe.isChecked()) {
				AbstractSangreFactory sf = new SangreStringFactory((String) sangre.getSelectedItem());
				Sangre s = sf.crearSangre();
				busqueda = new BusquedaPuedeRecibirDe(s, tipo, busqueda);
			}
		}
		if (swTieneSangre.isChecked()) {
			AbstractSangreFactory sf = new SangreStringFactory((String)sangre.getSelectedItem());
			Sangre s = sf.crearSangre();
			busqueda = new BusquedaTieneSangre(s, busqueda);
		}
		if (swUltimaDonacion.isChecked()) {
			busqueda = new BusquedaDonoHaceMasDeMeses(spinner_ultima_donacion_to_int[ultima_donacion.getSelectedItemPosition()], getDonantesDeSangre(), busqueda);
		}
		if (swEsFavorito.isChecked()) {
			busqueda = new BusquedaEsFavorito(busqueda);
		}
		if (intentParaBusqueda != null) {
			intentParaBusqueda.putExtra("busqueda", busqueda);
			startActivityForResult(intentParaBusqueda, CODE_BUSQUEDA);
		}  else {
			Intent intent = new Intent();
			intent.putExtra("busqueda", busqueda);
			setResult(RESULT_OK, intent);
			finish();
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		if (resultCode == RESULT_OK) {
			if (data != null && requestCode == CODE_BUSQUEDA) {
				Persona donante = (Persona) data.getSerializableExtra("donante");
				Intent intent = new Intent();
				intent.putExtra("donante", donante);
				setResult(RESULT_OK, intent);
				finish();
			}
		}
	}
}
