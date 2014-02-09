package proyectoanalista.negocio;

import java.util.Calendar;
import java.util.List;

import proyectoanalista.persistencia.Automovil;
import proyectoanalista.persistencia.Reparacion;
import proyectoanalista.persistencia.ReparacionDao;

public class ReparacionManager implements IReparacionManager{
	private int estadoFinal;
	private ReparacionDao reparacionDao;
	
	public ReparacionManager(){
		reparacionDao = new ReparacionDao();
	}
	
	public int agregar(Reparacion reparacion) throws Exception {
		if(validarDatos(reparacion))
			estadoFinal = reparacionDao.agregar( reparacion );
		return estadoFinal;
	}

	public int modificar(Reparacion reparacion) throws Exception {
		if (validarDatos(reparacion))
			estadoFinal = reparacionDao.modificar( reparacion );
		return estadoFinal;
	}

	public int eliminar(Reparacion reparacion) throws Exception {
		if (true) // aca iría una validacion, pero siempre se cumpliría
			estadoFinal = reparacionDao.eliminar( reparacion );
		return estadoFinal;
	}

	public List<Reparacion> listaReparaciones(Automovil automovil) throws Exception {
		return reparacionDao.listaReparaciones(automovil);
	}

	public List<Reparacion> listaReparaciones() throws Exception {
		return reparacionDao.listaReparaciones();
	}

	private boolean validarDatos (Reparacion reparacion){
		if( !esUnaFechaValida( reparacion.getFechaReparacion() ) ) {
			estadoFinal = 2;
			return false;
		}
		if( !esUnKilometrajeValido(reparacion.getKilometraje())){
			estadoFinal = 3;
			return false;
		}
			
		return true;
	}
	
	private boolean esUnaFechaValida(Calendar fecha){
		try {
			fecha.getTimeInMillis(); // si la fecha no es correcta getTime() lanza una excepción
		} catch (Exception e) {
			return false;
		}
		return true;
	}
	
	private boolean esUnKilometrajeValido(int km){
		if(km < 999999) // no existe en la práctica un automóvil con mas de 1 millón de kilómetros
			return true;
		return false;
	}

	public int nextID() throws Exception {
		return reparacionDao.nextID();
	}

	public int lastValue() throws Exception {
		return reparacionDao.lastValue();
	}

	public double importe(Reparacion reparacion) throws Exception {
		return reparacionDao.importe(reparacion);
	}

	public int tiempoMinimo(Reparacion reparacion) throws Exception {
		return reparacionDao.tiempoMinimo(reparacion);
	}

	public int tiempoMaximo(Reparacion reparacion) throws Exception {
		return reparacionDao.tiempoMaximo(reparacion);
	}

}
