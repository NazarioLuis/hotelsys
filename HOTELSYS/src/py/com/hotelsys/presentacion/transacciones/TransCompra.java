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
import py.com.hotelsys.util.VariablesDelSistema;

@SuppressWarnings("serial")
public class TransCompra extends JDialog 
	implements InterfaceBusquedaProveedor,InterfaceBusquedaProducto{

	
	private JTextField tfId;
	private JFormattedTextField tfFactura;
	
	
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
	private PlaceholderTextField tfCosto;
	private PlaceholderTextField tfPromedio;
	private PlaceholderTextField tfPrecioVenta;
	private PlaceholderTextField tfNuevoPrecio;
	private PlaceholderTextField tfTotal;
	private ProductoDao productoDao;
	private Proveedor proveegor;
	private Producto p;
	private Object[] object;
	private JButton btnEliminar;
	private PlaceholderTextField tfCantidad;
	private CompraItemDao compraItemDao;
	private JLabel lblTimbrado_1;
	private JFormattedTextField tfVencimientoTimbrado;
	private JTextField tfTimbrado;
	private Compra compra;
	private CompraItem compraItem;
	private List<CompraItem> compraItems = new ArrayList<>();
	private String promedio;
	private JPanel panelGeneral;
	private JPanel panelProveedor;
	private JPanel panelProducto;
	private String accion;
	private JLabel lblCant;
	private JLabel lblPrecioCompra;
	private JLabel lblPromedioDeCosto;
	private JLabel lblPVentaActual;
	private JLabel lblNuevoPVenta;
	private TranBotonInterface tbi;
	

	public void setTbi(TranBotonInterface tbi) {
		this.tbi = tbi;
	}

	/**
	 * Create the dialog.
	 * @param accion 
	 * @param  
	 */
	public TransCompra(JDialog d, Compra c, String accion) {
		super(d,false);
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
		
		JButton btnBuscarProveedor = new JButton("");
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
		
		table = new CustomTable(new String[] {"#", "Producto", "Cantidad", "Costo de compra", "Costo promedio", "Precio anterior", "Nuevo precio", "Subtotal compra"}, new int[] {30, 200, 60, 90, 90, 90, 90, 90});
		scrollPane.setViewportView(table);
		
		tfTotal = new PlaceholderTextField();
		tfTotal.setText("0");
		tfTotal.setPlaceholder("Total");
		tfTotal.setEnabled(false);
		tfTotal.setBounds(615, 369, 152, 20);
		getContentPane().add(tfTotal);
		
		panelGeneral =new JCustomPanel1();
		panelGeneral.setBounds(20, 19, 403, 99);
		getContentPane().add(panelGeneral);
		panelGeneral.setLayout(null);
		tfFecha = new JFormattedTextField(VariablesDelSistema.formatoFecha());
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
		
		
		tfFactura = new JFormattedTextField(VariablesDelSistema.formatoFactura());
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
		
		tfVencimientoTimbrado = new JFormattedTextField(VariablesDelSistema.formatoFecha());
		tfVencimientoTimbrado.setBounds(305, 70, 79, 20);
		panelGeneral.add(tfVencimientoTimbrado);
		
		tfTimbrado = new JTextField();
		tfTimbrado.setBounds(89, 70, 142, 20);
		panelGeneral.add(tfTimbrado);
		tfTimbrado.setColumns(10);
		
		
		
		
		btnEliminar = new JButton("");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>=0){
					tfTotal.setText(Integer.parseInt(tfTotal.getText())-(int)table.campo(7)+"");
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
		
		tfCantidad = new PlaceholderTextField();
		tfCantidad.setBounds(306, 16, 46, 20);
		panelProducto.add(tfCantidad);
		tfCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfCosto.requestFocus();
				}
			}
		});
		tfCantidad.setPlaceholder("Cantidad");
		
		tfCosto = new PlaceholderTextField();
		tfCosto.setBounds(362, 16, 86, 20);
		panelProducto.add(tfCosto);
		tfCosto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					calcularPromedioDeCosto();
					tfNuevoPrecio.requestFocus();
				}
			}
		});
		
		tfCosto.setPlaceholder("Costo de compra");
		
		tfPromedio = new PlaceholderTextField();
		tfPromedio.setBounds(458, 16, 86, 20);
		panelProducto.add(tfPromedio);
		tfPromedio.setEnabled(false);
		tfPromedio.setPlaceholder("Costo promedio");
		
		tfPrecioVenta = new PlaceholderTextField();
		tfPrecioVenta.setBounds(554, 16, 86, 20);
		panelProducto.add(tfPrecioVenta);
		tfPrecioVenta.setEnabled(false);
		tfPrecioVenta.setPlaceholder("Precio de anterior");
		
		tfNuevoPrecio = new PlaceholderTextField();
		tfNuevoPrecio.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					if(verificarRepetidos())
						agregarProducto();
					else
						JOptionPane.showMessageDialog(null, "Ya se agrego el Producto");
				}
			}
		});
		tfNuevoPrecio.setBounds(650, 16, 86, 20);
		panelProducto.add(tfNuevoPrecio);
		tfNuevoPrecio.setPlaceholder("Nuevo precio");
		
		lblCant = new JLabel("Cant.");
		lblCant.setBounds(306, 0, 46, 14);
		panelProducto.add(lblCant);
		lblCant.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		lblPrecioCompra = new JLabel("Precio Compra");
		lblPrecioCompra.setBounds(362, 0, 88, 14);
		panelProducto.add(lblPrecioCompra);
		lblPrecioCompra.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		lblPromedioDeCosto = new JLabel("Promedio de Costo");
		lblPromedioDeCosto.setBounds(460, 0, 88, 14);
		panelProducto.add(lblPromedioDeCosto);
		lblPromedioDeCosto.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		lblPVentaActual = new JLabel("P. Venta actual");
		lblPVentaActual.setBounds(558, 0, 88, 14);
		panelProducto.add(lblPVentaActual);
		lblPVentaActual.setFont(new Font("Tahoma", Font.PLAIN, 9));
		
		lblNuevoPVenta = new JLabel("Nuevo P. Venta");
		lblNuevoPVenta.setBounds(649, 0, 88, 14);
		panelProducto.add(lblNuevoPVenta);
		lblNuevoPVenta.setFont(new Font("Tahoma", Font.PLAIN, 9));
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
		tfTotal.setText(compra.getTotal()+"");
		
		for (CompraItem ci: compra.getCompraItems()) {
			Object[] fila = new Object[8];
			fila[0] = ci.getProducto().getId();
			fila[1] = ci.getProducto().getDescripcion();
			fila[2] = ci.getCantidad();
			fila[3] = ci.getCosto();
			fila[4] = ci.getCostoPromedio();
			fila[5] = "---";
			fila[6] = "---";
			fila[7] = ci.getCantidad()*ci.getCosto();
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
		compra.setTotal(Double.parseDouble(tfTotal.getText()));
		compra.setProveedor(proveegor);
		
		for (int i = 0; i < table.getRowCount(); i++) {
			compraItem = new CompraItem();
			
			productoDao = new ProductoDao();
			p = productoDao.recuperarPorId(Integer.parseInt((String) table.getModelo().getValueAt(i, 0)));
			compraItem.setProducto(p);
			compraItem.setCantidad(Integer.parseInt((String)table.getModelo().getValueAt(i, 2)));
			compraItem.setCosto(Integer.parseInt((String)table.getModelo().getValueAt(i, 3)));
			compraItem.setCostoPromedio(Integer.parseInt((String) table.getModelo().getValueAt(i, 4)));
			compraItem.setCompra(compra);
			
			actualizarStock(Integer.parseInt(table.getModelo().getValueAt(i, 2)+""),Integer.parseInt(table.getModelo().getValueAt(i, 6)+""));
			
			compraItems.add(compraItem);
			
		}
		
		compra.setCompraItems(compraItems);
		compra.setEstado(true);
		
		
	}
	
	private void confirmar() {
		
		if (accion == "AGREGAR") {
			cargarAtributos();
			compraDao = new CompraDao();
			try {
				compraDao.insertar(compra);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		if (accion == "ANULAR") {
			if (!stockNegativo(compra.getCompraItems())) {
				compra.setEstado(false);
				try {
					compraDao = new CompraDao();
					try {
						compraDao.actualizar(compra);
					} catch (Exception e) {
						e.printStackTrace();
					}
					for (CompraItem ci:compra.getCompraItems()) {
						productoDao = new ProductoDao();
						ci.getProducto().getStock().setCantidad(ci.getProducto().getStock().getCantidad()-ci.getCantidad());
						productoDao.actualizar(ci.getProducto());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else
				JOptionPane.showMessageDialog(null, "No puede anular esta compra pues dejaria productos con stock negativo");
		}
		
		tbi.buscar();
		dispose();
		
		
	}


	private boolean stockNegativo(List<CompraItem> cis) {
		for (CompraItem ci:cis) {
			
			if(ci.getProducto().getStock().getCantidad()-ci.getCantidad()<0){
				return true;
			}
			
		}
		return true;
	}

	private void actualizarStock(int cantidad,int precio) {
		
		p.getStock().setCantidad(p.getStock().getCantidad()+cantidad);
		if (precio != 0) 
			p.getStock().setPrecio(precio);
	}

	private void agregarProducto() {
		if(comprobarProductoVacio()){
			object = new Object[8];
			object[0] = tfIdProducto.getText();
			object[1] = tfProducto.getText();
			object[2] = tfCantidad.getText();
			object[3] = tfCosto.getText();
			object[4] = tfPromedio.getText();
			object[5] = tfPrecioVenta.getText();
			if (tfNuevoPrecio.getText().isEmpty()) {
				object[6] = 0;
			}else
				object[6] = tfNuevoPrecio.getText();
			object[7] = Integer.parseInt(tfCosto.getText())*Integer.parseInt(tfCantidad.getText());
			table.getModelo().addRow(object);
			p=null;
			tfTotal.setText(Integer.parseInt(tfCosto.getText())*Integer.parseInt(tfCantidad.getText())+Integer.parseInt(tfTotal.getText())+"");
			vaciarProducto();
		}
	}

	private void vaciarProducto() {
		tfIdProducto.setText("");
		tfProducto.setText("");
		tfCosto.setText("");
		tfPromedio.setText("");
		tfPrecioVenta.setText("");
		tfNuevoPrecio.setText("");
		tfCantidad.setText("");
	}

	private boolean comprobarProductoVacio() {
		if(tfIdProducto.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "Debe selecionar un producto");
			tfIdProducto.requestFocus();
			return false;
		}
		if(tfCantidad.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "El campo cantidad no puede estar vacio");
			tfCantidad.requestFocus();
			return false;
		}
		if(tfCosto.getText().isEmpty()){
			JOptionPane.showMessageDialog(null, "El campo costo de compra no puede estar vacio");
			tfCosto.requestFocus();
			return false;
		}
		return true;
	}

	private void calcularPromedioDeCosto() {
		compraItemDao = new CompraItemDao();
		if (!tfIdProducto.getText().isEmpty()) {
			if (compraItemDao.cosultarPromedio(p)!=0) {
				promedio = (((p.getStock().getCantidad()*compraItemDao.cosultarPromedio(p))+(Integer.parseInt(tfCosto.getText())*Integer.parseInt(tfCantidad.getText())))/
				(p.getStock().getCantidad()+Integer.parseInt(tfCantidad.getText())))+"";
				tfPromedio.setText(promedio);
			}else{
				
				tfPromedio.setText(tfCosto.getText());
			}
			
			
				
			
			compraItemDao.cerrar();
		}
		
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
		tfCantidad.requestFocus();
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
	}

	@Override
	public void cargar(Producto p) {
		this.p = p;
		tfIdProducto.setText(p.getId()+"");
		tfProducto.setText(p.getDescripcion());
		tfPrecioVenta.setText((int)p.getStock().getPrecio()+"");
	}
}
