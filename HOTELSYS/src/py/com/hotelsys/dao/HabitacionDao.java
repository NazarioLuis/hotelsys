package py.com.hotelsys.dao;

import java.util.List;

import py.com.hotelsys.modelo.Habitacion;

public class HabitacionDao extends GenericGao<Habitacion>{

	public HabitacionDao() {
		super(Habitacion.class);
	}

	@Override
	public List<Habitacion> cosultarPorFiltros(String filtro) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
