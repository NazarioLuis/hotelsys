package py.com.hotelsys.dao;

import java.util.List;




import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Estadia;

public class EstadiaDao extends GenericDao<Estadia>{

	public EstadiaDao() {
		super(Estadia.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Estadia> recuperarPorFiltros(String [] filtro) {
		criteria.add(
				Restrictions.like("descripcion", "%"+filtro[0]+"%").ignoreCase()
			);

		list = criteria.list();
		return list;
		
	}
	
	
}
