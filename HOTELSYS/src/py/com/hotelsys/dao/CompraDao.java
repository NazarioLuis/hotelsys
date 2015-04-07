package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Compra;
import py.com.hotelsys.modelo.CompraItem;

public class CompraDao extends GenericGao<CompraItem>{

	public CompraDao() {
		super(CompraItem.class);
	}

	@Override
	public List<CompraItem> cosultarPorFiltros(String [] filtro) {
		return list;
		
	}
	
}
