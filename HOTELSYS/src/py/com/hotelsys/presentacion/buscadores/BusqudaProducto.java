package py.com.hotelsys.presentacion.buscadores;

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

import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaProducto;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.util.VariablesDelSistema;

public class BusqudaProducto extends JDialog {


	private Timer timer;
	private TimerTask task;
	private PlaceholderTextField tBuscar;
	private ProductoDao productoDao;
	private List<Producto> listaProducto;
	private CustomTable table;
	private Object[] fila;
	private InterfaceBusquedaProducto ibp;

	public void setIbp(InterfaceBusquedaProducto ibp) {
		this.ibp = ibp;
	}

	/**
	 * Create the dialog.
	 */
	public BusqudaProducto(JDialog dialog) {
		super(dialog,false);
		setBounds(100, 100, 540, 296);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 504, 204);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Descripcion","Cant.", "Precio"}, new int[] {50, 200,100, 100});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ibp.cargar(listaProducto.get(table.getSelectedRow()));
					dispose();
				}
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
		
		productoDao = new ProductoDao();
		listaProducto = productoDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
		cargarGrilla();

	}
	
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				

				@Override
				public void run() {
					productoDao = new ProductoDao();
					listaProducto = productoDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
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
		for (Producto p:listaProducto) {
			fila[0] = p.getId();
			fila[1] = p.getDescripcion();
			fila[2] = p.getStock().getCantidad();
			fila[3] = VariablesDelSistema.formatoDecimal(p.getStock().getPrecio());
			table.agregar(fila);
 		}
		
		
		
	}
}
