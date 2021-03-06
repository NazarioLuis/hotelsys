package py.com.hotelsys.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
@Entity
@Table(uniqueConstraints=@UniqueConstraint(columnNames={"pro_documento"}))
public class Proveedor {
	
	@Id
	@Column(name="pro_codigo")
	private int id;
	@Column(name="pro_nombre",nullable=false)
	private String nombre;
	@Column(name="pro_documento",nullable=false)
	private String documento;
	@Column(name="pro_telefono",nullable=false)
	private String telefono;
	@Column(name="pro_email")
	private String email;
	@Column(name="pro_direccion",nullable=false)
	private String direccion;
	@Column(name="pro_observacion")
	private String observacion;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDireccion() {
		return direccion;
	}
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	public String getObservacion() {
		return observacion;
	}
	public void setObservacion(String observacion) {
		this.observacion = observacion;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}