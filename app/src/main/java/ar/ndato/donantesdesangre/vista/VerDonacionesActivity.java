package ar.ndato.donantesdesangre.vista;

import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.Persona;

public class VerDonacionesActivity extends ActividadPersistente {
	
	private Persona donante;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ver_donaciones);
		donante = (Persona)getIntent().getSerializableExtra("donante");
		
		TextView nombre = findViewById(R.id.nombre);
		TextView sangre = findViewById(R.id.sangre);
		TextView donaciones = findViewById(R.id.donaciones);
		ImageView favorito = findViewById(R.id.favorito);
		nombre.setText(donante.getNombre());
		sangre.setText(donante.getSangre().toString());
		donaciones.setText(String.valueOf(getDonantesDeSangre().getDonaciones(donante).size()));
		if (donante.equals(getDonantesDeSangre().getYo())) {
			favorito.setImageResource(R.drawable.ic_person);
		} else if (donante.isFavorito()) {
			favorito.setImageResource(R.drawable.ic_star);
		} else {
			favorito.setImageResource(R.drawable.ic_star_border);
		}
		
		RecyclerView rw = findViewById(R.id.donaciones);
		rw.setHasFixedSize(true);
		rw.setLayoutManager(new LinearLayoutManager(this));
		rw.addItemDecoration(new DividerItemDecoration(rw.getContext(), DividerItemDecoration.VERTICAL));
		rw.setAdapter(new VerDonacionesActivity.ListarAdapter(donante));
	}
	
	@Override
	public void onSaveInstanceState(Bundle bundle) {
		super.onSaveInstanceState(bundle);
		bundle.putSerializable("donante", donante);
	}
	
	@Override
	public void onRestoreInstanceState(Bundle bundle) {
		super.onRestoreInstanceState(bundle);
		donante = (Persona)bundle.getSerializable("donante");
	}
	
	protected class ListarAdapter extends RecyclerView.Adapter<VerDonacionesActivity.ListarAdapter.ViewHolder> implements View.OnClickListener {
		List<Donacion> donaciones;
		
		public ListarAdapter(Persona persona) {
			Set<Donacion> donaciones_desordenados = getDonantesDeSangre().getDonaciones(persona);
			donaciones = new ArrayList<Donacion>(donaciones_desordenados);
			Collections.sort(donaciones);
		}
		@Override
		public ListarAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_doncion, parent, false);
			layout.setOnClickListener(this);
			return new VerDonacionesActivity.ListarAdapter.ViewHolder(layout);
		}
		
		@Override
		public void onBindViewHolder(ListarAdapter.ViewHolder holder, int position) {
			holder.setDonacion(donaciones.get(position));
		}
		
		@Override
		public int getItemCount() {
			return donaciones == null ? 0 : donaciones.size();
		}
		
		@Override
		public void onClick(View view) {
			RecyclerView rw = findViewById(R.id.donaciones);
			int idx = rw.getChildLayoutPosition(view);
			Intent intent = new Intent();
			intent.putExtra("donante", donaciones.get(idx).getReceptor());
			setResult(RESULT_OK, intent);
			finish();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder {
			ConstraintLayout layout;
			
			public ViewHolder(ConstraintLayout layout) {
				super(layout);
				this.layout = layout;
			}
			
			public void setDonacion(Donacion donacion) {
				if (donacion != null) {
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					TextView nombre = layout.findViewById(R.id.nombre);
					TextView sangre = layout.findViewById(R.id.sangre);
					TextView fecha = layout.findViewById(R.id.fecha);
					nombre.setText(donacion.getReceptor().getNombre());
					sangre.setText(donacion.getReceptor().getSangre().toString());
					fecha.setText(dateFormat.format(donacion.getFecha().getTime()));
				}
			}
		}
		
	}
}
