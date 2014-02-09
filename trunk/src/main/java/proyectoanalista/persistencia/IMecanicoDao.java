package proyectoanalista.persistencia;

import java.util.List;

public interface IMecanicoDao {
	public int agregar (  Mecanico mecanico ) throws Exception;
	public int modificar( Mecanico mecanico ) throws Exception;
	public int eliminar ( Mecanico mecanico ) throws Exception;
	public List<Mecanico> listaMecanicos() throws Exception;
	public Mecanico getMecanico (int id_mecanico) throws Exception;
}
