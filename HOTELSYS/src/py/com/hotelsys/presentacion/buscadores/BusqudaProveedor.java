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
import py.com.hotelsys.dao.ProveedorDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaProveedor;
import py.com.hotelsys.modelo.Proveedor;

@SuppressWarnings("serial")
public class BusqudaProveedor extends JDialog {


	private Timer timer;
	private TimerTask task;
	private PlaceholderTextField tBuscar;
	private ProveedorDao proveedorDao;
	private List<Proveedor> listaProveedor;
	private CustomTable table;
	private Object[] fila;
	private InterfaceBusquedaProveedor ibp;

	public void setIbp(InterfaceBusquedaProveedor ibp) {
		this.ibp = ibp;
	}

	/**
	 * Create the dialog.
	 */
	public BusqudaProveedor(JDialog dialog) {
		super(dialog,false);
		setBounds(100, 100, 540, 296);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 504, 204);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Nombre", "RUC"}, new int[] {60, 250, 150});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ibp.cargar(listaProveedor.get(table.getSelectedRow()));
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
		
		proveedorDao = new ProveedorDao();
		listaProveedor = proveedorDao.recuperarPorFiltros(new String[]{tBuscar.getText()});
		cargarGrilla();

	}
	
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				

				@Override
				public void run() {
					proveedorDao = new ProveedorDao();
					listaProveedor = proveedorDao.recuperarPorFiltros(new String[]{tBuscar.getText()});
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
		for (Proveedor p:listaProveedor) {
			fila[0] = p.getId();
			fila[1] = p.getNombre();
			fila[2] = p.getDocumento();
			table.agregar(fila);
 		}
		
		
		
	}
}
