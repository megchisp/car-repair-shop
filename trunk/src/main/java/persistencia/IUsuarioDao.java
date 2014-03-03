package persistencia;

import java.util.List;

public interface IUsuarioDao {
	public Usuario getUsuario(String username) throws Exception;
	public boolean login(String username, String password) throws Exception;
	public int modificarPassword( Usuario usuario, String oldPassword, String newPassword ) throws Exception;
	public List<Usuario> listaUsuarios() throws Exception;
	public int nextID() throws Exception;
	public int lastValue() throws Exception;
}
