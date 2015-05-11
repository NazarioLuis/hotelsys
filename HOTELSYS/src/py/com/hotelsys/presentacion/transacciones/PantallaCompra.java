package py.com.hotelsys.presentacion.transacciones;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import py.com.hotelsys.componentes.BotonGrup2;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.JCustomPanel2;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.CompraDao;
import py.com.hotelsys.interfaces.TranBotonInterface;
import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.Util;

@SuppressWarnings("serial")
public class PantallaCompra extends JDialog implements TranBotonInterface{


	
	private PlaceholderTextField tBuscar;
	private CompraDao compraDao;
	private List<Compra> listaCompra;
	private CustomTable table;
	private Object[] fila;
	private TransCompra transCompra;
	private String accion;
	private JFormattedTextField tFecha2;
	private JFormattedTextField tFecha1;

	
	
	
	public PantallaCompra(JFrame dialog) {
		super(dialog,false);
		setTitle("Compras Realizadas");
		setBounds(100, 100, 934, 410);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 70, 763, 291);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Factura", "Timbrado", "Proveedor", "Fecha", "Monto", "Estado"}, new int[] {20, 100, 100, 100, 50, 50, 50});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel = new JCustomPanel1();
		panel.setBounds(10, 11, 763, 48);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tBuscar = new PlaceholderTextField();
		tBuscar.setBounds(10, 11, 378, 24);
		panel.add(tBuscar);
		
		tBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tBuscar.setPlaceholder("Buscar");
		
		JLabel lblFechaDeCompra = new JLabel("Fecha de Compra");
		lblFechaDeCompra.setBounds(394, 15, 118, 14);
		panel.add(lblFechaDeCompra);
		lblFechaDeCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		tFecha1 = new JFormattedTextField(Util.formatoFecha());
		
		tFecha1.setBounds(522, 9, 103, 26);
		panel.add(tFecha1);
		
		JLabel lblA = new JLabel("a");
		lblA.setBounds(624, 15, 27, 14);
		panel.add(lblA);
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		
		tFecha2 = new JFormattedTextField(Util.formatoFecha());
		tFecha2.setBounds(650, 9, 103, 26);
		panel.add(tFecha2);
		
		JPanel panel_1 = new JCustomPanel2();
		panel_1.setBounds(776, 70, 132, 217);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		BotonGrup2 botonGrup2 = new BotonGrup2();
		botonGrup2.setBounds(10, 13, 111, 193);
		panel_1.add(botonGrup2);
		botonGrup2.setTbi(this);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(783, 11, 111, 35);
		getContentPane().add(btnConfirmar);
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		
		buscar();

	}
	
	
	
	public void buscar() {
		String fecha1;
		String fecha2;
					if (tFecha1.getText().equals("__/__/____")) {
						fecha1 = "01/01/2001";
					}else{
						fecha1 = tFecha1.getText();
					}
					if (tFecha2.getText().equals("__/__/____")) {
						fecha2 = "01/01/2090";
					}else{
						fecha2 = tFecha2.getText();
					}
					compraDao = new CompraDao();
					listaCompra = compraDao.cosultarPorFiltros(new String[]{tBuscar.getText(),fecha1,fecha2});
					System.out.println(listaCompra.size());
					cargarGrilla();
					
			
	}
	
	private void cargarGrilla() {
		table.vaciar();
		
		
		fila = new Object[table.getColumnCount()];
		for (Compra c:listaCompra) {
			fila[0] = c.getId();
			fila[1] = c.getFactura();
			fila[2] = c.getTimbrado();
			fila[3] = c.getProveedor().getNombre();
			fila[4] = FormatoFecha.dateAString(c.getFecha());
			fila[5] = c.getTotal();
			if (c.isEstado())
				fila[6] = "Activo";
			else
				fila[6] = "Anulado";
			table.agregar(fila);
 		}
		
		
		
	}

	@Override
	public void habilitarCampos(boolean b) {
	
		
	}

	@Override
	public void cargarAtributos() {
		
		
	}

	@Override
	public void inicializar() {
		
		
	}

	@Override
	public void advertencia(String texto, int t) {
		
		
	}

	@Override
	public void cargarFormulario() {
		
		
	}

	@Override
	public void limpiarCampos() {
		
		
	}

	@Override
	public void nuevo() {
		accion = "AGREGAR";
		transCompra = new TransCompra(this,null,accion);
		transCompra.setTbi(this);
		transCompra.setVisible(true);
	}

	@Override
	public void ver() {
		if (table.getSelectedRow() >= 0) {
			accion = "VER";
			transCompra = new TransCompra(this,listaCompra.get(table.getSelectedRow()),accion);
			transCompra.setTbi(this);
			transCompra.setVisible(true);
		}
		
	}

	@Override
	public void anular() {
		if (table.getSelectedRow() >= 0) {
			accion = "ANULAR";
			transCompra = new TransCompra(this,listaCompra.get(table.getSelectedRow()),accion);
			transCompra.setTbi(this);
			transCompra.setVisible(true);
		}
	}

	@Override
	public void salir() {
		dispose();
	}



	@Override
	public void cargarFormularioProducto() {
		
		
	}



	@Override
	public void cargarAtributosProductos() {
		// TODO Auto-generated method stub
		
	}
}
