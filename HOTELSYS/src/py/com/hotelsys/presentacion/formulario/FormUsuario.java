package py.com.hotelsys.presentacion.formulario;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AbstractDocument;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.RolDao;
import py.com.hotelsys.dao.UsuarioDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.interfaces.InterfaceRol;
import py.com.hotelsys.modelo.Rol;
import py.com.hotelsys.modelo.Usuario;
import py.com.hotelsys.util.JPass;
import py.com.hotelsys.util.UppercaseDocumentFilter;
import py.com.hotelsys.util.Util;



@SuppressWarnings("serial")
public class FormUsuario extends JDialog implements AbmBotonInterface,InterfaceRol{

	

	private UsuarioDao usuarioDao;
	private List<Usuario> listUsuario;
	private CustomTable tabla;
	private Object[] fila;
	private BotonGrup abmBoton;
	private String accion = "";
	private PlaceholderTextField tNombre;
	private PlaceholderTextField tAlias;
	private PlaceholderTextField tfBuscar;
	private JPanel panel;
	private Usuario usuario;
	private Timer timer;
	private TimerTask task;
	private int ultimaFila;
	private JComboBox<Object> cbRol;
	private RolDao rolDao;
	private List<Rol> lisRol;
	private JPasswordField tPass;
	private JLabel lblCaracterIncorrecto;
	private JLabel lblCaracterIncorrecto_1;


