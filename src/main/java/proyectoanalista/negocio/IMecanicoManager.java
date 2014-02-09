package proyectoanalista.negocio;

import java.util.List;

import proyectoanalista.persistencia.Mecanico;

public interface IMecanicoManager {
	public int agregar (Mecanico mecanico) throws Exception;
	public int modificar(Mecanico mecanico) throws Exception;
	public int eliminar(Mecanico mecanico) throws Exception;
	public List<Mecanico> listaMecanicos() throws Exception;
	public Mecanico getMecanico (int id_mecanico) throws Exception;
}
