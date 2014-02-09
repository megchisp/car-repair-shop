package proyectoanalista.negocio;

import java.util.List;

import proyectoanalista.persistencia.ManoDeObra;
import proyectoanalista.persistencia.Servicio;

public interface IManoDeObraManager {
	public int agregar ( ManoDeObra manoDeObra ) throws Exception;
	public int modificar( ManoDeObra manoDeObra ) throws Exception;
	public int eliminar ( ManoDeObra manoDeObra ) throws Exception;
	public List<ManoDeObra> listaManosDeObras(Servicio servicio) throws Exception;
	public List<ManoDeObra> listaManosDeObras() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
}
