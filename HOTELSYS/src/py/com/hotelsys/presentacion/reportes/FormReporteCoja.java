package py.com.hotelsys.presentacion.reportes;

import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollPane;

import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.dao.CajaDao;

@SuppressWarnings("serial")
public class FormReporteCoja extends JDialog {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormReporteCoja dialog = new FormReporteCoja(null);
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the dialog.
	 */
	public FormReporteCoja(JFrame f) {
		super(f);
		setTitle("Informe de Caja");
		setBounds(100, 100, 784, 476);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 497, 147);
		getContentPane().add(scrollPane);
		
		CustomTable tablaCaja = new CustomTable(new String[] {"Cajero", "Apertura", "Cierre", "Monto"}, new int[] {250, 50, 50, 60});
		scrollPane.setViewportView(tablaCaja);
	}
	
	private void recuperarDatos(){
		CajaDao cajaDao = new CajaDao();
	}
}
