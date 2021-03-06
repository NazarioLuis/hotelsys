package py.com.hotelsys.presentacion.transacciones;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup3;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.FormatoTabla;
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
	private JButton btnAddProducto;

	private JScrollPane scrollPane_1;
	private CustomTable tablaProductos;
	private JPanel panel_1;
	private PlaceholderTextField placeholderTextField_5;
	
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
	private DeudaDao deudaDao;
	private JButton btnBuscarCli;
	private JButton btnBuscarHa;
	private JButton btnCncelarDetalle;
	private JButton btnEliminarDetalle;
	private JButton btnGuardarDetalle;
	private JCheckBox cbCerrados;
	private JCheckBox cbTodos;
	private JCheckBox cbAbiertos;
	private ButtonGroup bGroup;
	private String filtro;
	

	public TransEstadia(JFrame frame) {
		super(frame);
		getContentPane().setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		getContentPane().setLocation(-25, -344);
		setTitle("Hospedar a Cliente");
		setBounds(100, 100, 1050, 600);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		getContentPane().setLayout(null);
		
		panel =	new JCustomPanel1();
		panel.setBounds(33, 22, 286, 258);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tCodHabitacion = new PlaceholderTextField();
		tCodHabitacion.setEnabled(false);
		tCodHabitacion.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCodHabitacion.setPlaceholder("Habitaci\u00F3n");
		tCodHabitacion.setBounds(10, 80, 226, 20);
		panel.add(tCodHabitacion);
		
		tCodCliente = new PlaceholderTextField();
		tCodCliente.setEnabled(false);
		tCodCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCodCliente.setPlaceholder("Cliente");
		tCodCliente.setBounds(10, 48, 226, 20);
		panel.add(tCodCliente);
		
		tObservacin = new JTextArea("");
		tObservacin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					botonGrup3.btnGuardar.requestFocus();
				}
			}
		});
		tObservacin.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tObservacin.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tObservacin.setRows(10);
		tObservacin.setLineWrap(true);
		tObservacin.setBounds(10, 124, 266, 118);
		panel.add(tObservacin);
		
		label = new JLabel("Observaci\u00F3n:");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(15, 104, 88, 20);
		panel.add(label);
		
	
		
		
		btnBuscarCli = new JButton("");
		btnBuscarCli.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/1428455167_698838-icon-111-search-16.png")));
		btnBuscarCli.setEnabled(false);
		btnBuscarCli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarCliente();
					
			}
			
		});
		
	
		
		btnBuscarCli.setBounds(239, 46, 34, 23);
		panel.add(btnBuscarCli);
		
		btnBuscarHa = new JButton("");
		btnBuscarHa.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/1428455167_698838-icon-111-search-16.png")));
		btnBuscarHa.setEnabled(false);
		btnBuscarHa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarHabitacion();
			}
		});
		btnBuscarHa.setBounds(239, 78, 34, 23);
		panel.add(btnBuscarHa);
		
		
		tFecha = new JFormattedTextField(Util.formatoFecha());
		tFecha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					tHora.requestFocus();
				}
			}
		});
		tFecha.setDisabledTextColor(Color.DARK_GRAY);
		tFecha.setBounds(10, 11, 74, 20);
		panel.add(tFecha);
		
		tHora = new JFormattedTextField(Util.formatoHora());
		tHora.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					btnBuscarCli.requestFocus();
				}
			}
		});
		tHora.setDisabledTextColor(Color.DARK_GRAY);
		tHora.setEnabled(false);
		tHora.setBounds(94, 11, 52, 20);
		panel.add(tHora);
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(423, 11, 611, 181);
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
		
		
		tablaEstadia = new CustomTable(new String[] {"#", "Cliente", "Habitaci\u00F3n", "Precio", "salida"}, new int[] {75, 220, 270,90});
		scrollPane.setViewportView(tablaEstadia);
		tablaEstadia.ocultarColumna(4);
		tablaEstadia.setDefaultRenderer(Object.class, new FormatoTabla(4,"null"));
		
		JTabbedPane tpServicios = new JTabbedPane(JTabbedPane.TOP);
		tpServicios.setBounds(424, 266, 610, 283);
		getContentPane().add(tpServicios);
		
		panelProducto = new JPanel();
		tpServicios.addTab("Productos", null, panelProducto, null);
		panelProducto.setLayout(null);
		
		JPanel panel_2 = new JCustomPanel1();
		panel_2.setBounds(10, 6, 476, 94);
		panelProducto.add(panel_2);
		panel_2.setLayout(null);
		
		tPrecioProducto = new PlaceholderTextField2();
		tPrecioProducto.setEnabled(false);
		tPrecioProducto.setBounds(36, 29, 104, 34);
		panel_2.add(tPrecioProducto);
		tPrecioProducto.setPlaceholder("Guaran\u00ED");
		
		tCodProducto = new PlaceholderTextField();
		tCodProducto.setEnabled(false);
		tCodProducto.setBounds(7, 7, 315, 20);
		panel_2.add(tCodProducto);
		tCodProducto.setPlaceholder("Producto");
		
		btnAddProducto = new JButton("#");
		btnAddProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarBuscadorProducto();
			}
		});
		btnAddProducto.setBounds(332, 7, 46, 20);
		panel_2.add(btnAddProducto);
		
		tCantidadProducto = new PlaceholderTextField();
		tCantidadProducto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validarNumero(e);
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					guardarProducto();
				}
				
			}
		});
		tCantidadProducto.setPlaceholder("Cantidad");
		tCantidadProducto.setBounds(7, 67, 104, 20);
		panel_2.add(tCantidadProducto);
		
		tPrecioProductoRs = new PlaceholderTextField2();
		tPrecioProductoRs.setEnabled(false);
		tPrecioProductoRs.setPlaceholder("Real");
		tPrecioProductoRs.setBounds(190, 29, 81, 34);
		panel_2.add(tPrecioProductoRs);
		
		tPrecioProductoUs = new PlaceholderTextField2();
		tPrecioProductoUs.setEnabled(false);
		tPrecioProductoUs.setPlaceholder("Dolar");
		tPrecioProductoUs.setBounds(331, 29, 81, 34);
		panel_2.add(tPrecioProductoUs);
		
		JLabel lblGs = new JLabel("");
		lblGs.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/pry.png")));
		lblGs.setBounds(8, 32, 29, 31);
		panel_2.add(lblGs);
		
		JLabel lblUs = new JLabel("");
		lblUs.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/brE.png")));
		lblUs.setBounds(161, 32, 37, 31);
		panel_2.add(lblUs);
		
		JLabel lblRs = new JLabel("");
		lblRs.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/eeuue.png")));
		lblRs.setBounds(301, 32, 29, 31);
		panel_2.add(lblRs);
		
		btnEliminarDetalle = new JButton("Eliminar");
		btnEliminarDetalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				eliminarProducto();
			}
		});
		btnEliminarDetalle.setBounds(496, 60, 89, 23);
		panelProducto.add(btnEliminarDetalle);
		btnEliminarDetalle.setForeground(new Color(0, 0, 0));
		btnEliminarDetalle.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		
		btnCncelarDetalle = new JButton("C\u00E1ncelar");
		btnCncelarDetalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				limpiarCamposDetalle();
			}
		});
		
		btnCncelarDetalle.setBounds(496, 34, 89, 23);
		panelProducto.add(btnCncelarDetalle);
		btnCncelarDetalle.setForeground(new Color(0, 0, 0));
		btnCncelarDetalle.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 103, 585, 110);
		panelProducto.add(scrollPane_1);
		
		
		tablaProductos = new CustomTable(new String[] {"#","Descripci�n producto","Cant.","Precio","Subtotal"}, new int[] {50,180,35,35});
		tablaProductos.ocultarColumna(0);
		scrollPane_1.setViewportView(tablaProductos);
		
		tTotalDetalle = new PlaceholderTextField();
		tTotalDetalle.setPlaceholder("");
		tTotalDetalle.setFont(new Font("Tahoma", Font.BOLD, 11));
		tTotalDetalle.setDisabledTextColor(new Color(220, 20, 60));
		tTotalDetalle.setEnabled(false);
		tTotalDetalle.setBounds(248, 214, 86, 20);
		panelProducto.add(tTotalDetalle);
		tTotalDetalle.setColumns(10);
		
		JLabel lblMontoTotal_1 = new JLabel("Monto Total");
		lblMontoTotal_1.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblMontoTotal_1.setBounds(142, 215, 83, 14);
		panelProducto.add(lblMontoTotal_1);
		
		btnGuardarDetalle = new JButton("Guardar");
		btnGuardarDetalle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				guardarProducto();
			}
		});
		btnGuardarDetalle.setForeground(Color.BLACK);
		btnGuardarDetalle.setFont(new Font("Microsoft New Tai Lue", Font.BOLD, 9));
		btnGuardarDetalle.setBounds(496, 8, 89, 23);
		panelProducto.add(btnGuardarDetalle);
		
		tTotalDetalleRs = new PlaceholderTextField();
		tTotalDetalleRs.setPlaceholder("");
		tTotalDetalleRs.setFont(new Font("Tahoma", Font.BOLD, 11));
		tTotalDetalleRs.setDisabledTextColor(new Color(220, 20, 60));
		tTotalDetalleRs.setEnabled(false);
		tTotalDetalleRs.setColumns(10);
		tTotalDetalleRs.setBounds(382, 214, 79, 20);
		panelProducto.add(tTotalDetalleRs);
		
		tTotalDetalleUs = new PlaceholderTextField();
		tTotalDetalleUs.setPlaceholder("");
		tTotalDetalleUs.setFont(new Font("Tahoma", Font.BOLD, 11));
		tTotalDetalleUs.setDisabledTextColor(new Color(220, 20, 60));
		tTotalDetalleUs.setEnabled(false);
		tTotalDetalleUs.setColumns(10);
		tTotalDetalleUs.setBounds(512, 214, 79, 20);
		panelProducto.add(tTotalDetalleUs);
		
		JLabel lblRs_1 = new JLabel("");
		lblRs_1.setVerticalAlignment(SwingConstants.TOP);
		lblRs_1.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/brE.png")));
		lblRs_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblRs_1.setBounds(355, 213, 28, 30);
		panelProducto.add(lblRs_1);
		
		JLabel lblUs_1 = new JLabel("");
		lblUs_1.setVerticalAlignment(SwingConstants.TOP);
		lblUs_1.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/eeuue.png")));
		lblUs_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblUs_1.setBounds(485, 212, 27, 32);
		panelProducto.add(lblUs_1);
		
		JLabel label_1 = new JLabel("");
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(221, 212, 28, 32);
		panelProducto.add(label_1);
		label_1.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/pry.png")));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		
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
		panel_4.setBounds(33, 297, 380, 248);
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
		
		
		
		tFechaSalida = new JFormattedTextField(Util.formatoFecha());
		tFechaSalida.setDisabledTextColor(Color.DARK_GRAY);
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
		tHoraSalida.setDisabledTextColor(Color.DARK_GRAY);
		tHoraSalida.setEnabled(false);
		tHoraSalida.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					tExcedente.requestFocus();
					calcularValoresSalida();
					
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
		panel_3.setBounds(10, 177, 359, 56);
		panel_4.add(panel_3);
		panel_3.setLayout(null);
		
		tTotalGs = new PlaceholderTextField2();
		tTotalGs.setBounds(31, 11, 94, 34);
		panel_3.add(tTotalGs);
		tTotalGs.setPlaceholder("Guaran\u00ED");
		tTotalGs.setEnabled(false);
		
		JLabel label_2 = new JLabel("Gs.");
		label_2.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/pry.png")));
		label_2.setBounds(5, 14, 30, 31);
		panel_3.add(label_2);
		
		tTotalRs = new PlaceholderTextField2();
		tTotalRs.setBounds(157, 11, 81, 34);
		panel_3.add(tTotalRs);
		tTotalRs.setPlaceholder("Real");
		tTotalRs.setEnabled(false);
		
		tTotalUs = new PlaceholderTextField2();
		tTotalUs.setBounds(272, 11, 81, 34);
		panel_3.add(tTotalUs);
		tTotalUs.setPlaceholder("Dolar");
		tTotalUs.setEnabled(false);
		
		JLabel label_3 = new JLabel("Rs.");
		label_3.setBounds(133, 14, 23, 31);
		panel_3.add(label_3);
		label_3.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/brE.png")));
		
		JLabel label_4 = new JLabel("Us.");
		label_4.setBounds(246, 14, 29, 31);
		panel_3.add(label_4);
		label_4.setIcon(new ImageIcon(TransEstadia.class.getResource("/img/eeuue.png")));
		
		lblTotales = new JLabel("Totales");
		lblTotales.setBounds(21, 162, 84, 14);
		panel_4.add(lblTotales);
		lblTotales.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		
		bGroup = new ButtonGroup();
		
		
		cbAbiertos = new JCheckBox("Abiertos");
		cbAbiertos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(cbAbiertos.isSelected());
					recuperaDatos();
			}
		});	
		cbAbiertos.setBounds(736, 199, 78, 23);
		bGroup.add(cbAbiertos);
		getContentPane().add(cbAbiertos);
		
		cbCerrados = new JCheckBox("Cerrados");
		cbCerrados.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(cbCerrados.isSelected());
					recuperaDatos();
			}
		});		
		cbCerrados.setBounds(816, 199, 84, 23);
		bGroup.add(cbCerrados);
		getContentPane().add(cbCerrados);
		
		cbTodos = new JCheckBox("Todos");
		cbTodos.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(cbTodos.isSelected());
					recuperaDatos();
			}
		});
		cbTodos.setBounds(915, 199, 84, 23);
		bGroup.add(cbTodos);
		getContentPane().add(cbTodos);
		
		bGroup.setSelected(cbAbiertos.getModel(), true);
		
		
		
		
		tablaEstadia.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
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
			deu.setEstado(true);
			deu.setMonto(Util.stringADouble(tCompleto.getText())+Util.stringADouble(tExcedente.getText()));
			deudaDao = new DeudaDao();
			deudaDao.insertar(deu);
			
			if (!tTotalDetalle.getText().equals(0)) {
				deu.setId(deu.getId()+1);
				deu.setTipo(1);		
				deu.setMonto(Util.stringADouble(tTotalDetalle.getText()));
				deudaDao = new DeudaDao();
				deudaDao.insertar(deu);
			}
			
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
		if (!(Util.diff<0)) {
			lblDias.setText(Util.dias+" Dia/s");
			lblHoras.setText(Util.horas+" Hora/s");
		
			tMontoDiario.setValue(estadia.getHabitacion().getPrecio());
			if (Util.horas<=12 && Util.horas>0) {
				montoHoras = estadia.getHabitacion().getPrecio() * 0.5;
			}else if (Util.horas>12)
				montoHoras = estadia.getHabitacion().getPrecio();
			
			tCompleto.setValue(estadia.getHabitacion().getPrecio()*Util.dias);
			tExcedente.setValue(montoHoras);
		}else{
			advertencia("Fecha y hora de salida es inferior es menor a la de entrada", 2);
			tFechaSalida.requestFocus();
		}
		
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
		estado();
		estadiaDao = new EstadiaDao();
		listaEstadias = estadiaDao.recuperarPorFiltros(new String[]{filtro,tBuscar.getText()});
		
		cargarGrillaEstadia();

	}

	private void estado() {
		filtro = "";
		@SuppressWarnings("rawtypes")
		Enumeration elements = bGroup.getElements();
	    while (elements.hasMoreElements()) {
	      AbstractButton button = (AbstractButton)elements.nextElement();
	      if (button.isSelected()) {
	    	  filtro = button.getText();
	      }
	    }
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
			fila[4] = e.getFechaSal();
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
		if (comprobarEstadiaVacia()) {
			cargarAtributos();
			estadiaDao = new EstadiaDao();
			
			if(accion.equals("AGREGAR"))
				try {
					estadiaDao.insertar(estadia);
					inicializar();
					
				} catch (Exception e) {
					e.printStackTrace();
					estadiaDao.rollback();
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
		
	}

	private boolean comprobarEstadiaVacia() {
		if (tCodCliente.getText().isEmpty()) {
			advertencia("Debe seleccionar un cliente", 2);
			btnBuscarCli.requestFocus();
			return false;
		}
		if (tCodHabitacion.getText().isEmpty()) {
			advertencia("Debe seleccionar una habitavi�n", 2);
			btnBuscarHa.requestFocus();
			return false;
		}
		return true;
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
		btnBuscarCli.setEnabled(b);
		btnBuscarHa.setEnabled(b);
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
		JOptionPane.showMessageDialog(null, texto, "Atenci�n", t);
	}
	
	
	

	@Override
	public void cargarFormulario() {
		if(tablaEstadia.getRowCount()==1)
			ultimaFila = -1;
		if (tablaEstadia.getSelectedRow()>=0 && (tablaEstadia.getSelectedRow()!=ultimaFila||accion=="CERRAR")) {
			accion = "DATOS";
			ultimaFila=tablaEstadia.getSelectedRow();
			botonGrup3.botones(false, accion);
			habilitarBotonesSalida(false);
			limpiarCamposDetalle();
			
			estadiaDao = new EstadiaDao();
			estadia = estadiaDao.recuperarPorId((int) tablaEstadia.campo(0));
			if (estadia!=null) {

				recuperaDatosDetalle();
				
				if (estadia.getFechaSal()==null) {
					limpiarCamposSalida();
					habilitarDetalle(true);
				}else{
					accion = "";
					botonGrup3.botones(false, accion);
					tFechaSalida.setValue(FormatoFecha.dateAString(estadia.getFechaSal()));
					tHoraSalida.setValue(FormatoFecha.dateAStringHora(estadia.getFechaSal()));
					calcularValoresSalida();
					tExcedente.setValue(estadia.getExcedente());
					calcularValoresTotales();
					btnCerrar.setEnabled(false);
					habilitarDetalle(false);
				}
				
				cliente = estadia.getCliente();
				habitacion = estadia.getHabitacion();
				
				tFecha.setValue(FormatoFecha.dateAString(estadia.getFecha()));
				tHora.setValue(FormatoFecha.dateAStringHora(estadia.getFecha()));
				tCodCliente.setText(estadia.getCliente().getNombre());
				tCodHabitacion.setText(estadia.getHabitacion().getDescripcion());
				tObservacin.setText(estadia.getObservacion());
				
				
			}
			
		}
		
	}

	private void habilitarDetalle(boolean b) {
		btnAddProducto.setEnabled(b);
		btnGuardarDetalle.setEnabled(b);
		btnEliminarDetalle.setEnabled(b);
		btnCncelarDetalle.setEnabled(b);
		tCantidadProducto.setEnabled(b);
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
					estado();
					estadiaDao = new EstadiaDao();
					listaEstadias = estadiaDao.recuperarPorFiltros(new String[]{filtro,tBuscar.getText()});
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
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar estadia: "+tablaEstadia.campo(1)+"?","Atenci�n",JOptionPane.YES_NO_OPTION);
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
		
		if(comprobarProductoVacio()){
			if (comprobarStock()) {
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
		
	}
	private boolean comprobarProductoVacio() {
		
		if (tCodProducto.getText().equals("")) {
			advertencia("Debe cargar un producto", 2);
			btnAddProducto.requestFocus();
			return false;
		}
		if (tCantidadProducto.getText().equals("")) {
			advertencia("Cantidad no puede estar vacio",2);
			tCantidadProducto.requestFocus();
			return false;
		}
		return true;
	}





	private boolean comprobarStock() {
		if(producto.getStock().getCantidad() - Integer.parseInt(tCantidadProducto.getText())<0){
			advertencia("Stock insuficiente. El producto "+producto.getDescripcion()+" solo tiene "+producto.getStock().getCantidad()+" items en stock", 0);
			tCantidadProducto.requestFocus();
			return false;
		}
		return true;
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
		int nuevaCantidad = p.getStock().getCantidad()+det.getCantidadProducto();
		Double nuevoCosto = (p.getStock().getCosto()*p.getStock().getCantidad())
				+(det.getCantidadProducto()*det.getCostoProducto())/nuevaCantidad;
		
		p.getStock().setCantidad(nuevaCantidad);
		p.getStock().setCosto(nuevoCosto);
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
		btnAddProducto.requestFocus();
	}


	private void limpiarCamposDetalle() {
		tCodProducto.setText("");
		tPrecioProducto.setText("");
		tCantidadProducto.setText("");
		tPrecioProductoRs.setText("");
		tPrecioProductoUs.setText("");
	}


	@Override
	//CARGAR ATRIBUTOS DEL PRODUCTOS
	public void cargarAtributosProductos() {
			
		detalle = new Detalle();
			
		detalleDao = new DetalleDao();
		detalle.setId(detalleDao.recuperMaxId()+1);
						
		detalle.setProducto(producto);
		detalle.setEstadia(estadia);
		detalle.setPrecioProducto(producto.getStock().getPrecio());
		detalle.setCantidadProducto(Integer.parseInt( tCantidadProducto.getText()));
		detalle.setCostoProducto(producto.getStock().getCosto());
		 
		}
	
	
	public void cargarAtributos() {
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
			fila[3] = Util.formatoDecimal(d.getPrecioProducto()) + " Gs.";
			fila[4] = Util.formatoDecimal(d.getPrecioProducto()*d.getCantidadProducto())  + " Gs.";
			totalDetalle+=d.getPrecioProducto()*d.getCantidadProducto();
			
		
			
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
		btnBuscarHa.requestFocus();
		

	
	}
	
	@Override
	public void cargar(Habitacion h) {
		tCodHabitacion.setText(h.getDescripcion());
		this.habitacion = h;
		tObservacin.requestFocus();
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
		accion = "CERRAR";
		recuperaDatos();
	}


	@Override
	public void cancelarCierre() {
		limpiarCamposSalida();
		habilitarCamposSalida(false);
		habilitarBotonesSalida(false);
	}
	}

	