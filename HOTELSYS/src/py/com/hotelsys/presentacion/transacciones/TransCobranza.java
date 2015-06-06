package py.com.hotelsys.presentacion.transacciones;

import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.JCustomPanel2;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.componentes.PlaceholderTextField2;
import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.dao.CobranzaDao;
import py.com.hotelsys.dao.DeudaDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Cliente;
import py.com.hotelsys.modelo.Cobranza;
import py.com.hotelsys.modelo.Deuda;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.Util;



@SuppressWarnings("serial")
public class TransCobranza extends JDialog implements AbmBotonInterface {

	

	private ClienteDao clienteDao;
	private List<Cliente> listaCliente;
	private CustomTable tabla;
	private Object[] fila;
	private JFormattedTextField tFechaCobro;
	private PlaceholderTextField tCliente;
	private JPanel panel;
	private Cliente cliente;
	private int ultimaFila;
	private DeudaDao deuDao;
	private Double monto;
	private PlaceholderTextField tDocumento;
	private PlaceholderTextField2 tGuarani;
	private PlaceholderTextField2 tReal;
	private PlaceholderTextField2 tDolar;
	private JButton btnCobrar;
	private JCheckBox cbFactura;
	private Cobranza cobraza;
	private CobranzaDao cobDao;
	private List<Deuda> listDeuda;
	private PlaceholderTextField tNombreFactura;
	private PlaceholderTextField tRuc;
	private JPanel panelFac;
	private String nombre;
	private String documento;
	private PlaceholderTextField tCiudad;
	private String ciudad;



	
	public TransCobranza(JFrame frame) {
		super(frame);
		setTitle("Registro de Cobranza");
		setBounds(100, 100, 844, 352);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		panel = new JCustomPanel1();
		panel.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		panel.setBounds(0, 11, 400, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tFechaCobro = new JFormattedTextField(Util.formatoFecha());
		tFechaCobro.setFont(new Font("Tahoma", Font.BOLD, 11));
		tFechaCobro.setBounds(10, 11, 98, 26);
		panel.add(tFechaCobro);
		
		tCliente = new PlaceholderTextField();
		tCliente.setEditable(false);
		tCliente.setFont(new Font("Tahoma", Font.BOLD, 11));
		tCliente.setPlaceholder("Cliente");
		tCliente.setBounds(10, 42, 254, 26);
		panel.add(tCliente);
		
		JCustomPanel2 customPanel2_1 = new JCustomPanel2();
		customPanel2_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		customPanel2_1.setAlignmentX(Component.RIGHT_ALIGNMENT);
		customPanel2_1.setLayout(null);
		customPanel2_1.setBounds(10, 131, 254, 160);
		panel.add(customPanel2_1);
		
		tGuarani = new PlaceholderTextField2();
		tGuarani.setPlaceholder("Guaran\u00ED");
		tGuarani.setEnabled(false);
		tGuarani.setBounds(61, 8, 176, 34);
		customPanel2_1.add(tGuarani);
		
		JLabel label_3 = new JLabel("");
		label_3.setIcon(new ImageIcon(TransCobranza.class.getResource("/img/PY.png")));
		label_3.setAlignmentX(Component.RIGHT_ALIGNMENT);
		label_3.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_3.setBounds(8, 8, 53, 47);
		customPanel2_1.add(label_3);
		
		tReal = new PlaceholderTextField2();
		tReal.setPlaceholder("Real");
		tReal.setEnabled(false);
		tReal.setBounds(62, 61, 175, 34);
		customPanel2_1.add(tReal);
		
		JLabel label_4 = new JLabel("");
		label_4.setIcon(new ImageIcon(TransCobranza.class.getResource("/img/BR.png")));
		label_4.setAlignmentX(Component.RIGHT_ALIGNMENT);
		label_4.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		label_4.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_4.setBounds(8, 57, 53, 44);
		customPanel2_1.add(label_4);
		
		tDolar = new PlaceholderTextField2();
		tDolar.setPlaceholder("Dolar");
		tDolar.setEnabled(false);
		tDolar.setBounds(61, 117, 176, 34);
		customPanel2_1.add(tDolar);
		
		JLabel label_5 = new JLabel("");
		label_5.setIcon(new ImageIcon(TransCobranza.class.getResource("/img/EEUU.png")));
		label_5.setAlignmentX(Component.RIGHT_ALIGNMENT);
		label_5.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		label_5.setFont(new Font("Tahoma", Font.PLAIN, 15));
		label_5.setBounds(8, 108, 53, 46);
		customPanel2_1.add(label_5);
		
		JLabel lblTotales = new JLabel("Deuda:");
		lblTotales.setFont(new Font("Times New Roman", Font.BOLD | Font.ITALIC, 17));
		lblTotales.setBounds(10, 106, 109, 14);
		panel.add(lblTotales);
		
		tDocumento = new PlaceholderTextField();
		tDocumento.setPlaceholder("Documento");
		tDocumento.setFont(new Font("Tahoma", Font.BOLD, 11));
		tDocumento.setEditable(false);
		tDocumento.setBounds(10, 73, 136, 26);
		panel.add(tDocumento);
		
		btnCobrar = new JButton("Cobrar");
		btnCobrar.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		btnCobrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(btnCobrar.getText().equals("Cobrar"))
					nuevo();
				else 
					guardar();
			}
		});
		btnCobrar.setBounds(274, 13, 116, 38);
		panel.add(btnCobrar);
		
		cbFactura = new JCheckBox("Factura");
		cbFactura.setVisible(false);
		cbFactura.setSelected(true);
		cbFactura.setBounds(167, 13, 97, 23);
		panel.add(cbFactura);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(408, 11, 424, 277);
		getContentPane().add(scrollPane);
		
		
		
		
		tabla = new CustomTable(new String[] {"#", "Cliente", "Documento"}, new int[] {10, 200, 50});
		scrollPane.setViewportView(tabla);
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				cargarFormulario();
			}
		});
		
		//Al iniciar deshavilita los campos y recupera los registros para la tabla
		inicializar();		
	}

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		clienteDao = new ClienteDao();
		listaCliente = clienteDao.recuperaClientesConDeuda();
		
		cargarGrilla();
		if (listaCliente.size()>0) 
			btnCobrar.setEnabled(true);
		else
			btnCobrar.setEnabled(false);
		
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		
		tabla.vaciar();
		
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Cliente c:listaCliente) {
			fila[0] = c.getId();
			fila[1] = c.getNombre();
			fila[2] = c.getDocumento();
			tabla.agregar(fila);
 		}
		
		
		//mantiene el foco en el ultimo registro cargado
		tabla.setSeleccion();
		
		
	}



	
	@Override
	public void guardar() {
		cargarAtributos();
		try {
			actualizarDeuda();
			cobDao = new CobranzaDao();
			cobDao.insertar(cobraza);
		} catch (Exception e) {
			e.printStackTrace();
			cobDao.rollback();
		}
		btnCobrar.setText("Cobrar");
		inicializar();
		imprimirFactura();
	}
	private void imprimirFactura() {
		if (cbFactura.isSelected()) {
			int s = JOptionPane.showConfirmDialog(null, "La Factura se imprimirá a nombre del Cliente", "Atención", JOptionPane.YES_NO_OPTION);
			if (s == JOptionPane.NO_OPTION) {
				tNombreFactura = new PlaceholderTextField();
				tNombreFactura.setPlaceholder("Nombre");
				tRuc = new PlaceholderTextField();
				tRuc.setPlaceholder("Ruc");
				tCiudad = new PlaceholderTextField();
				tCiudad.setPlaceholder("Ciudad");
				panelFac = new JPanel();
				panelFac.add(tNombreFactura);
				panelFac.add(tRuc);
				panelFac.add(tCiudad);
				panelFac.setLayout(new GridLayout(3, 1));
				while (tNombreFactura.getText().isEmpty()||tRuc.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, panelFac);
				}
				
				nombre = tNombreFactura.getText();
				documento = tRuc.getText();
				ciudad = tCiudad.getText();
			}else {
				nombre = cliente.getNombre();
				documento = cliente.getDocumento();
				ciudad = cliente.getDireccion();
			}
			
			System.out.println(nombre);
			System.out.println(documento);
			System.out.println(ciudad);
		}
	}

	private void actualizarDeuda() throws Exception {
		deuDao = new DeudaDao();
		listDeuda = deuDao.recuperarDeudasPorCliente(cliente.getId());
		for(Deuda d : listDeuda){
			deuDao = new DeudaDao();
			d.setEstado(false);
			deuDao.actualizar(d);
		}
	}

	public void cargarAtributos() {
		cobDao = new CobranzaDao();
		cobraza = new Cobranza();
		cobraza.setId(cobDao.recuperMaxId()+1);
		cobraza.setCliente(cliente);
		cobraza.setMonto(Util.stringADouble(tGuarani.getText()));
		cobraza.setFecha(FormatoFecha.stringToDate(tFechaCobro.getText()));
	}

	//deja la pantallaen su estado inicial
	@Override
	public void inicializar() {
		limpiarCampos();
		habilitarCampos(false);
		recuperaDatos();
		ultimaFila = -1;
	
	}

	@Override
	public void habilitarCampos(boolean b) {
		tFechaCobro.setEnabled(b);
		cbFactura.setVisible(b);
	}

	@Override
	public void advertencia(String texto, int t) {
		
	}

	@Override
	public void cargarFormulario() {
		if(tabla.getRowCount()==1)
			ultimaFila = -1;
		if (tabla.getSelectedRow()>=0 && tabla.getSelectedRow()!=ultimaFila) {
			ultimaFila=tabla.getSelectedRow();
			clienteDao = new ClienteDao();
			cliente = clienteDao.recuperarPorId((int) tabla.campo(0));
			if (cliente!=null) {
				deuDao = new DeudaDao();
				monto = deuDao.recuperarMontoDeuda(cliente.getId());
				
				tCliente.setText(cliente.getNombre());
				tDocumento.setText(cliente.getDocumento());
				tGuarani.setText(Util.formatoDecimal(monto));
				tDolar.setText(Util.formatoDecimal(monto/Util.cotizacionDolar));
				tReal.setText(Util.formatoDecimal(monto/Util.cotizacionReal));
			}
			
		}
	}

	@Override
	public void cargarFormularioProducto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpiarCampos() {
		tFechaCobro.setValue(null);
		tCliente.setText("");
		tDocumento.setText("");
		tGuarani.setText("");
		tDolar.setText("");
		tReal.setText("");
		cbFactura.setEnabled(true);
	}

	@Override
	public void buscar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarAtributosProductos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nuevo() {
		tFechaCobro.setValue(FormatoFecha.dateAString(new Date()));
		btnCobrar.setText("Confirmar");
		habilitarCampos(true);
	}

	@Override
	public void modificar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void eliminar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void salir() {
		dispose();
	}

	@Override
	public void cancelar() {
		// TODO Auto-generated method stub
		
	}	
}
