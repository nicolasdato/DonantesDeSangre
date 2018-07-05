package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.busqueda.Busqueda;

public class ListarDonantesActivity extends ActividadPersistente {
	private Busqueda busqueda;
	private Intent intentParaBusqueda = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_donantes);
		busqueda = (Busqueda)getIntent().getSerializableExtra("busqueda");
		intentParaBusqueda = (Intent)getIntent().getParcelableExtra("intent");
		RecyclerView rw = findViewById(R.id.recycler);
		rw.setHasFixedSize(true);
		rw.setLayoutManager(new LinearLayoutManager(this));
		rw.addItemDecoration(new DividerItemDecoration(rw.getContext(), DividerItemDecoration.VERTICAL));
		rw.setAdapter(new ListarDonantesActivity.ListarAdapter(busqueda));
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putSerializable("busqueda", busqueda);
		bundle.putParcelable("intent", intentParaBusqueda);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		busqueda = (Busqueda)bundle.getSerializable("busqueda");
		intentParaBusqueda = (Intent)bundle.getParcelable("intent");
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && data != null) {
			int resultado;
			resultado = data.getExtras().getInt("texto");
			Snackbar mensaje = Snackbar.make(findViewById(R.id.listar_donantes_activity), resultado, Snackbar.LENGTH_SHORT);
			mensaje.show();
		}
		RecyclerView rw = findViewById(R.id.recycler);
		rw.setAdapter(new ListarDonantesActivity.ListarAdapter(busqueda));
	}
	
	protected class ListarAdapter extends RecyclerView.Adapter<ListarAdapter.ViewHolder> implements View.OnClickListener {
		List<Persona> donantes;
		
		public ListarAdapter(Busqueda busqueda) {
			Set<Persona> donantes_desordenados = getDonantesDeSangre().buscarDonantes(busqueda);
			donantes = new ArrayList<Persona>(donantes_desordenados);
			Collections.sort(donantes);
		}
		@Override
		public ListarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			ConstraintLayout layout = (ConstraintLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_donante, parent, false);
			layout.setOnClickListener(this);
			return new ListarAdapter.ViewHolder(layout);
		}
		
		@Override
		public void onBindViewHolder(ListarAdapter.ViewHolder holder, int position) {
			holder.setDonante(donantes.get(position));
		}
		
		@Override
		public int getItemCount() {
			return donantes == null ? 0 : donantes.size();
		}
		
		@Override
		public void onClick(View view) {
			RecyclerView rw = findViewById(R.id.recycler);
			int idx = rw.getChildLayoutPosition(view);
			if (intentParaBusqueda != null) {
				intentParaBusqueda.putExtra("donante", donantes.get(idx));
				startActivityForResult(intentParaBusqueda, 0);
			} else {
				Intent intent = new Intent();
				intent.putExtra("donante", donantes.get(idx));
				setResult(RESULT_OK, intent);
				finish();
			}
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			ConstraintLayout layout;
			
			public ViewHolder(ConstraintLayout layout) {
				super(layout);
				this.layout = layout;
			}
			
			public void setDonante(Persona persona) {
				if (persona != null) {
					TextView nombre = layout.findViewById(R.id.nombre);
					TextView sangre = layout.findViewById(R.id.sangre);
					TextView donaciones = layout.findViewById(R.id.donaciones);
					ImageView favorito = layout.findViewById(R.id.favorito);
					nombre.setText(persona.getNombre());
					sangre.setText(persona.getSangre().toString());
					donaciones.setText(String.valueOf(getDonantesDeSangre().getDonaciones(persona).size()));
					if (persona.equals(getDonantesDeSangre().getYo())) {
						favorito.setImageResource(R.drawable.ic_person);
					} else if (persona.isFavorito()) {
						favorito.setImageResource(R.drawable.ic_star);
					} else {
						favorito.setImageResource(R.drawable.ic_star_border);
					}
				}
			}
		}
	}
}
