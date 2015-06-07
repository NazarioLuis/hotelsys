package py.com.hotelsys.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"pro_descripcion"}))
public class Producto{
	@Id
	@Column(name="pro_codigo")
	private int id;
	@Column(name="pro_descripcion",nullable=false)
	private String descripcion;
	@Column(name="pro_observacion")
	private String observacion;
	@Column(name="pro_stock_minimo")
	private int stockMinimo;
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="pro_stock_fk",unique=true)
	private Stock stock;
	
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
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public Stock getStock() {
		return stock;
	}
	public void setStock(Stock stock) {
		this.stock = stock;
	}
	public int getStockMinimo() {
		return stockMinimo;
	}
	public void setStockMinimo(int stockMinimo) {
		this.stockMinimo = stockMinimo;
	}
}
