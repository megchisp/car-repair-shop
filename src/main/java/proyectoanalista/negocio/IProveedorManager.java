package proyectoanalista.negocio;

import java.util.List;

import proyectoanalista.persistencia.Proveedor;

public interface IProveedorManager {
	public int agregar (Proveedor cliente) throws Exception;
	public int modificar(Proveedor cliente) throws Exception;
	public int eliminar(Proveedor cliente) throws Exception;
	public List<Proveedor> listaProveedores() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
	public Proveedor getProveedor (int id_proveedor) throws Exception;
	

}
