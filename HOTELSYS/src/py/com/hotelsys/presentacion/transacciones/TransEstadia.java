package py.com.hotelsys.presentacion.transacciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.Normalizer.Form;
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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup3;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.JCustomPanel2;
import py.com.hotelsys.componentes.NumberTextField;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.componentes.PlaceholderTextField2;
import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.dao.DetalleDao;
import py.com.hotelsys.dao.DeudaDao;
import py.com.hotelsys.dao.EstadiaDao;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaCliente;
import py.com.hotelsys.interfaces.InterfaceBusquedaHabitacion;
import py.com.hotelsys.interfaces.InterfaceBusquedaProducto;
import py.com.hotelsys.interfaces.InterfaceBusquedaServicio;
import py.com.hotelsys.interfaces.TranBotonInterface2;
import py.com.hotelsys.modelo.Cliente;
import py.com.hotelsys.modelo.Detalle;
import py.com.hotelsys.modelo.Deuda;
import py.com.hotelsys.modelo.Estadia;
import py.com.hotelsys.modelo.Habitacion;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Servicio;
import py.com.hotelsys.presentacion.buscadores.BusqudaCliente;
import py.com.hotelsys.presentacion.buscadores.BusqudaHabitacion;
import py.com.hotelsys.presentacion.buscadores.BusqudaProducto;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.Util;



@SuppressWarnings("serial")
public class TransEstadia extends JDialog implements TranBotonInterface2, InterfaceBusquedaCliente, InterfaceBusquedaHabitacion, InterfaceBusquedaServicio,InterfaceBusquedaProducto{
	
	private Cliente cliente;
	private ClienteDao clienteDao;
	
	private Producto producto;
	private ProductoDao productoDao;
	private Detalle detalle;
	private DetalleDao detalleDao;
	private List<Detalle> listaDetalles;
	
	
	
	
	private Habitacion habitacion;
	
	private EstadiaDao estadiaDao;

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
	private CustomTable tablaProductos;
	private JPanel panel_1;
	private PlaceholderTextField placeholderTextField_5;
	private JButton button_5;
	private JFormattedTextField tFechaSalida;
	private NumberTextField tMontoDiario;
	private NumberTextField tCompleto;
	private JLabel lblSubtotal;
	private JLabel lblDescuento;
	private NumberTextField tExcedente;
	private PlaceholderTextField tTotalDetalle;
	//private InterfaceBusquedaCliente ibc;
	private JPanel panelProducto;
	private JFormattedTextField tFecha;
	private BotonGrup3 botonGrup3;
	
	private int id;
	private PlaceholderTextField tCodProducto;
	private PlaceholderTextField2 tPrecioProducto;
	private PlaceholderTextField tCantidadProducto;
	private PlaceholderTextField2 tPrecioProductoRs;
	private PlaceholderTextField2 tPrecioProductoUs;
	private Double totalDetalle;
	private PlaceholderTextField tTotalDetalleRs;
	private PlaceholderTextField tTotalDetalleUs;
	private JLabel lblHospedar;
	private JLabel lblCierre;
	private JLabel lblTotales;
	private JPanel panel_4;
	private JFormattedTextField tHora;
	private JFormattedTextField tHoraSalida;
	private JLabel lblDias;
	private JLabel lblHoras;
	private double total;
	private PlaceholderTextField2 tTotalUs;
	private PlaceholderTextField2 tTotalRs;
	private PlaceholderTextField2 tTotalGs;
	private JButton btnGuardarSalida;
	private JButton btnCncelarSalida;
	private JButton btnCerrar;
	private Date fechaSalida;
	private double montoHoras;
	private Deuda deu;
	private Deuda deu2;
	private DeudaDao deudaDao;
	

