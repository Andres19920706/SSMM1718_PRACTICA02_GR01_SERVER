package es.ujaen.ssmm1718_practica02_gr01_usuarios;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

public class MapperUsuario  implements RowMapper<DtoUsuario>{
	private static final String SHUSER = "nombre";
	private static final String SHPASS = "pass";
	
	//Constructor por defecto
	public MapperUsuario(){};
	
	/**
	 * Método para mapear los atributos del Objeto DtoUsuario con los de la tabla de la base de datos
	 */
	public DtoUsuario mapRow(ResultSet rs, int rowNum) throws SQLException{
		DtoUsuario dto = new DtoUsuario();
		
		dto.setName(rs.getString(SHUSER));
		dto.setPass(rs.getString(SHPASS));

		return dto;
	}
}
