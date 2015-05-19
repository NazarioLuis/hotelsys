package py.com.hotelsys.util;

import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
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
	
	public static void comprobarAlfanumerico(KeyEvent e, JLabel lbl){
		char c = e.getKeyChar();
        if (Character.isLetterOrDigit(c) || e.getKeyChar() == KeyEvent.VK_BACK_SPACE || e.getKeyChar() == KeyEvent.VK_ENTER){
			lbl.setVisible(false);
		}else{
			e.consume();
			mostrarError(lbl);
		}
	}
	public static void comprobarEspacio(KeyEvent e, JLabel lbl){
		char c = e.getKeyChar();
        if (Character.isSpaceChar(c)){
			e.consume();
			mostrarError(lbl);
		}else{
			lbl.setVisible(false);
		}
	}

	private static Timer timer;
	private static TimerTask task;
	public static long diff;
	
	private static void mostrarError(final JLabel lbl) {
		lbl.setVisible(true);
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					lbl.setVisible(false);
					timer.cancel();
					timer=null;
					task=null;
				}
			};
						
			timer.schedule(task, 1500);
		}
		
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
		diff = milis1 - milis2;
		
		int diffHours = (int) (diff / (60 * 60 * 1000));
		System.out.println(diffHours);
		dias = 0;

		while(diffHours>=24){
			dias+=1;
			diffHours-=24;
			
		}
		horas = diffHours;
	}

	public static List<String> stringAArray(String str){

		String[] array = str.split("-");
		
		return Arrays.asList(array);
	}

	public static String charAString(char[] password) {
		String str = "";
		for (int i = 0; i < password.length; i++) {
			str+=password[i];
		}
		return str;
	}

	public static String alterar(String motherboardWindows, int n) {
		char[] cs = HardDiskSN.getHardDisk("C").toCharArray();
		String str = "";
		for (int i = 0; i < cs.length; i++) {
			if(Character.isLetterOrDigit(cs[i]+n))
				str=str+ Character.toChars((cs[i]+n))[0];
			else {
				if(Character.isLetterOrDigit(cs[i]-n))
					str=str+ Character.toChars((cs[i]-n))[0];
				else
					str=str+cs[i];
			}
			
			
		}
		return str;
	}
}
