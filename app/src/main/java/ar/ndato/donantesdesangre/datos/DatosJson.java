package ar.ndato.donantesdesangre.datos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ar.ndato.donantesdesangre.Donacion;
import ar.ndato.donantesdesangre.Persona;
import ar.ndato.donantesdesangre.factory.AbstractSangreFactory;
import ar.ndato.donantesdesangre.factory.SangreStringFactory;
import ar.ndato.donantesdesangre.sangre.Sangre;

public class DatosJson implements Datos {

	private File archivo;
	private Persona yo;
	private Map<Persona, Set<Donacion>> donantes;

	public DatosJson(File archivo) throws DatosException {
		if(archivo == null) {
			throw new DatosException("archivo es null");
		}

		this.archivo = archivo;
		yo = null;
		donantes = new HashMap<Persona, Set<Donacion>>();
	}


	@Override
	public void leer() throws DatosException {
		JSONObject object = null;
		JSONArray arrayDonantes = null;
		try {
			Reader reader = new FileReader(archivo);
			BufferedReader bufferedReader = new BufferedReader(reader);
			String line = null;
			String json = new String();
			while((line = bufferedReader.readLine()) != null) {
				json = json + line;
			}
			object = new JSONObject(json);
			yo = jsonAPersona(object.getJSONObject("yo"));
			arrayDonantes = object.getJSONArray("donantes");
			int cantidadDonantes = arrayDonantes.length();
			for(int i = 0; i < cantidadDonantes; i++) {
				JSONObject donante = null;
				JSONArray donaciones = null;
				Persona persona = null;
				donante = arrayDonantes.getJSONObject(i);
				persona = jsonAPersona(donante.getJSONObject("donante"));
				if(!donantes.containsKey(persona)) {
					donantes.put(persona, new HashSet<Donacion>());
				}
				donaciones = donante.getJSONArray("donaciones");
				int cantidadDonaciones = donaciones.length();
				for(int j = 0; j < cantidadDonaciones; j++) {
					Donacion donacion = null;
					donacion = jsonADonacion(donaciones.getJSONObject(j));
					if(!donantes.containsKey(donacion.getReceptor())){
						donantes.put(donacion.getReceptor(), new HashSet<Donacion>());
					}
					donantes.get(persona).add(donacion);

				}
			}
		} catch(DatosException | JSONException | IOException ex) {
			throw new DatosException(ex.getMessage());
		}
	}

	@Override
	public void guardar(Persona yo, Map<Persona, Set<Donacion>> donantes) throws DatosException {
		JSONObject object = new JSONObject();
		JSONArray arrayDonantes = new JSONArray();
		Writer writer;

		if(yo == null || donantes == null) {
			throw new DatosException("no hay \"yo\" o \"donantes\" para guardar");
		}

		try {
			object.put("yo", personaAJson(yo));
			for(Persona persona : donantes.keySet()) {
				JSONObject donante = new JSONObject();
				JSONArray arrayDonaciones = new JSONArray();
				for(Donacion donacion : donantes.get(persona)) {
					arrayDonaciones.put(donacionAJson(donacion));
				}
				donante.put("donante", personaAJson(persona));
				donante.put("donaciones", arrayDonaciones);
				arrayDonantes.put(donante);
			}
			object.put("donantes", arrayDonantes);
			writer = new FileWriter(archivo);
			writer.write(object.toString());
		} catch(DatosException | JSONException | IOException ex) {
			throw new DatosException(ex.getMessage());
		}
	}

	@Override
	public Persona getYo() {
		return yo;
	}

	@Override
	public Set<Persona> getDonantes() {
		return donantes.keySet();
	}

	@Override
	public Set<Donacion> getDonaciones(Persona persona) {
		Set<Donacion> resultado = null;
		if(persona != null && donantes.containsKey(persona)) {
			resultado = donantes.get(persona);
		}
		return resultado;
	}

	/**
	 * Crea un nuevo {@link JSONObject} representativo de la {@link Persona}
	 *
	 * @throws DatosException en caso de una excepcion en la creacion del {@link JSONObject}
	 */
	protected JSONObject personaAJson(Persona persona) throws DatosException {
		JSONObject object = new JSONObject();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		if (persona == null) {
			return object;
		}
		try {
			object.putOpt("nombre", persona.getNombre());
			object.putOpt("localidad", persona.getLocalidad());
			object.putOpt("provincia", persona.getProvincia());
			object.putOpt("direccion", persona.getDireccion());
			object.putOpt("telefono", persona.getTelefono());
			object.putOpt("mail", persona.getMail());
			object.putOpt("favorito", persona.isFavorito());
			object.putOpt("nacimiento", dateFormat.format(persona.getNacimiento().getTime()));
			object.putOpt("sangre", persona.getSangre().toString());
		} catch (JSONException ex) {
			throw new DatosException(ex.getMessage());
		}
		return object;
	}

	/**
	 * Crea un nuevo {@link JSONObject} representativo de la {@link Donacion}
	 *
	 * @throws DatosException en caso de una excepcion en la creacion del {@link JSONObject}
	 */
	protected JSONObject donacionAJson(Donacion donacion) throws DatosException {
		JSONObject object = new JSONObject();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (donacion == null) {
			return object;
		}
		try {
			object.putOpt("receptor", personaAJson(donacion.getReceptor()));
			object.putOpt("fecha", dateFormat.format(donacion.getFecha().getTime()));
		} catch (JSONException ex) {
			throw new DatosException(ex.getMessage());
		}
		return object;
	}

	/**
	 * Crea una nueva {@link Donacion} a partir de un {@link JSONObject}
	 *
	 * @throws DatosException en caso de una excepcion en la creacion de la {@link Donacion}
	 */
	protected Donacion jsonADonacion(JSONObject object) throws DatosException {
		Persona receptor = null;
		Calendar fecha = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			receptor = jsonAPersona(object.getJSONObject("receptor"));
			Date date = dateFormat.parse(object.getString("fecha"));
			fecha = Calendar.getInstance();
			fecha.setTime(date);
		} catch (JSONException | ParseException ex) {
			throw new DatosException(ex.getMessage());
		}

		return new Donacion(receptor, fecha);
	}

	/**
	 * Crea una nueva {@link Persona} a partir de un {@link JSONObject}
	 *
	 * @throws DatosException en caso de una excepcion en la creacion de la {@link Persona}
	 */
	protected Persona jsonAPersona(JSONObject object) throws DatosException {
		String nombre = null;
		String localidad = null;
		String provincia = null;
		String direccion = null;
		String telefono = null;
		String mail = null;
		Boolean favorito = null;
		Calendar nacimiento = null;
		AbstractSangreFactory sangreFactory = null;
		Sangre sangre = null;
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		try {
			nombre = object.getString("nombre");
			localidad = object.getString("localidad");
			provincia = object.getString("provincia");
			direccion = object.getString("direccion");
			telefono = object.getString("telefono");
			mail = object.getString("mail");
			favorito = object.getBoolean("favorito");
			Date date = dateFormat.parse(object.getString("nacimiento"));
			nacimiento = Calendar.getInstance();
			nacimiento.setTime(date);
			sangreFactory = new SangreStringFactory(object.getString("sangre"));
			sangre = sangreFactory.crearSangre();
		} catch (JSONException | ParseException ex) {
			throw new DatosException(ex.getMessage());
		}

		return new Persona(nombre, localidad, provincia, direccion, telefono, mail, favorito, nacimiento, sangre);
	}

}
