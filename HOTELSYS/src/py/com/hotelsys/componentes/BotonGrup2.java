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
	private JButton btnAnular;
	private JButton btnSalir;

	

	public TranBotonInterface getTbi() {
		return tbi;
	}

	public void setTbi(TranBotonInterface tbi) {
		this.tbi = tbi;
	}

	public BotonGrup2() {
		setLayout(new GridLayout(4, 0, 0, 5));
		
		
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tbi.nuevo();
			}
		});
		add(btnNuevo);

		
		btnAnular = new JButton("Anular");
		btnAnular.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnAnular.addActionListener(new ActionListener() {
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
		add(btnVer);
		add(btnAnular);
		
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
			btnAnular.setEnabled(true);
		}else{
			btnVer.setEnabled(false);
			btnAnular.setEnabled(false);
		}
	}

}
