package py.com.hotelsys.componentes;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import py.com.hotelsys.interfaces.TranBotonInterface;

@SuppressWarnings("serial")
public class BotonGrup2 extends JPanel {
	TranBotonInterface tbi;
	private JButton btnVer;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	public JButton btnEliminar;
	private JButton btnSalir;
	public JButton btnConfirmar;

	

	public TranBotonInterface getTbi() {
		return tbi;
	}

	public void setTbi(TranBotonInterface tbi) {
		this.tbi = tbi;
	}

	public BotonGrup2() {
		setLayout(new GridLayout(5, 0, 0, 5));
		
		
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tbi.nuevo();
			}
		});
		add(btnNuevo);

		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tbi.anular();
			}
		});
		
	
		
		btnVer = new JButton("Ver");
		btnVer.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnVer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tbi.ver();
			}
		});
		
		btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tbi.confimar();
			}
		});
		btnConfirmar.setFont(new Font("Tahoma", Font.BOLD, 11));
		add(btnConfirmar);
		add(btnVer);
		add(btnEliminar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tbi.salir();
			}
		});
		add(btnSalir);
	}
	
	public void botones(boolean b,String accion){
		btnNuevo.setEnabled(!b);
		btnSalir.setEnabled(!b);
		btnGuardar.setEnabled(b);
		btnCancelar.setEnabled(b);
		if(accion.equals("DATOS")){
			btnVer.setEnabled(true);
			btnEliminar.setEnabled(true);
		}else{
			btnVer.setEnabled(false);
			btnEliminar.setEnabled(false);
		}
	}

}
