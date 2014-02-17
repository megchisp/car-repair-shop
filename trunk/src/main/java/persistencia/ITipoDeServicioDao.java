package persistencia;

import java.util.List;

public interface ITipoDeServicioDao {
	
	public int agregar (TipoDeServicio tipoDeServicio) throws Exception;
	public int modificar (TipoDeServicio tipoDeServicio) throws Exception;
	public int eliminar (TipoDeServicio tipoDeServicio) throws Exception;
	public List<TipoDeServicio> listaTiposDeServicio() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
	public String getNombre (Servicio servicio) throws Exception;

}
