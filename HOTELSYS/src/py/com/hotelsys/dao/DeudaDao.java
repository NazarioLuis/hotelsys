package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Deuda;

public class DeudaDao extends GenericDao<Deuda>{

	public DeudaDao() {
		super(Deuda.class);
	}

	@Override
	public List<Deuda> recuperarPorFiltros(String [] filtro) {
		
		return list;
	}
	
}
