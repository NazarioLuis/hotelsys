package py.com.hotelsys.presentacion.transacciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import py.com.hotelsys.componentes.BotonGrup3;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.dao.DetalleDao;
import py.com.hotelsys.dao.EstadiaDao;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaCliente;
import py.com.hotelsys.interfaces.InterfaceBusquedaHabitacion;
import py.com.hotelsys.interfaces.InterfaceBusquedaProducto;
import py.com.hotelsys.interfaces.InterfaceBusquedaServicio;
import py.com.hotelsys.interfaces.TranBotonInterface2;
import py.com.hotelsys.modelo.Cliente;
import py.com.hotelsys.modelo.Detalle;
import py.com.hotelsys.modelo.Estadia;
import py.com.hotelsys.modelo.Habitacion;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Servicio;
import py.com.hotelsys.modelo.Stock;
import py.com.hotelsys.presentacion.buscadores.BusqudaCliente;
import py.com.hotelsys.presentacion.buscadores.BusqudaHabitacion;
import py.com.hotelsys.presentacion.buscadores.BusqudaProducto;
import py.com.hotelsys.presentacion.buscadores.BusqudaServicio;
import py.com.hotelsys.util.FormatoFecha;



@SuppressWarnings("serial")
public class TransEstadia extends JDialog implements TranBotonInterface2, InterfaceBusquedaCliente, InterfaceBusquedaHabitacion, InterfaceBusquedaServicio,InterfaceBusquedaProducto{
	
	private Cliente cliente;
	private ClienteDao clienteDao;
	
	private Producto producto;
	private ProductoDao productoDao;
	private List<Producto> listaProductos;
	
	private Detalle detalle;
	private DetalleDao detalleDao;
	private List<Detalle> listaDetalles;
	
	
	
	
	private Habitacion habitacion;
	
	private EstadiaDao estadiaDao;
	private MaskFormatter maskFecha;

	private List<Estadia> listaEstadias;
	private CustomTable tablaEstadia;
	private Object[] fila;
	private String accion = "";
	private PlaceholderTextField tCodHabitacion;
	private PlaceholderTextField tCodCliente;
	
	private JTextArea tObservacin;
	private PlaceholderTextField tBuscar;
	private JPanel panel;
	private Estadia estadia;
	
	
	private JLabel label;
	private Timer timer;
	private TimerTask task;
	private int ultimaFila;
	private JButton button_2;

	private JScrollPane scrollPane_1;
	private CustomTable tableServicio;
	private CustomTable tablaProductos;
	private JPanel panel_1;
	private PlaceholderTextField placeholderTextField_5;
	private JButton button_5;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblDetalle;
	private JTextField textField_2;
	private JLabel lblSubtotal;
	private JTextField textField_3;
	private JLabel lblDescuento;
	private JTextField textField_4;
	private JLabel lblMontoTotal;
	private JTextField textField_5;
	private JTextField tTotalDetalle;
	//private InterfaceBusquedaCliente ibc;
	private JPanel panelProducto;
	private JFormattedTextField tFecha;
	private BotonGrup3 botonGrup3;
	
	private int id;
	private PlaceholderTextField tCodProducto;
	private PlaceholderTextField tPrecioProducto;
	private PlaceholderTextField tCantidadProducto;
	

