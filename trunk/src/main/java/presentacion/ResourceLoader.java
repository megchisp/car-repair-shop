package presentacion;

import java.net.URL;

final public class ResourceLoader {
	/** Esta clase recupera las imagenes y m�sica de la carpeta resources 
	 * distingue si se est� llamando desde el IDE o desde el archivo .jar **/
	public URL load(String path){
		URL myImageURL = getClass().getResource(path);
		if (myImageURL != null)
			return myImageURL; // lo llama el IDE
		else
			return getClass().getResource("/resources" + path); // lo llama el JAR
	}
}
