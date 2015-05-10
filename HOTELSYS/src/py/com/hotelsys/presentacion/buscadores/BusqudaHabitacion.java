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
import py.com.hotelsys.dao.HabitacionDao;
import py.com.hotelsys.dao.ProductoDao;
import py.com.hotelsys.dao.ProveedorDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaHabitacion;
import py.com.hotelsys.interfaces.InterfaceBusquedaProducto;
import py.com.hotelsys.interfaces.InterfaceBusquedaProveedor;
import py.com.hotelsys.modelo.Habitacion;
import py.com.hotelsys.modelo.Producto;
import py.com.hotelsys.modelo.Proveedor;
import py.com.hotelsys.util.VariablesDelSistema;

public class BusqudaHabitacion extends JDialog {


	private Timer timer;
	private TimerTask task;
	private PlaceholderTextField tBuscar;
	private HabitacionDao habitacionDao;
	private List<Habitacion> listaHabitacions;
	private CustomTable table;
	private Object[] fila;
	private InterfaceBusquedaHabitacion ibh;

	public void setIbh(InterfaceBusquedaHabitacion ibh) {
		this.ibh = ibh;
	}

	/**
	 * Create the dialog.
	 */
	public BusqudaHabitacion(JDialog dialog) {
		super(dialog,false);
		setBounds(100, 100, 540, 296);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 504, 204);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Descripcion", "Precio"}, new int[] {100, 200, 200});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ibh.cargar(listaHabitacions.get(table.getSelectedRow()));
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
		
		habitacionDao = new HabitacionDao();
		listaHabitacions = habitacionDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
		cargarGrilla();

	}
	
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				

				@Override
				public void run() {
					habitacionDao = new HabitacionDao();
					listaHabitacions = habitacionDao.cosultarPorFiltros(new String[]{tBuscar.getText()});
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
		for (Habitacion h:listaHabitacions) {
			fila[0] = h.getId();
			fila[1] = h.getDescripcion();
			fila[2] = VariablesDelSistema.formatoDecimal(h.getPrecio());
			table.agregar(fila);
 		}
		
		
		
	}
}
