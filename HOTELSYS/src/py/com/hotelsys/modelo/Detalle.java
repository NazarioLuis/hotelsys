package py.com.hotelsys.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
@Entity
public class Detalle{
	@Id
	@Column(name="det_num")
	private int id;
	
	@OneToOne()
	@JoinColumn(name="det_cod_prod_fk")
	private Producto producto;
	
	@OneToOne()
	@JoinColumn(name="det_cod_est_fk")
	private Estadia estadia;
	
	
	@Column(name="det_cantPro")
	private int cantidadProducto;

	@Column(name="det_total")
	private Double total;

	public Estadia getEstadia() {
		return estadia;
	}


	public void setEstadia(Estadia estadia) {
		this.estadia = estadia;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public Producto getProducto() {
		return producto;
	}


	public void setProducto(Producto producto) {
		this.producto = producto;
	}


	public int getCantidadProducto() {
		return cantidadProducto;
	}


	public void setCantidadProducto(int cantidadProducto) {
		this.cantidadProducto = cantidadProducto;
	}


	public Double getTotal() {
		return total;
	}


	public void setTotal(Double total) {
		this.total = total;
	}
	
	
	
	

	

		
	}
