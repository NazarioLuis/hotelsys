package py.com.hotelsys.presentacion.formulario;

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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.NumberTextField;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.HabitacionDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Habitacion;
import py.com.hotelsys.util.Util;



@SuppressWarnings("serial")
public class FormHabitacion extends JDialog implements AbmBotonInterface {

	

	private HabitacionDao habitacionDao;
	private List<Habitacion> listaHabitacion;
	private CustomTable tabla;
	private Object[] fila;
	private BotonGrup abmBoton;
	private String accion = "";
	private PlaceholderTextField tDescripcion;
	private NumberTextField tPrecio;
	private JTextArea tObservacion;
	private PlaceholderTextField tBuscar;
	private JPanel panel;
	private Habitacion habitacion;
	private JLabel label;
	private Timer timer;
	private TimerTask task;
	private int ultimaFila;
	private JLabel lblGs;


	/**
	 * Create the dialog.
	 */
	public FormHabitacion(JFrame frame) {
		super(frame);
		setTitle("Archivo de Habitacion");
		setBounds(100, 100, 900, 410);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		panel = new JCustomPanel1();
		panel.setBounds(10, 11, 388, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tDescripcion = new PlaceholderTextField();
		tDescripcion.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					tPrecio.requestFocus();
				}
			}
		});
		tDescripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		tDescripcion.setPlaceholder("Descripcion Habitacion");
		tDescripcion.setBounds(25, 40, 301, 20);
		panel.add(tDescripcion);
		
		tPrecio = new NumberTextField();
		tPrecio.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					tObservacion.requestFocus();
				}
			}
		});
		tPrecio.setFont(new Font("Tahoma", Font.BOLD, 11));
		tPrecio.setBounds(25, 71, 100, 20);
		panel.add(tPrecio);
		
		tObservacion = new JTextArea("");
		tObservacion.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					abmBoton.btnGuardar.requestFocus();
				}
			}
		});
		tObservacion.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tObservacion.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tObservacion.setRows(10);
		tObservacion.setLineWrap(true);
		tObservacion.setBounds(25, 135, 334, 77);
		panel.add(tObservacion);
		
		label = new JLabel("Observaci\u00F3n:");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(25, 119, 107, 14);
		panel.add(label);
		
		lblGs = new JLabel("Gs.");
		lblGs.setBounds(135, 74, 46, 14);
		panel.add(lblGs);
		
		abmBoton = new BotonGrup();
		abmBoton.setBounds(10, 324, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(399, 11, 462, 277);
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
		
		
		tabla = new CustomTable(new String[] {"#", "Habitacion", "Precio"}, new int[] {5, 190, 30});
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

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		habitacionDao = new HabitacionDao();
		listaHabitacion = habitacionDao.recuperarTodo();
		
		cargarGrilla();
		if (listaHabitacion.size()>0)
			accion = "DATOS";
		else
			accion = "";
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		
		tabla.vaciar();
		
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Habitacion h:listaHabitacion) {
			fila[0] = h.getId();
			fila[1] = h.getDescripcion();
			fila[2] = Util.formatoDecimal(h.getPrecio())+" Gs.";
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
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el Habitacion: "+tabla.campo(1)+"?","Atenci�n",JOptionPane.YES_NO_OPTION);
		if (si==JOptionPane.YES_OPTION) {
			habitacionDao = new HabitacionDao();
			try {
				habitacionDao.eliminar((int) tabla.campo(0));
			} catch (Exception e) {
				e.printStackTrace();
				habitacionDao.rollback();
				advertencia("No se puede eliminar el Habitacion "+tabla.campo(1)+". Esta en uso!",2);
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
			habitacionDao = new HabitacionDao();
			
			if(accion.equals("AGREGAR"))
				try {
					habitacionDao.insertar(habitacion);
					inicializar();
				} catch (Exception e) {
					habitacionDao.rollback();
					advertencia("Ya existe habitacion con este nombre",2);
				}
			if (accion.equals("MODIFICAR"))
				try {
					habitacionDao.actualizar(habitacion);
					inicializar();
				} catch (Exception e) {
					habitacionDao.rollback();
					advertencia("Ya existe habitacion con este nombre",2);
				}
		}
				
		
	}

	@Override
	public void cancelar() {
		inicializar();
	}

	@Override
	public void habilitarCampos(boolean b) {
		tDescripcion.setEnabled(b);
		tPrecio.setEnabled(b);
		tObservacion.setEnabled(b);
		tBuscar.setEnabled(!b);
		tabla.setEnabled(!b);
		if(b==false)
			tabla.requestFocus();
		else
			tDescripcion.requestFocus();
	}

	//carga los atributos del objeto al ser persistido
	@Override
	public void cargarAtributos() {
		
		habitacion = new Habitacion();
		if(accion.equals("AGREGAR")){
			habitacionDao = new HabitacionDao();
			habitacion.setId(habitacionDao.recuperMaxId()+1);
		}else
			habitacion.setId((int) tabla.campo(0));
		habitacion.setDescripcion(tDescripcion.getText());
		habitacion.setPrecio(((Number)tPrecio.getValue()).doubleValue());
		habitacion.setObservacion(tObservacion.getText());
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
		JOptionPane.showMessageDialog(null, texto, "Atenci�n", t);
	}

	@Override
	public void cargarFormulario() {
		if(tabla.getRowCount()==1)
			ultimaFila = -1;
		if (tabla.getSelectedRow()>=0 && tabla.getSelectedRow()!=ultimaFila) {
			ultimaFila=tabla.getSelectedRow();
			abmBoton.botones(false, accion);
			habitacionDao = new HabitacionDao();
			habitacion = habitacionDao.recuperarPorId((int) tabla.campo(0));
			if (habitacion!=null) {
				tDescripcion.setText(habitacion.getDescripcion());
				tPrecio.setValue(habitacion.getPrecio());
				tObservacion.setText(habitacion.getObservacion());
			}
			
		}
		
	}

	@Override
	public void limpiarCampos() {
		tDescripcion.setText("");
		tPrecio.setValue(null);
		tObservacion.setText("");
	}

	@Override
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					habitacionDao = new HabitacionDao();
					listaHabitacion = habitacionDao.recuperarPorFiltros(new String[]{tBuscar.getText()});
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
		if (tDescripcion.getText().isEmpty()) {
			advertencia("Debe ingresar un monto", 2);
			tDescripcion.requestFocus();
			return false;
		}
		if (tPrecio.getText().isEmpty()) {
			advertencia("Debe ingresar un fecha", 2);
			tPrecio.requestFocus();
			return false;
		}
		
		return true;
	}
	
	
	
}