	public TransEstadia(JFrame frame) {
		super(frame);
		getContentPane().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		getContentPane().setLocation(-25, -344);
		setTitle("Hospedar a Cliente");
		setBounds(100, 100, 1000, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		getContentPane().setLayout(null);
		
		panel =	new JCustomPanel1();
		panel.setBounds(33, 22, 286, 258);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tCodHabitacion = new PlaceholderTextField();
		tCodHabitacion.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCodHabitacion.setPlaceholder("Habitaci\u00F3n");
		tCodHabitacion.setBounds(10, 79, 187, 20);
		panel.add(tCodHabitacion);
		
		tCodCliente = new PlaceholderTextField();
		tCodCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCodCliente.setPlaceholder("Cliente");
		tCodCliente.setBounds(10, 48, 187, 20);
		panel.add(tCodCliente);
		
		tObservacin = new JTextArea("");
		tObservacin.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tObservacin.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tObservacin.setRows(10);
		tObservacin.setLineWrap(true);
		tObservacin.setBounds(10, 124, 249, 118);
		panel.add(tObservacin);
		
		label = new JLabel("Observaci\u00F3n:");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(15, 104, 88, 20);
		panel.add(label);
		
	
		
		
		JButton button = new JButton("#");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarCliente();
					
			}
			
		});
		
	
		
		button.setBounds(207, 47, 52, 23);
		panel.add(button);
		
		JButton button_1 = new JButton("#");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarHabitacion();
			}
		});
		button_1.setBounds(207, 76, 52, 23);
		panel.add(button_1);
		
		
		tFecha = new JFormattedTextField(Util.formatoFecha());
		tFecha.setDisabledTextColor(new Color(128, 128, 128));
		tFecha.setBounds(10, 11, 74, 20);
		panel.add(tFecha);
		
		tHora = new JFormattedTextField(Util.formatoHora());
		tHora.setDisabledTextColor(new Color(128, 128, 128));
		tHora.setEnabled(false);
		tHora.setBounds(94, 11, 52, 20);
		panel.add(tHora);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(423, 11, 538, 181);
		getContentPane().add(scrollPane);
		
		
		tBuscar = new PlaceholderTextField();
		tBuscar.setBounds(423, 203, 307, 20);
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
		btnAbierto.setBounds(742, 203, 71, 23);
		getContentPane().add(btnAbierto);
		
		JButton btnCerrado = new JButton("Cerrado");
		btnCerrado.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnCerrado.setForeground(new Color(0, 0, 0));
		btnCerrado.setBounds(820, 203, 71, 23);
		getContentPane().add(btnCerrado);
		
		JButton btnTodos = new JButton("Todos");
		btnTodos.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnTodos.setForeground(new Color(0, 0, 0));
		btnTodos.setBounds(900, 203, 61, 23);
		getContentPane().add(btnTodos);
		
		JTabbedPane tpServicios = new JTabbedPane(JTabbedPane.TOP);
		tpServicios.setBounds(424, 266, 537, 271);
		getContentPane().add(tpServicios);
		
		panelProducto = new JPanel();
		tpServicios.addTab("Productos", null, panelProducto, null);
		panelProducto.setLayout(null);
		
		JPanel panel_2 = new JCustomPanel1();
		panel_2.setBounds(10, 6, 341, 94);
		panelProducto.add(panel_2);
		panel_2.setLayout(null);
		
		tPrecioProducto = new PlaceholderTextField2();
		tPrecioProducto.setEnabled(false);
		tPrecioProducto.setBounds(7, 29, 104, 34);
		panel_2.add(tPrecioProducto);
		tPrecioProducto.setPlaceholder("Guaran\u00ED");
		
		tCodProducto = new PlaceholderTextField();
		tCodProducto.setEnabled(false);
		tCodProducto.setBounds(7, 7, 263, 20);
		panel_2.add(tCodProducto);
		tCodProducto.setPlaceholder("Producto");
		
		button_2 = new JButton("#");
		button_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarBuscadorProducto();
			}
		});
		button_2.setBounds(280, 7, 46, 20);
		panel_2.add(button_2);
		
		tCantidadProducto = new PlaceholderTextField();
		tCantidadProducto.setPlaceholder("Cantidad");
		tCantidadProducto.setBounds(7, 67, 104, 20);
		panel_2.add(tCantidadProducto);
		
		tPrecioProductoRs = new PlaceholderTextField2();
		tPrecioProductoRs.setEnabled(false);
		tPrecioProductoRs.setPlaceholder("Real");
		tPrecioProductoRs.setBounds(132, 29, 81, 34);
		panel_2.add(tPrecioProductoRs);
		
		tPrecioProductoUs = new PlaceholderTextField2();
		tPrecioProductoUs.setEnabled(false);
		tPrecioProductoUs.setPlaceholder("Dolar");
		tPrecioProductoUs.setBounds(237, 29, 81, 34);
		panel_2.add(tPrecioProductoUs);
		
		JLabel lblGs = new JLabel("Gs.");
		lblGs.setBounds(111, 32, 22, 31);
		panel_2.add(lblGs);
		
		JLabel lblUs = new JLabel("Rs.");
		lblUs.setBounds(214, 32, 23, 31);
		panel_2.add(lblUs);
		
		JLabel lblRs = new JLabel("Us.");
		lblRs.setBounds(319, 32, 22, 31);
		panel_2.add(lblRs);
		
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
		scrollPane_1.setBounds(10, 103, 512, 110);
		panelProducto.add(scrollPane_1);
		
		
		tablaProductos = new CustomTable(new String[] {"#","Descripción producto","Cant.","Precio","Subtotal"}, new int[] {0,100,20,30});
		tablaProductos.ocultarColumna(0);
		scrollPane_1.setViewportView(tablaProductos);
		
		tTotalDetalle = new PlaceholderTextField();
		tTotalDetalle.setFont(new Font("Tahoma", Font.BOLD, 11));
		tTotalDetalle.setDisabledTextColor(new Color(220, 20, 60));
		tTotalDetalle.setEnabled(false);
		tTotalDetalle.setBounds(230, 214, 79, 20);
		panelProducto.add(tTotalDetalle);
		tTotalDetalle.setColumns(10);
		
		JLabel lblMontoTotal_1 = new JLabel("Monto Total");
		lblMontoTotal_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMontoTotal_1.setBounds(151, 215, 83, 14);
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
		
		tTotalDetalleRs = new PlaceholderTextField();
		tTotalDetalleRs.setFont(new Font("Tahoma", Font.BOLD, 11));
		tTotalDetalleRs.setDisabledTextColor(new Color(220, 20, 60));
		tTotalDetalleRs.setEnabled(false);
		tTotalDetalleRs.setColumns(10);
		tTotalDetalleRs.setBounds(347, 214, 52, 20);
		panelProducto.add(tTotalDetalleRs);
		
		tTotalDetalleUs = new PlaceholderTextField();
		tTotalDetalleUs.setFont(new Font("Tahoma", Font.BOLD, 11));
		tTotalDetalleUs.setDisabledTextColor(new Color(220, 20, 60));
		tTotalDetalleUs.setEnabled(false);
		tTotalDetalleUs.setColumns(10);
		tTotalDetalleUs.setBounds(442, 214, 60, 20);
		panelProducto.add(tTotalDetalleUs);
		
		JLabel label_1 = new JLabel("Gs.");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(308, 212, 22, 20);
		panelProducto.add(label_1);
		
		JLabel lblRs_1 = new JLabel("Rs.");
		lblRs_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRs_1.setBounds(398, 212, 28, 20);
		panelProducto.add(lblRs_1);
		
		JLabel lblUs_1 = new JLabel("Us.");
		lblUs_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblUs_1.setBounds(500, 212, 27, 20);
		panelProducto.add(lblUs_1);
		
		botonGrup3 = new BotonGrup3();
		botonGrup3.setAbi(this);
		botonGrup3.setBounds(329, 21, 84, 215);
		
		getContentPane().add(botonGrup3);
		
		lblHospedar = new JLabel("Hospedar");
		lblHospedar.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblHospedar.setBounds(44, 9, 84, 14);
		getContentPane().add(lblHospedar);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(143, 188, 143), 1, true));
		panel_4.setBounds(33, 297, 355, 248);
		getContentPane().add(panel_4);
		panel_4.setLayout(null);
		
		lblCierre = new JLabel("Cierre");
		lblCierre.setBounds(21, 11, 84, 14);
		panel_4.add(lblCierre);
		lblCierre.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		
		panel_1 = new JCustomPanel2();
		panel_1.setBounds(10, 23, 241, 137);
		panel_4.add(panel_1);
		panel_1.setLayout(null);
		
		placeholderTextField_5 = new PlaceholderTextField();
		placeholderTextField_5.setPlaceholder("Monto del servicio");
		placeholderTextField_5.setBounds(469, 11, 108, 20);
		panel_1.add(placeholderTextField_5);
		
		button_5 = new JButton("#");
		button_5.setBounds(418, 11, 41, 20);
		panel_1.add(button_5);
		
		tFechaSalida = new JFormattedTextField(Util.formatoFecha());
		tFechaSalida.setEnabled(false);
		tFechaSalida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					tHoraSalida.requestFocus();
				}
				
			}
		});
		tFechaSalida.setBounds(22, 11, 73, 20);
		panel_1.add(tFechaSalida);
		tFechaSalida.setColumns(10);
		
		JLabel lblMontoDiaria = new JLabel("Monto diaria");
		lblMontoDiaria.setHorizontalAlignment(SwingConstants.RIGHT);
		lblMontoDiaria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblMontoDiaria.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblMontoDiaria.setBounds(10, 56, 83, 14);
		panel_1.add(lblMontoDiaria);
		
		tMontoDiario = new NumberTextField();
		tMontoDiario.setEnabled(false);
		tMontoDiario.setBounds(103, 53, 95, 20);
		panel_1.add(tMontoDiario);
		tMontoDiario.setColumns(10);
		
		tCompleto = new NumberTextField();
		tCompleto.setEnabled(false);
		tCompleto.setBounds(103, 81, 95, 20);
		panel_1.add(tCompleto);
		tCompleto.setColumns(10);
		
		lblSubtotal = new JLabel("Completo");
		lblSubtotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSubtotal.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSubtotal.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblSubtotal.setBounds(10, 82, 83, 17);
		panel_1.add(lblSubtotal);
		
		lblDescuento = new JLabel("Excedente");
		lblDescuento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescuento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblDescuento.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblDescuento.setBounds(10, 110, 83, 14);
		panel_1.add(lblDescuento);
		
		tExcedente = new NumberTextField();
		tExcedente.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					calcularValoresTotales();
					btnGuardarSalida.requestFocus();
				}
			}
		});
		tExcedente.setEnabled(false);
		tExcedente.setBounds(103, 107, 95, 20);
		panel_1.add(tExcedente);
		tExcedente.setColumns(10);
		
		tHoraSalida = new JFormattedTextField(Util.formatoHora());
		tHoraSalida.setEnabled(false);
		tHoraSalida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					calcularValoresSalida();
					tExcedente.requestFocus();
				}
			}
		});
		tHoraSalida.setBounds(102, 11, 52, 20);
		panel_1.add(tHoraSalida);
		
		lblDias = new JLabel("");
		lblDias.setForeground(new Color(220, 20, 60));
		lblDias.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDias.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblDias.setBounds(10, 31, 84, 23);
		panel_1.add(lblDias);
		
		lblHoras = new JLabel("");
		lblHoras.setHorizontalAlignment(SwingConstants.LEFT);
		lblHoras.setForeground(new Color(220, 20, 60));
		lblHoras.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblHoras.setBounds(103, 31, 95, 23);
		panel_1.add(lblHoras);
		
		btnCerrar = new JButton("Cerrar");
		btnCerrar.setEnabled(false);
		btnCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cerrar();
			}
		});
		btnCerrar.setBounds(261, 32, 80, 23);
		panel_4.add(btnCerrar);
		btnCerrar.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnCerrar.setForeground(new Color(0, 0, 0));
		
		btnGuardarSalida = new JButton("Guardar");
		btnGuardarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardarCierre();
			}
		});
		btnGuardarSalida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				guardarCierre();
			}
		});
		btnGuardarSalida.setEnabled(false);
		btnGuardarSalida.setBounds(261, 59, 80, 23);
		panel_4.add(btnGuardarSalida);
		btnGuardarSalida.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnGuardarSalida.setForeground(new Color(0, 0, 0));
		
		btnCncelarSalida = new JButton("C\u00E1ncelar");
		btnCncelarSalida.setEnabled(false);
		btnCncelarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				cancelarCierre();
			}
		});
		btnCncelarSalida.setBounds(261, 87, 80, 23);
		panel_4.add(btnCncelarSalida);
		btnCncelarSalida.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnCncelarSalida.setForeground(new Color(0, 0, 0));
		
		JPanel panel_3 = new JCustomPanel2();
		panel_3.setBounds(10, 177, 331, 56);
		panel_4.add(panel_3);
		panel_3.setLayout(null);
		
		tTotalGs = new PlaceholderTextField2();
		tTotalGs.setBounds(10, 11, 94, 34);
		panel_3.add(tTotalGs);
		tTotalGs.setPlaceholder("Guaran\u00ED");
		tTotalGs.setEnabled(false);
		
		JLabel label_2 = new JLabel("Gs.");
		label_2.setBounds(104, 14, 22, 31);
		panel_3.add(label_2);
		
		tTotalRs = new PlaceholderTextField2();
		tTotalRs.setBounds(125, 11, 81, 34);
		panel_3.add(tTotalRs);
		tTotalRs.setPlaceholder("Real");
		tTotalRs.setEnabled(false);
		
		JLabel label_3 = new JLabel("Rs.");
		label_3.setBounds(207, 14, 23, 31);
		panel_3.add(label_3);
		
		tTotalUs = new PlaceholderTextField2();
		tTotalUs.setBounds(230, 11, 81, 34);
		panel_3.add(tTotalUs);
		tTotalUs.setPlaceholder("Dolar");
		tTotalUs.setEnabled(false);
		
		JLabel label_4 = new JLabel("Us.");
		label_4.setBounds(312, 14, 22, 31);
		panel_3.add(label_4);
		
		lblTotales = new JLabel("Totales");
		lblTotales.setBounds(21, 162, 84, 14);
		panel_4.add(lblTotales);
		lblTotales.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		
		tablaEstadia.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				System.out.println("ok");
				cargarFormulario();
				
			}
		});
		
		//Al iniciar deshavilita los campos y recupera los registros para la tabla
		inicializar();
		
			
	}
	

	


	private void generarDeuda() {
		try {
			deu = new Deuda();
			deudaDao = new DeudaDao();
			deu.setId(deudaDao.recuperMaxId()+1);
			deu.setCliente(estadia.getCliente());
			deu.setEstadia(estadia);
			deu.setTipo(0);		
			deu.setMonto(Util.stringADouble(tCompleto.getText())+Util.stringADouble(tExcedente.getText()));
			deudaDao = new DeudaDao();
			deudaDao.insertar(deu);
			
			deu.setId(deu.getId()+1);
			deu.setTipo(1);		
			deu.setMonto(Util.stringADouble(tTotalDetalle.getText()));
			deudaDao = new DeudaDao();
			deudaDao.insertar(deu);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private void calcularValoresTotales() {
		if(tExcedente.getText().isEmpty())
			tExcedente.setValue(0.0);
		total = Util.stringADouble(tCompleto.getText())+Util.stringADouble(tExcedente.getText())+Util.stringADouble(tTotalDetalle.getText());
	
		tTotalGs.setText(Util.formatoDecimal(total));
		tTotalRs.setText(Util.formatoDecimal(total/Util.cotizacionReal));
		tTotalUs.setText(Util.formatoDecimal(total/Util.cotizacionDolar));
	}


	private void calcularValoresSalida() {
		montoHoras = 0.0;
		fechaSalida = FormatoFecha.stringToDateHora(tFechaSalida.getText()+" "+tHoraSalida.getText());
		Util.restarFecha(fechaSalida, estadia.getFecha());
		lblDias.setText(Util.dias+" Dia/s");
		lblHoras.setText(Util.horas+" Hora/s");
	
		tMontoDiario.setValue(estadia.getHabitacion().getPrecio());
		if (Util.horas<=12 && Util.horas>0) {
			montoHoras = estadia.getHabitacion().getPrecio() * 0.5;
		}else if (Util.horas>12)
			montoHoras = estadia.getHabitacion().getPrecio();
		
		tCompleto.setValue(estadia.getHabitacion().getPrecio()*Util.dias);
		tExcedente.setValue(montoHoras);
	}


	private void eliminarProducto() {
		if (tablaProductos.getSelectedRow()>=0) {
			id=(int) tablaProductos.campo(0);
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
//		if (listaEstadias.size()>0)
//			accion = "DATOS";
//		else
//			accion = "";
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrillaEstadia() {
		
		tablaEstadia.vaciar();
		
		fila = new Object[tablaEstadia.getColumnCount()];
		for (Estadia e:listaEstadias) {
			fila[0] = e.getId();
			fila[1] = e.getCliente().getNombre();
			fila[2] = e.getHabitacion().getDescripcion();
			fila[3] = Util.formatoDecimal(e.getHabitacion().getPrecio()) + " Gs.";
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
		tHora.setValue(FormatoFecha.dateAStringHora(new Date()));
		
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
				
			} catch (Exception e) {
				e.printStackTrace();
				estadiaDao.rollback();
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
		tHora.setEnabled(b);
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
		habilitarCampos(false);
		limpiarCampos();
		recuperaDatos();
		
		botonGrup3.botones(false, accion);
		
		
		
		
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
			accion = "DATOS";
			ultimaFila=tablaEstadia.getSelectedRow();
			botonGrup3.botones(false, accion);
			habilitarBotonesSalida(false);
			System.out.println(accion);
			
			estadiaDao = new EstadiaDao();
			estadia = estadiaDao.recuperarPorId((int) tablaEstadia.campo(0));
			if (estadia!=null) {
				
				if (estadia.getFechaSal()==null) {
					limpiarCamposSalida();
					
				}else{
					accion = "";
					botonGrup3.botones(false, accion);
					tFechaSalida.setValue(FormatoFecha.dateAString(estadia.getFechaSal()));
					tHoraSalida.setValue(FormatoFecha.dateAStringHora(estadia.getFechaSal()));
					calcularValoresSalida();
					tExcedente.setValue(estadia.getExcedente());
					calcularValoresTotales();
					btnCerrar.setEnabled(false);
				}
				
				cliente = estadia.getCliente();
				habitacion = estadia.getHabitacion();
				
				tFecha.setValue(FormatoFecha.dateAString(estadia.getFecha()));
				tHora.setValue(FormatoFecha.dateAStringHora(estadia.getFecha()));
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
		tFecha.setValue(null);;
		tHora.setValue(null);
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
		estadia.setFecha(FormatoFecha.stringToDateHora(tFecha.getText()+" "+tHora.getText()));
		estadia.setCliente(cliente);
		estadia.setHabitacion(habitacion);
		estadia.setObservacion(tObservacin.getText());
	}
	//cargar grilla o tabla de productos
	private void cargarGrillaProductos() {
		totalDetalle = 0.0;
		tablaProductos.vaciar();
		tTotalDetalle.setText("");
		fila = new Object[tablaProductos.getColumnCount()];
		for (Detalle d: listaDetalles) {
			fila[0] = d.getId();
			fila[1] = d.getProducto().getDescripcion();
			fila[2] = d.getCantidadProducto();
			fila[3] = Util.formatoDecimal(d.getProducto().getStock().getPrecio()) + " Gs.";
			fila[4] = Util.formatoDecimal(d.getTotal())  + " Gs.";
			totalDetalle+=d.getTotal();
			
		
			
			tablaProductos.agregar(fila);
 		}
		tTotalDetalle.setText(Util.formatoDecimal(totalDetalle));
		tTotalDetalleRs.setText(Util.formatoDecimal(totalDetalle/Util.cotizacionReal));
		tTotalDetalleUs.setText(Util.formatoDecimal(totalDetalle/Util.cotizacionDolar));
		//mantiene el foco en el ultimo registro cargado
		//tablaEstadia.setSeleccion();
	}
	
	
		@Override
		public void cargarFormularioProducto() {
		if(tablaProductos.getRowCount()==1)
			ultimaFila = -1;
		if (tablaProductos.getSelectedRow()>=0 && tablaProductos.getSelectedRow()!=ultimaFila) {
			ultimaFila=tablaProductos.getSelectedRow();
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
		tPrecioProducto.setText(Util.formatoDecimal(p.getStock().getPrecio()));
		tPrecioProductoRs.setText(Util.formatoDecimal(p.getStock().getPrecio()/Util.cotizacionReal));
		tPrecioProductoUs.setText(Util.formatoDecimal(p.getStock().getPrecio()/Util.cotizacionDolar));
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
		habilitarCamposSalida(true);
		tFechaSalida.setValue(FormatoFecha.dateAString(new Date()));
		tHoraSalida.setValue(FormatoFecha.dateAStringHora(new Date()));
		tFechaSalida.requestFocus();
		habilitarBotonesSalida(true);
	}


	private void habilitarBotonesSalida(boolean b) {
		
		btnCerrar.setEnabled(!b);
		
		btnCncelarSalida.setEnabled(b);
		btnGuardarSalida.setEnabled(b);
	}

	private void limpiarCamposSalida() {
		tFechaSalida.setValue(null);
		tHoraSalida.setText(null);
		lblDias.setText("");
		lblHoras.setText("");
		tMontoDiario.setValue(null);
		tCompleto.setValue(null);
		tExcedente.setValue(null);
		tTotalGs.setText("");
		tTotalRs.setText("");
		tTotalUs.setText("");
	}

	private void habilitarCamposSalida(boolean b) {
		tFechaSalida.setEnabled(b);
		tHoraSalida.setEnabled(b);
		tExcedente.setEnabled(b);
	}


	@Override
	public void guardarCierre() {
		estadia.setFechaSal(fechaSalida);
		estadia.setExcedente(montoHoras);
		estadiaDao = new EstadiaDao();
		try {
			estadiaDao.actualizar(estadia);
		} catch (Exception e) {
			estadiaDao.rollback();
		}
		
		
		generarDeuda();
		
		limpiarCamposSalida();
		habilitarCamposSalida(false);
		habilitarBotonesSalida(false);
		btnGuardarSalida.setEnabled(false);
		recuperaDatos();
	}


	@Override
	public void cancelarCierre() {
		limpiarCamposSalida();
		habilitarCamposSalida(false);
		habilitarBotonesSalida(false);
	}




	@Override
	public void cargarAtributos() {
		// TODO Auto-generated method stub
		
	}
	}

	