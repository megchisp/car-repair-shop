package proyectoanalista.negocio;

import java.util.List;

import proyectoanalista.persistencia.Reparacion;
import proyectoanalista.persistencia.Servicio;
import proyectoanalista.persistencia.ServicioDao;

public class ServicioManager implements IServicioManager {
	
	private int estadoFinal;
	private ServicioDao servicioDao;
	
	public ServicioManager(){
		servicioDao = new ServicioDao();
	}

	public int agregar(Servicio servicio) throws Exception {
		if( true ) // no hay nada que validar
			estadoFinal = servicioDao.agregar( servicio );
		
		return estadoFinal;
	}

	public int eliminar(Servicio servicio) throws Exception {
		if (true) // aca iría una validacion, pero siempre se cumpliría
			estadoFinal = servicioDao.eliminar( servicio );
		return estadoFinal;
	}

	public List<Servicio> listaServicios(Reparacion reparacion) throws Exception{
		return servicioDao.listaServicios(reparacion);
	}
	
	public List<Servicio> listaServicios() throws Exception{
		return servicioDao.listaServicios();
	}

	public int nextID() throws Exception {
		return servicioDao.nextID();
	}

	public int lastValue() throws Exception {
		return servicioDao.lastValue();
	}

	public double importe(Servicio servicio) throws Exception {
		return servicioDao.importe(servicio);
	}
}
