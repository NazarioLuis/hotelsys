package py.com.hotelsys.presentacion.transacciones;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

public class PantallaCompra extends JDialog implements TranBotonInterface{


	private Timer timer;
	private TimerTask task;
	private PlaceholderTextField tBuscar;
	private CompraDao compraDao;
	private List<Compra> listaCompra;
	private CustomTable table;
	private Object[] fila;
	private TransCompra transCompra;
	private String accion;

	
	public static void main(String[] args) {
		PantallaCompra compra = new PantallaCompra(null);
		compra.setVisible(true);
	}
	/**
	 * Create the dialog.
	 */
	public PantallaCompra(JDialog dialog) {
		super(dialog,false);
		setBounds(100, 100, 935, 501);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 141, 763, 294);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Factura", "Timbrado", "Proveedor", "Fecha", "Monto", "Estado"}, new int[] {40, 50, 100, 100, 50, 50, 50});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
			}
		});
		scrollPane.setViewportView(table);
		
		tBuscar = new PlaceholderTextField();
		tBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscar();
			}
		});
		tBuscar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tBuscar.setPlaceholder("Buscar");
		tBuscar.setBounds(10, 11, 408, 26);
		getContentPane().add(tBuscar);
		
		BotonGrup2 botonGrup2 = new BotonGrup2();
		botonGrup2.setTbi(this);
		botonGrup2.setBounds(798, 141, 111, 294);
		getContentPane().add(botonGrup2);
		
		compraDao = new CompraDao();
		listaCompra = compraDao.recuperaTodo();
		cargarGrilla();

	}
	
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				

				@Override
				public void run() {
					compraDao = new CompraDao();
					listaCompra = compraDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
					cargarGrilla();
					timer.cancel();
					timer=null;
					task=null;
				}
			};
						
			timer.schedule(task, 1000);		
		}
		
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
		transCompra.setVisible(true);
	}

	@Override
	public void ver() {
		if (table.getSelectedRow() >= 0) {
			accion = "VER";
			transCompra = new TransCompra(this,listaCompra.get(table.getSelectedRow()),accion);
			transCompra.setVisible(true);
		}
		
	}

	@Override
	public void anular() {
		if (table.getSelectedRow() >= 0) {
			accion = "ANULAR";
			transCompra = new TransCompra(this,listaCompra.get(table.getSelectedRow()),accion);
			transCompra.setVisible(true);
		}
	}

	@Override
	public void salir() {
		dispose();
	}

	

	
}
