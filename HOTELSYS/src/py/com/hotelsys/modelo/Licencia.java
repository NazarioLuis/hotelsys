package py.com.hotelsys.modelo;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Licencia {
	@Id
	@Column(name="lic_id")
	private int id;
	@Column(name="lic_ser")
	private String serial;
	@Column(name="lic_val")
	private Date valides;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSerial() {
		return serial;
	}
	public void setSerial(String serial) {
		this.serial = serial;
	}
	public Date getValides() {
		return valides;
	}
	public void setValides(Date valides) {
		this.valides = valides;
	}
}
