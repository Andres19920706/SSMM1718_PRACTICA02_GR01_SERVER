package es.ujaen.ssmm1718_practica02_gr01_usuarios;

public class DtoUsuario {
	private String name;
	private String pass;
	
	//Constructores
	public DtoUsuario(){
		this.name = "";
		this.pass = "";
	}
	
	public DtoUsuario(String user,String pass){
		this.name = user;
		this.pass = pass;
	}
	
	//Constuctor para las sessiones
	public DtoUsuario(String user){
		this.name = user;
		this.pass = "";
	}
	
	//Getters and Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
