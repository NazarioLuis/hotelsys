package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Servicio;

public class ServicioDao extends GenericGao<Servicio>{

	public ServicioDao() {
		super(Servicio.class);
	}

	@Override
	public List<Servicio> cosultarPorFiltros(String filtro) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
