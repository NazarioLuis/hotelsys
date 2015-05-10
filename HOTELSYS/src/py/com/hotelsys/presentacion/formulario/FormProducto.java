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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Stock;



@SuppressWarnings("serial")
public class FormProducto extends JDialog implements AbmBotonInterface {

	

	private ProductoDao productoDao;
	private List<Producto> listaProducto;
	private CustomTable tabla;
	private Object[] fila;
	private BotonGrup abmBoton;
	private String accion = "";
	private PlaceholderTextField tDescri;
	private JTextArea tObservacin;
	private PlaceholderTextField tBuscar;
	private JPanel panel;
	private Producto producto;
	private Stock stock;
	
	private int ultimaFila;
	private Timer timer;
	private TimerTask task;
	private JRadioButton rdbtnTieneStockAnterior;
	private PlaceholderTextField tPrecio;
	private PlaceholderTextField tStock;


	/**
	 * Create the dialog.
	 */
	public FormProducto(JFrame frame) {
		super(frame);
		setTitle("Archivo de Producto");
		setBounds(100, 100, 900, 385);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		
		panel = new JCustomPanel1();
		panel.setBounds(10, 11, 388, 280);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tDescri = new PlaceholderTextField();
		tDescri.setFont(new Font("Tahoma", Font.BOLD, 11));
		tDescri.setPlaceholder("Descripci\u00F3n del Producto");
		tDescri.setBounds(25, 11, 301, 20);
		panel.add(tDescri);
		
		tObservacin = new JTextArea("");
		tObservacin.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tObservacin.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tObservacin.setRows(10);
		tObservacin.setLineWrap(true);
		tObservacin.setBounds(24, 151, 334, 77);
		panel.add(tObservacin);
		
		JLabel lblObservacin = new JLabel("Observaci\u00F3n:");
		lblObservacin.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblObservacin.setBounds(25, 135, 107, 14);
		panel.add(lblObservacin);
		
		rdbtnTieneStockAnterior = new JRadioButton("Tiene Stock anterior");
		rdbtnTieneStockAnterior.setVisible(false);
		rdbtnTieneStockAnterior.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(rdbtnTieneStockAnterior.isSelected()){
					tStock.setVisible(true);
					
				}else{
					tStock.setVisible(false);
					
				}
					
			}
		});
		rdbtnTieneStockAnterior.setBounds(25, 72, 190, 23);
		panel.add(rdbtnTieneStockAnterior);
		
		tStock = new PlaceholderTextField();
		tStock.setVisible(false);
		tStock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((e.getKeyChar()<'0' || e.getKeyChar()>'9') && e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
					e.consume();
				}
			}
		});
		
		tStock.setPlaceholder("Stock anterior");
		tStock.setFont(new Font("Tahoma", Font.BOLD, 11));
		tStock.setBounds(25, 102, 147, 20);
		panel.add(tStock);
		
		tPrecio = new PlaceholderTextField();
		tPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((e.getKeyChar()<'0' || e.getKeyChar()>'9') && e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
					e.consume();
				}
			}
		});
		
		tPrecio.setPlaceholder("Precio");
		tPrecio.setFont(new Font("Tahoma", Font.BOLD, 11));
		tPrecio.setBounds(25, 42, 133, 20);
		panel.add(tPrecio);
		
		abmBoton = new BotonGrup();
		abmBoton.setBounds(10, 302, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(408, 11, 465, 249);
		getContentPane().add(scrollPane);
		
		
		tBuscar = new PlaceholderTextField();
		tBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscar();
			}
		});
		tBuscar.setPlaceholder("Criterio de Busqueda");
		tBuscar.setBounds(523, 271, 350, 20);
		getContentPane().add(tBuscar);
		
		
		tabla = new CustomTable(new String[] {"#", "Descripcion","Stock","Costo Medio","Precio Venta"}, new int[] {5, 155,15,45,45});
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
		productoDao = new ProductoDao();
		listaProducto = productoDao.recuperaTodo();
		
		cargarGrilla();
		
		if (listaProducto.size()>0)
			accion = "DATOS";
		else
			accion = "";
		
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		tabla.vaciar();
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Producto p:listaProducto) {
			fila[0] = p.getId();
			fila[1] = p.getDescripcion();
			fila[2] = p.getStock().getCantidad();
			fila[3] = "";
			fila[4] = (int)p.getStock().getPrecio();
			
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
			productoDao = new ProductoDao();
			try {
				productoDao.eliminar((int) tabla.campo(0));
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
				inicializar();
			} catch (Exception e) {
				e.printStackTrace();
				productoDao.rollback();
				advertencia("No se puede guardar el Producto. Los campos con * son obligatorios",2);
			}
		if (accion.equals("MODIFICAR"))
			try {
				productoDao.actualizar(producto);
				inicializar();
			} catch (Exception e) {
				productoDao.rollback();
				advertencia("No se puede actualizar el Producto. Los campos con * son obligatorios",2);
			}
		
		
		
		
	}

	@Override
	public void cancelar() {
		inicializar();
	}

	@Override
	public void habilitarCampos(boolean b) {
		tDescri.setEnabled(b);
		tObservacin.setEnabled(b);
		
		
		tPrecio.setEnabled(b);
		
		tBuscar.setEnabled(!b);
		tabla.setEnabled(!b);
		
		if(b==false)
			tabla.requestFocus();
		else
			tDescri.requestFocus();
		
		if (accion=="AGREGAR")
			rdbtnTieneStockAnterior.setVisible(b);
		
	}

	//carga los atributos del objeto al ser persistido
	@Override
	public void cargarAtributos() {
		
		producto = new Producto();
		if(accion.equals("AGREGAR")){
			productoDao = new ProductoDao();
			producto.setId(productoDao.recuperMaxId()+1);
			
			stock = new Stock();
			if (!tStock.getText().equals(""))
				stock.setCantidad(Integer.parseInt(tStock.getText()));
			else
				stock.setCantidad(0);
			
			
		}else
			producto.setId((int) tabla.campo(0));	
		if (!tDescri.getText().equals(""))
			producto.setDescripcion(tDescri.getText());
		producto.setObservacion(tObservacin.getText());
		
		if (!tPrecio.getText().equals(""))
			stock.setPrecio(Integer.parseInt(tPrecio.getText()));
		else
			stock.setPrecio(0);
		
		producto.setStock(stock);
	}

	//deja la pantallaen su estado inicial
	@Override
	public void inicializar() {
		limpiarCampos();
		habilitarCampos(false);
		recuperaDatos();
		abmBoton.botones(false, accion);
		ultimaFila=-1;
		rdbtnTieneStockAnterior.setSelected(false);
		
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
			productoDao = new ProductoDao();
			producto = productoDao.recuperarPorId((int) tabla.campo(0));
			if (producto!=null) {
				tDescri.setText(producto.getDescripcion());
				tObservacin.setText(producto.getObservacion());
				tPrecio.setText((int)producto.getStock().getPrecio()+"");
				stock = producto.getStock();
				
			}
			
		}
		
	}

	@Override
	public void limpiarCampos() {
		tDescri.setText("");
		tObservacin.setText("");
		tPrecio.setText("");
		tStock.setText("");
		
	}

	@Override
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					productoDao = new ProductoDao();
					listaProducto = productoDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
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
}
