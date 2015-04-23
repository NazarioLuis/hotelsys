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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Compra implements Serializable{
	
	@Column(name = "com_numero")
	private int id;
	@Id
	@Column(name = "com_factura")
	private String factura;
	@Id
	@Column(name = "com_timbrado")
	private String timbrado;
	@Column(name = "com_ven_tim")
	private Date vencimientoTimbrado;
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
	public String getFactura() {
		return factura;
	}
	public void setFactura(String factura) {
		this.factura = factura;
	}
	public String getTimbrado() {
		return timbrado;
	}
	public void setTimbrado(String timbrado) {
		this.timbrado = timbrado;
	}
	public Date getVencimientoTimbrado() {
		return vencimientoTimbrado;
	}
	public void setVencimientoTimbrado(Date vencimientoTimbrado) {
		this.vencimientoTimbrado = vencimientoTimbrado;
	}
	
	
}