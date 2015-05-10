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
import py.com.hotelsys.dao.EntradaStockDao;
import py.com.hotelsys.interfaces.TranBotonInterface;
import py.com.hotelsys.modelo.EntradaStock;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.VariablesDelSistema;

@SuppressWarnings("serial")
public class PantallaEntrada extends JDialog implements TranBotonInterface{


	
	private PlaceholderTextField tBuscar;
	private EntradaStockDao entradaDao;
	private List<EntradaStock> listaEntrada;
	private CustomTable table;
	private Object[] fila;
	private TransEntrada transEntrad;
	private String accion;
	private JFormattedTextField tFecha2;
	private JFormattedTextField tFecha1;

	
	
	
	public PantallaEntrada(JFrame dialog) {
		super(dialog,false);
		setTitle("Entradas de Productos al Stock");
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
		
		
		tFecha1 = new JFormattedTextField(VariablesDelSistema.formatoFecha());
		
		tFecha1.setBounds(237, 39, 103, 26);
		panel.add(tFecha1);
		
		JLabel lblA = new JLabel("a");
		lblA.setBounds(339, 46, 26, 14);
		panel.add(lblA);
		lblA.setHorizontalAlignment(SwingConstants.CENTER);
		
		tFecha2 = new JFormattedTextField(VariablesDelSistema.formatoFecha());
		tFecha2.setBounds(365, 39, 103, 26);
		panel.add(tFecha2);
		
		JButton btnConfirmar = new JButton("Confirmar");
		btnConfirmar.setBounds(559, 11, 111, 35);
		getContentPane().add(btnConfirmar);
		
		JPanel panel_1 = new JCustomPanel2();
		panel_1.setBounds(549, 84, 132, 222);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		BotonGrup2 botonGrup2 = new BotonGrup2();
		botonGrup2.setBounds(10, 11, 111, 193);
		panel_1.add(botonGrup2);
		botonGrup2.setTbi(this);
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
					entradaDao = new EntradaStockDao();
					listaEntrada = entradaDao.cosultarPorFiltros(new String[]{tBuscar.getText(),fecha1,fecha2});
					cargarGrilla();
					
			
	}
	
	private void cargarGrilla() {
		table.vaciar();
		
		
		fila = new Object[table.getColumnCount()];
		for (EntradaStock e:listaEntrada) {
			fila[0] = e.getId();
			fila[1] = e.getDescripcion();
			fila[2] = FormatoFecha.dateAString(e.getFecha());
			
			if (e.isEstado())
				fila[3] = "Activo";
			else
				fila[3] = "Anulado";
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
		transEntrad = new TransEntrada(this,null,accion);
		transEntrad.setTbi(this);
		transEntrad.setVisible(true);
	}

	@Override
	public void ver() {
		if (table.getSelectedRow() >= 0) {
			accion = "VER";
			transEntrad = new TransEntrada(this,listaEntrada.get(table.getSelectedRow()),accion);
			transEntrad.setTbi(this);
			transEntrad.setVisible(true);
		}
		
	}

	@Override
	public void anular() {
		if (table.getSelectedRow() >= 0) {
			accion = "ANULAR";
			transEntrad = new TransEntrada(this,listaEntrada.get(table.getSelectedRow()),accion);
			transEntrad.setTbi(this);
			transEntrad.setVisible(true);
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
