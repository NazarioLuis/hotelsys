package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.EntradaStockItem;

public class EntradaItemDao extends GenericDao<EntradaStockItem>{

	public EntradaItemDao() {
		super(EntradaStockItem.class);
	}

	@Override
	public List<EntradaStockItem> recuperarPorFiltros(String [] filtro) {
		
		return null;
		
	}
	
}