	public TransEstadia(JFrame frame) {
		super(frame);
		getContentPane().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		getContentPane().setLocation(-25, -344);
		setTitle("Archivo de Estadia");
		setBounds(100, 100, 899, 547);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(10, 11, 241, 269);
		panel.setBorder(new LineBorder(Color.GRAY));
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tCodHabitacion = new PlaceholderTextField();
		tCodHabitacion.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCodHabitacion.setPlaceholder("Habitaci\u00F3n");
		tCodHabitacion.setBounds(17, 95, 140, 20);
		panel.add(tCodHabitacion);
		
		tCodCliente = new PlaceholderTextField();
		tCodCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCodCliente.setPlaceholder("Cliente");
		tCodCliente.setBounds(17, 64, 140, 20);
		panel.add(tCodCliente);
		
		tObservacin = new JTextArea("");
		tObservacin.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tObservacin.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tObservacin.setRows(10);
		tObservacin.setLineWrap(true);
		tObservacin.setBounds(17, 140, 211, 118);
		panel.add(tObservacin);
		
		label = new JLabel("Observaci\u00F3n:");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(22, 120, 88, 20);
		panel.add(label);
		
	
		
		
		JButton button = new JButton("#");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarCliente();
					
			}
			
		});
		
		try {
			maskFecha = new MaskFormatter("##/##/####");
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		maskFecha.setPlaceholderCharacter('_');
		
		button.setBounds(165, 62, 52, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("#");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarHabitacion();
			}
		});
		button_1.setBounds(165, 91, 52, 23);
		panel.add(button_1);
		
		
		tFecha = new JFormattedTextField(maskFecha);
		tFecha.setBounds(124, 27, 93, 20);
		panel.add(tFecha);
		
		JLabel lblNewLabel = new JLabel("Fecha entrada");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel.setBounds(17, 30, 93, 14);
		panel.add(lblNewLabel);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(346, 11, 538, 181);
		getContentPane().add(scrollPane);
		
		
		tBuscar = new PlaceholderTextField();
		tBuscar.setBounds(346, 203, 307, 20);
		tBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscar();
			}
		});
		tBuscar.setPlaceholder("Criterio de Busqueda");
		getContentPane().add(tBuscar);
		
		
		tablaEstadia = new CustomTable(new String[] {"#", "Cliente", "Habitación","Precio"}, new int[] {75, 200, 200,100});
		scrollPane.setViewportView(tablaEstadia);
		
