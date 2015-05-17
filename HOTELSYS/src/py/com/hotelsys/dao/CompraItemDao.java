package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.CompraItem;

public class CompraItemDao extends GenericDao<CompraItem>{

	public CompraItemDao() {
		super(CompraItem.class);
	}


	@Override
	public List<CompraItem> recuperarPorFiltros(String[] filtro) {
		return null;
	}
	
}
