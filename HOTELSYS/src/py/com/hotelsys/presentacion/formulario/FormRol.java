package py.com.hotelsys.presentacion.formulario;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JDialog;
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
import py.com.hotelsys.dao.RolDao;
import py.com.hotelsys.interfaces.AbmBotonInterface;
import py.com.hotelsys.interfaces.InterfaceRol;
import py.com.hotelsys.modelo.MenuItem;
import py.com.hotelsys.modelo.Rol;
import py.com.hotelsys.util.OpcionesDeUsuario;
import py.com.hotelsys.util.Util;



@SuppressWarnings("serial")
public class FormRol extends JDialog implements AbmBotonInterface {


	private RolDao rolDao;
	private List<Rol> listRol;
	private CustomTable tabla;
	private Object[] fila;
	private BotonGrup abmBoton;
	private String accion = "";
	private PlaceholderTextField tfDescripcion;
	private PlaceholderTextField tfBuscar;
	private JPanel panel;
	private Rol rol;
	private Timer timer;
	private TimerTask task;
	private int ultimaFila;
	private CustomTable tablaPermi;
	private String permisos="";
	private boolean aux = false;
	private InterfaceRol ir;


	/**
	 * @wbp.parser.constructor
	 */
	public FormRol(JDialog dialog) {
		super(dialog);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				
				ir.cargarRol();
				
			}
		});
		inicializarComponentes();
		aux = true;
	}
	
	
	public FormRol(JFrame frame) {
		super(frame);
		inicializarComponentes();
	}
	public void inicializarComponentes() {
		
		setTitle("Archivo de Rol");
		setBounds(100, 100, 774, 410);
		getContentPane().setLayout(null);
		setResizable(false);
		setLocationRelativeTo(null);
		
		panel = new JCustomPanel1();
		panel.setBounds(10, 11, 386, 302);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		tfDescripcion = new PlaceholderTextField();
		tfDescripcion.setFont(new Font("Tahoma", Font.BOLD, 11));
		tfDescripcion.setPlaceholder("Descripcion del Rol");
		tfDescripcion.setBounds(22, 11, 301, 20);
		
		panel.add(tfDescripcion);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 50, 366, 241);
		panel.add(scrollPane_1);
		
		tablaPermi = new CustomTable(new String[] {"#", "Funcion", "P"}, new int[] {0, 290, 15},new int[] {2}){
			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 2) {
					return Boolean.class;
				}
				return super.getColumnClass(column);
			}
		};
		tablaPermi.ocultarColumna(0);
		scrollPane_1.setViewportView(tablaPermi);
		
		
		abmBoton = new BotonGrup();
		abmBoton.setBounds(10, 324, 647, 33);
		getContentPane().add(abmBoton);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(399, 11, 363, 277);
		getContentPane().add(scrollPane);
		
		
		tfBuscar = new PlaceholderTextField();
		tfBuscar.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				buscar();
			}
		});
		tfBuscar.setPlaceholder("Criterio de Busqueda");
		tfBuscar.setBounds(412, 293, 350, 20);
		getContentPane().add(tfBuscar);
		
		
		tabla = new CustomTable(new String[] {"#", "Descripción Servicio"}, new int[] {5, 190});
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
		rolDao = new RolDao();
		listRol = rolDao.recuperarTodo();
		
		cargarGrilla();
		if (listRol.size()>0)
			accion = "DATOS";
		else
			accion = "";
	}

	//Metodo que rellena la tabla con los datos obtenidos
	private void cargarGrilla() {
		
		tabla.vaciar();
		
		
		
		fila = new Object[tabla.getColumnCount()];
		for (Rol r : listRol) {
			fila[0] = r.getId();
			fila[1] = r.getDescri();
			
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
			rolDao = new RolDao();
			try {
				rolDao.eliminar((int)tabla.campo(0));
	
			} catch (Exception e) {
				e.printStackTrace();
				rolDao.rollback();
				advertencia("No se puede eliminar el Rol "+tabla.campo(1)+". Esta en uso!",2);
			}
			inicializar();
		}
		
	}

	@Override
	public void salir() {
		if (aux) {
			ir.cargarRol();
		}
		dispose();
	}

	@Override
	public void guardar() {
		if (comprobarVacio()) {
			cargarAtributos();
			rolDao = new RolDao();
			
			if(accion.equals("AGREGAR"))
				try {
					rolDao.insertar(rol);
					inicializar();
				} catch (Exception e) {
					rolDao.rollback();
					advertencia("Ya existe el rol "+tfDescripcion.getText(),2);
				}
			if (accion.equals("MODIFICAR"))
				try {
					rolDao.actualizar(rol);
					inicializar();
				} catch (Exception e) {
					rolDao.rollback();
					advertencia("Ya existe el rol "+tfDescripcion.getText(),2);
				}
			
			
		}
			
		
	}

	@Override
	public void cancelar() {
		inicializar();
	}

	@Override
	public void habilitarCampos(boolean b) {
		tfDescripcion.setEnabled(b);
		tablaPermi.setEnabled(b);
		tfBuscar.setEnabled(!b);
		tabla.setEnabled(!b);
		if(b==false)
			tabla.requestFocus();
		else
			tfDescripcion.requestFocus();
	}

	//carga los atributos del objeto al ser persistido
	@Override
	public void cargarAtributos() {
		
		rol = new Rol();
		if(accion.equals("AGREGAR")){
			rolDao = new RolDao();
			rol.setId(rolDao.recuperMaxId()+1);
		}else
			rol.setId((int) tabla.campo(0));
		if(!tfDescripcion.getText().equals(""))
			rol.setDescri(tfDescripcion.getText());
		
		for (int i = 0; i < tablaPermi.getRowCount(); i++) {
			System.out.println((boolean) tablaPermi.campo(i,2));
			if ((boolean) tablaPermi.campo(i,2)) {
				if(!permisos.equals(""))
					permisos += "-";
				permisos += tablaPermi.campo(i,0).toString();
				
			}
		}
		
		rol.setPermiso(permisos);
		
		
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
			rolDao = new RolDao();
			rol = rolDao.recuperarPorId((int) tabla.campo(0));
			if (rol!=null) {
				tfDescripcion.setText(rol.getDescri());
				
			}
			
			cargarTablaPermisos(Util.stringAArray(rol.getPermiso()));
			
		}
		
	}

	@Override
	public void limpiarCampos() {
		tfDescripcion.setText("");
		permisos = "";
		cargarTablaPermisosInicial();
	}
	
	private void cargarTablaPermisos(List<String> listPermi) {
		
		tablaPermi.vaciar();
		
		
		fila = new Object[tablaPermi.getColumnCount()];
		for (String p:listPermi) {
			System.out.println(p);
		}
		
		for (MenuItem mi: OpcionesDeUsuario.getMenuItems()) {
			fila[0] = mi.getId();
			fila[1] = mi.getDescri();
			
			
			fila[2] = listPermi.contains(mi.getId()+"");
			tablaPermi.getModelo().addRow(fila);
		}
	}

	private void cargarTablaPermisosInicial() {
		tablaPermi.vaciar();
		
		
		fila = new Object[tablaPermi.getColumnCount()];
		System.out.println(OpcionesDeUsuario.getMenuItems());
		for (MenuItem mi: OpcionesDeUsuario.getMenuItems()) {
			fila[0] = mi.getId();
			fila[1] = mi.getDescri();
			fila[2] = false;
			tablaPermi.getModelo().addRow(fila);
		}
	}

	@Override
	public void buscar() {
		if (timer==null&&task==null) {
			timer = new Timer();
			task = new TimerTask() {
				@Override
				public void run() {
					rolDao = new RolDao();
					listRol = rolDao.recuperarPorFiltros(new String[]{tfBuscar.getText()});
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
	
	public void setIr(InterfaceRol ir) {
		this.ir = ir;
	}
	
	private boolean comprobarVacio() {
		if (tfDescripcion.getText().isEmpty()) {
			advertencia("Debe ingresar una descripción", 2);
			tfDescripcion.requestFocus();
			return false;
		}
		
		
		return true;
	}
}
