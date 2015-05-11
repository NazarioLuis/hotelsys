package py.com.hotelsys.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Estadia {
	@Id
	@Column(name="est_numero")
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="est_fecha",nullable=false)
	private Date fecha;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="est_fecha_sal")
	private Date fechaSal;
	
	
	@Column(name="est_exce")
	private Double excedente;
	
	
		
	@ManyToOne
	@JoinColumn(name="est_cliente_fk",referencedColumnName="cli_codigo")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="est_habitacion_fk",referencedColumnName="hab_codigo")
	private Habitacion habitacion;
	
	
	
	
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

	public Date getFechaSal() {
		return fechaSal;
	}

	public void setFechaSal(Date fechaSal) {
		this.fechaSal = fechaSal;
	}

	public Double getExcedente() {
		return excedente;
	}

	public void setExcedente(Double excedente) {
		this.excedente = excedente;
	}
	
	

	
	
	
}
