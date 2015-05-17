package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.Query;

import py.com.hotelsys.modelo.Menu;

public class MenuDao extends GenericDao<Menu>{

	public MenuDao() {
		super(Menu.class);
	}

	@Override
	public List<Menu> recuperarPorFiltros(String [] filtro) {
		
		return list;
	}

	public void truncar() {
		 String hql = String.format("delete from Menu");
		 Query query = session.createQuery(hql);
		 query.executeUpdate();
		 cerrar();
	}
	
}
