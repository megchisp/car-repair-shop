package negocio;

import java.security.MessageDigest;
import java.util.List;

import persistencia.IUsuarioDao;
import persistencia.Usuario;
import persistencia.UsuarioDao;

public class UsuarioManager implements IUsuarioManager {
	private int estadoFinal;
	IUsuarioDao usuarioDao;
	
	public UsuarioManager(){
		usuarioDao = new UsuarioDao();
	}
	
	public int modificarPassword(Usuario usuario, char[] oldPassword, char[] newPassword) throws Exception {
		if (true) // aca iría una validacion, pero siempre se cumpliría
			estadoFinal = usuarioDao.modificarPassword(usuario, hashPassword(oldPassword), hashPassword(newPassword));
		return estadoFinal;
	}
	
	public int modificar(Usuario usuario) throws Exception{
		if(true)
			estadoFinal = usuarioDao.modificar(usuario);
		return estadoFinal;
	}

	public boolean login(String username, char[] password) throws Exception {
		return usuarioDao.login(username, hashPassword(password));
	}

	public Usuario getUsuario(String username) throws Exception {
		return usuarioDao.getUsuario(username);
	}
	
	// este método recibe un char[] y hash SHA1-1 en forma de String
	private String hashPassword(char[] password) throws Exception{
		byte[] mdbytes;
		MessageDigest cript = MessageDigest.getInstance("SHA-1");
        cript.reset();
        cript.update((String.valueOf(password) + "garciaosorio").getBytes("UTF-8"));
        mdbytes = cript.digest();
	
		//convert the byte to hex format
	    StringBuffer hexString = new StringBuffer();
		for (int i=0;i<mdbytes.length;i++) {
		  hexString.append(Integer.toHexString(0xFF & mdbytes[i]));
		}
		return hexString.toString();
	}

	public List<Usuario> listaUsuarios() throws Exception {
		return usuarioDao.listaUsuarios();
	}
	
	public int nextID() throws Exception {
		return usuarioDao.nextID();
	}

	public int lastValue() throws Exception {
		return usuarioDao.lastValue();
	}
}
