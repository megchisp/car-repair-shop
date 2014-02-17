package persistencia;

public class Automovil {
	
	private int id_automovil;
	private int id_cliente;
	private String dominio;
	private String numeroMotor;
	private String numeroChasis;
	private String marca;
	private String modelo;
	private int anio;
	private int color;
	private String tipoAceite;
	private String tipoCombustible;
	private boolean conGNC;
	private String numeroRadio;
	private String codigoLlave;
	private String uso;
	
	//variables auxiliares
	private String nombrePropietario;
	private String apellidoPropietario;

	public int getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(int id_cliente) {
		this.id_cliente = id_cliente;
	}

	public int getId_automovil() {
		return id_automovil;
	}

	public void setId_automovil(int id_automovil) {
		this.id_automovil = id_automovil;
	}

	public String getDominio() {
		return dominio;
	}

	public void setDominio(String dominio) {
		this.dominio = dominio;
	}

	public String getNumeroMotor() {
		return numeroMotor;
	}

	public void setNumeroMotor(String numeroMotor) {
		this.numeroMotor = numeroMotor;
	}

	public String getNumeroChasis() {
		return numeroChasis;
	}

	public void setNumeroChasis(String numeroChasis) {
		this.numeroChasis = numeroChasis;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public int getAño() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public String getTipoAceite() {
		return tipoAceite;
	}

	public void setTipoAceite(String tipoAceite) {
		this.tipoAceite = tipoAceite;
	}

	public String getTipoCombustible() {
		return tipoCombustible;
	}

	public void setTipoCombustible(String tipoCombustible) {
		this.tipoCombustible = tipoCombustible;
	}

	public boolean isConGNC() {
		return conGNC;
	}

	public void setConGNC(boolean conGNC) {
		this.conGNC = conGNC;
	}

	public String getNumeroRadio() {
		return numeroRadio;
	}

	public void setNumeroRadio(String numeroRadio) {
		this.numeroRadio = numeroRadio;
	}

	public String getCodigoLlave() {
		return codigoLlave;
	}

	public void setCodigoLlave(String codigoLlave) {
		this.codigoLlave = codigoLlave;
	}

	public String getUso() {
		return uso;
	}

	public void setUso(String uso) {
		this.uso = uso;
	}

	public Automovil() {
		super();
	}
	
	
	
	public Automovil(int id_automovil, int id_cliente, String dominio, String numeroMotor, String numeroChasis, String marca, String modelo, int anio, int color, String tipoAceite, String tipoCombustible, boolean conGNC, String numeroRadio, String codigoLlave, String uso, String nombrePropietario, String apellidoPropietario) {
		super();
		this.id_automovil = id_automovil;
		this.id_cliente = id_cliente;
		this.dominio = dominio;
		this.numeroMotor = numeroMotor;
		this.numeroChasis = numeroChasis;
		this.marca = marca;
		this.modelo = modelo;
		this.anio = anio;
		this.color = color;
		this.tipoAceite = tipoAceite;
		this.tipoCombustible = tipoCombustible;
		this.conGNC = conGNC;
		this.numeroRadio = numeroRadio;
		this.codigoLlave = codigoLlave;
		this.uso = uso;
		
		this.setNombrePropietario(nombrePropietario);
		this.setApellidoPropietario(apellidoPropietario);
	}

	public String getNombrePropietario() {
		return nombrePropietario;
	}

	public void setNombrePropietario(String nombrePropietario) {
		this.nombrePropietario = nombrePropietario;
	}

	public String getApellidoPropietario() {
		return apellidoPropietario;
	}

	public void setApellidoPropietario(String apellidoPropietario) {
		this.apellidoPropietario = apellidoPropietario;
	}
}
