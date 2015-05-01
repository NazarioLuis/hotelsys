package py.com.hotelsys.dao;

import java.util.List;


import py.com.hotelsys.modelo.Estadia;

public class EstadiaDao extends GenericGao<Estadia>{

	public EstadiaDao() {
		super(Estadia.class);
	}

	@Override
	public List<Estadia> cosultarPorFiltros(String [] filtro) {
		return list;
		
	}
	
}
