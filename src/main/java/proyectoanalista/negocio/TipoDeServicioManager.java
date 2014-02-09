package proyectoanalista.negocio;

import java.util.List;

import proyectoanalista.persistencia.Servicio;
import proyectoanalista.persistencia.TipoDeServicio;
import proyectoanalista.persistencia.TipoDeServicioDao;

public class TipoDeServicioManager implements ITipoDeServicioManager {
	
	int estadoFinal;
	private TipoDeServicioDao tipoDeServicioDao;
	
	public TipoDeServicioManager(){
		tipoDeServicioDao = new TipoDeServicioDao();
	}

	public int agregar(TipoDeServicio tipoDeServicio) throws Exception {
		if(validarDatos(tipoDeServicio))
			estadoFinal = tipoDeServicioDao.agregar( tipoDeServicio );
		return estadoFinal;
	}

	public int modificar(TipoDeServicio tipoDeServicio) throws Exception {
		if(validarDatos(tipoDeServicio))
			estadoFinal = tipoDeServicioDao.modificar( tipoDeServicio );
		return estadoFinal;
	}

	public int eliminar(TipoDeServicio tipoDeServicio) throws Exception {
		if(true) // aca iría una validacion, pero siempre se cumpliría
			estadoFinal = tipoDeServicioDao.eliminar( tipoDeServicio );
		return estadoFinal;
	}

	public List<TipoDeServicio> listaTiposDeServicio() throws Exception {
		return tipoDeServicioDao.listaTiposDeServicio();
	}

	public int nextID() throws Exception {
		return tipoDeServicioDao.nextID();
	}

	public int lastValue() throws Exception {
		return tipoDeServicioDao.lastValue();
	}
	
	private boolean validarDatos(TipoDeServicio tipoDeServicio){
		if(!sonTiemposValidos (tipoDeServicio.getTiempoMinimoReparacion(), tipoDeServicio.getTiempoMaximoReparacion())){
			estadoFinal = 3;
			return false;
		}
		return true;
	}
	
	private boolean sonTiemposValidos(int min, int max){
		// este método retorna true si min no es mas grande que max
		if(min > max)
			return false;
		else
			return true;
	}

	public String getNombre(Servicio servicio) throws Exception {
		return tipoDeServicioDao.getNombre(servicio);
	}

}
