package persistencia;

public class Mecanico {
	private int id_mecanico;
	
	private String nombre;

	private String apellido;
	
	private String telefono;
	
	private String celular;
	
	private String domicilio;
	
	private String localidad;
	
	private String codigoPostal;
	
	public Mecanico(){
		super();
	}
	
	public Mecanico(int id_mecanico, String nombre, String apellido, String telefono, String celular, String domicilio, String localidad, String codigoPostal) {
		super();
		this.id_mecanico = id_mecanico;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.celular = celular;
		this.domicilio = domicilio;
		this.localidad = localidad;
		this.codigoPostal = codigoPostal;
	}

	public int getId_mecanico() {
		return id_mecanico;
	}

	public void setId_mecanico(int id_mecanico) {
		this.id_mecanico = id_mecanico;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(String domicilio) {
		this.domicilio = domicilio;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal(String codigoPostal) {
		this.codigoPostal = codigoPostal;
	}
}
