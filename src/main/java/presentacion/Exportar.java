package presentacion;

import java.io.File;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.filechooser.FileNameExtensionFilter;

import negocio.AutomovilManager;
import negocio.ClienteManager;
import negocio.IAutomovilManager;
import negocio.IClienteManager;
import negocio.IManoDeObraManager;
import negocio.IMecanicoManager;
import negocio.IProveedorManager;
import negocio.IReparacionManager;
import negocio.IRepuestoManager;
import negocio.IServicioManager;
import negocio.ITipoDeServicioManager;
import negocio.IUsuarioManager;
import negocio.ManoDeObraManager;
import negocio.MecanicoManager;
import negocio.ProveedorManager;
import negocio.ReparacionManager;
import negocio.RepuestoManager;
import negocio.ServicioManager;
import negocio.TipoDeServicioManager;
import negocio.UsuarioManager;

import persistencia.Automovil;
import persistencia.Cliente;
import persistencia.ManoDeObra;
import persistencia.Mecanico;
import persistencia.Proveedor;
import persistencia.Reparacion;
import persistencia.Repuesto;
import persistencia.Servicio;
import persistencia.TipoDeServicio;
import persistencia.Usuario;

public class Exportar extends JFileChooser{
	private static final long serialVersionUID = 1L;
	
	IClienteManager clienteManager = null;
	IAutomovilManager automovilManager = null;
	IReparacionManager reparacionManager = null;
	ITipoDeServicioManager tipoDeServicioManager = null;
	IMecanicoManager mecanicoManager = null;
	IProveedorManager proveedorManager = null;
	IServicioManager servicioManager = null;
	IManoDeObraManager manoDeObraManager = null;
	IRepuestoManager repuestoManager = null;
	IUsuarioManager usuarioManager = null;
	
	List<Cliente> listaClientes = null;
	List<Automovil> listaAutomoviles = null;
	List<Reparacion> listaReparaciones = null;
	List<TipoDeServicio> listaTiposDeServicios = null;
	List<Mecanico> listaMecanicos = null;
	List<Proveedor> listaProveedores = null;
	List<Servicio> listaServicios = null;
	List<ManoDeObra> listaManosDeObras = null;
	List<Repuesto> listaRepuestos = null;
	List<Usuario> listaUsuarios = null;
	
	JFileChooser fileChooser = null;
	FileNameExtensionFilter filter = null;
	
	PrintWriter printerWriter = null;

	public Exportar(JFrame padre){
		/*** Este constructor arma el archivo y lo almacena mediante un JFileChooser ***/
		
		fileChooser = new JFileChooser();
//		fileChooser.showOpenDialog(padre); // setea icono al fileChooser
		clienteManager = new ClienteManager();
		automovilManager = new AutomovilManager();
		reparacionManager = new ReparacionManager();
		tipoDeServicioManager = new TipoDeServicioManager();
		mecanicoManager = new MecanicoManager();
		proveedorManager = new ProveedorManager();
		servicioManager = new ServicioManager();
		manoDeObraManager = new ManoDeObraManager();
		repuestoManager = new RepuestoManager();
		usuarioManager = new UsuarioManager();
		
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos SQL", "sql", "text");
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle(" Exportar ");
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			try {
				final WaitDialog waitDialog = new WaitDialog("Exportando la base de datos...");
    			SwingWorker<?,?> worker = new SwingWorker<Void,Void>(){
    				protected Void doInBackground() throws Exception{
    					escribirEnArchivo();
    					return null;  
    		          }  
    		  
    		          protected void done(){  
    		        	  waitDialog.dispose();  
    		          }  
    		        };  
    		        worker.execute();  
    		        waitDialog.setVisible(true);
                JOptionPane.showMessageDialog( this, "La base de datos se ha exportado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE );
            }
            catch (Exception e) {
            	JOptionPane.showMessageDialog( this, e, "Error", JOptionPane.ERROR_MESSAGE );
            }
			finally{
				printerWriter.close();
			}
		} 
	}
	
