package py.com.hotelsys.presentacion.formulario;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import py.com.hotelsys.componentes.AbmBoton;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.interfaces.AbmBotonInterface;



@SuppressWarnings("serial")
public class FormCliente extends JDialog implements AbmBotonInterface {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormCliente dialog = new FormCliente();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@SuppressWarnings("serial")
	private DefaultTableModel modelo = new DefaultTableModel(null,new String[] {"Id", "Nombre", "Documento", "Tel\u00E9fono"}){
		public boolean isCellEditable(int row, int column) {return false;};
	};
	private JTable table;

	/**
	 * Create the dialog.
	 */
	public FormCliente() {
		setBounds(100, 100, 900, 410);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(10, 11, 388, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		PlaceholderTextField tfNombre = new PlaceholderTextField();
		tfNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfNombre.setPlaceholder("Nombre del Cliente");
		tfNombre.setBounds(25, 24, 301, 20);
		panel.add(tfNombre);
		
		PlaceholderTextField tfDocumento = new PlaceholderTextField();
		tfDocumento.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfDocumento.setPlaceholder("Nro de Documento");
		tfDocumento.setBounds(25, 55, 173, 20);
		panel.add(tfDocumento);
		
		PlaceholderTextField tfTelefono = new PlaceholderTextField();
		tfTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfTelefono.setPlaceholder("Nro de Tel\u00E9fono");
		tfTelefono.setBounds(25, 86, 173, 20);
		panel.add(tfTelefono);
		
		PlaceholderTextField tfDireccion = new PlaceholderTextField();
		tfDireccion.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfDireccion.setPlaceholder("Direcci\u00F3n");
		tfDireccion.setBounds(25, 148, 334, 20);
		panel.add(tfDireccion);
		
		PlaceholderTextField tfEmail = new PlaceholderTextField();
		tfEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfEmail.setPlaceholder("Correo Electr\u00F3nico");
		tfEmail.setBounds(25, 117, 301, 20);
		panel.add(tfEmail);
		
		JTextArea txtrObservacin = new JTextArea("Observaci\u00F3n:");
		txtrObservacin.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		txtrObservacin.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		txtrObservacin.setRows(10);
		txtrObservacin.setLineWrap(true);
		txtrObservacin.setBounds(25, 195, 334, 77);
		panel.add(txtrObservacin);
		
		AbmBoton abmBoton = new AbmBoton();
		abmBoton.setAbi(this);
		abmBoton.setBounds(10, 324, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(408, 11, 466, 302);
		getContentPane().add(scrollPane);
		
		table = new JTable(modelo);
		scrollPane.setViewportView(table);
		
		
		

	}

	@Override
	public void nuevo() {
		JOptionPane.showMessageDialog(null, "hola");
	}

	@Override
	public void modificar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void salir() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void guardar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub
		
	}
}
