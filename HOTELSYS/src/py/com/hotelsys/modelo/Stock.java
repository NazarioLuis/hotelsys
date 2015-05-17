package py.com.hotelsys.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Stock {
	@Id
	@Column(name="stk_codigo")
	private int id;
	
	@Column(name="stk_cantidad",nullable=false)
	private int cantidad;
	
	@Column(name="stk_precio",nullable=false)
	private double precio;
	
	@Column(name="stk_costo",nullable=false)
	private double costo;
	
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
	public double getPrecio() {
		return precio;
	}
	public void setPrecio(double precio) {
		this.precio = precio;
	}
	public double getCosto() {
		return costo;
	}
	public void setCosto(double costo) {
		this.costo = costo;
	}

	
}
