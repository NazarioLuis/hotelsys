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
import py.com.hotelsys.dao.CajaDao;
import py.com.hotelsys.modelo.Caja;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.Util;
import py.com.hotelsys.util.VariableSys;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

@SuppressWarnings("serial")
public class TransAbertura extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private NumberTextField tRemanente;

	

	/**
	 * Create the dialog.
	 */
	public TransAbertura(JFrame f) {
		super(f);
		setBounds(100, 100, 305, 180);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		
		tRemanente = new NumberTextField();
		tRemanente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER)
					abrir();
			}
		});
		tRemanente.setBounds(141, 64, 117, 20);
		contentPanel.add(tRemanente);
		
		JLabel lblRemanente = new JLabel("Remanente");
		lblRemanente.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRemanente.setBounds(50, 67, 81, 14);
		contentPanel.add(lblRemanente);
		{
			JLabel lblFeca = new JLabel(FormatoFecha.DateFull(new Date()));
			lblFeca.setHorizontalAlignment(SwingConstants.LEFT);
			lblFeca.setFont(new Font("Tahoma", Font.ITALIC, 13));
			lblFeca.setBounds(10, 11, 217, 30);
			contentPanel.add(lblFeca);
		}
		{
			JLabel lblHora = new JLabel(FormatoFecha.hora(new Date()));
			lblHora.setHorizontalAlignment(SwingConstants.CENTER);
			lblHora.setFont(new Font("Tahoma", Font.ITALIC, 13));
			lblHora.setBounds(222, 11, 57, 30);
			contentPanel.add(lblHora);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("Abrir");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						abrir();
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
	}



	private void abrir() {
		if(tRemanente.getText().isEmpty())
			tRemanente.setText("0");
		CajaDao cajaDao = new CajaDao();
		Caja caja = new Caja();
		caja.setId(cajaDao.recuperMaxId()+1);
		caja.setApertura(new Date());
		caja.setRemanente(Util.stringADouble(tRemanente.getText()));
		cajaDao = new CajaDao();
		try {
			cajaDao.insertar(caja);
			JOptionPane.showMessageDialog(null, "Se ha abierto la caja exitosamente","Aviso",1);
			VariableSys.caja = caja;
			dispose();
		} catch (Exception e) {
			cajaDao.rollback();
		}
		
	}
}
