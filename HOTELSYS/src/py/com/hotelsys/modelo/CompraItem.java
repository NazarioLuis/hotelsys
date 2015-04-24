package py.com.hotelsys.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CompraItem {
	@Id
	@GeneratedValue
	@Column(name="cit_numero")
	private int id;
	@Column(name="cit_cantidad",nullable=false)
	private int cantidad;
	@Column(name="cit_costo",nullable=false)
	private double costo;
	@Column(name="cit_costo_promedio",nullable=false)
	private double costoPromedio;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cit_producto_fk")
	private Producto producto;
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name="cit_compra_fk",referencedColumnName="com_numero")
	private Compra compra;
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
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public Compra getCompra() {
		return compra;
	}
	public void setCompra(Compra compra) {
		this.compra = compra;
	}
	public double getCostoPromedio() {
		return costoPromedio;
	}
	public void setCostoPromedio(double costoPromedio) {
		this.costoPromedio = costoPromedio;
	}
	
	
}
