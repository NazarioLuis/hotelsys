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

import py.com.hotelsys.componentes.BotonGrup4;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.JCustomPanel2;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.SalidaStockDao;
import py.com.hotelsys.interfaces.TranBotonInterface;
import py.com.hotelsys.modelo.SalidaStock;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.Util;

@SuppressWarnings("serial")
public class PantallaSalida extends JDialog implements TranBotonInterface{


	
	private PlaceholderTextField tBuscar;
	private SalidaStockDao salidaDao;
	private List<SalidaStock> listaSalida;
	private CustomTable table;
	private Object[] fila;
	private TransSalida transSalid;
	private String accion;
	private JFormattedTextField tFecha2;
	private JFormattedTextField tFecha1;

	
	
	
	public PantallaSalida(JFrame dialog) {
		super(dialog,false);
		setTitle("Salidas de Productos del Stock");
		setBounds(100, 100, 703, 431);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 84, 529, 294);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Descripcion", "Fecha" , "Estado"}, new int[] {30, 300,80, 50});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane.setViewportView(table);
		
		JPanel panel = new JCustomPanel1();
		panel.setBounds(10, 11, 529, 70);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tBuscar = new PlaceholderTextField();
		tBuscar.setBounds(60, 11, 408, 24);
		panel.add(tBuscar);
		
		tBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tBuscar.setPlaceholder("Buscar");
		
		JLabel lblFechaDeCompra = new JLabel("Fecha de Compra");
		lblFechaDeCompra.setBounds(109, 45, 118, 14);
		panel.add(lblFechaDeCompra);
		lblFechaDeCompra.setHorizontalAlignment(SwingConstants.RIGHT);
		
		
		tFecha1 = new JFormattedTextField(Util.formatoFecha());
		
		tFecha1.setBounds(237, 39, 103, 26);
		panel.add(tFecha1);
		
		JLabel lblA = new JLabel("a");
		lblA.setBounds(339, 46, 26, 14);
		panel.add(lblA);
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		
		tFecha2 = new JFormattedTextField(Util.formatoFecha());
		tFecha2.setBounds(365, 39, 103, 26);
		panel.add(tFecha2);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(559, 11, 111, 35);
		getContentPane().add(btnConfirmar);
		
		JPanel panel_1 = new JCustomPanel2();
		panel_1.setBounds(549, 84, 132, 222);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		BotonGrup4 botonGrup4 = new BotonGrup4();
		botonGrup4.setBounds(10, 11, 111, 193);
		panel_1.add(botonGrup4);
		botonGrup4.setTbi(this);
		
		
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
					salidaDao = new SalidaStockDao();
					listaSalida = salidaDao.recuperarPorFiltros(new String[]{tBuscar.getText(),fecha1,fecha2});
					cargarGrilla();
					
			
	}
	
	private void cargarGrilla() {
		table.vaciar();
		
		int i = 0;
		fila = new Object[table.getColumnCount()];
		for (SalidaStock s:listaSalida) {
			if (i != s.getId()) {
				i = s.getId();
				fila[0] = s.getId();
				fila[1] = s.getDescripcion();
				fila[2] = FormatoFecha.dateAString(s.getFecha());
				
				if (s.isEstado())
					fila[3] = "Activo";
				else
					fila[3] = "Anulado";
				table.agregar(fila);
			}
			
			
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
		transSalid = new TransSalida(this,null,accion);
		transSalid.setTbi(this);
		transSalid.setVisible(true);
	}

	@Override
	public void ver() {
		if (table.getSelectedRow() >= 0) {
			accion = "VER";
			salidaDao = new SalidaStockDao();
			transSalid = new TransSalida(this,salidaDao.recuperarPorId((int) table.campo(0)),accion);
			transSalid.setTbi(this);
			transSalid.setVisible(true);
		}
		
	}

	@Override
	public void anular() {
		if (table.getSelectedRow() >= 0) {
			accion = "ANULAR";
			salidaDao = new SalidaStockDao();
			transSalid = new TransSalida(this,salidaDao.recuperarPorId((int) table.campo(0)),accion);
			transSalid.setTbi(this);
			transSalid.setVisible(true);
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



	@Override
	public void confimar() {
		// TODO Auto-generated method stub
		
	}
}
