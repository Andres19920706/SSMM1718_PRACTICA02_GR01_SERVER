package es.ujaen.ssmm1718.practica02_gr01_dispositivos;

import java.sql.Timestamp;
import java.util.ArrayList;

import com.panamahitek.ArduinoException;

import jssc.SerialPortException;

public interface DaoDispositivosInterface {
	
	//Control de dispositivos
	public ArrayList verDispositivo ();
	public DtoDispositivos verDispositivos (int id);
	public String cambiarEstado (String localizacion, boolean estado);

}
