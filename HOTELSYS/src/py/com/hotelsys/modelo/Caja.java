package py.com.hotelsys.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import py.com.hotelsys.util.VariableSys;

@Entity
public class Caja {
	@Id
	@Column(name="caj_codigo")
	private int id;
	@Column(name="caj_remanente")
	private Double remanente;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="caj_apertura")
	private Date apertura;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="caj_cierre")
	private Date cierre;
	@ManyToOne
	@JoinColumn(name="caj_usuario_fk",referencedColumnName="usu_codigo")
	private Usuario usuario;
	
	public Caja() {
		this.setUsuario(VariableSys.user);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getApertura() {
		return apertura;
	}

	public void setApertura(Date apertura) {
		this.apertura = apertura;
	}

	public Date getCierre() {
		return cierre;
	}

	public void setCierre(Date cierre) {
		this.cierre = cierre;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Double getRemanente() {
		return remanente;
	}

	public void setRemanente(Double remanente) {
		this.remanente = remanente;
	}
	
}
