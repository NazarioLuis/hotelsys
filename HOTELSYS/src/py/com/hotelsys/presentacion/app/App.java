package py.com.hotelsys.presentacion.app;

import java.awt.GridLayout;

import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import py.com.hotelsys.dao.LicenciaDao;
import py.com.hotelsys.modelo.Licencia;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.HibernateUtil;
import py.com.hotelsys.util.JPass;
import py.com.hotelsys.util.HardDiskSN;
import py.com.hotelsys.util.Util;

public class App {

	private static LicenciaDao licenciaDao;

	public static void main(String[] args) throws Exception {
		String id = HardDiskSN.getHardDisk("C");
		JPass.setKey("HoSys");
		HibernateUtil.buildIfNeeded();
		licenciaDao = new LicenciaDao(); 
		Licencia l = licenciaDao.recuperarValides(Util.alterar(id.toUpperCase(),3));
		if (l == null) {
			JFrame frame = new JFrame();
			JPanel p = new JPanel();
			JLabel l1 = new JLabel();
			l1.setText("Error de licencia. Comuniquese con el desarrollador");
			JLabel l2 = new JLabel();
			l2.setText("pc#: "+id.toLowerCase());
			JLabel l3 = new JLabel();
			l3.setText("Vto.");
			JTextField serial = new JTextField();
			JFormattedTextField fecha = new JFormattedTextField(Util.formatoFecha());
			p.setLayout(new GridLayout(5,1,0,0));
			p.add(l1);
			p.add(l2);
			p.add(serial);
			p.add(l3);
			p.add(fecha);
			JOptionPane.showConfirmDialog(frame, p, "Acceso denegado", JOptionPane.OK_OPTION,1);
			
			if (serial.getText().toUpperCase().equals(Util.alterar(id.toUpperCase(),3))) {
				licenciaDao = new LicenciaDao();
				l = new Licencia();
				l.setId(licenciaDao.recuperMaxId()+1);
				l.setSerial(serial.getText());
				if (fecha.getText().equals("__/__/____")) 
					l.setValides(FormatoFecha.stringToDate("12/12/2099"));
				else 
					l.setValides(FormatoFecha.stringToDate(fecha.getText()));
				licenciaDao = new LicenciaDao();
				licenciaDao.insertar(l);
			}else{
				HibernateUtil.cerrar();
				System.exit(0);
			}
		}
		Login login = new Login();
		login.setVisible(true);
		
		
	}

}
