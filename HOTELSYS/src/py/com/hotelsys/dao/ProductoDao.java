package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Producto;

public class ProductoDao extends GenericGao<Producto>{

	public ProductoDao() {
		super(Producto.class);
	}

	@Override
	public List<Producto> cosultarPorFiltros(String filtro) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
