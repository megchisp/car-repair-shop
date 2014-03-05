package negocio;

import java.util.List;

import persistencia.Usuario;

public interface IUsuarioManager {
	public boolean login (String username, char[] password) throws Exception;
	public int modificarPassword (Usuario usuario, char[] oldPassword, char[] newPassword) throws Exception;
	public int modificar( Usuario usuario ) throws Exception;
	public Usuario getUsuario (String username) throws Exception;
	public List<Usuario> listaUsuarios() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
}
