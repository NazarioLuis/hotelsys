package py.com.hotelsys.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormatoFecha {
	private static DateFormat dfFull = DateFormat.getDateInstance(DateFormat.FULL);
	private static DateFormat dfHora = new SimpleDateFormat("HH:mm:ss");
	private static String fecha;
	public static String showFull(Date date) {
		fecha = "Hoy es "+dfFull.format(date)+" y son las "+dfHora.format(date);
		return fecha;
	}
}
