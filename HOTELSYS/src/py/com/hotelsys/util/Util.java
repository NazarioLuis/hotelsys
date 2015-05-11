package py.com.hotelsys.util;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.text.NumberFormatter;

import py.com.hotelsys.dao.CotizacionDao;

public class Util {
	private static MaskFormatter formatter;
	public static Double cotizacionReal;
	public static Double cotizacionDolar;
	public static DecimalFormat df = new DecimalFormat("###,###.##");
	private static double monto;
	public static int dias;
	public static int horas;
	
	
		       
	public static Double stringADouble(String str){
		try {
			monto = df.parse(str).doubleValue();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return monto;
	}
	
	public static DefaultFormatterFactory getDff() {
		NumberFormatter dnff = new NumberFormatter(df);
		return new DefaultFormatterFactory(dnff); 

	}
	
	
	
	public static MaskFormatter formatoFecha(){
		try {
			formatter = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter.setPlaceholderCharacter('_');
		return formatter;
	}

	public static MaskFormatter formatoFactura() {
		try {
			formatter = new MaskFormatter("###-###-#######");
			
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		formatter.setPlaceholderCharacter('_');
		
		return formatter;
	}

	
	
	public static String formatoDecimal(Double valor) {
		  
		 return df.format(valor);
	}
	
	public static void cotizacionDelDia() {
		CotizacionDao cotizacionDao = new CotizacionDao();
		try {
			cotizacionReal = cotizacionDao.recuperarPorMoneda(0).getMonto();
			cotizacionDolar = cotizacionDao.recuperarPorMoneda(1).getMonto();
		} catch (Exception e) {
			cotizacionReal = 0.0;
			cotizacionDolar = 0.0;
		}
		
		cotizacionDao.cerrar();		
		
	}



	



	public static void validarNumero(KeyEvent e) {
		char c = e.getKeyChar();
		if(!Character.isDigit(c) && e.getKeyCode() != KeyEvent.VK_BACK_SPACE)
			e.consume();
	}

	public static MaskFormatter formatoHora() {
		try {
			formatter = new MaskFormatter("##:##");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter.setPlaceholderCharacter('_');
		return formatter;
	}
	
	public static void restarFecha(Date fech1, Date fech2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		
		// Establecer las fechas
		cal1.setTime(fech1);
		cal2.setTime(fech2);
		
		// conseguir la representacion de la fecha en milisegundos
		long milis1 = cal1.getTimeInMillis();
		long milis2 = cal2.getTimeInMillis();

		// calcular la diferencia en milisengundos
		long diff = milis1 - milis2;
		
		int diffHours = (int) (diff / (60 * 60 * 1000));
		System.out.println(diffHours);
		dias = 0;

		while(diffHours>=24){
			dias+=1;
			diffHours-=24;
			
		}
		horas = diffHours;
	}
}
