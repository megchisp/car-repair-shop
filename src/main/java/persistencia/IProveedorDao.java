package persistencia;

import java.util.List;

public interface IProveedorDao {
	public int agregar ( Proveedor proveedor ) throws Exception;
	public int modificar( Proveedor proveedor ) throws Exception;
	public int eliminar ( Proveedor proveedor ) throws Exception;
	public List<Proveedor> listaProveedores() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
	public Proveedor getProveedor (int id_proveedor) throws Exception;



}
