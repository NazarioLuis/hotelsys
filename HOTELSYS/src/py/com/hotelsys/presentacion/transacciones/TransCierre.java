package py.com.hotelsys.presentacion.transacciones;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import py.com.hotelsys.componentes.NumberTextField;
import py.com.hotelsys.componentes.PlaceholderTextField2;
import py.com.hotelsys.dao.CajaDao;
import py.com.hotelsys.dao.CobranzaDao;
import py.com.hotelsys.modelo.Caja;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.Util;
import py.com.hotelsys.util.VariableSys;

@SuppressWarnings("serial")
public class TransCierre extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private NumberTextField tRemanente;
	private NumberTextField tCobranzas;
	private PlaceholderTextField2 tTotal;
	private Caja caja;

	

	/**
	 * Create the dialog.
	 */
	public TransCierre(JFrame f) {
		super(f);
		setBounds(100, 100, 369, 235);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		
		tRemanente = new NumberTextField();
		tRemanente.setEnabled(false);
		
		tRemanente.setBounds(162, 64, 117, 20);
		contentPanel.add(tRemanente);
		
		JLabel lblRemanente = new JLabel("Remanente");
		lblRemanente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRemanente.setBounds(71, 67, 81, 14);
		contentPanel.add(lblRemanente);
		{
			JLabel lblFeca = new JLabel(FormatoFecha.DateFull(new Date()));
			lblFeca.setHorizontalAlignment(SwingConstants.LEFT);
			lblFeca.setFont(new Font("Tahoma", Font.ITALIC, 13));
			lblFeca.setBounds(37, 11, 217, 30);
			contentPanel.add(lblFeca);
		}
		{
			JLabel lblHora = new JLabel(FormatoFecha.hora(new Date()));
			lblHora.setHorizontalAlignment(SwingConstants.CENTER);
			lblHora.setFont(new Font("Tahoma", Font.ITALIC, 13));
			lblHora.setBounds(249, 11, 57, 30);
			contentPanel.add(lblHora);
		}
		{
			JLabel lblTotalCobranza = new JLabel("Total Cobranzas");
			lblTotalCobranza.setHorizontalAlignment(SwingConstants.RIGHT);
			lblTotalCobranza.setBounds(57, 42, 95, 14);
			contentPanel.add(lblTotalCobranza);
		}
		
		tCobranzas = new NumberTextField();
		tCobranzas.setEnabled(false);
		tCobranzas.setBounds(162, 39, 117, 20);
		contentPanel.add(tCobranzas);
		
		JLabel lblTotalEnCaja = new JLabel("Total en Caja");
		lblTotalEnCaja.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotalEnCaja.setBounds(71, 100, 81, 14);
		contentPanel.add(lblTotalEnCaja);
		
		tTotal = new PlaceholderTextField2();
		tTotal.setEnabled(false);
		tTotal.setBounds(162, 89, 117, 37);
		contentPanel.add(tTotal);
		{
			JLabel lblGs = new JLabel("Gs.");
			lblGs.setHorizontalAlignment(SwingConstants.CENTER);
			lblGs.setBounds(281, 39, 26, 17);
			contentPanel.add(lblGs);
		}
		{
			JLabel label = new JLabel("Gs.");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(281, 64, 26, 17);
			contentPanel.add(label);
		}
		{
			JLabel label = new JLabel("Gs.");
			label.setHorizontalAlignment(SwingConstants.CENTER);
			label.setBounds(281, 99, 26, 17);
			contentPanel.add(label);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Cerrar");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cerrar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
		traerValores();
	}

	private void traerValores(){
		caja = VariableSys.caja;
		
		CobranzaDao cobranzaDao = new CobranzaDao();
		Double cobranzas = cobranzaDao.recuperaTotalPorCaja(caja);
		
		
		tRemanente.setValue(caja.getRemanente());
		tCobranzas.setValue(cobranzas);
		tTotal.setText(Util.formatoDecimal(caja.getRemanente()+cobranzas));
	}

	private void cerrar() {
		int s = JOptionPane.showConfirmDialog(null, "Esta seguro de que desea cerrar la caja.\n"
				+ "Si es así se darán por concluidas todas las actividades relacionadas a la misma", "Atención", 
				JOptionPane.YES_NO_CANCEL_OPTION);
		if (s == JOptionPane.YES_OPTION){
			CajaDao cajaDao = new CajaDao();
			caja.setCierre(new Date());
			try {
				cajaDao.actualizar(caja);
				JOptionPane.showMessageDialog(null, "Se ha cerrado la caja exitosamente","Aviso",1);
				VariableSys.caja = null;
				dispose();
			} catch (Exception e) {
				cajaDao.rollback();
			}
		}
		
	}
}
