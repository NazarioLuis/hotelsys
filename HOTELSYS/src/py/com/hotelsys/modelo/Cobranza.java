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
public class Cobranza {
	@Id
	@Column(name="cob_numero")
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="est_fecha",nullable=false)
	private Date fecha;
	

	@ManyToOne
	@JoinColumn(name="cob_cliente_fk",referencedColumnName="cli_codigo")
	private Cliente cliente;
	
	@ManyToOne
	@JoinColumn(name="cob_habitacion_fk",referencedColumnName="hab_codigo")
	private Habitacion habitacion;
	

	public Cobranza() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	
}
