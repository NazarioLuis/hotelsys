package py.com.hotelsys.presentacion.transacciones;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Cliente;
import py.com.hotelsys.componentes.JCustomPanel2;
import py.com.hotelsys.componentes.PlaceholderTextField2;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;



@SuppressWarnings("serial")
public class TransCobranza extends JDialog implements AbmBotonInterface {

	

	private ClienteDao clienteDao;
	private List<Cliente> listaCliente;
	private CustomTable tabla;
	private Object[] fila;
	private String accion = "";
	private PlaceholderTextField tFechaCobro;
	private PlaceholderTextField tNroDeuda;
	private PlaceholderTextField tCodCliente;
	private PlaceholderTextField tMontoPagarDetalle;
	private PlaceholderTextField tTotalHabitacion;
	private PlaceholderTextField tBuscar;
	private JPanel panel;
	private Cliente cliente;
	private Timer timer;
	private TimerTask task;
	private int ultimaFila;
	private JLabel lblCliente;



	
	public TransCobranza(JFrame frame) {
		super(frame);
		setTitle("Archivo de Cobranza");
		setBounds(100, 100, 900, 410);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		panel = new JCustomPanel1();
		panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel.setBounds(0, 11, 400, 359);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tFechaCobro = new PlaceholderTextField();
		tFechaCobro.setFont(new Font("Tahoma", Font.BOLD, 11));
		tFechaCobro.setPlaceholder("");
		tFechaCobro.setBounds(121, 11, 98, 27);
		panel.add(tFechaCobro);
		
		tNroDeuda = new PlaceholderTextField();
		tNroDeuda.setEditable(false);
		tNroDeuda.setFont(new Font("Tahoma", Font.BOLD, 11));
		tNroDeuda.setPlaceholder("");
		tNroDeuda.setBounds(121, 46, 116, 27);
		panel.add(tNroDeuda);
		
		tCodCliente = new PlaceholderTextField();
		tCodCliente.setEditable(false);
		tCodCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCodCliente.setPlaceholder("");
		tCodCliente.setBounds(121, 77, 228, 26);
		panel.add(tCodCliente);
		
		tMontoPagarDetalle = new PlaceholderTextField();
		tMontoPagarDetalle.setFont(new Font("Tahoma", Font.BOLD, 11));
		tMontoPagarDetalle.setPlaceholder("");
		tMontoPagarDetalle.setBounds(121, 144, 129, 27);
		panel.add(tMontoPagarDetalle);
		
		tTotalHabitacion = new PlaceholderTextField();
		tTotalHabitacion.setFont(new Font("Tahoma", Font.BOLD, 11));
		tTotalHabitacion.setPlaceholder("");
		tTotalHabitacion.setBounds(121, 110, 129, 29);
		panel.add(tTotalHabitacion);
		
		JLabel lblFechaCobro = new JLabel("Fecha Cobro");
		lblFechaCobro.setVerticalAlignment(SwingConstants.BOTTOM);
		lblFechaCobro.setVerticalTextPosition(SwingConstants.TOP);
		lblFechaCobro.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblFechaCobro.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaCobro.setBounds(47, 17, 79, 14);
		panel.add(lblFechaCobro);
		
		JLabel lblNrodeuda = new JLabel("Nro. Deuda");
		lblNrodeuda.setVerticalAlignment(SwingConstants.BOTTOM);
		lblNrodeuda.setVerticalTextPosition(SwingConstants.TOP);
		lblNrodeuda.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblNrodeuda.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNrodeuda.setBounds(55, 52, 72, 14);
		panel.add(lblNrodeuda);
		
		lblCliente = new JLabel("Cliente");
		lblCliente.setVerticalAlignment(SwingConstants.BOTTOM);
		lblCliente.setVerticalTextPosition(SwingConstants.TOP);
		lblCliente.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblCliente.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblCliente.setBounds(79, 80, 46, 14);
		panel.add(lblCliente);
		
		JLabel lblTotalHabitacin = new JLabel("Total Habitaci\u00F3n");
		lblTotalHabitacin.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTotalHabitacin.setVerticalTextPosition(SwingConstants.TOP);
		lblTotalHabitacin.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblTotalHabitacin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTotalHabitacin.setBounds(27, 116, 93, 14);
		panel.add(lblTotalHabitacin);
		
		JLabel lblTotalProducto = new JLabel("Total producto");
		lblTotalProducto.setVerticalAlignment(SwingConstants.BOTTOM);
		lblTotalProducto.setVerticalTextPosition(SwingConstants.TOP);
		lblTotalProducto.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblTotalProducto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblTotalProducto.setBounds(36, 150, 93, 14);
		panel.add(lblTotalProducto);
		
		JCustomPanel2 customPanel2_1 = new JCustomPanel2();
		customPanel2_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		customPanel2_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		customPanel2_1.setLayout(null);
		customPanel2_1.setBounds(125, 194, 244, 160);
		panel.add(customPanel2_1);
		
		PlaceholderTextField2 placeholderTextField2_3 = new PlaceholderTextField2();
		placeholderTextField2_3.setPlaceholder("Guaran\u00ED");
		placeholderTextField2_3.setEnabled(false);
		placeholderTextField2_3.setBounds(61, 8, 176, 34);
		customPanel2_1.add(placeholderTextField2_3);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(TransCobranza.class.getResource("/img/PY.png")));
		label_3.setAlignmentX(Component.RIGHT_ALIGNMENT);
		label_3.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_3.setBounds(8, 7, 53, 47);
		customPanel2_1.add(label_3);
		
