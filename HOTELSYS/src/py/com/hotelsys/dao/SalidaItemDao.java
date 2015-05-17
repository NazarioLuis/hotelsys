package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.SalidaStockItem;

public class SalidaItemDao extends GenericDao<SalidaStockItem>{

	public SalidaItemDao() {
		super(SalidaStockItem.class);
	}

	@Override
	public List<SalidaStockItem> recuperarPorFiltros(String [] filtro) {
		
		return null;
		
	}
	
}
