package py.com.hotelsys.componentes;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import py.com.hotelsys.interfaces.AbmBotonInterface;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;

@SuppressWarnings("serial")
public class AbmBoton extends JPanel {
	AbmBotonInterface abi;

	public void setAbi(AbmBotonInterface abi) {
		this.abi = abi;
	}

	public AbmBoton() {
		setLayout(new GridLayout(0, 6, 5, 0));
		
		JButton btnModificar = new JButton("Modificar");
		btnModificar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				abi.modificar();
			}
		});
		
		JButton btnGuardar = new JButton("Guardar");
		btnGuardar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnGuardar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.guardar();
			}
		});
		
		JButton btnNuevo = new JButton("Nuevo");
		btnNuevo.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnNuevo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.nuevo();
			}
		});
		add(btnNuevo);
		add(btnGuardar);
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnCancelar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.cancelar();
			}
		});
		add(btnCancelar);
		add(btnModificar);
		
		JButton btnEliminar = new JButton("Eliminar");
		btnEliminar.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.eliminar();
			}
		});
		add(btnEliminar);
		
		JButton btnSalir = new JButton("Salir");
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				abi.salir();
			}
		});
		add(btnSalir);
	}

}
