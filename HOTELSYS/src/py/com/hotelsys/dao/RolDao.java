package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Rol;

public class RolDao extends GenericDao<Rol>{

	public RolDao() {
		super(Rol.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Rol> recuperarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(Restrictions.like("descri", "%"+filtro[0]+"%").ignoreCase());
		
		list = criteria.list();
		cerrar();
		return list;
	}
	
}
