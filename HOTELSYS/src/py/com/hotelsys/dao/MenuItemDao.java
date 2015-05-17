package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.MenuItem;

public class MenuItemDao extends GenericDao<MenuItem>{

	public MenuItemDao() {
		super(MenuItem.class);
	}

	@Override
	public List<MenuItem> recuperarPorFiltros(String [] filtro) {
		
		return list;
	}
	
}
