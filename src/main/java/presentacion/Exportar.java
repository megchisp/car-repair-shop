package presentacion;

import java.io.File;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
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
import persistencia.BD.ExportarBD;

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
	
	String stringSQLdatabase = null;
	
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
		String nombreArchivo = "backup_" + new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()) + ".sql";
		fileChooser.setFileFilter(filter);
		
		// Creo el archivo con el nombre dado
		fileChooser.setSelectedFile(new File(nombreArchivo)); 
		
		fileChooser.setDialogTitle(" Exportar ");
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			// Obtengo la base de datos SQL completa en forma de String
			try {
				stringSQLdatabase = new ExportarBD().obtenerStringSQLdatabase();
			
				// Escribo String al archivo
				printerWriter = new PrintWriter(fileToSave);
				printerWriter.print(stringSQLdatabase);
				
		        JOptionPane.showMessageDialog( this, "La base de datos se ha exportado correctamente." , "Éxito", JOptionPane.INFORMATION_MESSAGE );
				} catch (Exception e) {
					JOptionPane.showMessageDialog( this, e , "Error", JOptionPane.ERROR_MESSAGE );
				}
			
				finally{
				// cierro el archivo
				printerWriter.close();
			}
		} 
	}
	
}