//		JList list = new JList();
//		list.setBounds(356, 232, 538, 2);
//		getContentPane().add(list);
//		
//		list_1 = new JList();
//		list_1.setBounds(89, 393, 162, 0);
//		list_1.setForeground(Color.BLACK);
//		getContentPane().add(list_1);
		
		JButton btnAbierto = new JButton("Abierto");
		btnAbierto.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnAbierto.setForeground(new Color(0, 0, 0));
		btnAbierto.setBounds(665, 203, 71, 23);
		getContentPane().add(btnAbierto);
		
		JButton btnCerrado = new JButton("Cerrado");
		btnCerrado.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnCerrado.setForeground(new Color(0, 0, 0));
		btnCerrado.setBounds(743, 203, 71, 23);
		getContentPane().add(btnCerrado);
		
		JButton btnTodos = new JButton("Todos");
		btnTodos.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnTodos.setForeground(new Color(0, 0, 0));
		btnTodos.setBounds(823, 203, 61, 23);
		getContentPane().add(btnTodos);
		
		panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.inactiveCaption);
		panel_1.setLayout(null);
		panel_1.setBorder(new LineBorder(Color.GRAY));
		panel_1.setBounds(19, 304, 232, 201);
		getContentPane().add(panel_1);
		
		placeholderTextField_5 = new PlaceholderTextField();
		placeholderTextField_5.setPlaceholder("Monto del servicio");
		placeholderTextField_5.setBounds(469, 11, 108, 20);
		panel_1.add(placeholderTextField_5);
		
		button_5 = new JButton("#");
		button_5.setBounds(418, 11, 41, 20);
		panel_1.add(button_5);
		
		JLabel lblFechaCierre = new JLabel("Fecha cierre");
		lblFechaCierre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblFechaCierre.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblFechaCierre.setBounds(10, 14, 79, 14);
		panel_1.add(lblFechaCierre);
		
		textField = new JTextField();
		textField.setBounds(103, 11, 114, 20);
		panel_1.add(textField);
		textField.setColumns(10);
		
		JLabel lblMontoDiaria = new JLabel("Monto diaria");
		lblMontoDiaria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMontoDiaria.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblMontoDiaria.setBounds(10, 40, 79, 14);
		panel_1.add(lblMontoDiaria);
		
		textField_1 = new JTextField();
		textField_1.setBounds(103, 37, 125, 20);
		panel_1.add(textField_1);
		textField_1.setColumns(10);
		
		lblDetalle = new JLabel("Detalle");
		lblDetalle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDetalle.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblDetalle.setBounds(10, 71, 46, 14);
		panel_1.add(lblDetalle);
		
		textField_2 = new JTextField();
		textField_2.setBounds(103, 68, 125, 20);
		panel_1.add(textField_2);
		textField_2.setColumns(10);
		
		lblSubtotal = new JLabel("Sub total");
		lblSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSubtotal.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblSubtotal.setBounds(10, 103, 79, 17);
		panel_1.add(lblSubtotal);
		
		textField_3 = new JTextField();
		textField_3.setBounds(103, 100, 125, 20);
		panel_1.add(textField_3);
		textField_3.setColumns(10);
		
		lblDescuento = new JLabel("Descuento");
		lblDescuento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDescuento.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblDescuento.setBounds(10, 136, 79, 14);
		panel_1.add(lblDescuento);
		
		textField_4 = new JTextField();
		textField_4.setBounds(103, 131, 125, 20);
		panel_1.add(textField_4);
		textField_4.setColumns(10);
		
		lblMontoTotal = new JLabel("Monto Total");
		lblMontoTotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMontoTotal.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblMontoTotal.setBounds(10, 162, 79, 14);
		panel_1.add(lblMontoTotal);
		
		textField_5 = new JTextField();
		textField_5.setBounds(103, 159, 125, 20);
		panel_1.add(textField_5);
		textField_5.setColumns(10);
		
		JButton btnCerrar = new JButton("Cerrar");
		btnCerrar.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnCerrar.setForeground(new Color(0, 0, 0));
		btnCerrar.setBounds(258, 356, 75, 23);
		getContentPane().add(btnCerrar);
		
		JButton btnGuardar_1 = new JButton("Guardar");
		btnGuardar_1.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnGuardar_1.setForeground(new Color(0, 0, 0));
		btnGuardar_1.setBounds(258, 393, 75, 23);
		getContentPane().add(btnGuardar_1);
		
		JButton btnCncelar_1 = new JButton("C\u00E1ncelar");
		btnCncelar_1.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnCncelar_1.setForeground(new Color(0, 0, 0));
		btnCncelar_1.setBounds(258, 434, 75, 23);
		getContentPane().add(btnCncelar_1);
		
		JTabbedPane tpServicios = new JTabbedPane(JTabbedPane.TOP);
		tpServicios.setBounds(346, 234, 537, 271);
		getContentPane().add(tpServicios);
		
		panelProducto = new JPanel();
		tpServicios.addTab("Productos", null, panelProducto, null);
		panelProducto.setLayout(null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(20, 6, 331, 77);
		panelProducto.add(panel_2);
		panel_2.setBackground(SystemColor.activeCaption);
		panel_2.setLayout(null);
		panel_2.setBorder(new LineBorder(Color.GRAY));
		
		tPrecioProducto = new PlaceholderTextField();
		tPrecioProducto.setBounds(7, 29, 104, 20);
		panel_2.add(tPrecioProducto);
		tPrecioProducto.setPlaceholder("Precio producto");
		
		tCodProducto = new PlaceholderTextField();
		tCodProducto.setBounds(7, 7, 263, 20);
		panel_2.add(tCodProducto);
		tCodProducto.setPlaceholder("C\u00F3d. producto");
		
		button_2 = new JButton("#");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarBuscadorProducto();
			}
		});
		button_2.setBounds(280, 7, 41, 20);
		panel_2.add(button_2);
		
		tCantidadProducto = new PlaceholderTextField();
		tCantidadProducto.setPlaceholder("Cantidad");
		tCantidadProducto.setBounds(7, 52, 104, 20);
		panel_2.add(tCantidadProducto);
		
		JButton btnEliminar_1 = new JButton("Eliminar");
		btnEliminar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarProducto();
			}
		});
		btnEliminar_1.setBounds(361, 60, 89, 23);
		panelProducto.add(btnEliminar_1);
		btnEliminar_1.setForeground(new Color(0, 0, 0));
		btnEliminar_1.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		
		JButton btnCncelar_2 = new JButton("C\u00E1ncelar");
		
		btnCncelar_2.setBounds(361, 34, 89, 23);
		panelProducto.add(btnCncelar_2);
		btnCncelar_2.setForeground(new Color(0, 0, 0));
		btnCncelar_2.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 89, 512, 124);
		panelProducto.add(scrollPane_1);
		
		
		tablaProductos = new CustomTable(new String[] {"#","Descripción producto","Cant.","Precio","Subtotal"}, new int[] {0,100,20,30});
		tablaProductos.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaProductos.getColumnModel().getColumn(0).setMinWidth(0);
		tablaProductos.getColumnModel().getColumn(0).setPreferredWidth(0);
		scrollPane_1.setViewportView(tablaProductos);
		
		tTotalDetalle = new JTextField();
		tTotalDetalle.setBounds(323, 212, 199, 20);
		panelProducto.add(tTotalDetalle);
		tTotalDetalle.setColumns(10);
		
		JLabel lblMontoTotal_1 = new JLabel("Monto Total");
		lblMontoTotal_1.setBounds(249, 218, 71, 14);
		panelProducto.add(lblMontoTotal_1);
		
		JButton button_4 = new JButton("Guardar");
		button_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardarProducto();
			}
		});
		button_4.setForeground(Color.BLACK);
		button_4.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		button_4.setBounds(361, 8, 89, 23);
		panelProducto.add(button_4);
		
		botonGrup3 = new BotonGrup3();
		botonGrup3.setAbi(this);
		botonGrup3.setBounds(258, 11, 84, 215);
		
		getContentPane().add(botonGrup3);
		
		tablaEstadia.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				cargarFormulario();
				
			}
		});
		
		//Al iniciar deshavilita los campos y recupera los registros para la tabla
		
		
		inicializar();		
	}
	

	private void eliminarProducto() {
		if (tablaProductos.getSelectedRow()>=0) {
			id=(int) tablaProductos.getValueAt(tablaProductos.getSelectedRow(), 0);
			detalleDao = new DetalleDao();
			detalle = detalleDao.recuperarPorId(id);
			try {
				detalleDao = new DetalleDao();
				detalleDao.eliminar(detalle.getId());
				sumarStock(detalle);
				inicializarDetalle();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}


	//Metodo que recupera todos los registros de estadia para cargarlos a la tabla
	private void recuperaDatos() {
		estadiaDao = new EstadiaDao();
		listaEstadias = estadiaDao.recuperaTodo();
		
		cargarGrillaEstadia();
		if (listaEstadias.size()>0)
			accion = "DATOS";
		else
			accion = "";
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrillaEstadia() {
		
		tablaEstadia.vaciar();
		
		fila = new Object[tablaEstadia.getColumnCount()];
		for (Estadia e:listaEstadias) {
			fila[0] = e.getId();
			fila[1] = e.getCliente().getNombre();
			fila[2] = e.getHabitacion().getDescripcion();
			fila[3] = e.getHabitacion().getPrecio();
			tablaEstadia.agregar(fila);
 		}
		//mantiene el foco en el ultimo registro cargado
		tablaEstadia.setSeleccion();
		
		
	}
	
	

	@Override
	public void nuevo() {
		accion = "AGREGAR";
		limpiarCampos();
		tFecha.setValue(FormatoFecha.dateAString(new Date()));
		habilitarCampos(true);
		botonGrup3.botones(true, accion);
		
	}
	
	@Override
	public void guardar() {
		cargarAtributosEstadia();
		estadiaDao = new EstadiaDao();
		
		if(accion.equals("AGREGAR"))
			try {
				
				estadiaDao.insertar(estadia);
				
				inicializar();
				limpiarCampos();
			} catch (Exception e) {
				e.printStackTrace();
				estadiaDao.rollback();
				
				advertencia("No se guardar estadia---: "+tablaEstadia.campo(2)+". Esta en uso!",2);
				advertencia("No se puede guardar el Estadia. Los campos con * son obligatorios",2);
			}
		if (accion.equals("MODIFICAR"))
			try {
				estadiaDao.actualizar(estadia);
				inicializar();
				limpiarCampos();
			} catch (Exception e) {
				clienteDao.rollback();
				advertencia("No se puede actualizar el Estadia. Los campos con * son obligatorios",2);
			}
		
	}

	@Override
	public void cancelar() {
		inicializar();
	}

	@Override
	public void habilitarCampos(boolean b) {
	//	tNumero.setEnabled(b);
		tFecha.setEnabled(b);
		tCodHabitacion.setEnabled(b);
		tCodCliente.setEnabled(b);
		tObservacin.setEnabled(b);
		tBuscar.setEnabled(!b);
		tablaEstadia.setEnabled(!b);
		if(b==false)
			tablaEstadia.requestFocus();
		else
			tFecha.requestFocus();
		//	tNumero.requestFocus();
	}

	//deja la pantallaen su estado inicial
	@Override
	public void inicializar() {
		limpiarCampos();
		habilitarCampos(false);
		recuperaDatos();
		ultimaFila = -1;
	}

	
	//emite mensajes de forma dinamica de acuerdo al texto que se le envie
	@Override
	public void advertencia(String texto,int t) {
		JOptionPane.showMessageDialog(null, texto, "Atención", t);
	}
	
	
	

	@Override
	public void cargarFormulario() {
		if(tablaEstadia.getRowCount()==1)
			ultimaFila = -1;
		if (tablaEstadia.getSelectedRow()>=0 && tablaEstadia.getSelectedRow()!=ultimaFila) {
			ultimaFila=tablaEstadia.getSelectedRow();
			botonGrup3.botones(false, accion);
			estadiaDao = new EstadiaDao();
			estadia = estadiaDao.recuperarPorId((int) tablaEstadia.campo(0));
			if (estadia!=null) {
				
				cliente = estadia.getCliente();
				habitacion = estadia.getHabitacion();
				
				tFecha.setValue(FormatoFecha.dateAString(estadia.getFecha()));
				tCodCliente.setText(estadia.getCliente().getNombre());
				tCodHabitacion.setText(estadia.getHabitacion().getDescripcion());
				tObservacin.setText(estadia.getObservacion());
				
				recuperaDatosDetalle();
				
			}
			
		}
		
	}

	private void recuperaDatosDetalle() {
		detalleDao = new DetalleDao();
		listaDetalles = detalleDao.cosultarPorEstadia(estadia);
		cargarGrillaProductos();
	}


	@Override
	public void limpiarCampos() {
		tFecha.setText("");
		tCodCliente.setText("");
		tCodHabitacion.setText("");
		tObservacin.setText("");
	}

	@Override
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					estadiaDao = new EstadiaDao();
					listaEstadias = estadiaDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
					cargarGrillaEstadia();
					timer.cancel();
					timer=null;
					task=null;
				}
			};
						
			timer.schedule(task, 1000);
			
		}
	}

	

	@Override
	public void modificar() {
		accion = "MODIFICAR";
		habilitarCampos(true);
		botonGrup3.botones(true, accion);
	}


	@Override
	public void eliminar() {
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar estadia: "+tablaEstadia.campo(1)+"?","Atención",JOptionPane.YES_NO_OPTION);
		if (si==JOptionPane.YES_OPTION) {
			estadiaDao = new EstadiaDao();
			try {
				estadiaDao.eliminar((int) tablaEstadia.campo(0) );
				
			} catch (Exception e) {
				e.printStackTrace();
				estadiaDao.rollback();
				advertencia("No se eliminar el Estadia "+tablaEstadia.campo(1)+". Esta en uso!",2);
			}
			inicializar();
		}
		
		
	}
	
	//meodos para guardar producto
	private void guardarProducto() {
		
		if (tCantidadProducto.getText().equals("")) {
			JOptionPane.showMessageDialog(null,"cantidad no puede estar vacio");
			tCantidadProducto.requestFocus();
		}else{
			cargarAtributosProductos();
			
			detalleDao = new DetalleDao();
		//	JOptionPane.showMessageDialog(null,"hola"+ 1);
			
			try {
				detalleDao.insertar(detalle);
				restarStock(detalle);
				
				inicializarDetalle();
				
			} catch (Exception e) {
			}
		}
	}
	private void restarStock(Detalle det) {
		Producto p = det.getProducto();
		p.getStock().setCantidad(p.getStock().getCantidad()-det.getCantidadProducto());
		productoDao = new ProductoDao();
		try {
			productoDao.actualizar(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sumarStock(Detalle det) {
		Producto p = det.getProducto();
		p.getStock().setCantidad(p.getStock().getCantidad()+det.getCantidadProducto());
		productoDao = new ProductoDao();
		try {
			productoDao.actualizar(p);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	


	private void inicializarDetalle() {
		limpiarCamposDetalle();
		recuperaDatosDetalle();
	}


	private void limpiarCamposDetalle() {
		tCodProducto.setText("");
		tPrecioProducto.setText("");
		tCantidadProducto.setText("");
	}


	@Override
	//CARGAR ATRIBUTOS DEL PRODUCTOS
	public void cargarAtributosProductos() {
			
		detalle = new Detalle();
			
				detalleDao = new DetalleDao();
				detalle.setId(detalleDao.recuperMaxId()+1);
						
				detalle.setProducto(producto);
				detalle.setEstadia(estadia);
				detalle.setTotal(Double.parseDouble(tCantidadProducto.getText())*producto.getStock().getPrecio());
				detalle.setCantidadProducto(Integer.parseInt( tCantidadProducto.getText()));
		 
		}
	
	
	public void cargarAtributosEstadia() {
		estadia = new Estadia();
		if(accion.equals("AGREGAR")){
			estadiaDao = new EstadiaDao();
			estadia.setId(estadiaDao.recuperMaxId()+1);
		}else
		estadia.setId((int) tablaEstadia.campo(0));
		estadia.setFecha(FormatoFecha.stringToDate(tFecha.getText()));
		estadia.setCliente(cliente);
		estadia.setHabitacion(habitacion);
		estadia.setObservacion(tObservacin.getText());
	}
	//cargar grilla o tabla de productos
	private void cargarGrillaProductos() {
		
		tablaProductos.vaciar();
		tTotalDetalle.setText("");
		fila = new Object[tablaProductos.getColumnCount()];
		for (Detalle d: listaDetalles) {
			fila[0] = d.getId();
			fila[1] = d.getProducto().getDescripcion();
			fila[2] = d.getCantidadProducto();
			fila[3] = d.getProducto().getStock().getPrecio();
			fila[4] = d.getTotal();
			
			if (tTotalDetalle.getText().equals("")) 
				tTotalDetalle.setText(fila[3].toString());
			else
				tTotalDetalle.setText(""+(Double.parseDouble(tTotalDetalle.getText())+(Double)fila[3]));
			tablaProductos.agregar(fila);
 		}
		//mantiene el foco en el ultimo registro cargado
		//tablaEstadia.setSeleccion();
	}
	
	
		@Override
		public void cargarFormularioProducto() {
		if(tablaProductos.getRowCount()==1)
			ultimaFila = -1;
		if (tablaProductos.getSelectedRow()>=0 && tablaProductos.getSelectedRow()!=ultimaFila) {
			ultimaFila=tablaProductos.getSelectedRow();
			botonGrup3.botones(false, accion);
			productoDao = new ProductoDao();
			producto = productoDao.recuperarPorId((int) tablaProductos.campo(0));
			
			if (detalle!=null) {
				cliente = estadia.getCliente();
				habitacion = estadia.getHabitacion();
				
				producto = detalle.getProducto();
			
				
				tCodProducto.setText(detalle.getProducto().getDescripcion());
				tCodProducto.setText(""+detalle.getProducto().getStock().getPrecio());
				tCodProducto.setText(""+detalle.getProducto().getStock().getCantidad());
				
			}
			
		}
		
	}
			

	


	@Override
	public void salir() {
		dispose();
		
	}

	private void mostrarCliente() {
		BusqudaCliente busqudaCliente = new BusqudaCliente(this);
		busqudaCliente.setIbc(this);
		busqudaCliente.setVisible(true);
		
	}
	private void mostrarHabitacion() {
		BusqudaHabitacion busqudaHabitacion = new BusqudaHabitacion(this);
		busqudaHabitacion.setIbh(this);
		busqudaHabitacion.setVisible(true);
	}


	@Override
	public void cargar(Cliente c) {
		tCodCliente.setText(c.getNombre());
		this.cliente = c;
		
		

	
	}
	
	@Override
	public void cargar(Habitacion h) {
		tCodHabitacion.setText(h.getDescripcion());
		this.habitacion = h;
	}
	
	private void mostrarBuscadorProducto() {
		BusqudaProducto busquedaProducto = new BusqudaProducto(this);
		busquedaProducto.setIbp(this);
		busquedaProducto.setVisible(true);
	}
	


	@Override
	public void cargar(Producto p) {
	this.producto = p;
	
	tCodProducto.setText(p.getDescripcion());
	tPrecioProducto.setText((int)p.getStock().getPrecio()+"");
	
	tCantidadProducto.requestFocus();
	
	}


	@Override
	public void cargar(Servicio s) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void agregar() {
		
	}


	@Override
	public void cerrar() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void guardarCierre() {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void cancelarCierre() {
		// TODO Auto-generated method stub
		
	}




	@Override
	public void cargarAtributos() {
		// TODO Auto-generated method stub
		
	}
		
	}

	