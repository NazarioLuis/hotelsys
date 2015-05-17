package py.com.hotelsys.presentacion.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import py.com.hotelsys.componentes.FondoPrincipal;
import py.com.hotelsys.componentes.VisorFechaHora;
import py.com.hotelsys.presentacion.formulario.FormCliente;
import py.com.hotelsys.presentacion.formulario.FormCotizacion;
import py.com.hotelsys.presentacion.formulario.FormHabitacion;
import py.com.hotelsys.presentacion.formulario.FormProducto;
import py.com.hotelsys.presentacion.formulario.FormProveedor;
import py.com.hotelsys.presentacion.formulario.FormRol;
import py.com.hotelsys.presentacion.formulario.FormUsuario;
import py.com.hotelsys.presentacion.transacciones.PantallaCompra;
import py.com.hotelsys.presentacion.transacciones.PantallaEntrada;
import py.com.hotelsys.presentacion.transacciones.PantallaSalida;
import py.com.hotelsys.presentacion.transacciones.TransEstadia;
import py.com.hotelsys.util.HibernateUtil;
import py.com.hotelsys.util.OpcionesDeUsuario;
import py.com.hotelsys.util.Util;

@SuppressWarnings("serial")
public class Principal extends JFrame  {

	private JPanel contentPane;
	private JMenuBar mbSistema;


