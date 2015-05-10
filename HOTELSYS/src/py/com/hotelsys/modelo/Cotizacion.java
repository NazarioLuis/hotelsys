package py.com.hotelsys.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@SuppressWarnings("serial")
@Entity
public class Cotizacion implements Serializable{
	@Column(name="cot_codigo")
	private int id;
	@Id
	@Column(name="cot_moneda")
	private int moneda;
	@Id
	@Column(name="cot_fecha")
	private Date fecha;
	
	@Column(name="cot_monto")
	private Double monto;

	public int getMoneda() {
		return moneda;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setMoneda(int moneda) {
		this.moneda = moneda;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	
}
