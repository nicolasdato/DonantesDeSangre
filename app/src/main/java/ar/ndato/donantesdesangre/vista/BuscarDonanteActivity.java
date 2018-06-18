package ar.ndato.donantesdesangre.vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Switch;

import ar.ndato.donantesdesangre.DonantesDeSangre;

public class BuscarDonanteActivity extends AppCompatActivity {
	
	private DonantesDeSangre donantesDeSangre;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_buscar_donante);
		donantesDeSangre = (DonantesDeSangre)getIntent().getSerializableExtra("donantesDeSangre");
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
				findViewById(R.id.sangre).setEnabled(sw1.isChecked() || sw2.isChecked());
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
	
	}
}
