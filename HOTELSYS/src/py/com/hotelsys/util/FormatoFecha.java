package py.com.hotelsys.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoFecha {
	private static DateFormat dfFull = DateFormat.getDateInstance(DateFormat.FULL);
	private static DateFormat dfHora = new SimpleDateFormat("HH:mm:ss");
	private static String fecha;
	private static Date date;
	private static DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	private static DateFormat dfh = new SimpleDateFormat("dd/MM/yyyy HH:mm");
	private static DateFormat dh = new SimpleDateFormat("HH:mm");
	public static String showFull(Date date) {
		fecha = "Hoy es "+dfFull.format(date)+" y son las "+dfHora.format(date);
		return fecha;
	}
	
	public static Date stringToDateHora(String fecha) {
		try {
			date = dfh .parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date stringToDate(String fecha) {
		try {
			date = df .parse(fecha);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public static String dateAString(Date date) {
		try {
			return df.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static Object dateAStringHora(Date date) {
		try {
			return dh.format(date);
		} catch (Exception e) {
			return "";
		}
	}

	public static String DateFull(Date date) {
		fecha = dfFull.format(date);
		return fecha;
	}
	public static String hora(Date date) {
		fecha = dh.format(date);
		return fecha;
	}
}
