package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Compra;

public class CompraItemDao extends GenericGao<Compra>{

	public CompraItemDao() {
		super(Compra.class);
	}

	@Override
	public List<Compra> cosultarPorFiltros(String [] filtro) {
		return list;
		
	}
	
}
