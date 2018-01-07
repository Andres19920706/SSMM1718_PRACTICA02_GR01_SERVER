package es.ujaen.ssmm1718_practica02_gr01_servidor;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.ujaen.ssmm1718.practica02_gr01_dispositivos.DaoDispositivos;
import es.ujaen.ssmm1718.practica02_gr01_dispositivos.DtoDispositivos;
import es.ujaen.ssmm1718_practica02_gr01_usuarios.DaoUsuario;
import es.ujaen.ssmm1718_practica02_gr01_usuarios.DtoUsuario;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final String [] USER = {"Andres","123"};
	private static String [] PUNTO1 = {"1","ON"};
	private static String [] PUNTO2 = {"2","ON"};
	private static String [] PUNTO3 = {"3","OFF"};
	private static int EXPIREDMS = 3600000;
	private String sesionID;
	@Autowired
	DaoUsuario daoUsser;
	
	@Autowired
	DaoDispositivos daoDispositivos;
	
	//Aviso de errores
	private static final String [] ERRCLIENTE ={"401:Unauthorized","403 - Forbidden","423:Locked"};
		//Errores del cliente:
			// 401:Unauthorized -  la autentificaci�n es posible pero ha fallado o a�n no ha sido provista.
			// 403: Forbidden - La solicitud fue legal, pero el servidor reh�sa responderla dado que el cliente no tiene los privilegios para hacerla.
			// 423:Locked - El recurso al que se est� teniendo acceso est� bloqueado
	private static final String [] VISTAS = {"autentica"};
	/**
	 * M�todo: home -> Recurso por defecto.
	 * Petici�n: Get & Post
	 * @parem request de tipo HttpServletRequest
	 * @param locale de tipo Locale
	 * @param model de tipo Model
	 * @return resultado de tipo String
	 * @author Andres
	 * Petici�n Get: http://localhost:8080/ssmm1718_practica02_gr01_servidor/?usuario=andres&clave=123 
	 */
	@RequestMapping(value ={"/", "/index"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String home(HttpServletRequest request, Model model) {
		logger.info("Welcome home!");
		//Declaraci�n de variables
		String resultado; //Contiene la respuesta como resultado de ejecutar la petici�n.
		
		//Extremos los datos y los procesamos
		if(request.getParameter("usuario")==null ||  request.getParameter("clave")==null){
			resultado = this.ERRCLIENTE[1];
		}else{
			System.out.println("Servidor: "+request.getParameter("usuario"));
			String usuario = request.getParameter("usuario");
			String pass = request.getParameter("clave");
			DtoUsuario dtoUsser = daoUsser.verUsuario(usuario);
			
			if(dtoUsser!=null && pass.equals(dtoUsser.getPass())){
				if(pass.equals(dtoUsser.getPass())){
					System.out.println("a: "+pass+", b: "+dtoUsser.getPass());
					HttpSession session;
					//Creamos la sesi�n
					session = request.getSession(true);
					//A�adimos el usuario en la sesi�n
					session.setAttribute ("usuario",usuario);
					sesionID = session.getId(); //Guardamos el identificador de la sesi�n, dado que con android no la guarda.
					
					//Establecemos la expiraci�n de la sesi�n a 1 hora
					
					session.setMaxInactiveInterval(EXPIREDMS/1000);
					
					
					//Devolvemos la fecha de expiracion de la sesi�n
					Long horaMilis = new Date().getTime();//Obtenemos la hora
			        //Long sessionExpired = horaMilis + (Long) incremetoMilis;
			        Timestamp fecha = new Timestamp(horaMilis);//Lo pasmaos al fotmarto "Timestamp"
			        fecha.setTime(fecha.getTime()+EXPIREDMS); //Aumentamos una hora
					
			        //Genermoas la respuesta del servidor
			        if(daoUsser.nuevaSession(session.getId(), usuario,fecha)){
			        	resultado = "SESSION-ID="+session.getId()+"&EXPIERD="+fecha;
			        	
			        }else{//Error al establcer conexi�n
			        	resultado = this.ERRCLIENTE[1];
			        	
			        }
					
				}else{
					resultado = this.ERRCLIENTE[0];	
				}
				
			}else{
				resultado = this.ERRCLIENTE[0];
			}
			
		}
		
		//Agregamos el resultado a la respuesta.
		model.addAttribute("resultado",resultado);
		
		//Devolvemos la respuesta
		return VISTAS[0];
		
		
	}
	
	//Borrar
	@RequestMapping(value ={"/hola"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String home2(HttpServletRequest request, Model model) {
		return "home";
	}
	
	//
	@RequestMapping(value ={"/dispositivos"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String dispositivos (HttpServletRequest request, Model model) {
		logger.info("Viendo los dispositivos");
		//Declaraci�n de variables
		String resultado; //Contiene la respuesta como resultado de ejecutar la petici�n.

		//Extremos los datos y los procesamos
		if(request.getParameter("usuario")==null ||  request.getParameter("sessionID")==null){
			resultado = this.ERRCLIENTE[1];
		}else{
			
			try{
				System.out.println("Sesion registrada: "+sesionID);
				System.out.println("Session recibida: "+request.getParameter("sessionID"));
				//Comprobamos la sessi�n el servidor
				if(daoUsser.comprobarSession(request.getParameter("sessionID"), request.getParameter("usuario"))){
					System.out.println("EEEEEE, hay sesion");
					ArrayList<DtoDispositivos> listaDispositivos = daoDispositivos.verDispositivo();
					System.out.println("Lista: "+listaDispositivos.get(0).getId());
					resultado = "lista=";
					//Generamos la respuesta para devolver la lista de productos
					for(int i=0;i<listaDispositivos.size();i++){
						resultado=resultado+listaDispositivos.get(i).getLocalizacion()
								+"@"+listaDispositivos.get(i).isEstado();
						if(i<(listaDispositivos.size()-1)){
							resultado=resultado+"&";
						}
					}
				}else{
					System.out.println("No hay session");
					resultado="Session erronea";
				}

			}catch(Exception e){
				System.out.println("Excepcion: "+e);
				resultado = "Error al conectar a la BBDD";
			}
			
		}
		//Agregamos el resultado a la respuesta.
		model.addAttribute("resultado",resultado);
		return VISTAS[0];
	}
	
	
	//Camibar estado de los dispositivos
		@RequestMapping(value ={"/estadoDispositivo"}, method = {RequestMethod.GET,RequestMethod.POST})
		public String dispositivoEstado (HttpServletRequest request, Model model) {
			logger.info("Cambio del estado de los dispositivos");
			//Declaraci�n de variables
			String resultado; //Contiene la respuesta como resultado de ejecutar la petici�n.

			//Extremos los datos y los procesamos
			if(request.getParameter("usuario")==null ||  request.getParameter("sessionID")==null
					|| request.getParameter("estado")==null || request.getParameter("nameDispositivo")==null){
				resultado = this.ERRCLIENTE[1];
			}else{
				//Comprobamos la session
				//Comprobamos la sessi�n el servidor
				if(daoUsser.comprobarSession(request.getParameter("sessionID"), request.getParameter("usuario"))){
					System.out.println("EEEEEE, hay sesion");
					String localizacion = request.getParameter("nameDispositivo");
					boolean estado = Boolean.parseBoolean(request.getParameter("estado"));
					
					resultado = daoDispositivos.cambiarEstado(localizacion, estado);
					
				}else{
					System.out.println("No hay session");
					resultado="Session erronea";
				}
			
			}
			System.out.println("E reulstado: "+resultado);
			//Agregamos el resultado a la respuesta.
			model.addAttribute("resultado",resultado);
			return VISTAS[0];
		}
	
	
}
