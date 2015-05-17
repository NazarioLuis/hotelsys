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
import py.com.hotelsys.dao.ClienteDao;
import py.com.hotelsys.interfaces.InterfaceBusquedaCliente;
import py.com.hotelsys.modelo.Cliente;


@SuppressWarnings("serial")
public class BusqudaCliente extends JDialog {


	private Timer timer;
	private TimerTask task;
	private PlaceholderTextField tBuscar;
	private ClienteDao clienteDao;
	private List<Cliente> listaClientes;
	private CustomTable table;
	private Object[] fila;
	private InterfaceBusquedaCliente ibc;

	public void setIbc(InterfaceBusquedaCliente ibc) {
		this.ibc = ibc;
	}

	
	public BusqudaCliente(JDialog dialog) {
		super(dialog,false);
		setBounds(100, 100, 540, 296);
		getContentPane().setLayout(null);
		
		setLocationRelativeTo(null);
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 42, 504, 204);
		getContentPane().add(scrollPane);
		
		table = new CustomTable(new String[] {"#", "Descripcion", "Nro documento"}, new int[] {100, 200, 200});
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ibc.cargar(listaClientes.get(table.getSelectedRow()));
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
		
		clienteDao = new ClienteDao();
		listaClientes = clienteDao.recuperarPorFiltros(new String[]{tBuscar.getText()});
		cargarGrilla();

	}
	
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				

				@Override
				public void run() {
					clienteDao = new ClienteDao();
					listaClientes = clienteDao.recuperarPorFiltros(new String[]{tBuscar.getText()});
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
		for (Cliente h:listaClientes) {
			fila[0] = h.getId();
			fila[1] = h.getNombre();
			fila[2] = h.getDocumento();
			table.agregar(fila);
 		}
		
		
		
	}
}
