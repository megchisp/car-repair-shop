package proyectoanalista.persistencia;

public class ManoDeObra {
	
	private int id_mano_de_obra;
	
	private int id_servicio;
	
	private int id_mecanico;
	
	private String nombre;
	
	private double precio_mano_de_obra;
	
	private String observaciones;

	
	public ManoDeObra(){
		super();
	}

	public ManoDeObra(int id_mano_de_obra, int id_servicio, int id_mecanico, String nombre, double precio_mano_de_obra, String observaciones) {
		super();
		this.setId_mano_de_obra(id_mano_de_obra);
		this.setId_servicio(id_servicio);
		this.setId_mecanico(id_mecanico);
		this.nombre = nombre;
		this.precio_mano_de_obra = precio_mano_de_obra;
		this.observaciones = observaciones;
	}



	public int getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(int id_servicio) {
		this.id_servicio = id_servicio;
	}

	public int getId_mecanico() {
		return id_mecanico;
	}

	public void setId_mecanico(int id_mecanico) {
		this.id_mecanico = id_mecanico;
	}

	public int getId_mano_de_obra() {
		return id_mano_de_obra;
	}

	public void setId_mano_de_obra(int id_mano_de_obra) {
		this.id_mano_de_obra = id_mano_de_obra;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecio() {
		return precio_mano_de_obra;
	}

	public void setPrecio(double precio) {
		this.precio_mano_de_obra = precio;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}
	

}
