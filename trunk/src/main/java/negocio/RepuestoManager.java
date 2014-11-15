package negocio;

import java.util.List;

import persistencia.IRepuestoDao;
import persistencia.Repuesto;
import persistencia.RepuestoDao;
import persistencia.Servicio;

public class RepuestoManager implements IRepuestoManager{
		
	private int estadoFinal;
	IRepuestoDao repuestoDao;
	
	public RepuestoManager(){
		repuestoDao = new RepuestoDao();
	}

	public int agregar(Repuesto repuesto) throws Exception {
		if( validarDatos( repuesto ) )
			estadoFinal = repuestoDao.agregar( repuesto );
		return estadoFinal;
	}
	
	private boolean validarDatos(Repuesto repuesto) {
		if( repuesto.getPrecioUnitario() <= 0 ){
			estadoFinal = 2;
			return false;
		}
		return true;
	}

	public int modificar(Repuesto repuesto) throws Exception {
		if( validarDatos( repuesto ) )
			estadoFinal = repuestoDao.modificar( repuesto );
		return estadoFinal;
	}

	public int eliminar(Repuesto repuesto) throws Exception {
		if(true)
			estadoFinal = repuestoDao.eliminar(repuesto);
		return estadoFinal;
	}

	public List<Repuesto> listaRepuestos(Servicio servicio) throws Exception {
		return repuestoDao.listaRepuestos(servicio);
	}
	
	public List<String> listaNombreRepuestos() throws Exception {
		return repuestoDao.listaNombreRepuestos();
	}

	public List<Repuesto> listaRepuestos() throws Exception {
		return repuestoDao.listaRepuestos();
	}

	public int nextID() throws Exception {
		return repuestoDao.nextID();
	}

	public int lastValue() throws Exception {
		return repuestoDao.lastValue();
	}

}
