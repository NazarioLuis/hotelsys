package py.com.hotelsys.componentes;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import py.com.hotelsys.interfaces.AbmBotonInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class BotonGrup extends JPanel {
	AbmBotonInterface abi;
	public JButton btnModificar;
	public JButton btnGuardar;
	public JButton btnNuevo;
	public JButton btnCancelar;
	public JButton btnEliminar;
	public JButton btnSalir;

	public void setAbi(AbmBotonInterface abi) {
		this.abi = abi;
	}

	public BotonGrup() {
		setLayout(new GridLayout(0, 6, 5, 0));
		
		btnModificar = new JButton("Modificar");
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abi.modificar();
			}
		});
		
		btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.guardar();
			}
		});
		
		btnNuevo = new JButton("Nuevo");
		btnNuevo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.nuevo();
			}
		});
		add(btnNuevo);
		add(btnGuardar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.cancelar();
			}
		});
		add(btnCancelar);
		add(btnModificar);
		
		btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.eliminar();
			}
		});
		add(btnEliminar);
		
		btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.salir();
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
			btnModificar.setEnabled(true);
			btnEliminar.setEnabled(true);
		}else{
			btnModificar.setEnabled(false);
			btnEliminar.setEnabled(false);
		}
	}

}
