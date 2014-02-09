package proyectoanalista.persistencia;

public class Servicio {
	
	private int id_servicio;
	
	private int id_reparacion;
	
	private int id_tipo_de_servicio;
	
	private String nombre;
	
	private String precio;
	
	private String observaciones;

	
	public Servicio(){
		super();
	}

	public Servicio(int id_servicio, int id_tipo_de_servicio, int id_reparacion) {
		super();
		this.id_servicio = id_servicio;
		this.id_tipo_de_servicio = id_tipo_de_servicio;
		this.id_reparacion = id_reparacion;
	}


	public int getId_tipo_de_servicio() {
		return id_tipo_de_servicio;
	}

	public void setId_tipo_de_servicio(int id_tipo_de_servicio) {
		this.id_tipo_de_servicio = id_tipo_de_servicio;
	}
	
	public int getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(int id_servicio) {
		this.id_servicio = id_servicio;
	}

	public int getId_reparacion() {
		return id_reparacion;
	}

	public void setId_reparacion(int id_reparacion) {
		this.id_reparacion = id_reparacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getPrecio() {
		return precio;
	}

	public void setPrecio(String precio) {
		this.precio = precio;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	

}
