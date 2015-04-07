package py.com.hotelsys.presentacion.transacciones;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.MaskFormatter;

import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.CompraDao;
import py.com.hotelsys.dao.ProveedorDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaProducto;
import py.com.hotelsys.interfaces.InterfaceBusquedaProveedor;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Proveedor;
import py.com.hotelsys.presentacion.buscadores.BusqudaProducto;
import py.com.hotelsys.presentacion.buscadores.BusqudaProveedor;

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
	private JSpinner tfCantidad;
	private PlaceholderTextField tfCosto;
	private PlaceholderTextField tfPromedio;
	private PlaceholderTextField tfPrecioVenta;
	private PlaceholderTextField tfNuevoPrecio;
	private PlaceholderTextField tfTotal;
	private ProveedorDao proveedorDao;
	private Proveedor proveegor;
	private Producto p;
	/**
	 * Create the dialog.
	 */
	public TransCompra() {
		
		
		setBounds(100, 100, 803, 471);
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
		panel.setBounds(418, 10, 348, 80);
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
		
		JButton btnBuscarProveedor = new JButton("...");
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
		scrollPane.setBounds(26, 145, 740, 232);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"Producto", "Cantidad", "Costo de compra", "Costo promedio", "Precio de venta", "Nuevo precio"}, new int[] {200, 60, 100, 90, 90, 90});
		scrollPane.setViewportView(table);
		
		tfProducto = new PlaceholderTextField();
		tfProducto.setPlaceholder("Producto");
		tfProducto.setEnabled(false);
		tfProducto.setBounds(94, 114, 222, 20);
		getContentPane().add(tfProducto);
		
		tfIdProducto = new PlaceholderTextField();
		tfIdProducto.setPlaceholder("Id");
		tfIdProducto.setBounds(26, 114, 38, 20);
		getContentPane().add(tfIdProducto);
		
		btnBuscarProducto = new JButton("New button");
		btnBuscarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarProducto();
			}
		});
		btnBuscarProducto.setBounds(61, 114, 31, 20);
		getContentPane().add(btnBuscarProducto);
		
		tfCantidad = new JSpinner();
		tfCantidad.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		tfCantidad.setBounds(335, 114, 38, 20);
		getContentPane().add(tfCantidad);
		
		tfCosto = new PlaceholderTextField();
		tfCosto.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				calcularPromedioDeCosto();
			}
		});
		tfCosto.setPlaceholder("Costo de compra");
		tfCosto.setBounds(383, 114, 86, 20);
		getContentPane().add(tfCosto);
		
		tfPromedio = new PlaceholderTextField();
		tfPromedio.setEnabled(false);
		tfPromedio.setPlaceholder("Costo promedio");
		tfPromedio.setBounds(479, 114, 86, 20);
		getContentPane().add(tfPromedio);
		
		tfPrecioVenta = new PlaceholderTextField();
		tfPrecioVenta.setEnabled(false);
		tfPrecioVenta.setPlaceholder("Precio de venta");
		tfPrecioVenta.setBounds(575, 114, 86, 20);
		getContentPane().add(tfPrecioVenta);
		
		tfNuevoPrecio = new PlaceholderTextField();
		tfNuevoPrecio.setPlaceholder("Nuevo precio");
		tfNuevoPrecio.setBounds(671, 114, 95, 20);
		getContentPane().add(tfNuevoPrecio);
		
		tfTotal = new PlaceholderTextField();
		tfTotal.setPlaceholder("Total");
		tfTotal.setEnabled(false);
		tfTotal.setBounds(625, 377, 141, 20);
		getContentPane().add(tfTotal);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 394, 80);
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
		tfFactura.setBounds(89, 49, 116, 20);
		panel_1.add(tfFactura);
		
		JLabel lblNDeFactura = new JLabel("N\u00B0 de Factura");
		lblNDeFactura.setBounds(0, 52, 86, 14);
		panel_1.add(lblNDeFactura);
		lblNDeFactura.setHorizontalAlignment(SwingConstants.RIGHT);
		
		nuevo();
	}

	private void calcularPromedioDeCosto() {
		//tfPromedio.setText((p.getStock().getCantidad()*p.getStock().getPrecio()));
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
	}

	private void nuevo() {
		compraDao = new CompraDao();
		tfId.setText((compraDao.recuperMaxId()+1)+"");
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
		tfPrecioVenta.setText(p.getStock().getPrecio()+"");
	}
}