		PlaceholderTextField2 placeholderTextField2_4 = new PlaceholderTextField2();
		placeholderTextField2_4.setPlaceholder("Real");
		placeholderTextField2_4.setEnabled(false);
		placeholderTextField2_4.setBounds(62, 61, 175, 34);
		customPanel2_1.add(placeholderTextField2_4);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(TransCobranza.class.getResource("/img/BR.png")));
		label_4.setAlignmentX(Component.RIGHT_ALIGNMENT);
		label_4.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_4.setBounds(8, 57, 53, 44);
		customPanel2_1.add(label_4);
		
		PlaceholderTextField2 placeholderTextField2_5 = new PlaceholderTextField2();
		placeholderTextField2_5.setPlaceholder("Dolar");
		placeholderTextField2_5.setEnabled(false);
		placeholderTextField2_5.setBounds(61, 117, 176, 34);
		customPanel2_1.add(placeholderTextField2_5);
		
		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(TransCobranza.class.getResource("/img/EEUU.png")));
		label_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
		label_5.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_5.setBounds(8, 108, 53, 46);
		customPanel2_1.add(label_5);
		
		JLabel lblTotales = new JLabel("Totales:");
		lblTotales.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		lblTotales.setBounds(128, 174, 109, 14);
		panel.add(lblTotales);
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		
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
		
		
		tabla = new CustomTable(new String[] {"Nro Deuda", "Nombre de Cliente", "Monto Deuda"}, new int[] {10, 200, 50});
		scrollPane.setViewportView(tabla);
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				cargarFormulario();
			}
		});
		
		//Al iniciar deshavilita los campos y recupera los registros para la tabla
		inicializar();		
	}

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		clienteDao = new ClienteDao();
		//listaCliente = clienteDao.recuperaTodo();
		
		cargarGrilla();
		if (listaCliente.size()>0)
			accion = "DATOS";
		else
			accion = "";
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
	public void guardar() {
		cargarAtributos();
		clienteDao = new ClienteDao();
		
		if(accion.equals("AGREGAR"))
			try {
				clienteDao.insertar(cliente);
				inicializar();
			} catch (Exception e) {
				clienteDao.rollback();
				advertencia("No se puede guardar el Cliente. Los campos con * son obligatorios",2);
			}
		if (accion.equals("MODIFICAR"))
			try {
				clienteDao.actualizar(cliente);
				inicializar();
			} catch (Exception e) {
				clienteDao.rollback();
				advertencia("No se puede actualizar el Cliente. Los campos con * son obligatorios",2);
			}
		
		
		
		
	}
	public void cargarAtributos() {
		
		cliente = new Cliente();
		if(accion.equals("AGREGAR")){
			clienteDao = new ClienteDao();
			cliente.setId(clienteDao.recuperMaxId()+1);
		}else
			cliente.setId((int) tabla.campo(0));
		if(!tFechaCobro.getText().equals(""))
			cliente.setNombre(tFechaCobro.getText());
		if(!tNroDeuda.getText().equals(""))
			cliente.setDocumento(tNroDeuda.getText());
		cliente.setTelefono(tCodCliente.getText());
		cliente.setDireccion(tMontoPagarDetalle.getText());
		cliente.setEmail(tTotalHabitacion.getText());
		
	}

	//deja la pantallaen su estado inicial
	@Override
	public void inicializar() {
		limpiarCampos();
		habilitarCampos(false);
		recuperaDatos();
		ultimaFila = -1;
	
	}

	@Override
	public void habilitarCampos(boolean b) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void advertencia(String texto, int t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarFormulario() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarFormularioProducto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpiarCampos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buscar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarAtributosProductos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nuevo() {
		// TODO Auto-generated method stub
		
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
	public void cancelar() {
		// TODO Auto-generated method stub
		
	}	
}
