package proyectoanalista.persistencia;

public class TipoDeServicio {

	private int id_tipo_servicio;
	
	private String nombre;

	private int tiempoMinimoReparacion;
	
	private int tiempoMaximoReparacion;
	
	public TipoDeServicio(int id_tipo_servicio, String nombre, int tiempoMinimoReparacion, int tiempoMaximoReparacion) {
		super();
		this.id_tipo_servicio = id_tipo_servicio;
		this.nombre = nombre;
		this.tiempoMinimoReparacion = tiempoMinimoReparacion;
		this.tiempoMaximoReparacion = tiempoMaximoReparacion;
	}
	
	public TipoDeServicio() {
		super();
	}

	public int getId_tipo_servicio() {
		return id_tipo_servicio;
	}

	public void setId_tipo_servicio(int id_tipo_servicio) {
		this.id_tipo_servicio = id_tipo_servicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getTiempoMinimoReparacion() {
		return tiempoMinimoReparacion;
	}

	public void setTiempoMinimoReparacion(int tiempoMinimoReparacion) {
		this.tiempoMinimoReparacion = tiempoMinimoReparacion;
	}

	public int getTiempoMaximoReparacion() {
		return tiempoMaximoReparacion;
	}

	public void setTiempoMaximoReparacion(int tiempoMaximoReparacion) {
		this.tiempoMaximoReparacion = tiempoMaximoReparacion;
	}

}
