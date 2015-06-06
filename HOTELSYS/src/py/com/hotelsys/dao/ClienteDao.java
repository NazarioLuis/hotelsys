package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Cliente;
import py.com.hotelsys.modelo.Deuda;

public class ClienteDao extends GenericDao<Cliente>{

	public ClienteDao() {
		super(Cliente.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> recuperarPorFiltros(String [] filtro) {
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

	@SuppressWarnings("unchecked")
	public List<Cliente> recuperaClientesConDeuda() {
		DetachedCriteria subCriteria = DetachedCriteria.forClass(Deuda.class);
		subCriteria.createAlias("cliente", "c");
		subCriteria.add(Restrictions.eq("estado",true));
		subCriteria.setProjection(Projections.property("c.id"));
		
		criteria = session.createCriteria(entity);
		criteria.add(Property.forName("id").in(subCriteria));
		
		list = criteria.list();
		cerrar();
		return list;
	}
	
}
