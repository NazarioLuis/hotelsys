package py.com.hotelsys.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Deuda {
	@Id
	@Column(name="deu_numero")
	private int id;
	
	@Column(name="deu_monto")
	private Double monto;
	
	@Column(name="deu_cobrado")
	private Double cobrado;
	
	@Column(name="deu_tipo")
	private int tipo;

	@ManyToOne
	@JoinColumn(name="deu_cliente_fk",referencedColumnName="cli_codigo")
	private Cliente cliente;
	@ManyToOne
	@JoinColumn(name="deu_estadia_fk",referencedColumnName="est_numero")
	private Estadia estadia;
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
	public Double getCobrado() {
		return cobrado;
	}
	public void setCobrado(Double cobrado) {
		this.cobrado = cobrado;
	}
	public int getTipo() {
		return tipo;
	}
	public void setTipo(int tipo) {
		this.tipo = tipo;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Estadia getEstadia() {
		return estadia;
	}
	public void setEstadia(Estadia estadia) {
		this.estadia = estadia;
	}
	
	
}
