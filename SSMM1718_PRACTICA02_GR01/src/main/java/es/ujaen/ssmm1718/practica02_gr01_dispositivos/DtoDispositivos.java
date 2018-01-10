package es.ujaen.ssmm1718.practica02_gr01_dispositivos;

public class DtoDispositivos {
	private int id;
	private String localizacion;
	private boolean estado;
	//Constructores
	public DtoDispositivos(){}
	
	public DtoDispositivos(int id,String localizacion,boolean estado){
		this.id = id;
		this.localizacion = localizacion;
		this.estado = estado;
	}
	
	//Getters and Setters
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLocalizacion() {
		return localizacion;
	}

	public void setLocalizacion(String localizacion) {
		this.localizacion = localizacion;
	}

	public boolean isEstado() {
		return estado;
	}

	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	
	
}
