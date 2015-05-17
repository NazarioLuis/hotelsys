package py.com.hotelsys.presentacion.app;

import py.com.hotelsys.util.HibernateUtil;
import py.com.hotelsys.util.JPass;

public class App {

	public static void main(String[] args) {
		JPass.setKey("HoSys");
		HibernateUtil.buildIfNeeded();
		Login login = new Login();
		login.setVisible(true);
	}

}
