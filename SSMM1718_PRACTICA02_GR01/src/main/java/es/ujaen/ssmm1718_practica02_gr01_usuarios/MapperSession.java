package es.ujaen.ssmm1718_practica02_gr01_usuarios;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.jdbc.core.RowMapper;

public class MapperSession implements RowMapper<DtoUsuario>{
	private static final String SHSESSIONID = "sessionid";
	private static final String SHUSER = "nombre";
	private static final String SHTIMEXPIRED = "dateExpired";
	
	//Constructor por defecto
	public MapperSession(){};
	
	public DtoSession mapRow(ResultSet rs, int rowNum) throws SQLException{
		DtoSession dto = new DtoSession();
		
		dto.setName(rs.getString(SHUSER));
		dto.setSessionID(rs.getString(SHSESSIONID));
		dto.setDateExpired(rs.getTimestamp(SHTIMEXPIRED));

		return dto;
	}
}
