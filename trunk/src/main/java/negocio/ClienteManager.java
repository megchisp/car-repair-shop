package negocio;

import java.sql.Date;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import persistencia.Automovil;
import persistencia.Cliente;
import persistencia.ClienteDao;
import persistencia.IClienteDao;

public class ClienteManager implements IClienteManager {
	// variables para la verificacion del CUIT
    private static int dniStc; 	
    private static int xyStc; 	
    private static int digitoStc; 

	private int estadoFinal;
	private IClienteDao clienteDao;
	
	public ClienteManager(){
		clienteDao = new ClienteDao();
	}
	
	public void setClienteDao( ClienteDao clienteDao ) {
		this.clienteDao = clienteDao;
	}
	
	public int agregar( Cliente cliente ) throws Exception {
		if( validarDatos( cliente ) )
			estadoFinal = clienteDao.agregar( cliente );
		
		return estadoFinal;
	}
	
	public int modificar( Cliente cliente )  throws Exception {
		if( validarDatos( cliente ) )
			estadoFinal = clienteDao.modificar( cliente );
		return estadoFinal;
	}
	
	public int eliminar(Cliente cliente) throws Exception {
		if (true) // aca iría una validacion, pero siempre se cumpliría
			estadoFinal = clienteDao.eliminar( cliente );
		return estadoFinal;
	}
	
	public List<Cliente> listaClientesPorNombre() throws Exception {
		return clienteDao.listaClientesPorNombre();
	}
	
	public List<Cliente> listaClientesPorID() throws Exception {
		return clienteDao.listaClientesPorID();
	}
	
	public List<Cliente> listaClientesNoFallecidos() throws Exception {
		return clienteDao.listaClientesNoFallecidos();
	}
	
	private boolean validarDatos( Cliente cliente ) {
		if( !validarCUIT( cliente.getCUIT() ) ) {
			estadoFinal = 3;

			return false;
		}
		
		if( !esUnaFechaValida( cliente.getFechaDeNacimiento() ) ) {
			estadoFinal = 4;
		
			return false;
		}
		
		if(codigoPostalSinLocalidad(cliente.getLocalidad(), cliente.getCodigoPostal())){
			estadoFinal = 6;
			
			return false;
		}
		
		if( !esUnEmailValido( cliente.getEmail() ) && !(cliente.getEmail().isEmpty()) ){
			estadoFinal = 5;
		
			return false;
		}
			
		return true;
	}
	
	
	private boolean codigoPostalSinLocalidad(String localidad, String codigoPostal){
		// esta funcion devuelve true si el usuario ingresa un código postal pero no ingresa la localidad
		if(localidad.isEmpty() && !codigoPostal.isEmpty())
			return true;
		return false;
	}
	
	
	private boolean validarCUIT(String cuit) {
		
		if(cuit.contains( "<" ) || cuit.length() == 0)
			return true;
		
		if (cuit.length() != 13){
			return false;
		}
	      //Verificaciones previas del formato.
        int posPrimerGuion = cuit.indexOf("-");
        
        if(posPrimerGuion==-1)
            return false;
        
        int posUltimoGuion = cuit.lastIndexOf("-");
               
        //Verificar que no haya solo un guion.
        if(posUltimoGuion==posPrimerGuion) {
            return false;
        }            
        
        //Verificar que no haya un guión al final.
        if(cuit.lastIndexOf("-")==(cuit.length()-1)) {
            return false;
        }
        
        String xyStr, dniStr, digitoStr;
        int digitoTmp;
        int n = cuit.lastIndexOf("-");
        xyStr = cuit.substring(0, 2);
        dniStr = cuit.substring(cuit.indexOf("-") + 1, n);
        digitoStr = cuit.substring(n + 1, n + 2); 	 	
        
        if (xyStr.length() != 2 || dniStr.length() > 8 || digitoStr.length() != 1) 
            return false;
        
        try { 	 		
            xyStc = Integer.parseInt(xyStr); 	 		
            dniStc = Integer.parseInt(dniStr); 	 		
            digitoTmp = Integer.parseInt(digitoStr); 	 	
        } catch (NumberFormatException e) {
            return false; 	 
        }

        if (xyStc != 20 && xyStc != 23 && xyStc != 24 && xyStc != 27 && xyStc != 30 && xyStc != 33 && xyStc != 34) 
            return false; 	 	 	

        calcular(); 	 	 	 

        if (digitoStc == digitoTmp && xyStc == Integer.parseInt(xyStr)) 
            return true; 

        return false; 	
    }
	

	private static void calcular() { 	  
        long tmp1, tmp2; 	
        long acum = 0; 	
        int n = 2; 	
        tmp1 = xyStc * 100000000L + dniStc; 	

        for (int i = 0; i < 10; i++) { 	  		
            tmp2 = tmp1 / 10; 	
            acum += (tmp1 - tmp2 * 10L) * n; 
            tmp1 = tmp2; 	  	
            if (n < 7) 	  		
                n++; 	  	
            else 	  	
                n = 2; 	  
        } 	  	 	  

        n = (int)(11 - acum % 11); 

        if (n == 10) { 	  
            if (xyStc == 20 || xyStc == 27 || xyStc == 24) 	 
                xyStc = 23; 	  	
            else 	  	
                xyStc = 33; 	  

            // No es necesario hacer la llamada recursiva a calcular(), 	  	
            // se puede poner el digito en 9 si el prefijo original era 	  	
            // 23 o 33 o poner el dijito en 4 si el prefijo era 27

            calcular(); 	  	
        } else { 	
            if (n == 11) 	 
                digitoStc = 0; 	  
            else 	  		
            digitoStc = n; 	  
        } 	
    }
	
	private boolean esUnEmailValido(String email){
		Pattern pattern;
		Matcher matcher;
		String email_pattern = 
				"^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		pattern = Pattern.compile(email_pattern);
		matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	private boolean esUnaFechaValida(Calendar fecha){
		try {
			fecha.getTimeInMillis(); // si la fecha no es correcta getTime() lanza una excepción
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	public int nextID() throws Exception {
		return clienteDao.nextID();
	}

	public int lastValue() throws Exception {
		return clienteDao.lastValue();
	}

	public String[] getApellidoNombre(Automovil automovil) throws Exception {
		return clienteDao.getApellidoNombre(automovil);
	}

	public Date ultimaVez(Cliente cliente) throws Exception {
		return clienteDao.ultimaVez(cliente);
	}
	
	public Cliente getCliente(Automovil automovil) throws Exception {
		return clienteDao.getCliente(automovil);
	}
}
