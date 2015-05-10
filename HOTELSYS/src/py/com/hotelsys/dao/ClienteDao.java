package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Cliente;

public class ClienteDao extends GenericDao<Cliente>{

	public ClienteDao() {
		super(Cliente.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> cosultarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(
					Restrictions.or(
						Restrictions.like("nombre", "%"+filtro[0]+"%").ignoreCase(),
						Restrictions.like("documento", "%"+filtro[0]+"%").ignoreCase()
					)
				);
		
		list = criteria.list();
		cerrar();
		return list;
	}
	
}
