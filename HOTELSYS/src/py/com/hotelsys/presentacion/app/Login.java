package py.com.hotelsys.presentacion.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AbstractDocument;

import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.CajaDao;
import py.com.hotelsys.dao.UsuarioDao;
import py.com.hotelsys.util.HibernateUtil;
import py.com.hotelsys.util.UppercaseDocumentFilter;
import py.com.hotelsys.util.Util;
import py.com.hotelsys.util.VariableSys;

@SuppressWarnings("serial")
public class Login extends JFrame {
	
	private final JPanel contentPanel = new JCustomPanel1();
	private JPasswordField tPass;
	private py.com.hotelsys.dao.UsuarioDao usuarioDao;
	private PlaceholderTextField tAlias;
	private JLabel lblError1;
	private JLabel lblError2;
	
	private JButton btnEntrar;


	/**
	 * Create the dialog.
	 */
	public Login() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				HibernateUtil.cerrar();
			}
		});
		setTitle("LOGIN");
		setBounds(100, 100, 309, 201);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/logo.gif")));
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		
		tAlias = new PlaceholderTextField();
		tAlias.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.comprobarAlfanumerico(e, lblError1);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					tPass.requestFocus();
				}
			}
		});
		tAlias.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tAlias.setPlaceholder("ALIAS");
		tAlias.setBounds(63, 46, 185, 27);
		contentPanel.add(tAlias);
		setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
		((AbstractDocument) tAlias.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		
		tPass = new JPasswordField();
		tPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.comprobarEspacio(e, lblError2);
			}
			@Override
			public void keyPressed(KeyEvent e) {
				
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					btnEntrar.requestFocus();
				}
			}
		});
		
		JButton button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				tPass.setEchoChar((char)0);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				tPass.setEchoChar('*');
				tPass.requestFocus();
			}
		});
		button.setIcon(new ImageIcon(Login.class.getResource("/img/ver.png")));
		button.setBounds(218, 79, 30, 26);
		contentPanel.add(button);
		tPass.setBounds(63, 79, 155, 27);
		contentPanel.add(tPass);
		((AbstractDocument) tPass.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		
		JLabel lblBienvenidoAHosys = new JLabel("Bienvenido a HoSys");
		lblBienvenidoAHosys.setForeground(new Color(255, 255, 240));
		lblBienvenidoAHosys.setOpaque(true);
		lblBienvenidoAHosys.setBackground(new Color(95, 158, 160));
		lblBienvenidoAHosys.setFont(new Font("Swis721 Hv BT", Font.ITALIC, 17));
		lblBienvenidoAHosys.setHorizontalAlignment(SwingConstants.CENTER);
		lblBienvenidoAHosys.setBounds(10, 11, 273, 30);
		contentPanel.add(lblBienvenidoAHosys);
		
		btnEntrar = new JButton("Entrar");
		btnEntrar.setBackground(new Color(95, 158, 160));
		btnEntrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				login();
			}
		});
		btnEntrar.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEntrar.setIcon(new ImageIcon(Login.class.getResource("/img/Login_in.png")));
		btnEntrar.setBounds(112, 117, 111, 36);
		contentPanel.add(btnEntrar);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Login.class.getResource("/img/User_Avatar-32.png")));
		label.setBounds(32, 39, 32, 34);
		contentPanel.add(label);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Login.class.getResource("/img/password_lock-32.png")));
		label_1.setBounds(32, 72, 32, 34);
		contentPanel.add(label_1);
		
		lblError1 = new JLabel("");
		lblError1.setVisible(false);
		lblError1.setIcon(new ImageIcon(Login.class.getResource("/img/error.png")));
		lblError1.setBounds(258, 46, 25, 27);
		contentPanel.add(lblError1);
		
		lblError2 = new JLabel("");
		lblError2.setVisible(false);
		lblError2.setIcon(new ImageIcon(Login.class.getResource("/img/error.png")));
		lblError2.setBounds(258, 79, 25, 27);
		contentPanel.add(lblError2);
	}

	private void login() {
		if (comprobarVacio()) {
			if (VariableSys.isSUPERUSER(tAlias.getText(), Util.charAString(tPass.getPassword()))) 
				entrar();
			else{
				usuarioDao = new UsuarioDao();
				VariableSys.user = usuarioDao.logerUsuario(tAlias.getText(),Util.charAString(tPass.getPassword()));
				if (VariableSys.user==null) {
					advertencia("Usuario o Contraseña incorecta", 0);
				}else{
					entrar();
					CajaDao cajaDao = new CajaDao();
					VariableSys.caja = cajaDao.cajaPorUsuario(VariableSys.user);
				}
			}
		}
		
	}

	private void entrar() {
		dispose();
		Principal p = new Principal();
		p.setVisible(true);
	}

	private boolean comprobarVacio() {
		if (tAlias.getText().isEmpty()) {
			advertencia("Debe ingresar el alias", 2);
			tAlias.requestFocus();
			return false;
		}
		if (tPass.getPassword().length==0) {
			advertencia("Debe ingresar la contraseña", 2);
			tPass.requestFocus();
			return false;
		}
		return true;
	}
	
	
	
	
	public void advertencia(String texto,int t) {
		JOptionPane.showMessageDialog(null, texto, "Atención", t);
	}
}
