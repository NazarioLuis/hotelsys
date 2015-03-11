package py.com.hotelsys.presentacion.formulario;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import py.com.hotelsys.componentes.AbmBoton;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Cliente;



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

	private ClienteDao clienteDao = new ClienteDao();;
	private List<Cliente> listaCliente;
	private CustomTable tabla;
	private Object[] fila;
	private AbmBoton abmBoton;
	private String accion = "";
	private PlaceholderTextField tNombre;
	private PlaceholderTextField tDocumento;
	private PlaceholderTextField tTelefono;
	private PlaceholderTextField tDireccion;
	private PlaceholderTextField tEmail;
	private JTextArea tObservacin;
	private PlaceholderTextField tBuscar;
	private JPanel panel;
	private Cliente cliente;


	/**
	 * Create the dialog.
	 */
	public FormCliente() {
		setBounds(100, 100, 900, 410);
		getContentPane().setLayout(null);
		
		
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(10, 11, 388, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tNombre = new PlaceholderTextField();
		tNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		tNombre.setPlaceholder("Nombre del Cliente");
		tNombre.setBounds(25, 24, 301, 20);
		panel.add(tNombre);
		
		tDocumento = new PlaceholderTextField();
		tDocumento.setFont(new Font("Tahoma", Font.BOLD, 11));
		tDocumento.setPlaceholder("Nro de Documento");
		tDocumento.setBounds(25, 55, 173, 20);
		panel.add(tDocumento);
		
		tTelefono = new PlaceholderTextField();
		tTelefono.setFont(new Font("Tahoma", Font.BOLD, 11));
		tTelefono.setPlaceholder("Nro de Tel\u00E9fono");
		tTelefono.setBounds(25, 86, 173, 20);
		panel.add(tTelefono);
		
		tDireccion = new PlaceholderTextField();
		tDireccion.setFont(new Font("Tahoma", Font.BOLD, 11));
		tDireccion.setPlaceholder("Direcci\u00F3n");
		tDireccion.setBounds(25, 148, 334, 20);
		panel.add(tDireccion);
		
		tEmail = new PlaceholderTextField();
		tEmail.setFont(new Font("Tahoma", Font.BOLD, 11));
		tEmail.setPlaceholder("Correo Electr\u00F3nico");
		tEmail.setBounds(25, 117, 301, 20);
		panel.add(tEmail);
		
		tObservacin = new JTextArea("Observaci\u00F3n:");
		tObservacin.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tObservacin.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tObservacin.setRows(10);
		tObservacin.setLineWrap(true);
		tObservacin.setBounds(25, 195, 334, 77);
		panel.add(tObservacin);
		
		abmBoton = new AbmBoton();
		abmBoton.setBounds(10, 324, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(408, 11, 465, 277);
		getContentPane().add(scrollPane);
		
		
		tBuscar = new PlaceholderTextField();
		tBuscar.setPlaceholder("Criterio de Busqueda");
		tBuscar.setBounds(523, 293, 350, 20);
		getContentPane().add(tBuscar);
		
		
		tabla = new CustomTable(new String[] {"#", "Nombre", "Documento", "Telefono"}, new int[] {5, 190, 30, 40});
		scrollPane.setViewportView(tabla);
		
		abmBoton.botones(false, accion);
		abmBoton.setAbi(this);
		
		
		habilitarCampos(false);
		recuperaDatos();		
	}

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		listaCliente = clienteDao.recuperaTodo();
		
		if (listaCliente.size()>0) {
			cargarGrilla();
		}
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		while (tabla.getRowCount()>0) {
			tabla.getModelo().removeRow(0);
		}
		fila = new Object[tabla.getColumnCount()];
		for (Cliente c:listaCliente) {
			fila[0] = c.getId();
			fila[1] = c.getNombre();
			fila[2] = c.getDocumento();
			fila[3] = c.getTelefono();
			tabla.getModelo().addRow(fila);
 		}
	}

	@Override
	public void nuevo() {
		accion = "AGREGAR";
		habilitarCampos(true);
		abmBoton.botones(true, accion);
	}

	
	@Override
	public void modificar() {
		accion = "MODIFICAR";
		habilitarCampos(true);
		abmBoton.botones(true, accion);
	}

	@Override
	public void eliminar() {
		cliente = new Cliente();
		cliente.setId((int) tabla.getValueAt(tabla.getSelectedRow(), 0));
		clienteDao.eliminar(cliente);
	}

	@Override
	public void salir() {
		dispose();
	}

	@Override
	public void guardar() {
		cargarAtributos();
		if(accion.equals("AGREGAR"))
			clienteDao.insertar(cliente);
		if (accion.equals("MODIFICAR")) 
			clienteDao.actualizar(cliente);
	}

	@Override
	public void cancelar() {
		accion = "";
		habilitarCampos(false);
		abmBoton.botones(false, accion);
	}

	@Override
	public void habilitarCampos(boolean b) {
		tNombre.setEnabled(b);
		tDocumento.setEnabled(b);
		tDireccion.setEnabled(b);
		tTelefono.setEnabled(b);
		tEmail.setEnabled(b);
		tObservacin.setEnabled(b);
		tBuscar.setEnabled(!b);
		if(b==false)
			tBuscar.requestFocus();
		else
			tNombre.requestFocus();
	}

	@Override
	public void cargarAtributos() {
		// TODO Auto-generated method stub
		
	}
}
