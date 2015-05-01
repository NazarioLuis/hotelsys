package py.com.hotelsys.presentacion.transacciones;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import py.com.hotelsys.componentes.BotonGrup2;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.CompraDao;
import py.com.hotelsys.interfaces.TranBotonInterface;
import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.util.FormatoFecha;

import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.text.MaskFormatter;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PantallaCompra extends JDialog implements TranBotonInterface{


	private MaskFormatter Maskfecha;
	private PlaceholderTextField tBuscar;
	private CompraDao compraDao;
	private List<Compra> listaCompra;
	private CustomTable table;
	private Object[] fila;
	private TransCompra transCompra;
	private String accion;
	private JFormattedTextField tFecha2;
	private JFormattedTextField tFecha1;

	
	public static void main(String[] args) {
		PantallaCompra compra = new PantallaCompra(null);
		compra.setVisible(true);
	}
	/**
	 * Create the dialog.
	 */
	public PantallaCompra(JDialog dialog) {
		super(dialog,false);
		setBounds(100, 100, 917, 431);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 763, 294);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Factura", "Timbrado", "Proveedor", "Fecha", "Monto", "Estado"}, new int[] {40, 50, 100, 100, 50, 50, 50});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane.setViewportView(table);
		
		BotonGrup2 botonGrup2 = new BotonGrup2();
		botonGrup2.setTbi(this);
		botonGrup2.setBounds(783, 84, 111, 193);
		getContentPane().add(botonGrup2);
		
		JPanel panel = new JPanel();
		panel.setBackground(SystemColor.inactiveCaption);
		panel.setBounds(10, 11, 763, 67);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tBuscar = new PlaceholderTextField();
		tBuscar.setBounds(10, 11, 408, 24);
		panel.add(tBuscar);
		
		tBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tBuscar.setPlaceholder("Buscar");
		
		JLabel lblFechaDeCompra = new JLabel("Fecha de Compra");
		lblFechaDeCompra.setBounds(59, 45, 118, 14);
		panel.add(lblFechaDeCompra);
		lblFechaDeCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		
		try {
			Maskfecha = new MaskFormatter("##/##/####");
			Maskfecha.setPlaceholderCharacter('_');
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		tFecha1 = new JFormattedTextField(Maskfecha);
		
		tFecha1.setBounds(187, 39, 103, 26);
		panel.add(tFecha1);
		
		JLabel lblA = new JLabel("a");
		lblA.setBounds(293, 45, 12, 14);
		panel.add(lblA);
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		
		tFecha2 = new JFormattedTextField(Maskfecha);
		tFecha2.setBounds(315, 39, 103, 26);
		panel.add(tFecha2);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				buscar();
			}
		});
		btnConfirmar.setBounds(642, 11, 111, 35);
		panel.add(btnConfirmar);
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarAtributos() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void inicializar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void advertencia(String texto, int t) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarFormulario() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void limpiarCampos() {
		// TODO Auto-generated method stub
		
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
}
