package py.com.hotelsys.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Compra {
	@Id
	@Column(name = "com_numero")
	private int id;
	@Column(name = "com_factura")
	private int factura;
	@Column(name = "com_fecha")
	private Date fecha;
	@Column(name = "com_total")
	private double total;
	@Column(name = "com_estado")
	private boolean estado;
	@ManyToOne(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	@JoinColumn(name="com_proveedor_fk")
	private Proveedor proveedor;
	@OneToMany(cascade=CascadeType.ALL,mappedBy="compra")
	private List<CompraItem> compraItems = new ArrayList<CompraItem>();
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getFactura() {
		return factura;
	}
	public void setFactura(int factura) {
		this.factura = factura;
	}
	public boolean isEstado() {
		return estado;
	}
	public void setEstado(boolean estado) {
		this.estado = estado;
	}
	public Date getFecha() {
		return fecha;
	}
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Proveedor getProveedor() {
		return proveedor;
	}
	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}
	public List<CompraItem> getCompraItems() {
		return compraItems;
	}
	public void setCompraItems(List<CompraItem> compraItems) {
		this.compraItems = compraItems;
	}
	
	
}