package py.com.hotelsys.presentacion.transacciones;

import java.awt.Font;
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
import javax.swing.text.MaskFormatter;

import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.JCustomPanel2;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.EntradaItemDao;
import py.com.hotelsys.dao.EntradaStockDao;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaProducto;
import py.com.hotelsys.interfaces.TranBotonInterface;
import py.com.hotelsys.modelo.EntradaStock;
import py.com.hotelsys.modelo.EntradaStockItem;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.presentacion.buscadores.BusqudaProducto;
import py.com.hotelsys.util.FormatoFecha;

@SuppressWarnings("serial")
public class TransEntrada extends JDialog 
	implements InterfaceBusquedaProducto{

	
	private JTextField tfId;
	private MaskFormatter maskFactura;
	private MaskFormatter maskFecha;
	private JFormattedTextField tfFecha;
	private JLabel lblFechaDeCompra;
	private EntradaStockDao entradaDao;
	private CustomTable table;
	private PlaceholderTextField tfProducto;
	private PlaceholderTextField tfIdProducto;
	private JButton btnBuscarProducto;
	private ProductoDao productoDao;
	private Producto producto;
	private Object[] object;
	private JButton btnEliminar;
	private PlaceholderTextField tfCantidad;
	private JTextField tfDescri;
	private EntradaStock entrada;
	private EntradaStockItem entradaItem;
	private List<EntradaStockItem> entradaItems = new ArrayList<>();
	private JPanel panelGeneral;
	private JPanel panelProducto;
	private String accion;
	private JLabel lblCant;
	private TranBotonInterface tbi;
	private EntradaItemDao entradaItemDao;
	

	public void setTbi(TranBotonInterface tbi) {
		this.tbi = tbi;
	}

	/**
	 * Create the dialog.
	 * @param accion 
	 * @param  
	 */
	public TransEntrada(JDialog d, EntradaStock c, String accion) {
		super(d,false);
		setTitle("Entrada Stock");
		this.accion = accion;
		this.entrada = c;
		
		setBounds(100, 100, 493, 420);
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
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(20, 145, 414, 193);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Producto", "Cantidad"}, new int[] {30, 300, 50});
		scrollPane.setViewportView(table);
		
		panelGeneral = new JCustomPanel1();
		panelGeneral.setBounds(20, 19, 414, 69);
		getContentPane().add(panelGeneral);
		panelGeneral.setLayout(null);
		tfFecha = new JFormattedTextField(maskFecha);
		tfFecha.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					tfDescri.requestFocus();
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
		
		JLabel lblDescri = new JLabel("Descripci\u00F3n");
		lblDescri.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDescri.setBounds(0, 42, 86, 14);
		panelGeneral.add(lblDescri);
		
		tfDescri = new JTextField();
		tfDescri.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_ENTER||e.getKeyCode()==KeyEvent.VK_TAB) {
					btnBuscarProducto.requestFocus();
				}
			}
		});
		tfDescri.setBounds(89, 39, 295, 20);
		panelGeneral.add(tfDescri);
		tfDescri.setColumns(10);
		
		btnEliminar = new JButton("");
		btnEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(table.getSelectedRow()>=0){
					
					table.getModelo().removeRow(table.getSelectedRow());
				}
				
			}
		});
		btnEliminar.setIcon(new ImageIcon(TransEntrada.class.getResource("/img/1428454951_Delete.png")));
		btnEliminar.setBounds(434, 145, 33, 23);
		getContentPane().add(btnEliminar);
		
		JLabel lblGeneral = new JLabel("Entrada de Producto");
		lblGeneral.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblGeneral.setBounds(26, 7, 132, 14);
		getContentPane().add(lblGeneral);
		
		JButton btnConfirmar = new JButton("");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				confirmar();
			}
		});
		btnConfirmar.setIcon(new ImageIcon(TransEntrada.class.getResource("/img/1428454811_button_ok.png")));
		btnConfirmar.setBounds(377, 338, 57, 32);
		getContentPane().add(btnConfirmar);
		
		panelProducto = new JCustomPanel2();
		panelProducto.setBounds(20, 99, 414, 47);
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
		btnBuscarProducto.setIcon(new ImageIcon(TransEntrada.class.getResource("/img/1428455167_698838-icon-111-search-16.png")));
		
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
					if(verificarRepetidos())
						agregarProducto();
					else
						JOptionPane.showMessageDialog(null, "Ya se agrego el Producto");
				}
			}
		});
		tfCantidad.setPlaceholder("Cantidad");
		
		lblCant = new JLabel("Cant.");
		lblCant.setBounds(306, 2, 46, 14);
		panelProducto.add(lblCant);
		lblCant.setFont(new Font("Tahoma", Font.PLAIN, 9));
		btnBuscarProducto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				mostrarProducto();
			}
		});
		
		
		if (accion.equals("AGREGAR")) {
			nuevo();
		}else {
			entrada = c;

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
		
		table.setEnabled(false);
	}

	private void cargarDatos() {
		tfId.setText(entrada.getId()+"");
		tfFecha.setText(FormatoFecha.dateAString(entrada.getFecha()));
		tfDescri.setText(entrada.getDescripcion());
		
		for (EntradaStockItem ei: entrada.getEntradaItems()) {
			Object[] fila = new Object[8];
			fila[0] = ei.getProducto().getId();
			fila[1] = ei.getProducto().getDescripcion();
			fila[2] = ei.getCantidad();

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
		entrada = new EntradaStock();
		entrada.setId(Integer.parseInt(tfId.getText()));
		entrada.setFecha(FormatoFecha.stringToDate(tfFecha.getText()));
		entrada.setDescripcion(tfDescri.getText());
		
		entradaItemDao = new EntradaItemDao();
		int itemId = entradaItemDao.recuperMaxId()+1;
		for (int i = 0; i < table.getRowCount(); i++) {
			entradaItem = new EntradaStockItem();
			
			System.out.println(table.getValueAt(i, 0));
			productoDao = new ProductoDao();
			entradaItem.setId(itemId);
			producto = productoDao.recuperarPorId(Integer.parseInt((String) table.getModelo().getValueAt(i, 0)));
			entradaItem.setProducto(producto);
			entradaItem.setCantidad(Integer.parseInt((String)table.getModelo().getValueAt(i, 2)));
			entradaItem.setEntrada(entrada);
			
			entradaItems.add(entradaItem);
			itemId++;
			//actualizarStock(Integer.parseInt(table.getModelo().getValueAt(i, 2)+""));
		}
		
		entrada.setEntradaItems(entradaItems);
		entrada.setEstado(false);
		
		
	}
	
	private void confirmar() {
		
		if (accion == "AGREGAR") {
			if (comprobarCabeceraYTabla()) {
				cargarAtributos();
				entradaDao = new EntradaStockDao();
				try {
					entradaDao.insertar(entrada);
				} catch (Exception e) {
					e.printStackTrace();
				}
				tbi.buscar();
				dispose();
			}
			
		}else {

			if (accion == "ANULAR") {
				
					entrada.setEstado(false);
					try {
						entradaDao = new EntradaStockDao();
						
						entradaDao.eliminar(entrada);
						
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
			object[2] = tfCantidad.getText();
			
			table.getModelo().addRow(object);
			producto=null;

			vaciarProducto();
		}
	}

	private void vaciarProducto() {
		tfIdProducto.setText("");
		tfProducto.setText("");
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
		
		return true;
	}

	
	
	
	private void mostrarProducto() {
		BusqudaProducto busqudaProducto = new BusqudaProducto(this);
		busqudaProducto.setIbp(this);
		busqudaProducto.setVisible(true);
		tfCantidad.requestFocus();
	}

	private void nuevo() {
		entradaDao = new EntradaStockDao();
		tfId.setText((entradaDao.recuperMaxId()+1)+"");
		tfFecha.setValue(FormatoFecha.dateAString(new Date()));
	}

	public void advertencia(String texto, int t) {
		JOptionPane.showMessageDialog(null, texto, "Atención", t);
	}
	

	private boolean comprobarCabeceraYTabla() {
		if (tfDescri.getText().isEmpty()) {
			advertencia("Debe cargar una descripción", 2);
			tfDescri.requestFocus();
			return false;
		}
		if (tfFecha.getText().equals("__/__/____")) {
			advertencia("Debe cargar una fecha", 2);
			tfFecha.requestFocus();
			return false;
		}
		if (table.getRowCount()==0) {
			advertencia("Debe cargar almenos un producto en la tabla", 2);
			btnBuscarProducto.requestFocus();
			return false;
		}
		return true;
	}
	
	@Override
	public void cargar(Producto p) {
		this.producto = p;
		tfIdProducto.setText(p.getId()+"");
		tfProducto.setText(p.getDescripcion());
	}
}
