package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import ar.ndato.donantesdesangre.busqueda.Busqueda;
import ar.ndato.donantesdesangre.busqueda.BusquedaBase;
import ar.ndato.donantesdesangre.busqueda.BusquedaEdadMayorA;
import ar.ndato.donantesdesangre.busqueda.BusquedaEdadMenorA;
import ar.ndato.donantesdesangre.busqueda.BusquedaEsFavorito;
import ar.ndato.donantesdesangre.busqueda.BusquedaLocalidad;
import ar.ndato.donantesdesangre.busqueda.BusquedaNombre;
import ar.ndato.donantesdesangre.busqueda.BusquedaProvincia;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeDonarA;
import ar.ndato.donantesdesangre.busqueda.BusquedaPuedeRecibirDe;
import ar.ndato.donantesdesangre.factory.AbstractSangreFactory;
import ar.ndato.donantesdesangre.factory.SangreStringFactory;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class BuscarDonanteActivity extends ActividadPersistente {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_donante);
	}
	
	public void clickSwitch(View view) {
		Switch sw = (Switch)view;
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
			case R.id.switch_donar_a:
			case R.id.switch_recibir_de: {
				Switch sw1 = findViewById(R.id.switch_donar_a);
				Switch sw2 = findViewById(R.id.switch_recibir_de);
				findViewById(R.id.nombre).setEnabled(sw1.isChecked() || sw2.isChecked());
				break;
			}
			case R.id.switch_edad_mayor:
			case R.id.switch_edad_menor: {
				Switch sw1 = findViewById(R.id.switch_edad_mayor);
				Switch sw2 = findViewById(R.id.switch_edad_menor);
				findViewById(R.id.edad).setEnabled(sw1.isChecked() || sw2.isChecked());
				break;
			}
		}
	}
	
	public void buscarDonante(View view) {
		Busqueda busqueda = new BusquedaBase();
		
		Switch swNombre = findViewById(R.id.switch_nombre);
		Switch swProvincia = findViewById(R.id.switch_provincia);
		Switch swLocalidad = findViewById(R.id.switch_localidad);
		Switch swMayorA = findViewById(R.id.switch_edad_mayor);
		Switch swMenorA = findViewById(R.id.switch_edad_menor);
		Switch swDonaA = findViewById(R.id.switch_donar_a);
		Switch swRecibeDe = findViewById(R.id.switch_recibir_de);
		Switch swEsFavorito = findViewById(R.id.switch_es_favorito);
		TextView nombre = findViewById(R.id.nombre);
		TextView provincia = findViewById(R.id.provincia);
		TextView localidad = findViewById(R.id.localidad);
		TextView edad = findViewById(R.id.edad);
		Spinner sangre = findViewById(R.id.sangre);
		
		if (swNombre.isChecked()) {
			busqueda = new BusquedaNombre(nombre.getText().toString(), busqueda);
		}
		if (swProvincia.isChecked()) {
			busqueda = new BusquedaProvincia(provincia.getText().toString(), busqueda);
		}
		if (swLocalidad.isChecked()) {
			busqueda = new BusquedaLocalidad(localidad.getText().toString(), busqueda);
		}
		if (swMayorA.isChecked()) {
			busqueda = new BusquedaEdadMayorA(Integer.getInteger(edad.getText().toString()), busqueda);
		}
		if (swMenorA.isChecked()) {
			busqueda = new BusquedaEdadMenorA(Integer.getInteger(edad.getText().toString()), busqueda);
		}
		if (swDonaA.isChecked()) {
			AbstractSangreFactory sf = new SangreStringFactory((String)sangre.getSelectedItem());
			Sangre s = sf.crearSangre();
			busqueda = new BusquedaPuedeDonarA(s, busqueda);
		}
		if (swRecibeDe.isChecked()) {
			AbstractSangreFactory sf = new SangreStringFactory((String)sangre.getSelectedItem());
			Sangre s = sf.crearSangre();
			busqueda = new BusquedaPuedeRecibirDe(s, busqueda);
		}
		if (swEsFavorito.isChecked()) {
			busqueda = new BusquedaEsFavorito(busqueda);
		}
	
		Intent intent = new Intent(this, ListarDonantesActivity.class);
		intent.putExtra("busqueda", busqueda);
		startActivity(intent);
	}
}
