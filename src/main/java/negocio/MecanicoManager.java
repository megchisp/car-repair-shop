package negocio;

import java.util.List;

import persistencia.Mecanico;
import persistencia.MecanicoDao;

public class MecanicoManager implements IMecanicoManager {
	
	private int estadoFinal;
	private MecanicoDao mecanicoDao;
	
	public MecanicoManager(){
		mecanicoDao = new MecanicoDao();
	}

	public int agregar(Mecanico mecanico) throws Exception {
		if( validarDatos( mecanico ) )
			estadoFinal = mecanicoDao.agregar( mecanico );
		
		return estadoFinal;
	}

	public int modificar(Mecanico mecanico) throws Exception {
		if(validarDatos( mecanico ))
			estadoFinal = mecanicoDao.modificar(mecanico);
		return estadoFinal;
	}

	public int eliminar(Mecanico mecanico) throws Exception {
		if (true) // aca iría una validacion, pero siempre se cumpliría
			estadoFinal = mecanicoDao.eliminar( mecanico );
		return estadoFinal;
	}

	public List<Mecanico> listaMecanicos() throws Exception {
		return mecanicoDao.listaMecanicos();
	}
	
	private boolean codigoPostalSinLocalidad(String localidad, String codigoPostal){
		// esta funcion devuelve true si el usuario ingresa un código postal pero no ingresa la localidad
		if(localidad.isEmpty() && !codigoPostal.isEmpty())
			return true;
		return false;
	}
	
	private boolean validarDatos( Mecanico mecanico ) {
		if(codigoPostalSinLocalidad(mecanico.getLocalidad(), mecanico.getCodigoPostal())){
			estadoFinal = 3;
			return false;
		}
		return true;
	}

	public Mecanico getMecanico(int id_mecanico) throws Exception {
		return mecanicoDao.getMecanico(id_mecanico);
	}

}
