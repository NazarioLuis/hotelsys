package py.com.hotelsys.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import py.com.hotelsys.util.VariableSys;

@Entity
public class Cobranza {
	@Id
	@Column(name="cob_numero")
	private int id;
	
	@Column(name="cob_monto")
	private Double monto;
	
	@Column(name="est_fecha",nullable=false)
	private Date fecha;
	
	@ManyToOne
	@JoinColumn(name="cob_caja_fk",referencedColumnName="caj_codigo")
	private Caja caja;
	
	
	public Cobranza() {
		this.caja = VariableSys.caja;
	}

	@ManyToOne
	@JoinColumn(name="cob_cliente_fk",referencedColumnName="cli_codigo")
	private Cliente cliente;


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Double getMonto() {
		return monto;
	}


	public void setMonto(Double monto) {
		this.monto = monto;
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
	

	
	
}
