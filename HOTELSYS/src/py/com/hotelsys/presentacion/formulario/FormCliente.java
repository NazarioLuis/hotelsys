package py.com.hotelsys.presentacion.formulario;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup;
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

	private ClienteDao clienteDao;
	private List<Cliente> listaCliente;
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
	private Cliente cliente;
	private JLabel label;
	private Timer timer;
	private TimerTask task;
	private int ultimaFila;


	/**
	 * Create the dialog.
	 */
	public FormCliente() {
		setTitle("Archivo de Cliente");
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
		
		tObservacin = new JTextArea("");
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
		tBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscar();
			}
		});
		tBuscar.setPlaceholder("Criterio de Busqueda");
		tBuscar.setBounds(523, 293, 350, 20);
		getContentPane().add(tBuscar);
		
		
		tabla = new CustomTable(new String[] {"#", "Nombre", "Documento", "Telefono"}, new int[] {5, 190, 30, 40});
		scrollPane.setViewportView(tabla);
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				cargarFormulario();
			}
		});
		
		
		abmBoton.botones(false, accion);
		abmBoton.setAbi(this);
		
		//Al iniciar deshavilita los campos y recupera los registros para la tabla
		habilitarCampos(false);
		recuperaDatos();		
	}

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		clienteDao = new ClienteDao();
		listaCliente = clienteDao.recuperaTodo();
		
		cargarGrilla();
		
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		
		tabla.vaciar();
		
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Cliente c:listaCliente) {
			fila[0] = c.getId();
			fila[1] = c.getNombre();
			fila[2] = c.getDocumento();
			fila[3] = c.getTelefono();
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
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el Cliente: "+tabla.campo(1)+"?","Atención",JOptionPane.YES_NO_OPTION);
		if (si==JOptionPane.YES_OPTION) {
			cliente = new Cliente();
			clienteDao = new ClienteDao();
			cliente.setId((int) tabla.campo(0));
			try {
				clienteDao.eliminar(cliente);
			} catch (Exception e) {
				clienteDao.rollback();
				advertencia("No se eliminar el Cliente "+tabla.campo(1)+". Esta en uso!",2);
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
		clienteDao = new ClienteDao();
		
		if(accion.equals("AGREGAR"))
			try {
				clienteDao.insertar(cliente);
			} catch (Exception e) {
				clienteDao.rollback();
				advertencia("No se puede guardar el Cliente. Los campos con * son obligatorios",2);
			}
		if (accion.equals("MODIFICAR"))
			try {
				clienteDao.actualizar(cliente);
			} catch (Exception e) {
				clienteDao.rollback();
				advertencia("No se puede actualizar el Cliente. Los campos con * son obligatorios",2);
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
		
		cliente = new Cliente();
		if(accion.equals("AGREGAR")){
			clienteDao = new ClienteDao();
			cliente.setId(clienteDao.recuperMaxId()+1);
		}else
			cliente.setId((int) tabla.campo(0));		
		cliente.setNombre(tNombre.getText());
		cliente.setDocumento(tDocumento.getText());
		cliente.setTelefono(tTelefono.getText());
		cliente.setDireccion(tDireccion.getText());
		cliente.setObservacion(tObservacin.getText());
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
		if (tabla.getSelectedRow()>=0 && tabla.getSelectedRow()!=ultimaFila) {
			ultimaFila=tabla.getSelectedRow();
			accion = "DATOS";
			abmBoton.botones(false, accion);
			clienteDao = new ClienteDao();
			cliente = clienteDao.recuperarPorId((int) tabla.getValueAt(tabla.getSelectedRow(), 0));
			if (cliente!=null) {
				tNombre.setText(cliente.getNombre());
				tDocumento.setText(cliente.getDocumento());
				tDireccion.setText(cliente.getDireccion());
				tEmail.setText(cliente.getEmail());
				tTelefono.setText(cliente.getTelefono());
				tObservacin.setText(cliente.getObservacion());
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

	@Override
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					clienteDao = new ClienteDao();
					listaCliente = clienteDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
					cargarGrilla();
					timer.cancel();
					timer=null;
					task=null;
				}
			};
						
			timer.schedule(task, 1000);		
		}
		
	}
		
	
	
	
}
