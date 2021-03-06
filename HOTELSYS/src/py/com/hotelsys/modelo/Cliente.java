package py.com.hotelsys.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames={"cli_documento"}))
public class Cliente {
	
	@Id
	@Column(name="cli_codigo")
	private int id;
	@Column(name="cli_nombre",nullable=false)
	private String nombre;
	@Column(name="cli_documento",nullable=false)
	private String documento;
	@Column(name="cli_email")
	private String email;
	@Column(name="cli_telefono")
	private String telefono;
	@Column(name="cli_direccion")
	private String direccion;
	@Column(name="cli_observacion")
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
