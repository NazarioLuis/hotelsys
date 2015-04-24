package py.com.hotelsys.presentacion.transacciones;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;
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
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.CompraDao;
import py.com.hotelsys.dao.CompraItemDao;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.dao.ProveedorDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaProducto;
import py.com.hotelsys.interfaces.InterfaceBusquedaProveedor;
import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.modelo.CompraItem;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Proveedor;
import py.com.hotelsys.modelo.Stock;
import py.com.hotelsys.presentacion.buscadores.BusqudaProducto;
import py.com.hotelsys.presentacion.buscadores.BusqudaProveedor;
import py.com.hotelsys.util.FormatoFecha;

import java.awt.Font;

public class TransCompra extends JDialog 
	implements InterfaceBusquedaProveedor,InterfaceBusquedaProducto{

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			TransCompra dialog = new TransCompra();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private JTextField tfId;
	private JFormattedTextField tfFactura;
	private MaskFormatter maskFactura;
	private MaskFormatter maskFecha;
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
	private ProveedorDao proveedorDao;
	private ProductoDao productoDao;
	private Proveedor proveegor;
	private Producto p;
	private JButton btnAddProducto;
	private Object[] object;
	private JButton button;
	private PlaceholderTextField tfCantidad;
	private CompraItemDao compraItemDao;
	private JLabel lblTimbrado_1;
	private JFormattedTextField tfVencimientoTimbrado;
	private JTextField tfTimbrado;
	private Compra compra;
	private CompraItem compraItem;
	private List<CompraItem> compraItems = new ArrayList<>();
	/**
	 * Create the dialog.
	 */
	public TransCompra() {
		
		
		setBounds(100, 100, 837, 471);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		try {
			maskFactura = new MaskFormatter("###-###-#######");
			maskFactura.setPlaceholderCharacter('_');
		} catch (ParseException e2) {
			e2.printStackTrace();
		}
		
		try {
			maskFecha = new MaskFormatter("##/##/####");
			maskFecha.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(451, 19, 348, 80);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tfProveedor = new PlaceholderTextField();
		tfProveedor.setEnabled(false);
		tfProveedor.setBounds(90, 11, 237, 20);
		panel.add(tfProveedor);
		tfProveedor.setPlaceholder("Proveedor");
		
		tfIdProveedor = new PlaceholderTextField();
		tfIdProveedor.addKeyListener(new KeyAdapter() {
			

			

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					proveedorDao = new ProveedorDao();
					proveegor = proveedorDao.recuperarPorId(Integer.parseInt(tfIdProveedor.getText()));
					if(proveegor!=null)
						cargar(proveegor);
				}
			}
		});
		tfIdProveedor.setBounds(21, 11, 38, 20);
		panel.add(tfIdProveedor);
		tfIdProveedor.setPlaceholder("Id");
		
		JButton btnBuscarProveedor = new JButton("");
		btnBuscarProveedor.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428455167_698838-icon-111-search-16.png")));
		btnBuscarProveedor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarProveedor();
			}
		});
		btnBuscarProveedor.setBounds(56, 11, 31, 20);
		panel.add(btnBuscarProveedor);
		
		tfRuc = new PlaceholderTextField();
		tfRuc.setEnabled(false);
		tfRuc.setBounds(21, 49, 164, 20);
		panel.add(tfRuc);
		tfRuc.setPlaceholder("RUC");
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(26, 153, 730, 216);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Producto", "Cantidad", "Costo de compra", "Costo promedio", "Precio anterior", "Nuevo precio", "Subtotal compra"}, new int[] {30, 200, 60, 90, 90, 90, 90, 90});
		scrollPane.setViewportView(table);
		
		tfProducto = new PlaceholderTextField();
		tfProducto.setPlaceholder("Producto");
		tfProducto.setEnabled(false);
		tfProducto.setBounds(94, 129, 222, 20);
		getContentPane().add(tfProducto);
		
		tfIdProducto = new PlaceholderTextField();
		tfIdProducto.addKeyListener(new KeyAdapter() {
			

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					productoDao = new ProductoDao();
					p = productoDao.recuperarPorId(Integer.parseInt(tfIdProducto.getText()));
					if(p!=null){
						cargar(p);
						tfCantidad.requestFocus();
					}
				}
			}
		});
		tfIdProducto.setPlaceholder("Id");
		tfIdProducto.setBounds(26, 129, 38, 20);
		getContentPane().add(tfIdProducto);
		
		btnBuscarProducto = new JButton("");
		btnBuscarProducto.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428455167_698838-icon-111-search-16.png")));
		btnBuscarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarProducto();
			}
		});
		btnBuscarProducto.setBounds(61, 129, 31, 20);
		getContentPane().add(btnBuscarProducto);
		
		tfCosto = new PlaceholderTextField();
		tfCosto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER) {
					calcularPromedioDeCosto();
					tfNuevoPrecio.requestFocus();
					btnAddProducto.setEnabled(true);
				}
			}
		});
		
		tfCosto.setPlaceholder("Costo de compra");
		tfCosto.setBounds(383, 129, 86, 20);
		getContentPane().add(tfCosto);
		
		tfPromedio = new PlaceholderTextField();
		tfPromedio.setEnabled(false);
		tfPromedio.setPlaceholder("Costo promedio");
		tfPromedio.setBounds(479, 129, 86, 20);
		getContentPane().add(tfPromedio);
		
		tfPrecioVenta = new PlaceholderTextField();
		tfPrecioVenta.setEnabled(false);
		tfPrecioVenta.setPlaceholder("Precio de anterior");
		tfPrecioVenta.setBounds(575, 129, 86, 20);
		getContentPane().add(tfPrecioVenta);
		
		tfNuevoPrecio = new PlaceholderTextField();
		tfNuevoPrecio.setPlaceholder("Nuevo precio");
		tfNuevoPrecio.setBounds(671, 129, 86, 20);
		getContentPane().add(tfNuevoPrecio);
		
		tfTotal = new PlaceholderTextField();
		tfTotal.setText("0");
		tfTotal.setPlaceholder("Total");
		tfTotal.setEnabled(false);
		tfTotal.setBounds(615, 369, 141, 20);
		getContentPane().add(tfTotal);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.GRAY));
		panel_1.setBounds(20, 19, 403, 99);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		tfFecha = new JFormattedTextField(maskFecha);
		tfFecha.setBounds(305, 11, 79, 20);
		panel_1.add(tfFecha);
		
		lblFechaDeCompra = new JLabel("Fecha de Compra");
		lblFechaDeCompra.setBounds(195, 14, 100, 14);
		panel_1.add(lblFechaDeCompra);
		lblFechaDeCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		
		tfId = new JTextField();
		tfId.setEnabled(false);
		tfId.setBounds(89, 11, 66, 20);
		panel_1.add(tfId);
		tfId.setColumns(10);
		
		JLabel lblNroDeCompra = new JLabel("N\u00B0 de Compra");
		lblNroDeCompra.setBounds(0, 14, 86, 14);
		panel_1.add(lblNroDeCompra);
		lblNroDeCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		tfFactura = new JFormattedTextField(maskFactura);
		tfFactura.setBounds(89, 39, 116, 20);
		panel_1.add(tfFactura);
		
		JLabel lblNDeFactura = new JLabel("N\u00B0 de Factura");
		lblNDeFactura.setBounds(0, 42, 86, 14);
		panel_1.add(lblNDeFactura);
		lblNDeFactura.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblTimbrado = new JLabel("Timbrado");
		lblTimbrado.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimbrado.setBounds(0, 73, 86, 14);
		panel_1.add(lblTimbrado);
		
		
		
		lblTimbrado_1 = new JLabel("Venci.");
		lblTimbrado_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTimbrado_1.setBounds(234, 73, 61, 14);
		panel_1.add(lblTimbrado_1);
		
		tfVencimientoTimbrado = new JFormattedTextField(maskFecha);
		tfVencimientoTimbrado.setBounds(305, 70, 79, 20);
		panel_1.add(tfVencimientoTimbrado);
		
		tfTimbrado = new JTextField();
		tfTimbrado.setBounds(89, 70, 142, 20);
		panel_1.add(tfTimbrado);
		tfTimbrado.setColumns(10);
		
		btnAddProducto = new JButton("");
		btnAddProducto.setEnabled(false);
		btnAddProducto.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428454811_button_ok.png")));
		btnAddProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(verificarRepetidos())
					agregarProducto();
			}
		});
		btnAddProducto.setBounds(767, 129, 32, 22);
		getContentPane().add(btnAddProducto);
		
		button = new JButton("");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>=0){
					tfTotal.setText(Integer.parseInt(tfTotal.getText())-Integer.parseInt((String)table.getModelo().getValueAt(table.getSelectedRow(), 7))+"");
					table.getModelo().removeRow(table.getSelectedRow());
				}
				
			}
		});
		button.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428454951_Delete.png")));
		button.setBounds(766, 153, 33, 23);
		getContentPane().add(button);
		
		tfCantidad = new PlaceholderTextField();
		tfCantidad.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					tfCosto.requestFocus();
				}
			}
		});
		tfCantidad.setPlaceholder("Cantidad");
		tfCantidad.setBounds(326, 129, 46, 20);
		getContentPane().add(tfCantidad);
		
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
		
		JButton button_1 = new JButton("");
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cargarAtributos();
			}
		});
		button_1.setIcon(new ImageIcon(TransCompra.class.getResource("/img/1428454811_button_ok.png")));
		button_1.setBounds(710, 390, 46, 32);
		getContentPane().add(button_1);
		
		nuevo();
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
		compra.setTotal(Double.parseDouble(tfTotal.getText()));
		compra.setProveedor(proveegor);
		
		for (int i = 0; i < table.getRowCount(); i++) {
			compraItem = new CompraItem();
			
			System.out.println(table.getValueAt(i, 0));
			productoDao = new ProductoDao();
			p = productoDao.recuperarPorId(Integer.parseInt((String) table.getModelo().getValueAt(i, 0)));
			compraItem.setProducto(p);
			compraItem.setCantidad(Integer.parseInt((String)table.getModelo().getValueAt(i, 2)));
			compraItem.setCosto(Integer.parseInt((String)table.getModelo().getValueAt(i, 3)));
			compraItem.setCostoPromedio(Integer.parseInt((String) table.getModelo().getValueAt(i, 4)));
			
			compraItems.add(compraItem);
			
			actualizarStock(Integer.parseInt((String)table.getModelo().getValueAt(i, 2)),Integer.parseInt((String)table.getModelo().getValueAt(i, 6)));
		}
		
		compra.setCompraItems(compraItems);
		compra.setEstado(true);
		
		compraDao = new CompraDao();
		try {
			compraDao.insertar(compra);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void actualizarStock(int cantidad,int precio) {
		p.getStock().setCantidad(p.getStock().getCantidad()+cantidad);
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
			object[6] = tfNuevoPrecio.getText();
			object[7] = Integer.parseInt(tfCosto.getText())*Integer.parseInt(tfCantidad.getText());
			table.getModelo().addRow(object);
			p=null;
			btnAddProducto.setEnabled(false);
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
			tfPromedio.setText((((p.getStock().getCantidad()*compraItemDao.cosultarPromedio(p))+(Integer.parseInt(tfCosto.getText())*Integer.parseInt(tfCantidad.getText())))/
				(p.getStock().getCantidad()+Integer.parseInt(tfCantidad.getText())))+"");
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
