package py.com.hotelsys.modelo;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Estadia {
	@Id
	@Column(name="est_numero")
	private int id;
	
	@Column(name="est_fecha",nullable=false)
	private Date fecha;
		
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="est_cliente_fk",referencedColumnName="cli_codigo")
	private Cliente cliente;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="est_habitacion_fk",referencedColumnName="hab_codigo")
	private Habitacion habitacion;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="est_producto_fk",referencedColumnName="pro_codigo")
	private Producto producto;
	
	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}

	public Servicio getServicio() {
		return servicio;
	}

	public void setServicio(Servicio servicio) {
		this.servicio = servicio;
	}

	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="est_ssservicio_fk",referencedColumnName="ser_codigo")
	private Servicio servicio;
	
	
	@Column(name="est_observacion",nullable=false)
	private String observacion;
	
	
	
	public String getObservacion() {
		return observacion;
	}

	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}

	public Estadia() {
		super();
		// TODO Auto-generated constructor stub
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Habitacion getHabitacion() {
		return habitacion;
	}

	public void setHabitacion(Habitacion habitacion) {
		this.habitacion = habitacion;
	}
	
	

	
	
	
}
