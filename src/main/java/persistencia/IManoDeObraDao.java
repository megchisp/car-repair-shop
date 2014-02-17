package persistencia;

import java.util.List;

public interface IManoDeObraDao {
	public int agregar ( ManoDeObra manoDeObra ) throws Exception;
	public int modificar( ManoDeObra manoDeObra ) throws Exception;
	public int eliminar ( ManoDeObra manoDeObra ) throws Exception;
	public List<ManoDeObra> listaManosDeObras(Servicio servicio) throws Exception;
	public List<ManoDeObra> listaManosDeObras() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
}
