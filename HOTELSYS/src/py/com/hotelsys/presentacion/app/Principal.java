package py.com.hotelsys.presentacion.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
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
import py.com.hotelsys.presentacion.formulario.FormProducto;
import py.com.hotelsys.presentacion.formulario.FormProveedor;
import py.com.hotelsys.util.HibernateUtil;

@SuppressWarnings("serial")
public class Principal extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HibernateUtil.buildIfNeeded();
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
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
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnGral = new JMenu("General");
		menuBar.add(mnGral);
		
		JMenuItem mntmCliente = new JMenuItem("Cliente");
		mntmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormCliente();
			}
		});
		mntmCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, 0));
		mnGral.add(mntmCliente);
		
		JMenuItem mntmServicio = new JMenuItem("Servicio");
		mntmServicio.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, 0));
		mnGral.add(mntmServicio);
		
		JMenuItem mntmMoneda = new JMenuItem("Moneda");
		mntmMoneda.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, 0));
		mnGral.add(mntmMoneda);
		
		JMenu mnEstada = new JMenu("Estad\u00EDa");
		menuBar.add(mnEstada);
		
		JMenuItem mntmHabitacin = new JMenuItem("Habitaci\u00F3n");
		mnEstada.add(mntmHabitacin);
		mntmHabitacin.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, 0));
		
		JMenuItem mntmReservar = new JMenuItem("Reservar");
		mntmReservar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, 0));
		mnEstada.add(mntmReservar);
		
		JMenuItem mntmRegistrar = new JMenuItem("Hospedar");
		mntmRegistrar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, 0));
		mnEstada.add(mntmRegistrar);
		
		JMenuItem mntmGenerarInformes = new JMenuItem("Generar Informe");
		mntmGenerarInformes.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, InputEvent.ALT_MASK));
		mnEstada.add(mntmGenerarInformes);
		
		JMenu mnStock = new JMenu("Stock");
		menuBar.add(mnStock);
		
		JMenuItem mntmProductos = new JMenuItem("Producto");
		mnStock.add(mntmProductos);
		mntmProductos.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, 0));
		mntmProductos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormProducto();
			}
		});
		
		JMenuItem mntmAjusteDeStock = new JMenuItem("Importar Stock");
		mntmAjusteDeStock.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_K, 0));
		mnStock.add(mntmAjusteDeStock);
		
		JMenuItem mntmImprimirInforme = new JMenuItem("Imprimir Informe");
		mntmImprimirInforme.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.ALT_MASK));
		mnStock.add(mntmImprimirInforme);
		
		JMenu mnCompras = new JMenu("Compras");
		menuBar.add(mnCompras);
		
		JMenuItem mntmProveedor = new JMenuItem("Proveedor");
		mnCompras.add(mntmProveedor);
		mntmProveedor.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, 0));
		mntmProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormProveedor();
			}
		});
		
		JMenuItem mntmRegistrar_1 = new JMenuItem("Registrar Compra");
		mntmRegistrar_1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, 0));
		mnCompras.add(mntmRegistrar_1);
		
		JMenuItem mntmGenerarInforme = new JMenuItem("Generar Informe");
		mntmGenerarInforme.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.ALT_MASK));
		mnCompras.add(mntmGenerarInforme);
		
		JMenu mnSalir = new JMenu("Salir");
		mnSalir.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				HibernateUtil.cerrar();
				System.exit(0);
			}
		});
		menuBar.add(mnSalir);
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
	}

	private void verFormCliente() {
		FormCliente fc = new FormCliente(this);
		fc.setVisible(true);
	}
	private void verFormProveedor() {
		FormProveedor fp = new FormProveedor(this);
		fp.setVisible(true);
	}
	private void verFormProducto() {
		FormProducto fp = new FormProducto(this);
		fp.setVisible(true);
	}
}
