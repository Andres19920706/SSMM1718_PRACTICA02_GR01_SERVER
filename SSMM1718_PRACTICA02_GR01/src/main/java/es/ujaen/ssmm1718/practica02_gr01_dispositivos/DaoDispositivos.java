package es.ujaen.ssmm1718.practica02_gr01_dispositivos;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import com.panamahitek.*;
import es.ujaen.ssmm1718_practica02_gr01_usuarios.DtoSession;
import es.ujaen.ssmm1718_practica02_gr01_usuarios.MapperSession;
import jssc.SerialPortException;

public class DaoDispositivos implements DaoDispositivosInterface {
	private JdbcTemplate jdbcTemplate;//Para trabajar con JDBC que nos proporciona spring
	private DataSource dataSource;
	private static final String[] nameTable = {"dispositivos","sessiones"}; 
	
	public void setDataSource(DataSource dataSource) {
		   this.dataSource = dataSource;
		   this.jdbcTemplate = new JdbcTemplate(dataSource);
	}//Fin del setDataSource
	
	@Override
	public ArrayList<DtoDispositivos> verDispositivo() {
		//Sentencia SQL
		String sql= "select * from "+nameTable[0];
		//Parámetros para la consulta
		Object[] parametros = null; //Necesaria para query
				//Instancia de la clase de mapeo del usuario
				MapperDispositivos mapper = new MapperDispositivos();
				//Devuelve los usuarios con el nombre
			    ArrayList<DtoDispositivos> dispositivo = (ArrayList) this.jdbcTemplate.query(sql,parametros, mapper);
			    //Devolvemos el resultado
			    if(dispositivo.isEmpty()){
			    	return null;
			    }else{
			    	return dispositivo;
			    }
	}//Fin de verDispositivos
	
	@Override
	public DtoDispositivos verDispositivos(int id) {
		//Sentencia SQL
		String sql= "select * from "+nameTable[0]+" where id = ? ";
		//Parámetros para la consulta
		Object[] parametros = {id}; //Necesaria para query
				//Instancia de la clase de mapeo del usuario
				MapperDispositivos mapper = new MapperDispositivos();
				//Devuelve los usuarios con el nombre
			    ArrayList dispositivo = (ArrayList) this.jdbcTemplate.query(sql,parametros, mapper);
			    
			    //Devolvemos el resultado
			    if(dispositivo.isEmpty()){
			    	return null;
			    }else{
			    	return (DtoDispositivos) dispositivo.get(0);
			    }
	}//Fin de verDispositivos
	
	
	@Override
	public String cambiarEstado(String localizacion, boolean estado) {
		//Sentencia SQL
		String sql = "update "+nameTable[0]+" set estado= ? where localizacion= ?";
		try{
			int resul = this.jdbcTemplate.update(sql,estado, localizacion);
			System.out.println("hola "+resul);
			if(resul==0){
				return "false";
			}else{				
//				conectarArduino();
				return "true";
			}
		}catch(Exception io){
			return "Error al establcer conexión";
		}
		
	}//Fin de verDispositivos
	
//	private void conectarArduino(){
//		PanamaHitek_Arduino ardu = new PanamaHitek_Arduino();
//		
//		//Iniciamos la transmisión
//		try {
//			ardu.arduinoTX("COM5", 9600);
//			System.out.println("Inicado");
//		} catch (ArduinoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			System.out.println("no Inicado");
//		}
		
		//camibamos el estado
//		try {
//			ardu.sendData("25");
//		} catch (ArduinoException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (SerialPortException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		//
		
//	}
}
