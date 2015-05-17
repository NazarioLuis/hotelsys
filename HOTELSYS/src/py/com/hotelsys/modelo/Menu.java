package py.com.hotelsys.modelo;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Menu {
	@Id
	@Column(name="mnu_codigo")
	private int id;
	@Column(name="mnu_descri")
	private String descri;
	@Column(name="mnu_index")
	private int index;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="menu",fetch = FetchType.EAGER,orphanRemoval=true)
	private List<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescri() {
		return descri;
	}
	public void setDescri(String descri) {
		this.descri = descri;
	}
	public List<MenuItem> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(List<MenuItem> menuItems) {
		this.menuItems = menuItems;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
}
