package py.com.hotelsys.util;

import java.text.DecimalFormat;
import java.text.ParseException;

import javax.swing.text.MaskFormatter;

import py.com.hotelsys.dao.CotizacionDao;

public class VariablesDelSistema {
	private static MaskFormatter formatter;
	public static Double cotizacionReal;
	public static Double cotizacionDolar;
	
	public static MaskFormatter formatoFecha(){
		try {
			formatter = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		formatter.setPlaceholderCharacter('_');
		return formatter;
	}

	public static Object formatoFactura() {
		try {
			formatter = new MaskFormatter("###-###-#######");
			
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		formatter.setPlaceholderCharacter('_');
		return formatter;
	}

	private static DecimalFormat df;
	
	public static String formatoDecimal(Double valor) {
		 df = new DecimalFormat("###,###.##");
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
		
		System.out.println(cotizacionReal);
		System.out.println(cotizacionDolar);
		cotizacionDao.cerrar();		
		
	}
}
