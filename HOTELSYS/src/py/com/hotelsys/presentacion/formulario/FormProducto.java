package py.com.hotelsys.presentacion.formulario;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Producto;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;



@SuppressWarnings("serial")
public class FormProducto extends JDialog implements AbmBotonInterface {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormProducto dialog = new FormProducto();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private ProductoDao productoDao;
	private List<Producto> listaCliente;
	private CustomTable tabla;
	private Object[] fila;
	private BotonGrup abmBoton;
	private String accion = "";
	private PlaceholderTextField tDescri;
	private JTextArea tObservacin;
	private PlaceholderTextField tBuscar;
	private JPanel panel;
	private Producto producto;
	private int ultimaFila;
	private Timer timer;
	private TimerTask task;


	/**
	 * Create the dialog.
	 */
	public FormProducto() {
		setTitle("Archivo de Producto");
		setBounds(100, 100, 900, 356);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(10, 11, 388, 225);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tDescri = new PlaceholderTextField();
		tDescri.setFont(new Font("Tahoma", Font.BOLD, 11));
		tDescri.setPlaceholder("Descripci\u00F3n del Producto");
		tDescri.setBounds(25, 24, 301, 20);
		panel.add(tDescri);
		
		tObservacin = new JTextArea("");
		tObservacin.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tObservacin.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tObservacin.setRows(10);
		tObservacin.setLineWrap(true);
		tObservacin.setBounds(25, 78, 334, 77);
		panel.add(tObservacin);
		
		JLabel lblObservacin = new JLabel("Observaci\u00F3n:");
		lblObservacin.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblObservacin.setBounds(24, 62, 107, 14);
		panel.add(lblObservacin);
		
		abmBoton = new BotonGrup();
		abmBoton.setBounds(10, 281, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(408, 11, 465, 225);
		getContentPane().add(scrollPane);
		
		
		tBuscar = new PlaceholderTextField();
		tBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscar();
			}
		});
		tBuscar.setPlaceholder("Criterio de Busqueda");
		tBuscar.setBounds(524, 247, 350, 20);
		getContentPane().add(tBuscar);
		
		
		tabla = new CustomTable(new String[] {"#", "Descripcion"}, new int[] {10, 250});
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
		habilitarCampos(false);
		recuperaDatos();		
	}

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		productoDao = new ProductoDao();
		listaCliente = productoDao.recuperaTodo();
		
		cargarGrilla();
		
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		tabla.vaciar();
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Producto p:listaCliente) {
			fila[0] = p.getId();
			fila[1] = p.getDescripcion();
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
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el Producto: "+tabla.campo(1)+"?","Atención",JOptionPane.YES_NO_OPTION);
		if (si==JOptionPane.YES_OPTION) {
			producto = new Producto();
			productoDao = new ProductoDao();
			producto.setId((int) tabla.campo(0));
			try {
				productoDao.eliminar(producto);
			} catch (Exception e) {
				productoDao.rollback();
				advertencia("No se eliminar el Producto "+tabla.campo(1)+". Esta en uso!",2);
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
		cargarAtributos();
		productoDao = new ProductoDao();
		
		if(accion.equals("AGREGAR"))
			try {
				productoDao.insertar(producto);
			} catch (Exception e) {
				productoDao.rollback();
				advertencia("No se puede guardar el Producto. Los campos con * son obligatorios",2);
			}
		if (accion.equals("MODIFICAR"))
			try {
				productoDao.actualizar(producto);
			} catch (Exception e) {
				productoDao.rollback();
				advertencia("No se puede actualizar el Producto. Los campos con * son obligatorios",2);
			}
		
		
		inicializar();
		
	}

	@Override
	public void cancelar() {
		inicializar();
	}

	@Override
	public void habilitarCampos(boolean b) {
		tDescri.setEnabled(b);
		tObservacin.setEnabled(b);
		tBuscar.setEnabled(!b);
		tabla.setEnabled(!b);
		if(b==false)
			tabla.requestFocus();
		else
			tDescri.requestFocus();
	}

	//carga los atributos del objeto al ser persistido
	@Override
	public void cargarAtributos() {
		
		producto = new Producto();
		if(accion.equals("AGREGAR")){
			productoDao = new ProductoDao();
			producto.setId(productoDao.recuperMaxId()+1);
		}else
			producto.setId((int) tabla.campo(0));		
		producto.setDescripcion(tDescri.getText());
		producto.setObservacion(tObservacin.getText());
	}

	//deja la pantallaen su estado inicial
	@Override
	public void inicializar() {
		accion = "";
		limpiarCampos();
		habilitarCampos(false);
		recuperaDatos();
		abmBoton.botones(false, accion);
			
	}

	
	//emite mensajes de forma dinamica de acuerdo al texto que se le envie
	@Override
	public void advertencia(String texto,int t) {
		JOptionPane.showMessageDialog(null, texto, "Atención", t);
	}

	@Override
	public void cargarFormulario() {
		if (tabla.getSelectedRow()>=0 && tabla.getSelectedRow()!=ultimaFila) {
			ultimaFila=tabla.getSelectedRow();
			accion = "DATOS";
			abmBoton.botones(false, accion);
			productoDao = new ProductoDao();
			producto = productoDao.recuperarPorId((int) tabla.campo(0));
			if (producto!=null) {
				tDescri.setText(producto.getDescripcion());
				
				tObservacin.setText(producto.getObservacion());
			}
			
		}
		
	}

	@Override
	public void limpiarCampos() {
		tDescri.setText("");
		tObservacin.setText("");
	}

	@Override
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					productoDao = new ProductoDao();
					listaCliente = productoDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
					cargarGrilla();
					timer.cancel();
					timer=null;
					task=null;
				}
			};
						
			timer.schedule(task, 1000);		
		}
		
	}
}
