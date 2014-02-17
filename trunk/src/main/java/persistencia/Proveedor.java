package persistencia;

public class Proveedor {
	
	private int id_proveedor;
	
	private String nombre;

	private String telefono;
	
	private String telefonoAlternativo;
	
		
	public String getNombre() {
		return nombre;
	}

	public void setNombre( String nombre ) {
		this.nombre = nombre;
	}

	
	public int getId_proveedor() {
		return id_proveedor;
	}

	public void setId_proveedor(int id_proveedor) {
		this.id_proveedor = id_proveedor;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono( String telefono ) {
		this.telefono = telefono;
	}

	public String getTelefonoAlternativo() {
		return telefonoAlternativo;
	}

	public void setTelefonoAlternativo( String telefonoAlternativo ) {
		this.telefonoAlternativo = telefonoAlternativo;
	}

	
	public Proveedor() {
		super();
	}
	
	public Proveedor( int id_proveedor, String nombre, String telefono, String telefonoAlternativo) {
		super();
		
		this.id_proveedor = id_proveedor;
		this.nombre = nombre;
		this.telefono = telefono;
		this.telefonoAlternativo = telefonoAlternativo;
	}
	
	public Proveedor( String nombre, String telefono, String telefonoAlternativo) {
		super();
		
		
		this.nombre = nombre;
		this.telefono = telefono;
		this.telefonoAlternativo = telefonoAlternativo;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		
		int result = 1;
		
		result = prime * result + ( ( nombre == null ) ? 0 : nombre.hashCode() );
		result = prime * result + ( ( telefono == null ) ? 0 : telefono.hashCode() );
		result = prime * result + ( ( telefonoAlternativo == null ) ? 0 : telefonoAlternativo.hashCode() );
		
		
		return result;
	}

	@Override
	public boolean equals( Object obj ) {
		if( this == obj )
			return true;
		
		if( obj == null )
			return false;
		
		if( getClass() != obj.getClass() )
			return false;
		
		Proveedor other = ( Proveedor ) obj;
		
		if( nombre == null ) {
			if( other.nombre != null )
				return false;
		} else if( !nombre.equals( other.nombre ) )
			return false;
		
		if( telefono == null ) {
			if( other.telefono != null )
				return false;
		} else if( !telefono.equals( other.telefono ) )
			return false;
		
		if( telefonoAlternativo == null ) {
			if( other.telefonoAlternativo != null )
				return false;
		} else if( !telefonoAlternativo.equals( other.telefonoAlternativo ) )
			return false;
		
		return true;
	}

	@Override
	public String toString() {
		return "Proveedor [nombre=" + nombre + ", telefono=" + telefono + ", telefono alternativo=" + telefonoAlternativo + "]";
	}
}

