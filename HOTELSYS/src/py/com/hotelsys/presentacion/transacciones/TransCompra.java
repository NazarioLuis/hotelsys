package py.com.hotelsys.presentacion.transacciones;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.JCustomPanel2;
import py.com.hotelsys.componentes.NumberTextField;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.CompraDao;
import py.com.hotelsys.dao.CompraItemDao;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaProducto;
import py.com.hotelsys.interfaces.InterfaceBusquedaProveedor;
import py.com.hotelsys.interfaces.TranBotonInterface;
import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.modelo.CompraItem;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Proveedor;
import py.com.hotelsys.presentacion.buscadores.BusqudaProducto;
import py.com.hotelsys.presentacion.buscadores.BusqudaProveedor;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.Util;

@SuppressWarnings("serial")
public class TransCompra extends JDialog 
	implements InterfaceBusquedaProveedor,InterfaceBusquedaProducto{

	
	private JTextField tfId;
	private JFormattedTextField tfFactura;
	
	private Double total=0.0;
	private JFormattedTextField tfFecha;
	private JLabel lblFechaDeCompra;
	private CompraDao compraDao;
	private PlaceholderTextField tfIdProveedor;
	private PlaceholderTextField tfProveedor;
	private CustomTable table;
	private PlaceholderTextField tfProducto;
	private PlaceholderTextField tfRuc;
	private PlaceholderTextField tfIdProducto;
	private JButton btnBuscarProducto;
	private NumberTextField tCosto;
	private NumberTextField tTotal;
	private ProductoDao productoDao;
	private Proveedor proveegor;
	private Producto p;
	private Object[] object;
	private JButton btnEliminar;
	private PlaceholderTextField tCantidad;
	private CompraItemDao compraItemDao;
	private JLabel lblTimbrado_1;
	private JFormattedTextField tfVencimientoTimbrado;
	private JTextField tfTimbrado;
	private Compra compra;
	private CompraItem compraItem;
	private List<CompraItem> compraItems = new ArrayList<>();
	private JPanel panelGeneral;
	private JPanel panelProveedor;
	private JPanel panelProducto;
	private String accion;
	private JLabel lblCant;
	private JLabel lblPrecioCompra;
	private TranBotonInterface tbi;
	private JButton btnBuscarProveedor;
	

	public void setTbi(TranBotonInterface tbi) {
		this.tbi = tbi;
	}

	/**
	 * Create the dialog.
	 * @param accion 
	 * @param  
	 */
	public TransCompra(JDialog d, Compra c, String accion) {
		super(d);
		setTitle("Compra");
		this.accion = accion;
		this.compra = c;
		
		setBounds(100, 100, 837, 471);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		
		
		
		
		panelProveedor = new JCustomPanel1();
		panelProveedor.setBounds(451, 19, 348, 80);
		getContentPane().add(panelProveedor);
		panelProveedor.setLayout(null);
		
		tfProveedor = new PlaceholderTextField();
		tfProveedor.setEnabled(false);
		tfProveedor.setBounds(90, 11, 237, 20);
		panelProveedor.add(tfProveedor);
		tfProveedor.setPlaceholder("Proveedor");
		
		tfIdProveedor = new PlaceholderTextField();
		tfIdProveedor.setEnabled(false);
		
		tfIdProveedor.setBounds(21, 11, 38, 20);
		panelProveedor.add(tfIdProveedor);
		tfIdProveedor.setPlaceholder("Id");
		
		btnBuscarProveedor = new JButton("");
		btnBuscarProveedor.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428455167_698838-icon-111-search-16.png")));
		btnBuscarProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarProveedor();
			}
		});
		btnBuscarProveedor.setBounds(56, 11, 31, 20);
		panelProveedor.add(btnBuscarProveedor);
		
		tfRuc = new PlaceholderTextField();
		tfRuc.setEnabled(false);
		tfRuc.setBounds(21, 49, 164, 20);
		panelProveedor.add(tfRuc);
		tfRuc.setPlaceholder("RUC");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 176, 747, 193);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Producto", "Cantidad", "Costo Unitario", "Subtotal compra"}, new int[] {30, 200, 60,  90, 90});
		scrollPane.setViewportView(table);
		
		tTotal = new NumberTextField();
		tTotal.setText("0");
		tTotal.setEnabled(false);
		tTotal.setBounds(615, 369, 152, 20);
		getContentPane().add(tTotal);
		
		panelGeneral =new JCustomPanel1();
		panelGeneral.setBounds(20, 19, 403, 99);
		getContentPane().add(panelGeneral);
		panelGeneral.setLayout(null);
		tfFecha = new JFormattedTextField(Util.formatoFecha());
		tfFecha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					tfFactura.requestFocus();
				}
			}
		});
		tfFecha.setBounds(305, 11, 79, 20);
		panelGeneral.add(tfFecha);
		
		lblFechaDeCompra = new JLabel("Fecha de Compra");
		lblFechaDeCompra.setBounds(195, 14, 100, 14);
		panelGeneral.add(lblFechaDeCompra);
		lblFechaDeCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfId = new JTextField();
		tfId.setEnabled(false);
		tfId.setBounds(89, 11, 66, 20);
		panelGeneral.add(tfId);
		tfId.setColumns(10);
		
		JLabel lblNroDeCompra = new JLabel("N\u00B0 de Compra");
		lblNroDeCompra.setBounds(0, 14, 86, 14);
		panelGeneral.add(lblNroDeCompra);
		lblNroDeCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		tfFactura = new JFormattedTextField(Util.formatoFactura());
		tfFactura.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					tfTimbrado.requestFocus();
				}
			}
		});
		tfFactura.setBounds(89, 39, 116, 20);
		panelGeneral.add(tfFactura);
		
		JLabel lblNDeFactura = new JLabel("N\u00B0 de Factura");
		lblNDeFactura.setBounds(0, 42, 86, 14);
		panelGeneral.add(lblNDeFactura);
		lblNDeFactura.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblTimbrado = new JLabel("Timbrado");
		lblTimbrado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimbrado.setBounds(0, 73, 86, 14);
		panelGeneral.add(lblTimbrado);
		
		
		
		lblTimbrado_1 = new JLabel("Venci.");
		lblTimbrado_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimbrado_1.setBounds(234, 73, 61, 14);
		panelGeneral.add(lblTimbrado_1);
		
		tfVencimientoTimbrado = new JFormattedTextField(Util.formatoFecha());
		tfVencimientoTimbrado.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					btnBuscarProveedor.requestFocus();
				}
			}
		});
		tfVencimientoTimbrado.setBounds(305, 70, 79, 20);
		panelGeneral.add(tfVencimientoTimbrado);
		
		tfTimbrado = new JTextField();
		tfTimbrado.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validarNumero(e);
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					tfVencimientoTimbrado.requestFocus();
				}
			}
		});
		tfTimbrado.setBounds(89, 70, 142, 20);
		panelGeneral.add(tfTimbrado);
		tfTimbrado.setColumns(10);
		
		
		
		
		btnEliminar = new JButton("");
		btnEliminar.addActionListener(new ActionListener() {
			

			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>=0){
					total-=Util.stringADouble(table.campo(4).toString());
					tTotal.setText(Util.formatoDecimal(total));
					table.getModelo().removeRow(table.getSelectedRow());
				}
				
			}
		});
		btnEliminar.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428454951_Delete.png")));
		btnEliminar.setBounds(766, 177, 33, 23);
		getContentPane().add(btnEliminar);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setBounds(505, 372, 100, 14);
		getContentPane().add(lblTotal);
		
		JLabel lblGeneral = new JLabel("General");
		lblGeneral.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblGeneral.setBounds(26, 7, 46, 14);
		getContentPane().add(lblGeneral);
		
		JLabel lblProveedor = new JLabel("Proveedor");
		lblProveedor.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblProveedor.setBounds(458, 7, 76, 14);
		getContentPane().add(lblProveedor);
		
		JButton btnConfirmar = new JButton("");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmar();
			}
		});
		btnConfirmar.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428454811_button_ok.png")));
		btnConfirmar.setBounds(710, 390, 57, 32);
		getContentPane().add(btnConfirmar);
		
		panelProducto =new JCustomPanel2();
		panelProducto.setBounds(20, 129, 747, 47);
		getContentPane().add(panelProducto);
		panelProducto.setLayout(null);
		
		tfIdProducto = new PlaceholderTextField();
		tfIdProducto.setEnabled(false);
		tfIdProducto.setBounds(10, 16, 38, 20);
		panelProducto.add(tfIdProducto);
		
		tfIdProducto.setPlaceholder("Id");
		
		btnBuscarProducto = new JButton("");
		btnBuscarProducto.setBounds(45, 16, 31, 20);
		panelProducto.add(btnBuscarProducto);
		btnBuscarProducto.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428455167_698838-icon-111-search-16.png")));
		
		tfProducto = new PlaceholderTextField();
		tfProducto.setBounds(86, 16, 211, 20);
		panelProducto.add(tfProducto);
		tfProducto.setPlaceholder("Producto");
		tfProducto.setEnabled(false);
		
		tCantidad = new PlaceholderTextField();
		tCantidad.setPlaceholder("Cantidad");
		tCantidad.setBounds(306, 16, 46, 20);
		panelProducto.add(tCantidad);
		tCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				Util.validarNumero(e);
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tCosto.requestFocus();
				}
			}
		});
		
		tCosto = new NumberTextField();
		tCosto.setBounds(362, 16, 86, 20);
		panelProducto.add(tCosto);
		tCosto.addKeyListener(new KeyAdapter() {
			
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if (e.getKeyCode()==KeyEvent.VK_ENTER) {
						if(verificarRepetidos())
							agregarProducto();
						else
							JOptionPane.showMessageDialog(null, "Ya se agrego el Producto");
					}
				}
			}
		});
		
		lblCant = new JLabel("Cant.");
		lblCant.setBounds(306, 0, 46, 14);
		panelProducto.add(lblCant);
		lblCant.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		lblPrecioCompra = new JLabel("Precio Compra");
		lblPrecioCompra.setBounds(362, 0, 88, 14);
		panelProducto.add(lblPrecioCompra);
		lblPrecioCompra.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnBuscarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarProducto();
			}
		});
		
		
		if (accion.equals("AGREGAR")) {
			nuevo();
		}else {
			compra = c;

			cargarDatos();
			desactivarCampos();
		}
	}
	
	private void desactivarCampos() {
		for (int i = 0; i < panelGeneral.getComponentCount(); i++) {
			panelGeneral.getComponent(i).setEnabled(false);
		}
		for (int i = 0; i < panelProducto.getComponentCount(); i++) {
			panelProducto.getComponent(i).setEnabled(false);
		}
		for (int i = 0; i < panelProveedor.getComponentCount(); i++) {
			panelProveedor.getComponent(i).setEnabled(false);
		}
		table.setEnabled(false);
	}

	private void cargarDatos() {
		tfId.setText(compra.getId()+"");
		tfFactura.setText(compra.getFactura());
		tfFecha.setText(FormatoFecha.dateAString(compra.getFecha()));
		tfTimbrado.setText(compra.getTimbrado());
		tfVencimientoTimbrado.setText(FormatoFecha.dateAString(compra.getVencimientoTimbrado()));
		tfIdProveedor.setText(compra.getProveedor().getId()+"");
		tfProveedor.setText(compra.getProveedor().getNombre());
		tfRuc.setText(compra.getProveedor().getDocumento());
		tTotal.setValue(compra.getTotal());
		
	
		
		for (CompraItem ci: compra.getCompraItems()) {
			Object[] fila = new Object[8];
			fila[0] = ci.getProducto().getId();
			fila[1] = ci.getProducto().getDescripcion();
			fila[2] = ci.getCantidad();
			fila[3] = Util.formatoDecimal(ci.getCosto());
			fila[4] = Util.formatoDecimal(ci.getCantidad()*ci.getCosto());
			table.getModelo().addRow(fila);
		}
	}

	private boolean verificarRepetidos() {
		for (int i = 0; i < table.getRowCount(); i++) {
			if (tfIdProducto.getText().equals((String)table.getModelo().getValueAt(i, 0))) {
				i = table.getRowCount();
				return false;
			}
		}
		return true;
	}

	private void cargarAtributos() {
		
		compra = new Compra();
		compra.setId(Integer.parseInt(tfId.getText()));
		compra.setFactura(tfFactura.getText());
		compra.setFecha(FormatoFecha.stringToDate(tfFecha.getText()));
		compra.setTimbrado(tfTimbrado.getText());
		compra.setVencimientoTimbrado(FormatoFecha.stringToDate(tfVencimientoTimbrado.getText()));
		compra.setTotal(Util.stringADouble(tTotal.getText()));
		compra.setProveedor(proveegor);
		
		
		compraItemDao = new CompraItemDao();
		int itemId = compraItemDao.recuperMaxId()+1;
		for (int i = 0; i < table.getRowCount(); i++) {
			compraItem = new CompraItem();
			productoDao = new ProductoDao();
			compraItem.setId(itemId);
			p = productoDao.recuperarPorId(Integer.parseInt((String) table.getModelo().getValueAt(i, 0)));
			compraItem.setProducto(p);
			compraItem.setCantidad(Integer.parseInt(table.getModelo().getValueAt(i, 2).toString()));
			compraItem.setCosto(Util.stringADouble(table.getModelo().getValueAt(i, 3).toString()));
			compraItem.setCompra(compra);
			
			
			compraItems.add(compraItem);
			itemId++;
			
		}
		
		compra.setCompraItems(compraItems);
		compra.setEstado(false);
		
		
	}
	
	private void confirmar() {
		
		if (accion == "AGREGAR") {
			if (comprobarCabeceraYTabla()) {
				cargarAtributos();
				compraDao = new CompraDao();
				try {
					compraDao.insertar(compra);
				} catch (Exception e) {
					e.printStackTrace();
				}
				tbi.buscar();
				dispose();
			}
			
		}else {
				
			if (accion == "ANULAR") {
				
					try {
						compraDao = new CompraDao();
					
						compraDao.eliminar(compra);
						
							
					} catch (Exception e) {
						e.printStackTrace();
					}
	
			}
			tbi.buscar();
			dispose();
		}
		
		
	}




	private void agregarProducto() {
		if(comprobarProductoVacio()){
			object = new Object[8];
			object[0] = tfIdProducto.getText();
			object[1] = tfProducto.getText();
			object[2] = tCantidad.getText();
			object[3] = Util.formatoDecimal(Util.stringADouble(tCosto.getText()));
			object[4] = Util.formatoDecimal(Util.stringADouble(tCosto.getText())*Integer.parseInt(tCantidad.getText()));
			table.getModelo().addRow(object);
			p=null;
			total += Util.stringADouble(tCosto.getText())*Integer.parseInt(tCantidad.getText());
			tTotal.setText(Util.formatoDecimal(total));
			vaciarProducto();
		}
	}

	private void vaciarProducto() {
		tfIdProducto.setText("");
		tfProducto.setText("");
		tCosto.setValue(null);
		tCantidad.setText("");
		btnBuscarProducto.requestFocus();
	}

	private boolean comprobarProductoVacio() {
		if(tfIdProducto.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe selecionar un producto");
			tfIdProducto.requestFocus();
			return false;
		}
		if(tCantidad.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "El campo cantidad no puede estar vacio");
			tCantidad.requestFocus();
			return false;
		}
		if(tCosto.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "El campo costo de compra no puede estar vacio");
			tCosto.requestFocus();
			return false;
		}
		return true;
	}



	private void mostrarProveedor() {
		BusqudaProveedor busqudaProveedor = new BusqudaProveedor(this);
		busqudaProveedor.setIbp(this);
		busqudaProveedor.setVisible(true);
	}
	
	private void mostrarProducto() {
		BusqudaProducto busqudaProducto = new BusqudaProducto(this);
		busqudaProducto.setIbp(this);
		busqudaProducto.setVisible(true);
		tCantidad.requestFocus();
	}

	private void nuevo() {
		compraDao = new CompraDao();
		tfId.setText((compraDao.recuperMaxId()+1)+"");
		tfFecha.setValue(FormatoFecha.dateAString(new Date()));
	}

	@Override
	public void cargar(Proveedor p) {
		this.proveegor = p;
		tfIdProveedor.setText(p.getId()+"");
		tfProveedor.setText(p.getNombre());
		tfRuc.setText(p.getDocumento());
		btnBuscarProveedor.requestFocus();
	}

	@Override
	public void cargar(Producto p) {
		this.p = p;
		tfIdProducto.setText(p.getId()+"");
		tfProducto.setText(p.getDescripcion());
		//tPrecioVenta.setValue(p.getStock().getPrecio());
	}
	public void advertencia(String texto, int t) {
		JOptionPane.showMessageDialog(null, texto, "Atención", t);
	}
	
	private boolean comprobarCabeceraYTabla() {
		if (tfFactura.getText().equals("___-___-_______")) {
			advertencia("Debe cargar el nro. de factura", 2);
			tfFactura.requestFocus();
			return false;
		}
		if (tfFecha.getText().equals("__/__/____")) {
			advertencia("Debe cargar una fecha de compra", 2);
			tfFecha.requestFocus();
			return false;
		}
		if (tfTimbrado.getText().isEmpty()) {
			advertencia("Debe cargar un timbrado", 2);
			tfTimbrado.requestFocus();
			return false;
		}
		if (tfVencimientoTimbrado.getText().equals("__/__/____")) {
			advertencia("Debe cargar la fecha de vencimiento del timbrado", 2);
			tfVencimientoTimbrado.requestFocus();
			return false;
		}
		
		if (tfProveedor.getText().isEmpty()) {
			advertencia("Debe cargar un proveedor", 2);
			btnBuscarProveedor.requestFocus();
			return false;
		}
		if (table.getRowCount()==0) {
			advertencia("Debe cargar almenos un producto en la tabla", 2);
			btnBuscarProducto.requestFocus();
			return false;
		}
		
		return true;
	}
}
