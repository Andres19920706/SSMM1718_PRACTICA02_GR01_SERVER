package es.ujaen.ssmm1718_practica02_gr01_servidor;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	private static final String [] USER = {"Andres","123"};
	//Aviso de errores
	private static final String [] ERRCLIENTE ={"401:Unauthorized","403 - Forbidden","423:Locked"};
		//Errores del cliente:
			// 401:Unauthorized -  la autentificación es posible pero ha fallado o aún no ha sido provista.
			// 403: Forbidden - La solicitud fue legal, pero el servidor rehúsa responderla dado que el cliente no tiene los privilegios para hacerla.
			// 423:Locked - El recurso al que se está teniendo acceso está bloqueado
	private static final String [] VISTAS = {"autentica"};
	/**
	 * Método: home -> Recurso por defecto.
	 * Petición: Get & Post
	 * @parem request de tipo HttpServletRequest
	 * @param locale de tipo Locale
	 * @param model de tipo Model
	 * @return resultado de tipo String
	 * @author Andres
	 * Petición Get: http://localhost:8080/ssmm1718_practica02_gr01_servidor/?usuario=andres&clave=123 
	 */
	@RequestMapping(value ={"/", "/index"}, method = {RequestMethod.GET,RequestMethod.POST})
	public String home(HttpServletRequest request, Model model) {
		logger.info("Welcome home!");
		//Declaración de variables
		String resultado; //Contiene la respuesta como resultado de ejecutar la petición.
		
		
		//Extremos los datos y los procesamos
		if(request.getParameter("usuario")==null ||  request.getParameter("clave")==null){
			resultado = this.ERRCLIENTE[1];
		}else{
			System.out.println("Servidor: "+request.getParameter("usuario"));
			String usuario = request.getParameter("usuario");
			String pass = request.getParameter("clave");
			if(usuario.equals(USER[0]) && pass.equals(USER[1])){
				
				
				HttpSession session;
				//Creamos la sesión
				session = request.getSession(true);
				//Añadimos el usuario en la sesión
				session.setAttribute ("usuario",usuario);
				//Establecemos la expiración de la sesión a 1 hora
				session.setMaxInactiveInterval(3600);
				
				
				//Devolvemos la fecha de expiracion de la sesión
				Calendar cal = Calendar.getInstance();
				cal.setTime(new Date());
				
				//Aumentamos unara hora
				Date tempDate = cal.getTime();
				System.out.println("Fecha actual: " + tempDate);
				
				//Aumentamos una 1, a la actual
				//cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+ 1);
				cal.set(Calendar.MINUTE,cal.get(Calendar.MINUTE)+2); // + 15 minutos
				
				tempDate = cal.getTime();
				System.out.println("Hora modificada: " + tempDate);
				
				//Formateamos
				SimpleDateFormat dt1 = new SimpleDateFormat("HH:mm:ss");
		        System.out.println(dt1.format(tempDate));
				
		        //Genermoas la respuesta del servidor
				resultado = "SESSION-ID="+session.getId()+"&EXPIERD="+dt1.format(tempDate);
			}else{
				resultado = "Error en la autenticacion";
			}
			
		}
		
		//Agregamos el resultado a la respuesta.
		model.addAttribute("resultado",resultado);
		
		//Devolvemos la respuesta
		return VISTAS[0];
		
		
	}
	
}