	void escribirEnArchivo() throws Exception{
		File file = new File(fileChooser.getSelectedFile().toString() + (fileChooser.getSelectedFile().toString().endsWith(".sql") ? "" : ".sql"));
        printerWriter = new PrintWriter(file);

        // Recupero el día, mes y año acual y lo imprimo
        printerWriter.println("----------------------------------------| BACKUP DEL " + fechaActual()  + " |-----------------------------------------");
		
        // imprime la sentencia para agregar los clientes
		inicializarListaClientes();
        printerWriter.println("\n-- TABLA CLIENTE --");
        printerWriter.println(sentenciaClientes());
        listaClientes.clear();
        
        // imprime la sentencia para agregar los automoviles
		inicializarListaAutomoviles();
        printerWriter.println("\n-- TABLA AUTOMOVIL --");
        printerWriter.println(sentenciaAutomoviles());
        listaAutomoviles.clear();
        
        // imprime la sentencia para agregar las reparaciones
		inicializarListaReparaciones();
        printerWriter.println("\n-- TABLA REPARACION --");
        printerWriter.println(sentenciaReparaciones());
        listaReparaciones.clear();
        
        // imprime la sentencia para agregar los tipos de servicios
		inicializarListaTiposDeServicios();
        printerWriter.println("\n-- TABLA TIPO_DE_SERVICIO --");
        printerWriter.println(sentenciaTiposDeServicios());
        listaTiposDeServicios.clear();
        
        // imprime la sentencia para agregar los mecanicos
		inicializarListaMecanicos();
        printerWriter.println("\n-- TABLA MECANICO --");
        printerWriter.println(sentenciaMecanicos());
        listaMecanicos.clear();
        
        // imprime la sentencia para agregar los proveedores
		inicializarListaProveedores();
        printerWriter.println("\n-- TABLA PROVEEDOR --");
        printerWriter.println(sentenciaProveedores());
        listaProveedores.clear();
        
        // imprime la sentencia para agregar los servicios
		inicializarListaServicios();
        printerWriter.println("\n-- TABLA SERVICIO --");
        printerWriter.println(sentenciaServicios());
        listaServicios.clear();
        
        // imprime la sentencia para agregar las manos de obras
		inicializarListaManosDeObras();
        printerWriter.println("\n-- TABLA MANO_DE_OBRA --");
        printerWriter.println(sentenciaManosDeObras());
        listaManosDeObras.clear();
         
        // imprime la sentencia para agregar los repuestos
		inicializarListaRepuestos();
		printerWriter.println("\n-- TABLA REPUESTO --");
		printerWriter.println(sentenciaRepuestos());
		listaRepuestos.clear();
       
		// imprime la sentencia para agregar los repuestos
		inicializarListaUsuarios();
		printerWriter.println("\n-- TABLA USUARIO --");
		printerWriter.println(sentenciaUsuarios());
		listaUsuarios.clear();
        
        // imprime las sentencias para setear los últimos números de secuencia
        printerWriter.println("\n-- SETEO DE NUMEROS DE SECUENCIA --");
        printerWriter.println(secuenciaClientes());
        printerWriter.println(secuenciaAutomoviles());
        printerWriter.println(secuenciaReparaciones());
        printerWriter.println(secuenciaTiposDeServicios());
        printerWriter.println(secuenciaServicios());
        printerWriter.println(secuenciaManosDeObras());
        printerWriter.println(secuenciaRepuestos());
        printerWriter.println(secuenciaUsuarios());
                        
        printerWriter.close();
        super.approveSelection();
	}
	
	String fechaActual(){
		// este método devuelve la fecha actual en forma de string
		Calendar cal1 = Calendar.getInstance();
		java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/YYYY", new Locale("es", "AR"));
		return sdf.format(new java.sql.Date(cal1.getTimeInMillis()));
	}
	
