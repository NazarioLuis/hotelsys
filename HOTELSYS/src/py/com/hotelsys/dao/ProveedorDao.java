package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Proveedor;

public class ProveedorDao extends GenericGao<Proveedor>{

	public ProveedorDao() {
		super(Proveedor.class);
	}

	@Override
	public List<Proveedor> cosultarPorFiltros(String filtro) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
