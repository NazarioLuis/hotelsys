package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Cliente;

public class ClienteDao extends GenericGao<Cliente>{

	public ClienteDao() {
		super(Cliente.class);
	}

	@Override
	public List<Cliente> cosultarPorFiltros(String filtro) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
