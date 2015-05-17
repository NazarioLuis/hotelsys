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
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.NumberTextField;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Stock;
import py.com.hotelsys.util.Util;



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
	private NumberTextField tPrecio;
	private PlaceholderTextField tStock;
	private JLabel lblGs;
	private NumberTextField tCosto;
	private JLabel lblCostoDelProducto;
	private JLabel lblGs2;
	private JLabel lblPrecioVenta;


	/**
	 * Create the dialog.
	 */
	public FormProducto(JFrame frame) {
		super(frame);
		setTitle("Archivo de Producto");
		setBounds(100, 100, 900, 385);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		
		UIManager.put("Button.defaultButtonFollowsFocus", Boolean.TRUE);
		
		panel = new JCustomPanel1();
		panel.setBounds(10, 11, 388, 280);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tDescri = new PlaceholderTextField();
		tDescri.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					tPrecio.requestFocus();
				}
			}
		});
		tDescri.setFont(new Font("Tahoma", Font.BOLD, 11));
		tDescri.setPlaceholder("Descripci\u00F3n del Producto");
		tDescri.setBounds(25, 11, 301, 20);
		panel.add(tDescri);
		
		tObservacin = new JTextArea("");
		tObservacin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				abmBoton.btnGuardar.requestFocus();
			}
		});
		tObservacin.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tObservacin.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tObservacin.setRows(10);
		tObservacin.setLineWrap(true);
		tObservacin.setBounds(25, 171, 334, 77);
		panel.add(tObservacin);
		
		JLabel lblObservacin = new JLabel("Observaci\u00F3n:");
		lblObservacin.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblObservacin.setBounds(26, 155, 107, 14);
		panel.add(lblObservacin);
		
		rdbtnTieneStockAnterior = new JRadioButton("Tiene Stock anterior");
		rdbtnTieneStockAnterior.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					if(rdbtnTieneStockAnterior.isSelected())
						tStock.requestFocus();
					else
						tObservacin.requestFocus();
				}
			}
		});
		rdbtnTieneStockAnterior.setVisible(false);
		rdbtnTieneStockAnterior.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if(rdbtnTieneStockAnterior.isSelected()){
					tStock.setVisible(true);
					tCosto.setVisible(true);
					lblCostoDelProducto.setVisible(true);
					lblGs2.setVisible(true);
					
				}else{
					tStock.setVisible(false);
					tCosto.setVisible(false);
					lblCostoDelProducto.setVisible(false);
					lblGs2.setVisible(false);
				}
					
			}
		});
		rdbtnTieneStockAnterior.setBounds(25, 69, 190, 23);
		panel.add(rdbtnTieneStockAnterior);
		
		tStock = new PlaceholderTextField();
		tStock.setVisible(false);
		tStock.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validarNumero(e);
			}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					tCosto.requestFocus();
				}
			}
		});
		
		tStock.setPlaceholder("Stock anterior");
		tStock.setFont(new Font("Tahoma", Font.BOLD, 11));
		tStock.setBounds(25, 99, 147, 20);
		panel.add(tStock);
		
		tPrecio = new NumberTextField();
		tPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validarNumero(e);
			}
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER||e.getKeyCode() == KeyEvent.VK_TAB) {
					if(accion.equals("AGREGAR"))
						rdbtnTieneStockAnterior.requestFocus();
					else
						tObservacin.requestFocus();
				}
			}
		});
		
		tPrecio.setFont(new Font("Tahoma", Font.BOLD, 11));
		tPrecio.setBounds(25, 42, 107, 20);
		panel.add(tPrecio);
		
		lblGs = new JLabel("Gs.");
		lblGs.setBounds(142, 42, 40, 20);
		panel.add(lblGs);
		
		tCosto = new NumberTextField();
		tCosto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER || e.getKeyCode()==KeyEvent.VK_TAB) {
					tObservacin.requestFocus();
				}
				
			}
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validarNumero(e);
			}
		});
		tCosto.setVisible(false);
		tCosto.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCosto.setBounds(26, 133, 107, 20);
		panel.add(tCosto);
		
		lblGs2 = new JLabel("Gs.");
		lblGs2.setVisible(false);
		lblGs2.setBounds(142, 133, 40, 20);
		panel.add(lblGs2);
		
		lblCostoDelProducto = new JLabel("Costo de compra");
		lblCostoDelProducto.setVisible(false);
		lblCostoDelProducto.setBounds(25, 119, 108, 14);
		panel.add(lblCostoDelProducto);
		
		lblPrecioVenta = new JLabel("Precio Venta");
		lblPrecioVenta.setBounds(25, 29, 91, 14);
		panel.add(lblPrecioVenta);
		
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
		listaProducto = productoDao.recuperarTodo();
		
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
			fila[3] = Util.formatoDecimal(p.getStock().getCosto())+ " Gs.";;
			fila[4] = Util.formatoDecimal(p.getStock().getPrecio())+ " Gs.";
			
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
		if (comprobarVacio()) {
			cargarAtributos();
			productoDao = new ProductoDao();
			
			if(accion.equals("AGREGAR"))
				try {
					productoDao.insertar(producto);
					inicializar();
				} catch (Exception e) {
					e.printStackTrace();
					productoDao.rollback();
					advertencia("Ya existe producto "+tDescri.getText(),2);
				}
			if (accion.equals("MODIFICAR"))
				try {
					productoDao.actualizar(producto);
					inicializar();
				} catch (Exception e) {
					productoDao.rollback();
					advertencia("Ya existe producto "+tDescri.getText(),2);
				}
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
			stock.setId(producto.getId());
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
			stock.setPrecio(((Number) tPrecio.getValue()).doubleValue());
		else
			stock.setPrecio(0);
		if (!tCosto.getText().equals(""))
			stock.setCosto(((Number) tPrecio.getValue()).doubleValue());
		else
			stock.setCosto(0);
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
				tPrecio.setValue(producto.getStock().getPrecio());
				stock = producto.getStock();
				
			}
			
		}
		
	}

	@Override
	public void limpiarCampos() {
		tDescri.setText("");
		tObservacin.setText("");
		tPrecio.setValue(null);
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
					listaProducto = productoDao.recuperarPorFiltros(new String[]{tBuscar.getText()});
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
		if (tDescri.getText().isEmpty()) {
			advertencia("Debe ingresar un nombre", 2);
			tDescri.requestFocus();
			return false;
		}
		if (!tStock.getText().isEmpty()&&(tCosto.getText().isEmpty()||tCosto.getText().equals("0"))) {
			advertencia("Debe ingresar un costo", 2);
			tCosto.requestFocus();
			return false;
		}
		
		return true;
	}
}
