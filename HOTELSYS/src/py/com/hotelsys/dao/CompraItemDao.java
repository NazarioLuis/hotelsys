package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.CompraItem;

public class CompraItemDao extends GenericGao<CompraItem>{

	public CompraItemDao() {
		super(CompraItem.class);
	}

	@Override
	public List<CompraItem> cosultarPorFiltros(String [] filtro) {
		return list;
		
	}
	
}
