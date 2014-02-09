package proyectoanalista.persistencia;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Reparacion {
	private int id_reparacion;
	
	private int id_automovil;
	
	private Calendar fechaReparacion;
	
	private int kilometraje;

	private String observaciones;
	
	public int getId_reparacion() {
		return id_reparacion;
	}

	public int getId_automovil() {
		return id_automovil;
	}

	public void setId_automovil(int id_automovil) {
		this.id_automovil = id_automovil;
	}

	public void setId_reparacion(int id_reparacion) {
		this.id_reparacion = id_reparacion;
	}

	public Reparacion(){
		super();
	}

	public Reparacion(int id_reparacion, int id_automovil, Date fechaReparacion, int kilometraje,  String observaciones) {
		super();
		
		Calendar myCal = new GregorianCalendar();
		myCal.setTime(fechaReparacion);
		
		this.id_reparacion = id_reparacion;
		this.id_automovil = id_automovil;
		this.fechaReparacion = myCal;
		this.kilometraje = kilometraje;
		this.observaciones = observaciones;
	}

	public Calendar getFechaReparacion() {
		return fechaReparacion;
	}

	public void setFechaReparacion(Calendar fechaReparacion) {
		this.fechaReparacion = fechaReparacion;
	}

	public int getKilometraje() {
		return kilometraje;
	}

	public void setKilometraje(int kilometraje) {
		this.kilometraje = kilometraje;
	}

	public String getObservaciones() {
		return observaciones;
	}

	public void setObservaciones(String observaciones) {
		this.observaciones = observaciones;
	}

}
