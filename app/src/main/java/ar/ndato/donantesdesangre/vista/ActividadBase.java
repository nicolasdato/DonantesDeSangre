package ar.ndato.donantesdesangre.vista;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import ar.ndato.donantesdesangre.DonantesDeSangre;

public abstract class ActividadBase extends AppCompatActivity {
	private DonantesDeSangre donantesDeSangre;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		donantesDeSangre = (DonantesDeSangre)getIntent().getSerializableExtra("donantesDeSangre");
		if (donantesDeSangre == null) {
			donantesDeSangre = DonantesDeSangre.getInstance();
		}
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putSerializable("donantesDeSangre", donantesDeSangre);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		donantesDeSangre = (DonantesDeSangre)bundle.getSerializable("donantesDeSangre");
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu, menu);
		return true;
	}
	
	public DonantesDeSangre getDonantesDeSangre() {
		return donantesDeSangre;
	}
}
