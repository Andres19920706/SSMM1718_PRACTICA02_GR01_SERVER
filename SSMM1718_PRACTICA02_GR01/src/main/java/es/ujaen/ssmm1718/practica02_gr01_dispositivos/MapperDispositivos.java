package es.ujaen.ssmm1718.practica02_gr01_dispositivos;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;



public class MapperDispositivos implements RowMapper<DtoDispositivos>{
	private static final String SHID = "id";
	private static final String SHLOCATE = "localizacion";
	private static final String SHESTADO = "estado";
	
	//Constructor por defecto
	public MapperDispositivos(){};
	
	/**
	 * Método para mapear los atributos del Objeto DtoUsuario con los de la tabla de la base de datos
	 */
	public DtoDispositivos mapRow(ResultSet rs, int rowNum) throws SQLException{
		DtoDispositivos dto = new DtoDispositivos();
		
		dto.setId(rs.getInt(SHID));
		dto.setLocalizacion(rs.getString(SHLOCATE));
		dto.setEstado(rs.getBoolean(SHESTADO));

		return dto;
	}

}
