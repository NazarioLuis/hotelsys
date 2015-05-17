package py.com.hotelsys.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EntradaStockItem {
	@Id
	@Column(name="eit_numero")
	private int id;
	@Column(name="eit_cantidad",nullable=false)
	private int cantidad;
	@ManyToOne
	@JoinColumn(name="eit_producto_fk")
	private Producto producto;
	@ManyToOne
	@JoinColumn(name="eit_entrada_fk",referencedColumnName="ent_numero")
	private EntradaStock entrada;
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
	public EntradaStock getEntrada() {
		return entrada;
	}
	public void setEntrada(EntradaStock entrada) {
		this.entrada = entrada;
	}
	
	
}
