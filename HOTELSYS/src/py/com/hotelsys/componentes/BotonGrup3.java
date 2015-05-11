package py.com.hotelsys.componentes;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import py.com.hotelsys.interfaces.TranBotonInterface2;

@SuppressWarnings("serial")
public class BotonGrup3 extends JPanel {
	TranBotonInterface2 abi;
	private JButton btnModificar;
	private JButton btnGuardar;
	private JButton btnNuevo;
	private JButton btnCancelar;
	private JButton btnEliminar;
	private JButton btnSalir;
	private JButton btnAgregar;

	public void setAbi(TranBotonInterface2 abi) {
		this.abi = abi;
	}

	public BotonGrup3() {
		setLayout(new GridLayout(7, 0, 0, 5));
		
		btnModificar = new JButton("Modificar");
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abi.modificar();
			}
		});
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.guardar();
			}
		});
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.nuevo();
			}
		});
		add(btnNuevo);
		add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.cancelar();
			}
		});
		add(btnCancelar);
		add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.eliminar();
			}
		});
		add(btnEliminar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 10));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.salir();
			}
		});
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abi.agregar();
			}
		});
		btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 10));
		add(btnAgregar);
		add(btnSalir);
	}
	
	public void botones(boolean b,String accion){
		btnNuevo.setEnabled(!b);
		btnSalir.setEnabled(!b);
		btnGuardar.setEnabled(b);
		btnCancelar.setEnabled(b);
		
		if(accion.equals("DATOS")){
			btnModificar.setEnabled(true);
			btnEliminar.setEnabled(true);
			btnAgregar.setEnabled(true);
		}else{
			btnModificar.setEnabled(false);
			btnEliminar.setEnabled(false);
			btnAgregar.setEnabled(false);
		}
	}

}
