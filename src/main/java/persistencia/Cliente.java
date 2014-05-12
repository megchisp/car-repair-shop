package persistencia;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Cliente {
	private int id_cliente;
	private String cuit;
	private Calendar fechaDeNacimiento;
	private boolean booleanHabilitaFechaDeNacimiento;
	private boolean fallecido;
	private String nombre;
	private String apellido;
	private String telefono;
	private String celular;
	private String domicilio;
	private String localidad;
	private String codigoPostal;
	private String email;
	private String observaciones;

	public String getCUIT() {
		return cuit;
	}

	public void setCUIT( String cuit ) {
		this.cuit = cuit;
	}
	
	public Calendar getFechaDeNacimiento() {
		return fechaDeNacimiento;
	}

	
	public void setFechaDeNacimiento( Calendar date ) {
		this.fechaDeNacimiento = date;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre( String nombre ) {
		this.nombre = nombre;
	}

	public boolean isBooleanHabilitaFechaDeNacimiento() {
		return booleanHabilitaFechaDeNacimiento;
	}

	public void setBooleanHabilitaFechaDeNacimiento(
			boolean booleanHabilitaFechaDeNacimiento) {
		this.booleanHabilitaFechaDeNacimiento = booleanHabilitaFechaDeNacimiento;
	}

	public boolean isFallecido() {
		return fallecido;
	}

	public void setFallecido(boolean fallecido) {
		this.fallecido = fallecido;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido( String apellido ) {
		this.apellido = apellido;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono( String telefono ) {
		this.telefono = telefono;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular( String celular ) {
		this.celular = celular;
	}

	public String getDomicilio() {
		return domicilio;
	}

	public void setDomicilio( String domicilio ) {
		this.domicilio = domicilio;
	}

	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad( String localidad ) {
		this.localidad = localidad;
	}

	public String getCodigoPostal() {
		return codigoPostal;
	}

	public void setCodigoPostal( String codigoPostal ) {
		this.codigoPostal = codigoPostal;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail( String email ) {
		this.email = email;
	}
	
	public Cliente() {
		super();
	}
	
	public Cliente(int id_cliente, String cuit, Date date, boolean booleanHabilitaFechaDeNacimiento , boolean fallecido, String nombre, String apellido, String telefono, String celular, String domicilio, String localidad, String codigoPostal, String email, String observaciones ) {
		super();

		Calendar myCal = new GregorianCalendar();
		myCal.setTime(date);
		
		this.id_cliente = id_cliente;
		this.cuit = cuit;
		this.fechaDeNacimiento = myCal;
		this.booleanHabilitaFechaDeNacimiento = booleanHabilitaFechaDeNacimiento;
		this.fallecido = fallecido;
		this.nombre = nombre;
		this.apellido = apellido;
		this.telefono = telefono;
		this.celular = celular;
		this.domicilio = domicilio;
		this.localidad = localidad;
		this.codigoPostal = codigoPostal;
		this.email = email;
		this.observaciones = observaciones;
	}

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
}