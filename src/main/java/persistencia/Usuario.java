package persistencia;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Usuario {
	private int id_usuario;
	private String username;
	private String password;
	private int privilegio; // 1 - administrador
	private Calendar lastLogin;
	
	public Usuario() {
		super();
	}
	
	
	public Usuario(int id_usuario, String username, String password,
			 int privilegio, Date lastLogin) {
		super();
		Calendar myCal = new GregorianCalendar();
		myCal.setTime(lastLogin);
		
		this.id_usuario = id_usuario;
		this.username = username;
		this.password = password;
		this.privilegio = privilegio;
		this.lastLogin = myCal;
	}


	public int getId_usuario() {
		return id_usuario;
	}
	public void setId_usuario(int id_usuario) {
		this.id_usuario = id_usuario;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public int getPrivilegio() {
		return privilegio;
	}
	public void setPrivilegio(int privilegio) {
		this.privilegio = privilegio;
	}

	public Calendar getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Calendar lastLogin) {
		this.lastLogin = lastLogin;
	}
	
	
}