	public Principal() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				HibernateUtil.cerrar();
			}
		});
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setExtendedState(MAXIMIZED_BOTH);
		
		
		mbSistema = new JMenuBar();
		setJMenuBar(mbSistema);
		
		JMenu mnGral = new JMenu("General");
		mbSistema.add(mnGral);
		
		JMenuItem mntmCliente = new JMenuItem("Clientes");
		mntmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormCliente();
			}
		});
		mntmCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
		mnGral.add(mntmCliente);
		
		JMenuItem mntmMoneda = new JMenuItem("Cotizaci\u00F3n de Monedas");
		mntmMoneda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormCotizacion();
			}

			
		});
		mntmMoneda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
		mnGral.add(mntmMoneda);
		
		JMenuItem mntmRolesDelSistema = new JMenuItem("Roles del Sistema");
		mntmRolesDelSistema.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
		mntmRolesDelSistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormRol();
			}
		});
		mnGral.add(mntmRolesDelSistema);
		
		JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
		mntmUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
		mntmUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormUsuario();
			}
		});
		mnGral.add(mntmUsuarios);
		
		JMenu mnEstada = new JMenu("Estad\u00EDa");
		mbSistema.add(mnEstada);
		
		JMenuItem mntmHabitacin = new JMenuItem("Habitaci\u00F3nes");
		mntmHabitacin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				verFormHabitacion();	
			}
		});
		mnEstada.add(mntmHabitacin);
		mntmHabitacin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
		
		JMenuItem mntmRegistrar = new JMenuItem("Hospedar");
		mntmRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarEstadia();
			}
		});
		mntmRegistrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0));
		mnEstada.add(mntmRegistrar);
		
		JMenuItem mntmGenerarInformes = new JMenuItem("Generar Informe");
		mntmGenerarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));
		mnEstada.add(mntmGenerarInformes);
		
		JMenu mnStock = new JMenu("Stock");
		mbSistema.add(mnStock);
		
		JMenuItem mntmProductos = new JMenuItem("Productos");
		mnStock.add(mntmProductos);
		mntmProductos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		mntmProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormProducto();
			}
		});
		
		JMenuItem mntmRegistrarEntrada = new JMenuItem("Registrar Alta");
		mntmRegistrarEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarEntradas();
			}
		});
		mntmRegistrarEntrada.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
		mnStock.add(mntmRegistrarEntrada);
		
		JMenuItem mntmRegistrarSalida = new JMenuItem("Registrar Baja");
		mntmRegistrarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarSalidas();
			}
		});
		mntmRegistrarSalida.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, 0));
		mnStock.add(mntmRegistrarSalida);
		
		JMenuItem mntmImprimirInforme = new JMenuItem("Imprimir listado de Productos");
		mntmImprimirInforme.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
		mnStock.add(mntmImprimirInforme);
		
		JMenu mnCompras = new JMenu("Compra");
		mbSistema.add(mnCompras);
		
		JMenuItem mntmRegistrar_1 = new JMenuItem("Registrar Compra");
		mntmRegistrar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarCompras();
			}
		});
		mntmRegistrar_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
		mnCompras.add(mntmRegistrar_1);
		
		JMenuItem mntmProveedores = new JMenuItem("Proveedores");
		mntmProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormProveedor();
			}
		});
		mntmProveedores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0));
		mnCompras.add(mntmProveedores);
		
		JMenu mnSalir = new JMenu("Salir");
		mnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				HibernateUtil.cerrar();
				System.exit(0);
			}
		});
		
		
		mbSistema.add(mnSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		FondoPrincipal fondoPrincipal = new FondoPrincipal();
		fondoPrincipal.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		contentPane.add(fondoPrincipal, BorderLayout.CENTER);
		
		VisorFechaHora vsrfchrHoy = new VisorFechaHora();
		vsrfchrHoy.setForeground(new Color(255, 255, 204));
		vsrfchrHoy.setHorizontalAlignment(SwingConstants.CENTER);
		vsrfchrHoy.ejecutar();
		vsrfchrHoy.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 20));
		
		JLabel label = new JLabel("HoSys");
		label.setFont(new Font("Traditional Arabic", Font.BOLD | Font.ITALIC, 80));
		
		JLabel lblSistemaDeGestin = new JLabel("Sistema de Gesti\u00F3n Hotelera");
		lblSistemaDeGestin.setHorizontalAlignment(SwingConstants.LEFT);
		lblSistemaDeGestin.setFont(new Font("Swis721 LtEx BT", Font.BOLD | Font.ITALIC, 20));
		GroupLayout gl_fondoPrincipal = new GroupLayout(fondoPrincipal);
		gl_fondoPrincipal.setHorizontalGroup(
			gl_fondoPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_fondoPrincipal.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_fondoPrincipal.createParallelGroup(Alignment.LEADING)
						.addComponent(vsrfchrHoy, GroupLayout.DEFAULT_SIZE, 1330, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSistemaDeGestin, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_fondoPrincipal.setVerticalGroup(
			gl_fondoPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_fondoPrincipal.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSistemaDeGestin)
					.addPreferredGap(ComponentPlacement.RELATED, 518, Short.MAX_VALUE)
					.addComponent(vsrfchrHoy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		fondoPrincipal.setLayout(gl_fondoPrincipal);
		
		
		OpcionesDeUsuario.agregarMenuAOpcionesDeUsuario(mbSistema);
		OpcionesDeUsuario.recuperarOpcionesDeUsuario(mbSistema);
		Util.cotizacionDelDia();
	}

	private void verFormUsuario() {
		FormUsuario fu = new FormUsuario(this);
		fu.setVisible(true);
	}



	private void verFormProveedor() {
		FormProveedor fp = new FormProveedor(this);
		fp.setVisible(true);
	}



	private void verFormRol() {
		FormRol fr = new FormRol(this);
		fr.setVisible(true);
	}



	private void mostrarEntradas() {
		PantallaEntrada pe = new PantallaEntrada(this);
		pe.setVisible(true);
	}

	private void mostrarSalidas() {
		PantallaSalida ps = new PantallaSalida(this);
		ps.setVisible(true);
	}

	private void verFormCliente() {
		FormCliente fc = new FormCliente(this);
		fc.setVisible(true);
	}
	private void verFormProducto() {
		FormProducto fp = new FormProducto(this);
		fp.setVisible(true);
	}
	private void verFormHabitacion() {
		FormHabitacion fh = new FormHabitacion(this);
		fh.setVisible(true);
	}
	
	private void verFormCotizacion() {
		FormCotizacion fc = new FormCotizacion(this);
		fc.setVisible(true);
	}
	private void mostrarEstadia() {
		TransEstadia te= new TransEstadia(this);
		te.setVisible(true);
		
	}
	private void mostrarCompras() {
		PantallaCompra pc = new PantallaCompra(this);
		pc.setVisible(true);

	}
}
