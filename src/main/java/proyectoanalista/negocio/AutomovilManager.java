package proyectoanalista.negocio;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import proyectoanalista.persistencia.Automovil;
import proyectoanalista.persistencia.AutomovilDao;
import proyectoanalista.persistencia.Cliente;
import proyectoanalista.persistencia.Reparacion;

public class AutomovilManager implements IAutomovilManager {
	
	private AutomovilDao automovilDao;
	private int estadoFinal;
	public AutomovilManager(){
		automovilDao = new AutomovilDao();
	}

	public int agregar(Automovil automovil) throws Exception {
		if (validarDatos (automovil))
			estadoFinal = automovilDao.agregar( automovil );
		return estadoFinal;
		
	}

	public int modificar(Automovil automovil) throws Exception {
		if (validarDatos (automovil))
			estadoFinal = automovilDao.modificar(automovil);
		return estadoFinal;
	}

	public int eliminar(Automovil automovil) throws Exception {
		if (true) // aca iría una validacion, pero siempre se cumpliría
			estadoFinal = automovilDao.eliminar( automovil );
		return estadoFinal;
	}

	public List<Automovil> listaAutomoviles(Cliente cliente) throws Exception {
		return automovilDao.listaAutomoviles(cliente);
	}

	public List<Automovil> listaAutomoviles() throws Exception {
		return automovilDao.listaAutomoviles();
	}
	
	private boolean validarDatos(Automovil automovil){
		if(!esUnDominioValido(automovil)){
			estadoFinal = 5;
			return false;
		}
		return true;
	}
	
	private boolean esUnDominioValido(Automovil automovil){
		Pattern pattern;
		Matcher matcher;
		String dominio_pattern ="^[a-zA-Z]{3}\\d{3}$";
		pattern = Pattern.compile(dominio_pattern);
		matcher = pattern.matcher(automovil.getDominio());
		return matcher.matches();
	}

	public int nextID() throws Exception {
		return automovilDao.nextID();
	}

	public int lastValue() throws Exception {
		return automovilDao.lastValue();
	}

	public Automovil getAutomovil(Reparacion reparacion) throws Exception {
		return automovilDao.getAutomovil(reparacion);
	}

}
