package negocio;

import java.util.List;

import persistencia.IManoDeObraDao;
import persistencia.ManoDeObra;
import persistencia.ManoDeObraDao;
import persistencia.Servicio;

public class ManoDeObraManager implements IManoDeObraManager {
	
	private int estadoFinal;
	IManoDeObraDao manoDeObraDao;
	
	public ManoDeObraManager(){
		manoDeObraDao = new ManoDeObraDao();
	}

	public int agregar(ManoDeObra manoDeObra) throws Exception {
		if( validarDatos( manoDeObra ) )
			estadoFinal = manoDeObraDao.agregar( manoDeObra );
		return estadoFinal;
	}

	private boolean validarDatos(ManoDeObra manoDeObra) {
//		if( manoDeObra.getPrecio() <= 0 ){
//			estadoFinal = 2;
//			return false;
//		}
		return true;
	}

	public int modificar(ManoDeObra manoDeObra) throws Exception {
		if( validarDatos( manoDeObra ) )
			estadoFinal = manoDeObraDao.modificar( manoDeObra );
		return estadoFinal;
	}

	public int eliminar(ManoDeObra manoDeObra) throws Exception {
		if(true)
			estadoFinal = manoDeObraDao.eliminar(manoDeObra);
		return estadoFinal;
	}

	public List<ManoDeObra> listaManosDeObras(Servicio servicio) throws Exception {
		return manoDeObraDao.listaManosDeObras(servicio);
	}
	
	public List<ManoDeObra> listaManosDeObras() throws Exception {
		return manoDeObraDao.listaManosDeObras();
	}

	public int nextID() throws Exception {
		return manoDeObraDao.nextID();
	}

	public int lastValue() throws Exception {
		return manoDeObraDao.lastValue();
	}

}