	void inicializarListaClientes() throws Exception{
		// este metodo inicializa la lista de clientes

		try {
			listaClientes = clienteManager.listaClientes(); // obtengo todos los clientes de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaAutomoviles() throws Exception{
		// este metodo inicializa la lista de automoviles

		try {
			listaAutomoviles = automovilManager.listaAutomoviles(); // obtengo todos los automoviles de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaReparaciones() throws Exception{
		// este metodo inicializa la lista de automoviles

		try {
			listaReparaciones = reparacionManager.listaReparaciones(); // obtengo todos los automoviles de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaTiposDeServicios() throws Exception{
		// este metodo inicializa la lista de tipos de servicios

		try {
			listaTiposDeServicios = tipoDeServicioManager.listaTiposDeServicio(); // obtengo todos los tipos de servicios de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaMecanicos() throws Exception{
		// este metodo inicializa la lista de mecanicos

		try {
			listaMecanicos = mecanicoManager.listaMecanicos(); // obtengo todos los mecanicos de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaProveedores() throws Exception{
		// este metodo inicializa la lista de proveedores

		try {
			listaProveedores = proveedorManager.listaProveedores(); // obtengo todos los proveedores de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaServicios() throws Exception{
		// este metodo inicializa la lista de servicios

		try {
			listaServicios = servicioManager.listaServicios(); // obtengo todos los servicios de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaManosDeObras() throws Exception{
		// este metodo inicializa la lista de manos de obras

		try {
			listaManosDeObras = manoDeObraManager.listaManosDeObras(); // obtengo todas las manos de obras de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaRepuestos() throws Exception{
		// este metodo inicializa la lista de repuestos

		try {
			listaRepuestos = repuestoManager.listaRepuestos(); // obtengo todos los repuestos de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	void inicializarListaUsuarios() throws Exception{
		// este metodo inicializa la lista de usuarios

		try {
			listaUsuarios = usuarioManager.listaUsuarios(); // obtengo todos los usuarios de la BD
		} catch (Exception e) {
			throw e;
		}
	}
	
	String sentenciaClientes(){
		// este método arma la sentencia que agrega todos los clientes de la BD
		
		Cliente cliente;
		Iterator<Cliente> iterator = listaClientes.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaClientes.isEmpty())
			stringBuilder.append("INSERT INTO cliente (id_cliente,cuit,fecha_nacimiento, fallecido, nombre,apellido,telefono,celular,domicilio,localidad,codigo_postal,mail,observaciones) VALUES\n");
		
		while (iterator.hasNext()){
			cliente = iterator.next();
			stringBuilder.append("('" + cliente.getId_cliente() + "', '" + cliente.getCUIT() + "', '" + new java.sql.Date(	cliente.getFechaDeNacimiento().getTimeInMillis()) + "', '" + cliente.isFallecido() + "', '" + cliente.getNombre().trim().replace("'", "''") + "', '" + cliente.getApellido().trim().replace("'", "''") + "', '" + cliente.getTelefono().trim() + "', '" + cliente.getCelular() + "', '" + cliente.getDomicilio().replace("'", "''") + "', '" + cliente.getLocalidad().replace("'", "''") + "', '" + cliente.getCodigoPostal().replace("'", "''") + "', '" + cliente.getEmail().trim() + "', '" + cliente.getObservaciones().replace("'", "''") + "')");
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaAutomoviles(){
		// este método arma la sentencia que agrega todos los clientes de la BD
		
		Automovil automovil;
		Iterator<Automovil> iterator = listaAutomoviles.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaAutomoviles.isEmpty())
			stringBuilder.append("INSERT INTO automovil (id_automovil, id_cliente, dominio, num_motor, num_chasis, marca, modelo, anio, color, aceite, tipo_combustible, con_gnc, num_radio, codigo_llave, uso) VALUES\n");
		
		while (iterator.hasNext()){
			automovil = iterator.next();
			stringBuilder.append("('" + automovil.getId_automovil() + "', '" + automovil.getId_cliente() + "', '" + automovil.getDominio() + "', '" + automovil.getNumeroMotor().replace("'", "''") + "', '" + automovil.getNumeroChasis().replace("'", "''") + "', '" + automovil.getMarca().replace("'", "''") + "', '" + automovil.getModelo().replace("'", "''") +
					"', '" + automovil.getAño() + "', '" + automovil.getColor() + "', '" + automovil.getTipoAceite().replace("'", "''") + "', '" + automovil.getTipoCombustible() +"', '" + automovil.isConGNC() + "', '" + automovil.getNumeroRadio().replace("'", "''") + "', '" + automovil.getCodigoLlave().replace("'", "''") + "', '" + automovil.getUso().replace("'", "''") + "')");
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaReparaciones(){
		// este método arma la sentencia que agrega todas las reparaciones de la BD
		
		Reparacion reparacion;
		Iterator<Reparacion> iterator = listaReparaciones.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaReparaciones.isEmpty())
			stringBuilder.append("INSERT INTO reparacion (id_reparacion,id_automovil,fecha,km,observaciones) VALUES\n");
		
		while (iterator.hasNext()){
			reparacion = iterator.next();
			stringBuilder.append("('" + reparacion.getId_reparacion() + "', '" + reparacion.getId_automovil()+ "', '" + new java.sql.Date(	reparacion.getFechaReparacion().getTimeInMillis()) + "', '" + reparacion.getKilometraje() +
					"', '" + reparacion.getObservaciones().replace("'", "''") + "')");
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaTiposDeServicios(){
		// este método arma la sentencia que agrega todos los tipos de servicios de la BD
		
		TipoDeServicio tipoDeServicio;
		Iterator<TipoDeServicio> iterator = listaTiposDeServicios.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaTiposDeServicios.isEmpty())
			stringBuilder.append("INSERT INTO TIPO_DE_SERVICIO (id_tipo_de_servicio,nombre,tiempo_min_reparacion_estimado,tiempo_max_reparacion_estimado) VALUES\n");
		
		while (iterator.hasNext()){
			tipoDeServicio = iterator.next();
			stringBuilder.append("('" + tipoDeServicio.getId_tipo_servicio() + "', '" + tipoDeServicio.getNombre().replace("'", "''") + "', '" + tipoDeServicio.getTiempoMinimoReparacion() + "', '" + tipoDeServicio.getTiempoMaximoReparacion() + "')");
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaMecanicos(){
		// este método arma la sentencia que agrega todos los mecanicos de la BD
		
		Mecanico mecanico;
		Iterator<Mecanico> iterator = listaMecanicos.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaMecanicos.isEmpty())
			stringBuilder.append("INSERT INTO MECANICO (id_mecanico,nombre,apellido,telefono_fijo, telefono_celular, domicilio, localidad, codigo_postal) VALUES\n");
		
		while (iterator.hasNext()){
			mecanico = iterator.next();
			stringBuilder.append("('" + mecanico.getId_mecanico() + "', '" + mecanico.getNombre().replace("'", "''") + "', '" + mecanico.getApellido().replace("'", "''") + "', '" + mecanico.getTelefono() + "', '" + mecanico.getCelular() + "', '" + mecanico.getDomicilio().replace("'", "''") +"', '" + mecanico.getLocalidad().replace("'", "''") + "', '" + mecanico.getCodigoPostal().replace("'", "''") + "')");
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaProveedores(){
		// este método arma la sentencia que agrega todos los proveedores de la BD
		
		Proveedor proveedor;
		Iterator<Proveedor> iterator = listaProveedores.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaProveedores.isEmpty())
			stringBuilder.append("INSERT INTO PROVEEDOR (id_proveedor,nombre,telefono, telefono_alternativo) VALUES\n");
		
		while (iterator.hasNext()){
			proveedor = iterator.next();
			stringBuilder.append("('" + proveedor.getId_proveedor() + "', '" + proveedor.getNombre().replace("'", "''") + "', '" + proveedor.getTelefono() + "', '" + proveedor.getTelefonoAlternativo() + "')");
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaServicios(){
		// este método arma la sentencia que agrega todos los servicios de la BD
		
		Servicio servicio;
		Iterator<Servicio> iterator = listaServicios.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaServicios.isEmpty())
			stringBuilder.append("INSERT INTO SERVICIO (id_servicio,id_reparacion,id_tipo_de_servicio) VALUES\n");
		
		while (iterator.hasNext()){
			servicio = iterator.next();
			stringBuilder.append("('" + servicio.getId_servicio() + "', '" + servicio.getId_reparacion()+ "', '" + servicio.getId_tipo_de_servicio() + "')");
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaManosDeObras(){
		// este método arma la sentencia que agrega todas las manos de obras de la BD
		
		ManoDeObra manoDeObra;
		Iterator<ManoDeObra> iterator = listaManosDeObras.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaManosDeObras.isEmpty())
			stringBuilder.append("INSERT INTO MANO_DE_OBRA (id_mano_de_obra,id_servicio,id_mecanico,nombre, precio_mano_de_obra, observaciones) VALUES\n");
		
		while (iterator.hasNext()){
			manoDeObra = iterator.next();
			stringBuilder.append("('" + manoDeObra.getId_mano_de_obra() + "', '" + manoDeObra.getId_servicio() + "', " + (manoDeObra.getId_mecanico() == 0 ? "null" : ("'" + manoDeObra.getId_mecanico() + "'")) + ", '" + manoDeObra.getNombre().replace("'", "''") + "', '" + manoDeObra.getPrecio() + "', '" + manoDeObra.getObservaciones().replace("'", "''") + "')");
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaRepuestos(){
		// este método arma la sentencia que agrega todos los repuestos de la BD
		
		Repuesto repuesto;
		Iterator<Repuesto> iterator = listaRepuestos.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaRepuestos.isEmpty())
			stringBuilder.append("INSERT INTO REPUESTO (id_repuesto,id_servicio,id_proveedor, nombre, precio_unitario,cantidad, observaciones) VALUES\n");
		
		while (iterator.hasNext()){
			repuesto = iterator.next();
			stringBuilder.append("('" + repuesto.getId_repuesto() + "', '" + repuesto.getId_servicio() + "', " + (repuesto.getId_proveedor() == 0 ? "null" : ("'" + repuesto.getId_proveedor() + "'")) + ", '" + repuesto.getNombre().replace("'", "''") + "', '" + repuesto.getPrecioUnitario() + "', '" + repuesto.getCantidad() + "', '" + repuesto.getObservaciones().replace("'", "''") + "')");
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String sentenciaUsuarios(){
		// este método arma la sentencia que agrega todos los usuarios de la BD
		
		Usuario usuario;
		Iterator<Usuario> iterator = listaUsuarios.iterator();
		StringBuilder stringBuilder = new StringBuilder();
		if(!listaUsuarios.isEmpty())
			stringBuilder.append("INSERT INTO USUARIO (id_usuario, username, password, privilegio, last_login VALUES\n");
		
		while (iterator.hasNext()){
			usuario = iterator.next();
			stringBuilder.append("('" + usuario.getId_usuario() + "', '" + usuario.getUsername() + "', " + (usuario.getPassword() + ", '" + usuario.getPrivilegio() + "', '" + usuario.getLastLogin() + "')"));
		
			if(iterator.hasNext())
				stringBuilder.append(",\n");
		}
		stringBuilder.append(";");
		return stringBuilder.toString();
	}
	
	String secuenciaClientes() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla cliente
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = clienteManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE cliente_id_cliente_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
	
	String secuenciaAutomoviles() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla automovil
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = automovilManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE automovil_id_automovil_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
	
	String secuenciaReparaciones() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla reparacion
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = reparacionManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE reparacion_id_reparacion_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
	
	String secuenciaTiposDeServicios() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla tipo de servicio
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = tipoDeServicioManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE tipo_de_servicio_id_tipo_de_servicio_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
	
	String secuenciaProveedores() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla proveedor
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = proveedorManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE proveedor_id_proveedor_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
	
	String secuenciaServicios() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla servicio
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = servicioManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE servicio_id_servicio_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
	
	String secuenciaManosDeObras() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla mano de obra
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = manoDeObraManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE mano_de_obra_id_mano_de_obra_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
	
	String secuenciaRepuestos() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla respuesto
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = repuestoManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE repuesto_id_repuesto_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
	
	String secuenciaUsuarios() throws Exception{
		// este método recupera los últimos números de secuencia de la tabla usuario
		// y devuelve la sentencia correspondiente
		int last_value;
		last_value = usuarioManager.lastValue() + 1;
		String sentencia = "ALTER SEQUENCE usuario_id_usuario_seq RESTART WITH " + last_value +";";
		return sentencia;
	}
}

