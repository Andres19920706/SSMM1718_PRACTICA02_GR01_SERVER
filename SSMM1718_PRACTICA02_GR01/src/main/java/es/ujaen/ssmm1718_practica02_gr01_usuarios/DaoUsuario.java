package es.ujaen.ssmm1718_practica02_gr01_usuarios;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

public class DaoUsuario implements DaoUsuarioInterface{
	private JdbcTemplate jdbcTemplate;//Para trabajar con JDBC que nos proporciona spring
	private DataSource dataSource;
	
	public void setDataSource(DataSource dataSource) {
		   this.dataSource = dataSource;
		   this.jdbcTemplate = new JdbcTemplate(dataSource);
	}//Fin del setDataSource
	
	/**
	 * Método para obtener los datos de un usuario
	 */
	public DtoUsuario verUsuario (String nombre){
		//Sentencia SQL
		String sql= "select * from usuarios where nombre = ? ";
		//Parámetros para la consulta
		Object[] parametros = {nombre}; //Necesaria para query
		//Instancia de la clase de mapeo del usuario
		MapperUsuario mapper = new MapperUsuario();
		//Devuelve los usuarios con el nombre
	    ArrayList usuario = (ArrayList) this.jdbcTemplate.query(sql,parametros, mapper);
	    
	    //Devolvemos el resultado
	    if(usuario.isEmpty()){
	    	return null;
	    }else{
	    	return (DtoUsuario) usuario.get(0);
	    }
	}//Fin usuario
	
	//Guardar session
		public boolean nuevaSession (String ssesionID,String nombre,Timestamp fecha){
			//Sentencia SQL
					String sql = "INSERT INTO sessiones (nombre,sessionid,dateExpired) VALUES(?,?,?)";
					try{
						Object[] parametros = {nombre,ssesionID,fecha};
						int resul = this.jdbcTemplate.update(sql,parametros);
						System.out.println("hola resultado:  "+resul);
						if(resul==0){
							return false;
						}else{
							return true;
						}
					}catch(Exception io){
						
						System.out.println("Excepción "+io);
						return false;
					}
		}
		
		//Comprobar session
		public boolean comprobarSession (String ssesionID,String nombre){
			//Estado del resutlado
			Boolean resultado;
			//Sentencia SQL
		    String sql= "select * from sessiones where nombre = ? AND sessionid = ?";
			//Parámetros para la consulta
			Object[] parametros = {nombre,ssesionID}; //Necesaria para query
			//Instancia de la clase de mapeo del usuario
			MapperSession mapper = new MapperSession();
			//Devuelve los usuarios con el nombre
			 ArrayList<DtoSession> mySession = (ArrayList) this.jdbcTemplate.query(sql,parametros,mapper);
			//
			if(mySession.isEmpty()){
				//No hay sesión
				resultado = false;
			}else{
				//
				
				 //Comprobamos la fecha de la sesión
	            Long horaMilis = new Date().getTime();//Obtenemos la hora
	            //Long sessionExpired = horaMilis + (Long) incremetoMilis;
	            Timestamp fecha = new Timestamp(horaMilis);//Lo pasmaos al fotmarto "Timestamp"
	            Timestamp expired = mySession.get(0).getDateExpired();
	            
	            System.out.println("Fecha actual: "+fecha+"Fecha expired: "+expired);
	            if (expired.getTime() - fecha.getTime() > 0) {
	                resultado = true;
	            } else {
	                resultado = false;
	            }
			}
			
			return resultado;
		}
	
	
}