	/**
	 * Create the dialog.
	 */
	public FormUsuario(JFrame frame) {
		super(frame);
		setTitle("Archivo de Usuario");
		setBounds(100, 100, 900, 323);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		
		panel = new JCustomPanel1();
		panel.setBounds(10, 11, 388, 195);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tNombre = new PlaceholderTextField();
		tNombre.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					tAlias.requestFocus();
				}
			}
		});
		tNombre.setFont(new Font("Tahoma", Font.BOLD, 11));
		tNombre.setPlaceholder("Nombre");
		tNombre.setBounds(76, 40, 248, 20);
		panel.add(tNombre);
		
		
		
		tAlias = new PlaceholderTextField();
		tAlias.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.comprobarAlfanumerico(e, lblCaracterIncorrecto);
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					cbRol.requestFocus();
				}
			}
		});
		tAlias.setFont(new Font("Tahoma", Font.BOLD, 11));
		tAlias.setPlaceholder("Alias");
		tAlias.setBounds(76, 71, 133, 20);
		panel.add(tAlias);
		((AbstractDocument) tAlias.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		
		cbRol = new JComboBox<Object>();
		cbRol.setForeground(Color.BLACK);
		cbRol.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					tPass.requestFocus();
				}
			}
		});
		cbRol.setBounds(76, 102, 133, 20);
		
		panel.add(cbRol);
		cargarRol();
		UIManager.put("ComboBox.disabledForeground", Color.gray);
		
		JLabel lblNombre = new JLabel("Nombre");
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setBounds(10, 43, 62, 17);
		panel.add(lblNombre);
		
		JLabel lblAlias = new JLabel("Alias");
		lblAlias.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAlias.setBounds(10, 74, 62, 17);
		panel.add(lblAlias);
		
		JLabel lblRol = new JLabel("Rol");
		lblRol.setHorizontalAlignment(SwingConstants.RIGHT);
		lblRol.setBounds(10, 105, 62, 17);
		panel.add(lblRol);
		
		JLabel lblSea = new JLabel("Se\u00F1a");
		lblSea.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSea.setBounds(10, 136, 62, 17);
		panel.add(lblSea);
		
		tPass = new JPasswordField();
		tPass.setDisabledTextColor(Color.GRAY);
		tPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.comprobarAlfanumerico(e, lblCaracterIncorrecto_1);
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					abmBoton.btnGuardar.requestFocus();
				}
			}
		});
		tPass.setBounds(76, 133, 133, 20);
		panel.add(tPass);
		((AbstractDocument) tPass.getDocument()).setDocumentFilter(new UppercaseDocumentFilter());
		
		JButton btnAddRol = new JButton("");
		btnAddRol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				verFormularioRol();
			}
		});
		btnAddRol.setIcon(new ImageIcon(FormUsuario.class.getResource("/img/mas.png")));
		btnAddRol.setBounds(211, 99, 30, 26);
		panel.add(btnAddRol);
		
		lblCaracterIncorrecto = new JLabel("Caracter Incorrecto");
		lblCaracterIncorrecto.setVisible(false);
		lblCaracterIncorrecto.setIcon(new ImageIcon(FormUsuario.class.getResource("/img/error.png")));
		lblCaracterIncorrecto.setForeground(Color.RED);
		lblCaracterIncorrecto.setHorizontalAlignment(SwingConstants.LEFT);
		lblCaracterIncorrecto.setBounds(211, 71, 146, 20);
		panel.add(lblCaracterIncorrecto);
		
		lblCaracterIncorrecto_1 = new JLabel("Caracter Incorrecto");
		lblCaracterIncorrecto_1.setVisible(false);
		lblCaracterIncorrecto_1.setIcon(new ImageIcon(FormUsuario.class.getResource("/img/error.png")));
		lblCaracterIncorrecto_1.setHorizontalAlignment(SwingConstants.LEFT);
		lblCaracterIncorrecto_1.setForeground(Color.RED);
		lblCaracterIncorrecto_1.setBounds(247, 133, 131, 20);
		panel.add(lblCaracterIncorrecto_1);
		
		JButton button = new JButton("");
		
		button.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				tPass.setEchoChar((char)0);
			}
			@Override
			public void mouseReleased(MouseEvent arg0) {
				tPass.setEchoChar('*');
			}
		});
		button.setIcon(new ImageIcon(FormUsuario.class.getResource("/img/ver.png")));
		button.setBounds(211, 130, 30, 26);
		panel.add(button);
		
		abmBoton = new BotonGrup();
		abmBoton.setBounds(0, 248, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(399, 11, 462, 195);
		getContentPane().add(scrollPane);
		
		
		tfBuscar = new PlaceholderTextField();
		tfBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscar();
			}
		});
		tfBuscar.setPlaceholder("Criterio de Busqueda");
		tfBuscar.setBounds(511, 217, 350, 20);
		getContentPane().add(tfBuscar);
		
		
		tabla = new CustomTable(new String[] {"#", "Nombre", "Alias"}, new int[] {5, 190, 30});
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
		inicializar();		
	}
	private void verFormularioRol() {
		FormRol fr = new FormRol(this);
		fr.setIr(this);
		fr.setVisible(true);
	}
	@Override
	public void cargarRol() {
		rolDao = new RolDao();
		lisRol = rolDao.recuperarTodo();
		cbRol.setModel(new DefaultComboBoxModel<>());
		for (Rol r:lisRol) {
			cbRol.addItem(r.getDescri());
		}
		
	}

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		usuarioDao = new UsuarioDao();
		listUsuario = usuarioDao.recuperarTodo();
		
		cargarGrilla();
		if (listUsuario.size()>0)
			accion = "DATOS";
		else
			accion = "";
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		
		tabla.vaciar();
		
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Usuario u : listUsuario) {
			fila[0] = u.getId();
			fila[1] = u.getNombre();
			fila[2] = u.getAlias();
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
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el Usuario: "+tabla.campo(1)+"?","Atención",JOptionPane.YES_NO_OPTION);
		if (si==JOptionPane.YES_OPTION) {
			usuarioDao = new UsuarioDao();
			try {
				usuarioDao.eliminar((int)tabla.campo(0));
	
			} catch (Exception e) {
				e.printStackTrace();
				usuarioDao.rollback();
				advertencia("No se puede eliminar el Usuario "+tabla.campo(1)+". Esta en uso!",2);
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
		if (comprobarVacio()) {
			cargarAtributos();
			usuarioDao = new UsuarioDao();
			
			if(accion.equals("AGREGAR"))
				try {
					usuarioDao.insertar(usuario);
					inicializar();
				} catch (Exception e) {
					e.printStackTrace();
					usuarioDao.rollback();
					advertencia("Ya existe un usuario con el alias "+tAlias.getText(),2);
				}
			if (accion.equals("MODIFICAR"))
				try {
					usuarioDao.actualizar(usuario);
					inicializar();
				} catch (Exception e) {
					usuarioDao.rollback();
					advertencia("Ya existe un usuario con el alias "+tAlias.getText(),2);
				}
			
			
		}
		
		
		
		
	}

	@Override
	public void cancelar() {
		inicializar();
	}

	@Override
	public void habilitarCampos(boolean b) {
		tNombre.setEnabled(b);
		tAlias.setEnabled(b);
		tPass.setEnabled(b);
		cbRol.setEnabled(b);
		tfBuscar.setEnabled(!b);
		tabla.setEnabled(!b);
		if(b==false)
			tabla.requestFocus();
		else
			tNombre.requestFocus();
	}

	//carga los atributos del objeto al ser persistido
	@Override
	public void cargarAtributos() {
		
		usuario = new Usuario();
		if(accion.equals("AGREGAR")){
			usuarioDao = new UsuarioDao();
			usuario.setId(usuarioDao.recuperMaxId()+1);
		}else
			usuario.setId((int) tabla.campo(0));
		
		usuario.setNombre(tNombre.getText());
		usuario.setAlias(tAlias.getText());
		if (tPass.getPassword().length>0) {
			System.out.println(tPass.getPassword().length);
			JPass.encrypt(Util.charAString(tPass.getPassword()));
			usuario.setPass(JPass.getEncryptedString());
		}
				
		usuario.setRol(lisRol.get(cbRol.getSelectedIndex()));
		
	}

	//deja la pantallaen su estado inicial
	@Override
	public void inicializar() {
		limpiarCampos();
		habilitarCampos(false);
		recuperaDatos();
		abmBoton.botones(false, accion);
		ultimaFila = -1;
	}

	
	//emite mensajes de forma dinamica de acuerdo al texto que se le envie
	@Override
	public void advertencia(String texto,int t) {
		JOptionPane.showMessageDialog(null, texto, "Atención", t);
	}

	@Override
	public void cargarFormulario() {
		if(tabla.getRowCount()==1)
			ultimaFila = -1;
		if (tabla.getSelectedRow()>=0 && tabla.getSelectedRow()!=ultimaFila) {
			ultimaFila=tabla.getSelectedRow();
			abmBoton.botones(false, accion);
			usuarioDao = new UsuarioDao();
			usuario = usuarioDao.recuperarPorId((int) tabla.campo(0));
			if (usuario!=null) {
				tNombre.setText(usuario.getNombre());
				tAlias.setText(usuario.getAlias());
				cbRol.setSelectedItem(usuario.getRol().getDescri());
				JPass.decrypt(usuario.getPass());
				tPass.setText(JPass.getDecryptedString());
			}
			
		}
		
	}

	@Override
	public void limpiarCampos() {
		tNombre.setText("");
		tAlias.setText("");
		if (lisRol.size()>0) 
			cbRol.setSelectedIndex(0);
		tPass.setText("");
	}

	
	
	
	@Override
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					usuarioDao = new UsuarioDao();
					listUsuario = usuarioDao.recuperarPorFiltros(new String[]{tfBuscar.getText()});
					cargarGrilla();
					timer.cancel();
					timer=null;
					task=null;
				}
			};
						
			timer.schedule(task, 1000);
		}
		
	}

	@Override
	public void cargarFormularioProducto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarAtributosProductos() {
		// TODO Auto-generated method stub
		
	}
	
	private boolean comprobarVacio() {
		if (tNombre.getText().isEmpty()) {
			advertencia("Debe ingresar un nombre", 2);
			tNombre.requestFocus();
			return false;
		}
		if (tAlias.getText().isEmpty()) {
			advertencia("Debe ingresar un alias", 2);
			tAlias.requestFocus();
			return false;
		}
		if(accion.equals("AGREGAR")){
			if (tPass.getPassword().length==0) {
				advertencia("Debe ingresar una contraseña", 2);
				tPass.requestFocus();
				return false;
			}
		}
		
		return true;
	}
}
