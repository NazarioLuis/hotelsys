package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Compra;

public class CompraDao extends GenericGao<Compra>{

	public CompraDao() {
		super(Compra.class);
	}

	@Override
	public List<Compra> cosultarPorFiltros(String [] filtro) {
		return list;
		
	}
	
}
