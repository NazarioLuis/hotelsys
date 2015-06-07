package py.com.hotelsys.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class SalidaStockItem {
	@Id
	@Column(name="sit_numero")
	private int id;
	@Column(name="sit_cantidad",nullable=false)
	private int cantidad;
	@Column(name="sit_costo")
	private Double costo;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="sit_producto_fk")
	private Producto producto;
	@ManyToOne
	@JoinColumn(name="sit_salida_fk",referencedColumnName="sal_numero")
	private SalidaStock salida;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getCantidad() {
		return cantidad;
	}
	
	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public SalidaStock getSalida() {
		return salida;
	}
	public void setSalida(SalidaStock salida) {
		this.salida = salida;
	}
	public Double getCosto() {
		return costo;
	}
	public void setCosto(Double costo) {
		this.costo = costo;
	}
	
	
}
