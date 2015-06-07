package py.com.hotelsys.presentacion.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
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
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import py.com.hotelsys.componentes.ButtonTool;
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
import py.com.hotelsys.presentacion.transacciones.TransAbertura;
import py.com.hotelsys.presentacion.transacciones.TransCierre;
import py.com.hotelsys.presentacion.transacciones.TransCobranza;
import py.com.hotelsys.presentacion.transacciones.TransEstadia;
import py.com.hotelsys.util.HibernateUtil;
import py.com.hotelsys.util.OpcionesDeUsuario;
import py.com.hotelsys.util.Util;
import py.com.hotelsys.util.VariableSys;

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
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo.gif")));
		
		
		mbSistema = new JMenuBar();
		mbSistema.setBorder(new LineBorder(new Color(0, 0, 0)));
		setJMenuBar(mbSistema);
		
		JMenu mnGral = new JMenu("General");
		mbSistema.add(mnGral);
		
		JMenuItem mntmCliente = new JMenuItem("Clientes");
		mntmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarFormulario(new FormCliente(Principal.this));
			}
		});
		mntmCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
		mnGral.add(mntmCliente);
		
		JMenuItem mntmMoneda = new JMenuItem("Cotizaci\u00F3n de Monedas");
		mntmMoneda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarFormulario(new FormCotizacion(Principal.this));
			}

			
		});
		mntmMoneda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
		mnGral.add(mntmMoneda);
		
		JMenuItem mntmRolesDelSistema = new JMenuItem("Roles del Sistema");
		mntmRolesDelSistema.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
		mntmRolesDelSistema.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarFormulario(new FormRol(Principal.this));
			}
		});
		mnGral.add(mntmRolesDelSistema);
		
		JMenuItem mntmUsuarios = new JMenuItem("Usuarios");
		mntmUsuarios.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, 0));
		mntmUsuarios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarFormulario(new FormUsuario(Principal.this));
			}
		});
		mnGral.add(mntmUsuarios);
		
		JMenu mnEstada = new JMenu("Estad\u00EDa");
		mbSistema.add(mnEstada);
		
		JMenuItem mntmHabitacin = new JMenuItem("Habitaci\u00F3nes");
		mntmHabitacin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarFormulario(new FormHabitacion(Principal.this));	
			}
		});
		mnEstada.add(mntmHabitacin);
		mntmHabitacin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
		
		JMenuItem mntmRegistrar = new JMenuItem("Hospedar");
		mntmRegistrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarFormulario(new TransEstadia(Principal.this));
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
				mostrarFormulario(new FormProducto(Principal.this));
			}
		});
		
		JMenuItem mntmRegistrarEntrada = new JMenuItem("Registrar Alta");
		mntmRegistrarEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarFormulario(new PantallaEntrada(Principal.this));;
			}
		});
		mntmRegistrarEntrada.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0));
		mnStock.add(mntmRegistrarEntrada);
		
		JMenuItem mntmRegistrarSalida = new JMenuItem("Registrar Baja");
		mntmRegistrarSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarFormulario(new PantallaSalida(Principal.this));
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
				mostrarFormulario(new PantallaCompra(Principal.this));
			}
		});
		mntmRegistrar_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
		mnCompras.add(mntmRegistrar_1);
		
		JMenuItem mntmProveedores = new JMenuItem("Proveedores");
		mntmProveedores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarFormulario(new FormProveedor(Principal.this));
			}
		});
		mntmProveedores.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0));
		mnCompras.add(mntmProveedores);
		
		JMenu mnSalir = new JMenu("Salir");
		mnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				cerrar();
			}
		});
		
		JMenu mnAdministrativo = new JMenu("Administrativo");
		mbSistema.add(mnAdministrativo);
		
		JMenuItem mntmCobranza = new JMenuItem("Cobranza");
		mntmCobranza.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, 0));
		mntmCobranza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comprobarCaja())
					mostrarFormulario(new TransCobranza(Principal.this));
				else
					JOptionPane.showMessageDialog(null, "Debe abrir una caja","Error",0);
			}
		});
		
		JMenu mnCaja = new JMenu("Caja");
		mnAdministrativo.add(mnCaja);
		
		JMenuItem mntmAbrir = new JMenuItem("Abrir");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (VariableSys.user.getId()!=9999) {
					if(!comprobarCaja())
						mostrarFormulario(new TransAbertura(Principal.this));
					else
						JOptionPane.showMessageDialog(null, "Ya se ha abierto una caja","Error",0);
				}
				else
					JOptionPane.showMessageDialog(null, "El usuario ROOT no puede ser cajero","Error",0);
			}
		});
		mntmAbrir.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.ALT_MASK));
		mnCaja.add(mntmAbrir);
		
		JMenuItem mntmCerrar = new JMenuItem("Cerrar");
		mntmCerrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(comprobarCaja())
					mostrarFormulario(new TransCierre(Principal.this));
				else
					JOptionPane.showMessageDialog(null, "No hay ninguna caja abierta","Error",0);
			}
		});
		mntmCerrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		mnCaja.add(mntmCerrar);
		mnAdministrativo.add(mntmCobranza);
		
		
		mbSistema.add(mnSalir);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JToolBar tbSistema = new JToolBar();
		contentPane.add(tbSistema, BorderLayout.NORTH);
		
		ButtonTool btntlCliente = new ButtonTool((String) null);
		btntlCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarFormulario(new FormCliente(Principal.this));
			}
		});
		btntlCliente.setIcon(new ImageIcon(Principal.class.getResource("/img/cliente.png")));
		btntlCliente.setText("Clientes");
		tbSistema.add(btntlCliente);
		tbSistema.addSeparator();
		
		ButtonTool btntlHospedar = new ButtonTool((String) null);
		btntlHospedar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarFormulario(new TransEstadia(Principal.this));
			}
		});
		btntlHospedar.setIcon(new ImageIcon(Principal.class.getResource("/img/huesped.png")));
		btntlHospedar.setText("Hospedar");
		tbSistema.add(btntlHospedar);
		tbSistema.addSeparator();
		
		
		ButtonTool btntlCobranza = new ButtonTool((String) null);
		btntlCobranza.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(comprobarCaja())
					mostrarFormulario(new TransCobranza(Principal.this));
				else
					JOptionPane.showMessageDialog(null, "Debe abrir una caja","Error",0);
			}
		});
		btntlCobranza.setIcon(new ImageIcon(Principal.class.getResource("/img/cobrar.png")));
		btntlCobranza.setText("Cobranza");
		tbSistema.add(btntlCobranza);
		tbSistema.addSeparator();
		
		ButtonTool btntlSalir = new ButtonTool((String) null);
		btntlSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cerrar();
			}
		});
		btntlSalir.setIcon(new ImageIcon(Principal.class.getResource("/img/Close_window.png")));
		btntlSalir.setText("Salir");
		tbSistema.add(btntlSalir);
		
		
		
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
		
		
		btntlCliente.setEnabled(mntmCliente.isVisible());
		
		btntlHospedar.setEnabled(mntmHabitacin.isVisible());
		
		btntlCobranza.setEnabled(mntmCobranza.isVisible());
		
		
		setTitle("Bienbenidio "+VariableSys.user.getNombre());
	}

	
	protected boolean comprobarCaja() {
		if(VariableSys.caja == null)
			return false;
		return true;
	}


	protected void cerrar() {
		HibernateUtil.cerrar();
		System.exit(0);
	}


	private void mostrarFormulario(JDialog d){
		d.setVisible(true);
	}
}
