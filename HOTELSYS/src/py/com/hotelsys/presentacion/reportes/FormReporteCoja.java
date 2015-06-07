package py.com.hotelsys.presentacion.reportes;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

import py.com.hotelsys.componentes.CustomTable;
import py.com.hotelsys.componentes.PlaceholderTextField2;
import py.com.hotelsys.dao.CajaDao;
import py.com.hotelsys.dao.CobranzaDao;
import py.com.hotelsys.modelo.Caja;
import py.com.hotelsys.util.FormatoFecha;
import py.com.hotelsys.util.Util;

@SuppressWarnings("serial")
public class FormReporteCoja extends JDialog {


	private List<Caja> listCaja;
	private CajaDao cajaDao;
	private int d = 0;
	private CustomTable tablaCaja;
	private Object[] fila;
	private CobranzaDao cobranzaDao;
	private AbstractButton btnAnterior;
	private JButton btnSiguiente;
	private Double estadia;
	private Double Producto;
	private PlaceholderTextField2 tTotal;
	private PlaceholderTextField2 tProductos;
	private PlaceholderTextField2 tEstadia;

	/**
	 * Create the dialog.
	 */
	public FormReporteCoja(JFrame f) {
		super(f);
		setTitle("Resumen de Caja");
		setBounds(100, 100, 569, 476);
		getContentPane().setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 10, 533, 170);
		getContentPane().add(scrollPane);
		
		tablaCaja = new CustomTable(new String[] {"Cajero", "Apertura", "Cierre", "Monto"}, new int[] {200, 150, 150, 80});
		tablaCaja.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				cargarFormulario();
			}
		});
		scrollPane.setViewportView(tablaCaja);
		
		btnAnterior = new JButton("Anterior");
		btnAnterior.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				d=d-10;
				recuperarDatos();
			}
		});
		btnAnterior.setIcon(new ImageIcon(FormReporteCoja.class.getResource("/img/prev.png")));
		btnAnterior.setBounds(128, 188, 118, 33);
		getContentPane().add(btnAnterior);
		
		btnSiguiente = new JButton("Siguiente");
		btnSiguiente.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				d=d+10;
				recuperarDatos();
			}
		});
		btnSiguiente.setIcon(new ImageIcon(FormReporteCoja.class.getResource("/img/next.png")));
		btnSiguiente.setEnabled(false);
		btnSiguiente.setBounds(272, 188, 118, 33);
		getContentPane().add(btnSiguiente);
		
		tEstadia = new PlaceholderTextField2();
		tEstadia.setPlaceholder("Total Estadia");
		tEstadia.setBounds(144, 244, 201, 38);
		getContentPane().add(tEstadia);
		
		JLabel lblEstadia = new JLabel("Estadia");
		lblEstadia.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblEstadia.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEstadia.setBounds(43, 244, 91, 38);
		getContentPane().add(lblEstadia);
		
		JLabel lblProductos = new JLabel("Productos");
		lblProductos.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblProductos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblProductos.setBounds(43, 295, 91, 33);
		getContentPane().add(lblProductos);
		
		tProductos = new PlaceholderTextField2();
		tProductos.setPlaceholder("Total Productos");
		tProductos.setBounds(144, 290, 201, 38);
		getContentPane().add(tProductos);
		
		JLabel lblTotal = new JLabel("Total");
		lblTotal.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTotal.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblTotal.setBounds(43, 344, 91, 33);
		getContentPane().add(lblTotal);
		
		tTotal = new PlaceholderTextField2();
		tTotal.setPlaceholder("Total Cobrado");
		tTotal.setBounds(144, 339, 201, 38);
		getContentPane().add(tTotal);
		recuperarDatos();
	}
	
	private void cargarFormulario() {
		cobranzaDao = new CobranzaDao();
		estadia = cobranzaDao.recuperaTotalPorCajaTipoDeuda(listCaja.get(tablaCaja.getSelectedRow()),0);
		cobranzaDao = new CobranzaDao();
		Producto = cobranzaDao.recuperaTotalPorCajaTipoDeuda(listCaja.get(tablaCaja.getSelectedRow()),1);
		tEstadia.setText(Util.formatoDecimal(estadia) + " Gs.");
		tProductos.setText(Util.formatoDecimal(Producto) + " Gs.");
		tTotal.setText(""+tablaCaja.campo(3));
	}

	private void recuperarDatos(){
		cajaDao = new CajaDao();
		listCaja = cajaDao.recuperarPorLimites(d);
		cargarGrilla();
		habilitarBotones();
	}
	
	private void habilitarBotones() {
		cajaDao = new CajaDao();
		int m = cajaDao.contar();
		
		btnAnterior.setEnabled(d!=0);
		btnSiguiente.setEnabled((d+10)<=m);
		
			
	}

	private void cargarGrilla() {
		tablaCaja.vaciar();
		fila = new Object[tablaCaja.getColumnCount()];
		for(Caja c:listCaja){
			cobranzaDao = new CobranzaDao();
			Double total = cobranzaDao.recuperaTotalPorCaja(c);
			fila[0] = c.getUsuario().getNombre();
			fila[1] = FormatoFecha.dateADateHora(c.getApertura());
			fila[2] = FormatoFecha.dateADateHora(c.getCierre());
			fila[3] = Util.formatoDecimal(total)+" Gs.";
			tablaCaja.getModelo().addRow(fila);
		}
	}
}
