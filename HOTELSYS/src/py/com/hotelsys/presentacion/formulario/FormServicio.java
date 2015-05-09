package py.com.hotelsys.presentacion.formulario;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.HabitacionDao;
import py.com.hotelsys.dao.ServicioDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Habitacion;
import py.com.hotelsys.modelo.Servicio;



@SuppressWarnings("serial")
public class FormServicio extends JDialog implements AbmBotonInterface {

	

	private ServicioDao serviciodao;
	private List<Servicio> lisServicios;
	private CustomTable tabla;
	private Object[] fila;
	private BotonGrup abmBoton;
	private String accion = "";
	private PlaceholderTextField tfDescripcionServicio;
	private PlaceholderTextField tfPrecioServicio;
	private JTextArea tfObsServicio;
	private PlaceholderTextField tfBuscar;
	private JPanel panel;
	private Servicio servicio;
	private JLabel label;
	private Timer timer;
	private TimerTask task;
	private int ultimaFila;


	/**
	 * Create the dialog.
	 */
	public FormServicio(JFrame frame) {
		super(frame);
		setTitle("Archivo de Servicio");
		setBounds(100, 100, 900, 410);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.GRAY));
		panel.setBounds(10, 11, 388, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tfDescripcionServicio = new PlaceholderTextField();
		tfDescripcionServicio.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfDescripcionServicio.setPlaceholder("Descripcion Servicio");
		tfDescripcionServicio.setBounds(25, 40, 301, 20);
		panel.add(tfDescripcionServicio);
		
		tfPrecioServicio = new PlaceholderTextField();
		tfPrecioServicio.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfPrecioServicio.setPlaceholder("Precio del Servicio");
		tfPrecioServicio.setBounds(25, 71, 173, 20);
		panel.add(tfPrecioServicio);
		
		tfObsServicio = new JTextArea("");
		tfObsServicio.setFont(new Font("Monospaced", Font.BOLD, 13));
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		tfObsServicio.setBorder(BorderFactory.createCompoundBorder(border, 
		            BorderFactory.createEmptyBorder(10, 10, 10, 10)));
		
		tfObsServicio.setRows(10);
		tfObsServicio.setLineWrap(true);
		tfObsServicio.setBounds(25, 135, 334, 77);
		panel.add(tfObsServicio);
		
		label = new JLabel("Observaci\u00F3n:");
		label.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		label.setBounds(25, 119, 107, 14);
		panel.add(label);
		
		abmBoton = new BotonGrup();
		abmBoton.setBounds(10, 324, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(399, 11, 462, 277);
		getContentPane().add(scrollPane);
		
		
		tfBuscar = new PlaceholderTextField();
		tfBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscar();
			}
		});
		tfBuscar.setPlaceholder("Criterio de Busqueda");
		tfBuscar.setBounds(523, 293, 350, 20);
		getContentPane().add(tfBuscar);
		
		
		tabla = new CustomTable(new String[] {"#", "Descripción Servicio", "Precio"}, new int[] {5, 190, 30});
		scrollPane.setViewportView(tabla);
		tabla.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				cargarFormulario();
			}
		});
		
		
		abmBoton.botones(false, accion);
		abmBoton.setAbi(this);
		
		//Al iniciar deshavilita los campos y recupera los registros para la tabla
		inicializar();		
	}

	//Metodo que recupera todos los registros de cliente para cargarlos a la tabla
	private void recuperaDatos() {
		serviciodao = new ServicioDao();
		lisServicios = serviciodao.recuperaTodo();
		
		cargarGrilla();
		if (lisServicios.size()>0)
			accion = "DATOS";
		else
			accion = "";
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		
		tabla.vaciar();
		
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Servicio s : lisServicios) {
			fila[0] = s.getId();
			fila[1] = s.getDescripcion();
			fila[2] = (int)s.getPrecio();
			tabla.agregar(fila);
 		}
		
		
		//mantiene el foco en el ultimo registro cargado
		tabla.setSeleccion();
		
		
	}

	@Override
	public void nuevo() {
		accion = "AGREGAR";
		limpiarCampos();
		habilitarCampos(true);
		abmBoton.botones(true, accion);
	}

	
	@Override
	public void modificar() {
		accion = "MODIFICAR";
		habilitarCampos(true);
		abmBoton.botones(true, accion);
	}

	@Override
	public void eliminar() {
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar el Servicio: "+tabla.campo(1)+"?","Atención",JOptionPane.YES_NO_OPTION);
		if (si==JOptionPane.YES_OPTION) {
			serviciodao = new ServicioDao();
			try {
				serviciodao.eliminar((int)tabla.campo(0));
	
			} catch (Exception e) {
				e.printStackTrace();
				serviciodao.rollback();
				advertencia("No se puede eliminar el Servicio "+tabla.campo(1)+". Esta en uso!",2);
			}
			inicializar();
		}
		
	}

	@Override
	public void salir() {
		dispose();
	}

	@Override
	public void guardar() {
		cargarAtributos();
		serviciodao = new ServicioDao();
		
		if(accion.equals("AGREGAR"))
			try {
				serviciodao.insertar(servicio);
				inicializar();
			} catch (Exception e) {
				serviciodao.rollback();
				advertencia("No se puede guardar el Servicio Los campos con * son obligatorios",2);
			}
		if (accion.equals("MODIFICAR"))
			try {
				serviciodao.actualizar(servicio);
				inicializar();
			} catch (Exception e) {
				serviciodao.rollback();
				advertencia("No se puede actualizar el Servicio. Los campos con * son obligatorios",2);
			}
		
		
		
		
	}

	@Override
	public void cancelar() {
		inicializar();
	}

	@Override
	public void habilitarCampos(boolean b) {
		tfDescripcionServicio.setEnabled(b);
		tfPrecioServicio.setEnabled(b);
		tfObsServicio.setEnabled(b);
		tfBuscar.setEnabled(!b);
		tabla.setEnabled(!b);
		if(b==false)
			tabla.requestFocus();
		else
			tfDescripcionServicio.requestFocus();
	}

	//carga los atributos del objeto al ser persistido
	@Override
	public void cargarAtributos() {
		
		servicio = new Servicio();
		if(accion.equals("AGREGAR")){
			serviciodao = new ServicioDao();
			servicio.setId(serviciodao.recuperMaxId()+1);
		}else
			servicio.setId((int) tabla.campo(0));
		if(!tfDescripcionServicio.getText().equals(""))
			servicio.setDescripcion(tfDescripcionServicio.getText());
		if(!tfPrecioServicio.getText().equals(""))
		servicio.setPrecio(Integer.parseInt (tfPrecioServicio.getText()));
		servicio.setObservacion(tfObsServicio.getText());
	}

	//deja la pantallaen su estado inicial
	@Override
	public void inicializar() {
		limpiarCampos();
		habilitarCampos(false);
		recuperaDatos();
		abmBoton.botones(false, accion);
		ultimaFila = -1;
	}

	
	//emite mensajes de forma dinamica de acuerdo al texto que se le envie
	@Override
	public void advertencia(String texto,int t) {
		JOptionPane.showMessageDialog(null, texto, "Atención", t);
	}

	@Override
	public void cargarFormulario() {
		if(tabla.getRowCount()==1)
			ultimaFila = -1;
		if (tabla.getSelectedRow()>=0 && tabla.getSelectedRow()!=ultimaFila) {
			ultimaFila=tabla.getSelectedRow();
			abmBoton.botones(false, accion);
			serviciodao = new ServicioDao();
			servicio = serviciodao.recuperarPorId((int) tabla.campo(0));
			if (servicio!=null) {
				tfDescripcionServicio.setText(servicio.getDescripcion());
				tfPrecioServicio.setText((int)servicio.getPrecio()+"");
				tfObsServicio.setText(servicio.getObservacion());
			}
			
		}
		
	}

	@Override
	public void limpiarCampos() {
		tfDescripcionServicio.setText("");
		tfPrecioServicio.setText("");
		tfObsServicio.setText("");
	}

	@Override
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					serviciodao = new ServicioDao();
					lisServicios = serviciodao.cosultarPorFiltros(new String[]{tfBuscar.getText()});
					cargarGrilla();
					timer.cancel();
					timer=null;
					task=null;
				}
			};
						
			timer.schedule(task, 1000);
		}
		
	}

	@Override
	public void cargarFormularioProducto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarAtributosProductos() {
		// TODO Auto-generated method stub
		
	}
		
	
	
	
}
