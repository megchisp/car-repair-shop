package negocio;


import java.util.List;

import persistencia.*;

public class ProveedorManager implements IProveedorManager {

	private int estadoFinal;
	private ProveedorDao proveedorDao;

	public ProveedorManager(){
		proveedorDao = new ProveedorDao();
	}

//	public void setProveedorDao( ProveedorDao proveedorDao ) {
//		this.proveedorDao = proveedorDao;
//	}

	public int agregar( Proveedor proveedor ) throws Exception {
		if( true )
			estadoFinal = proveedorDao.agregar( proveedor );

		return estadoFinal;
	}

	public int modificar( Proveedor proveedor )  throws Exception {
		if( true )
			estadoFinal = proveedorDao.modificar( proveedor );
		return estadoFinal;
	}

	public int eliminar(Proveedor proveedor) throws Exception {
		if (true) // aca iría una validacion, pero siempre se cumpliría
			estadoFinal = proveedorDao.eliminar( proveedor );
		return estadoFinal;
	}

	public List<Proveedor> listaProveedores() throws Exception {
		return proveedorDao.listaProveedores();
	}


	public int nextID() throws Exception {
		return proveedorDao.nextID();
	}

	public int lastValue() throws Exception {
		return proveedorDao.lastValue();
	}

	public Proveedor getProveedor(int id_proveedor) throws Exception {
		return proveedorDao.getProveedor(id_proveedor);
	}

}
