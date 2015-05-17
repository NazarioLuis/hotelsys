package py.com.hotelsys.modelo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints= @UniqueConstraint(columnNames={"usu_alias"}))
public class Usuario {
	@Id
	@Column(name="usu_codigo")
	private int id;
	@Column(name="usu_nombre")
	private String nombre;
	@Column(name="usu_alias")
	private String alias;
	@Column(name="usu_pass")
	private String pass;
	@OneToOne
	@JoinColumn(name="usu_rol_fk")
	private Rol rol;
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
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	
	
}
