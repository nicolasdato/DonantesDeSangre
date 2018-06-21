package ar.ndato.donantesdesangre.vista;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
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
	Busqueda busqueda;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listar_donantes);
		busqueda = (Busqueda)getIntent().getSerializableExtra("busqueda");
		RecyclerView rw = findViewById(R.id.recycler);
		rw.setHasFixedSize(true);
		rw.setLayoutManager(new LinearLayoutManager(this));
		rw.setAdapter(new ListarDonantesActivity.ListarAdapter(busqueda));
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putSerializable("busqueda", busqueda);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		busqueda = (Busqueda)bundle.getSerializable("busqueda");
	}
	
	protected class ListarAdapter extends RecyclerView.Adapter<ListarAdapter.ViewHolder> {
		List<Persona> donantes;
		
		public ListarAdapter(Busqueda busqueda) {
			Set<Persona> donantes_desordenados = getDonantesDeSangre().buscarDonantes(busqueda);
			donantes = new ArrayList<Persona>(donantes_desordenados);
			Collections.sort(donantes);
		}
		@Override
		public ListarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			ConstraintLayout layout = (ConstraintLayout)LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_donante, null);
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
					if (persona.isFavorito()) {
						favorito.setImageResource(R.drawable.ic_star);
					} else {
						favorito.setImageResource(R.drawable.ic_star_border);
					}
				}
			}
		}
	
	}
}
