package py.com.hotelsys.presentacion.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		setExtendedState(MAXIMIZED_BOTH);
		
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnProcesos = new JMenu("Procesos");
		menuBar.add(mnProcesos);
		
		JMenuItem mntmCliente = new JMenuItem("Cliente");
		mntmCliente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormCliente fc = new FormCliente();
				fc.setVisible(true);
			}
		});
		mntmCliente.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		mnProcesos.add(mntmCliente);
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
						.addComponent(vsrfchrHoy, GroupLayout.DEFAULT_SIZE, 1338, Short.MAX_VALUE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSistemaDeGestin, GroupLayout.PREFERRED_SIZE, 409, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_fondoPrincipal.setVerticalGroup(
			gl_fondoPrincipal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_fondoPrincipal.createSequentialGroup()
					.addContainerGap()
					.addComponent(label, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSistemaDeGestin)
					.addPreferredGap(ComponentPlacement.RELATED, 517, Short.MAX_VALUE)
					.addComponent(vsrfchrHoy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		fondoPrincipal.setLayout(gl_fondoPrincipal);
	}
}
