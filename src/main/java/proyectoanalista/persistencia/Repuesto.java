package proyectoanalista.persistencia;

public class Repuesto {
	 private int id_repuesto;
	 
	 private int id_proveedor;
	 
	 private int id_servicio;
	 
	private String nombre;
	
	private double precioUnitario;
	
	private int cantidad;
	
	private String observaciones;
	


	
	
	public Repuesto() {
		super();
	}

	public Repuesto(int id_repuesto, int id_proveedor, int id_servicio,
			String nombre, double precioUnitario, int cantidad, String observaciones) {
		
		super();
		this.id_repuesto = id_repuesto;
		this.id_proveedor = id_proveedor;
		this.id_servicio = id_servicio;
		this.nombre = nombre;
		this.precioUnitario = precioUnitario;
		this.observaciones = observaciones;
		this.cantidad = cantidad;
	}

	public int getId_repuesto() {
		return id_repuesto;
	}

	public void setId_repuesto(int id_repuesto) {
		this.id_repuesto = id_repuesto;
	}

	public int getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public int getId_servicio() {
		return id_servicio;
	}

	public void setId_servicio(int id_servicio) {
		this.id_servicio = id_servicio;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public double getPrecioUnitario() {
		return precioUnitario;
	}

	public void setPrecioUnitario(double precioUnitario) {
		this.precioUnitario = precioUnitario;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

		
}