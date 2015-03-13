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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.dao.ProveedorDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Cliente;
import py.com.hotelsys.modelo.Proveedor;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;



@SuppressWarnings("serial")
public class FormProveedor extends JDialog implements AbmBotonInterface {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormProveedor dialog = new FormProveedor();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private ProveedorDao proveedorDao;
	private List<Proveedor> listaProveedor;
	private CustomTable tabla;
	private Object[] fila;
	private BotonGrup abmBoton;
	private String accion = "";
	private PlaceholderTextField tNombre;
	private PlaceholderTextField tDocumento;
	private PlaceholderTextField tTelefono;
	private PlaceholderTextField tDireccion;
	private PlaceholderTextField tEmail;
	private JTextArea tObservacin;
	private PlaceholderTextField tBuscar;
	private JPanel panel;
	private Proveedor proveedor;
	private JLabel label;


	/**
	 * Create the dialog.
	 */
	public FormProveedor() {
		setTitle("Archivo de Proveedor");
		setBounds(100, 100, 900, 410);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(10, 11, 388, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tNombre = new PlaceholderTextField();
		tNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		tNombre.setPlaceholder("Nombre del Proveedor");
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
		
		label = new JLabel("Observaci\u00F3n:");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(25, 179, 107, 14);
		panel.add(label);
		
		abmBoton = new BotonGrup();
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
		
		//Carga el formulario al cambiar la seleccion de la tabla
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				cargarFormulario();
			}
		});
		scrollPane.setViewportView(tabla);
		
		abmBoton.botones(false, accion);
		abmBoton.setAbi(this);
		
		//Al iniciar deshavilita los campos y recupera los registros para la tabla
		habilitarCampos(false);
		recuperaDatos();		
	}

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		proveedorDao = new ProveedorDao();
		listaProveedor = proveedorDao.recuperaTodo();
		
		cargarGrilla();
		
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		tabla.vaciar();
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Proveedor p:listaProveedor) {
			fila[0] = p.getId();
			fila[1] = p.getNombre();
			fila[2] = p.getDocumento();
			fila[3] = p.getTelefono();
			tabla.agregar(fila);
 		}
		
		//mantiene el foco en el ultimo registro cargado
		tabla.setSeleccion();
		
	}

	@Override
	public void nuevo() {
		accion = "AGREGAR";
		limpiarCampos();
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
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el Proveedor: "+tabla.campo(1)+"?","Atención",JOptionPane.YES_NO_OPTION);
		if (si==JOptionPane.YES_OPTION) {
			proveedor = new Proveedor();
			proveedorDao = new ProveedorDao();
			proveedor.setId((int) tabla.campo(0));
			try {
				proveedorDao.eliminar(proveedor);
			} catch (Exception e) {
				proveedorDao.rollback();
				advertencia("No se eliminar el Proveedor "+tabla.campo(1)+". Esta en uso!",2);
			}
			inicializar();
		}
		
	}

	@Override
	public void salir() {
		dispose();
	}

	@Override
	public void guardar() {
		cargarAtributos();
		proveedorDao = new ProveedorDao();
		
		if(accion.equals("AGREGAR"))
			try {
				proveedorDao.insertar(proveedor);
			} catch (Exception e) {
				proveedorDao.rollback();
				advertencia("No se puede guardar el Proveedor. Los campos con * son obligatorios",2);
			}
		if (accion.equals("MODIFICAR"))
			try {
				proveedorDao.actualizar(proveedor);
			} catch (Exception e) {
				proveedorDao.rollback();
				advertencia("No se puede actualizar el Proveedor. Los campos con * son obligatorios",2);
			}
		
		
		inicializar();
		
	}

	@Override
	public void cancelar() {
		inicializar();
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
		tabla.setEnabled(!b);
		if(b==false)
			tabla.requestFocus();
		else
			tNombre.requestFocus();
	}

	//carga los atributos del objeto al ser persistido
	@Override
	public void cargarAtributos() {
		
		proveedor = new Proveedor();
		if(accion.equals("AGREGAR")){
			proveedorDao = new ProveedorDao();
			proveedor.setId(proveedorDao.recuperMaxId()+1);
		}else
			proveedor.setId((int) tabla.campo(0));		
		proveedor.setNombre(tNombre.getText());
		proveedor.setDocumento(tDocumento.getText());
		proveedor.setTelefono(tTelefono.getText());
		proveedor.setDireccion(tDireccion.getText());
		proveedor.setObservacion(tObservacin.getText());
	}

	//deja la pantallaen su estado inicial
	@Override
	public void inicializar() {
		accion = "";
		limpiarCampos();
		habilitarCampos(false);
		recuperaDatos();
		abmBoton.botones(false, accion);
			
	}

	
	//emite mensajes de forma dinamica de acuerdo al texto que se le envie
	@Override
	public void advertencia(String texto,int t) {
		JOptionPane.showMessageDialog(null, texto, "Atención", t);
	}

	@Override
	public void cargarFormulario() {
		if (tabla.getSelectedRow()>=0) {
			accion = "DATOS";
			abmBoton.botones(false, accion);
			proveedorDao = new ProveedorDao();
			System.out.println((int) tabla.campo(0));
			proveedor = proveedorDao.recuperarPorId((int) tabla.campo(0));
			if (proveedor!=null) {
				tNombre.setText(proveedor.getNombre());
				tDocumento.setText(proveedor.getDocumento());
				tDireccion.setText(proveedor.getDireccion());
				tEmail.setText(proveedor.getEmail());
				tTelefono.setText(proveedor.getTelefono());
				tObservacin.setText(proveedor.getObservacion());
			}
			
		}
		
	}

	@Override
	public void limpiarCampos() {
		tNombre.setText("");
		tDocumento.setText("");
		tDireccion.setText("");
		tEmail.setText("");
		tTelefono.setText("");
		tObservacin.setText("");
	}
	
	
}
