package py.com.hotelsys.dao;

import java.util.List;





import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Estadia;

public class EstadiaDao extends GenericDao<Estadia>{

	public EstadiaDao() {
		super(Estadia.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Estadia> recuperarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity).createAlias("cliente", "c");
		criteria.add(Restrictions.like("c.nombre", "%"+filtro[1]+"%").ignoreCase());
		if(filtro[0].equals("Abiertos"))
			criteria.add(Restrictions.isNull("fechaSal"));
		if(filtro[0].equals("Cerrados"))
			criteria.add(Restrictions.isNotNull("fechaSal"));
		criteria.addOrder(Order.asc("id"));

		list = criteria.list();
		cerrar();
		return list;
		
	}
	
	
}
