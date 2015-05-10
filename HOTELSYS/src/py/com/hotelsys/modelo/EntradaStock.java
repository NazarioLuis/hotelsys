package py.com.hotelsys.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@SuppressWarnings("serial")
@Entity
public class EntradaStock implements Serializable{
	@Id
	@Column(name = "ent_numero")
	private int id;
	@Column(name = "ent_descri")
	private String descripcion;
	@Column(name = "ent_fecha")
	private Date fecha;
	@Column(name = "ent_estado")
	private boolean estado;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="entrada",fetch = FetchType.EAGER)
	private List<EntradaStockItem> entradaItems = new ArrayList<EntradaStockItem>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public List<EntradaStockItem> getEntradaItems() {
		return entradaItems;
	}
	public void setEntradaItems(List<EntradaStockItem> entradaItems) {
		this.entradaItems = entradaItems;
	}
	
	
}