package es.ujaen.ssmm1718_practica02_gr01_usuarios;

import java.sql.Timestamp;

public interface DaoUsuarioInterface {
	public DtoUsuario verUsuario (String nombre);
	//Control de sessiones en el servidor
	public boolean nuevaSession (String ssesionID,String nombre,Timestamp fecha);
	public boolean comprobarSession (String ssesionID,String nombre);
}
