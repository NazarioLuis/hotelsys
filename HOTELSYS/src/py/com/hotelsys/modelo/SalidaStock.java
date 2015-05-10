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
public class SalidaStock implements Serializable{
	@Id
	@Column(name = "sal_numero")
	private int id;
	@Column(name = "sal_descri")
	private String descripcion;
	@Column(name = "sal_fecha")
	private Date fecha;
	@Column(name = "sal_estado")
	private boolean estado;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="salida",fetch = FetchType.EAGER)
	private List<SalidaStockItem> salidaItems = new ArrayList<SalidaStockItem>();
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
	public List<SalidaStockItem> getSalidaItems() {
		return salidaItems;
	}
	public void setSalidaItems(List<SalidaStockItem> salidaItem) {
		this.salidaItems = salidaItem;
	}
	
}