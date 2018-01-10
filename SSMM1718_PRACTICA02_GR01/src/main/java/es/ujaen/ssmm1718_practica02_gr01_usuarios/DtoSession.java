package es.ujaen.ssmm1718_practica02_gr01_usuarios;

import java.sql.Timestamp;

public class DtoSession extends DtoUsuario {
	private String sessionID;
	private Timestamp dateExpired;
	
	//Constructores
		public DtoSession(){}
		
		public DtoSession(String nombre,String sessionID, Timestamp time){
			super(nombre);
			this.sessionID = sessionID;
			this.dateExpired = time;
		}
	
		//Métodos Getters and Setters
		public String getSessionID() {
			return sessionID;
		}

		public void setSessionID(String sessionID) {
			this.sessionID = sessionID;
		}

		public Timestamp getDateExpired() {
			return dateExpired;
		}

		public void setDateExpired(Timestamp dateExpired) {
			this.dateExpired = dateExpired;
		}

}
