package py.com.hotelsys.presentacion.formulario;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import py.com.hotelsys.componentes.BotonGrup;
import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.JCustomPanel1;
import py.com.hotelsys.componentes.PlaceholderTextField;
import py.com.hotelsys.dao.CotizacionDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.modelo.Cotizacion;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.VariablesDelSistema;



@SuppressWarnings("serial")
public class FormCotizacion extends JDialog implements AbmBotonInterface {

	

	private CotizacionDao cotizacioDao;
	private List<Cotizacion> listCotizacion;
	private CustomTable tabla;
	private Object[] fila;
	private BotonGrup abmBoton;
	private String accion = "";
	private PlaceholderTextField tMonto;
	private JFormattedTextField tFecha;
	private JPanel panel;
	private Cotizacion cotizacion;
	
	private int ultimaFila;
	@SuppressWarnings("rawtypes")
	private JComboBox cbMoneda;


	/**
	 * Create the dialog.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public FormCotizacion(JFrame frame) {
		super(frame);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				VariablesDelSistema.cotizacionDelDia();
			}
		});
		setTitle("Cotizaci\u00F3n de Monedas");
		setBounds(100, 100, 681, 297);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		panel = new JCustomPanel1();
		panel.setBounds(10, 11, 174, 188);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tMonto = new PlaceholderTextField();
		tMonto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if ((e.getKeyChar()<'0' || e.getKeyChar()>'9') && e.getKeyCode() != KeyEvent.VK_BACK_SPACE && e.getKeyCode() != KeyEvent.VK_BACK_SPACE) {
					e.consume();
				}
			}
		});
		tMonto.setFont(new Font("Tahoma", Font.BOLD, 11));
		tMonto.setPlaceholder("Valor");
		tMonto.setBounds(22, 66, 113, 20);
		panel.add(tMonto);
		
		tFecha = new JFormattedTextField(VariablesDelSistema.formatoFecha());
		tFecha.setFont(new Font("Tahoma", Font.BOLD, 11));
		tFecha.setBounds(22, 97, 113, 20);
		panel.add(tFecha);
		
		cbMoneda = new JComboBox();
		cbMoneda.setModel(new DefaultComboBoxModel(new String[] {"Real", "Dolar"}));
		cbMoneda.setBounds(22, 35, 113, 20);
		panel.add(cbMoneda);
		
		abmBoton = new BotonGrup();
		abmBoton.setBounds(10, 215, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(204, 11, 462, 188);
		getContentPane().add(scrollPane);
		
		
		tabla = new CustomTable(new String[] {"#","Moneda", "Valor","Fecha"}, new int[] {0,100, 50, 50});
		tabla.ocultarColumna(0);
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
		cotizacioDao = new CotizacionDao();
		listCotizacion = cotizacioDao.recuperaTodo();
		
		cargarGrilla();
		if (listCotizacion.size()>0)
			accion = "DATOS";
		else
			accion = "";
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		
		tabla.vaciar();
		
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Cotizacion c : listCotizacion) {
			fila[0]=c.getId();
			switch (c.getMoneda()) {
			case 0:
				fila[1] = "REAL";
				break;

			case 1:
				fila[1] = "DOLAR";
				break;
			}
			
			fila[2] = c.getMonto();
			fila[3] = FormatoFecha.dateAString(c.getFecha());
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
		tFecha.setText(FormatoFecha.dateAString(new Date()));
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
		int si = JOptionPane.showConfirmDialog(null, "Esta seguro que desea eliminar la Cotización: "+tabla.campo(1)+"?","Atención",JOptionPane.YES_NO_OPTION);
		if (si==JOptionPane.YES_OPTION) {
			cotizacioDao = new CotizacionDao();
			try {
				cotizacioDao.eliminar((int)tabla.campo(0));
	
			} catch (Exception e) {
				e.printStackTrace();
				cotizacioDao.rollback();
				advertencia("No se puede eliminar el Cotización "+tabla.campo(1)+". Esta en uso!",2);
			}
			inicializar();
		}
		
	}

	@Override
	public void salir() {
		dispose();
		VariablesDelSistema.cotizacionDelDia();
	}

	@Override
	public void guardar() {
		cargarAtributos();
		cotizacioDao = new CotizacionDao();
		
		if(accion.equals("AGREGAR"))
			try {
				cotizacioDao.insertar(cotizacion);
				inicializar();
			} catch (Exception e) {
				e.printStackTrace();
				cotizacioDao.rollback();
				advertencia("Cotización ya existe en fecha "+tFecha.getText(),2);
			}
		if (accion.equals("MODIFICAR"))
			try {
				cotizacioDao.actualizar(cotizacion);
				inicializar();
			} catch (Exception e) {
				e.printStackTrace();
				cotizacioDao.rollback();
				advertencia("Cotización ya existe en fecha "+tFecha.getText(),2);
			}
		
		
		
		
	}

	@Override
	public void cancelar() {
		inicializar();
	}

	@Override
	public void habilitarCampos(boolean b) {
		if (!accion.equals("MODIFICAR")) {
			cbMoneda.setEnabled(b);
			tFecha.setEnabled(b);
		}
		
		tMonto.setEnabled(b);
		
		tabla.setEnabled(!b);
		if(b==false)
			tabla.requestFocus();
		else
			tMonto.requestFocus();
	}

	//carga los atributos del objeto al ser persistido
	@Override
	public void cargarAtributos() {
		
		cotizacion = new Cotizacion();
		if(accion.equals("AGREGAR")){
			cotizacioDao = new CotizacionDao();
			cotizacion.setId(cotizacioDao.recuperMaxId()+1);
		}else
			cotizacion.setId((int) tabla.campo(0));
		if(!tMonto.getText().equals(""))
			cotizacion.setMonto(Double.parseDouble((tMonto.getText())));
		if(!tFecha.getText().equals("__/__/____"))
			cotizacion.setFecha(FormatoFecha.stringToDate((tFecha.getText())));
		cotizacion.setMoneda(cbMoneda.getSelectedIndex());
		
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
			cotizacioDao = new CotizacionDao();
			System.out.println(tabla.campo(0));
			cotizacion = cotizacioDao.recuperarPorId((int) tabla.campo(0));
				tMonto.setText(cotizacion.getMonto()+"");
				tFecha.setValue(FormatoFecha.dateAString(cotizacion.getFecha()));
				cbMoneda.setSelectedIndex(cotizacion.getMoneda());
			}
			
		}
		

	@Override
	public void limpiarCampos() {
		tMonto.setText("");
		tFecha.setValue(null);;
		cbMoneda.setSelectedIndex(0);
	}

	@Override
	public void cargarFormularioProducto() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buscar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void cargarAtributosProductos() {
		// TODO Auto-generated method stub
		
	}

}
