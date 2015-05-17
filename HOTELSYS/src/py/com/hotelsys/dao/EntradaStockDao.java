package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.EntradaStock;
import py.com.hotelsys.util.FormatoFecha;

public class EntradaStockDao extends GenericDao<EntradaStock>{

	public EntradaStockDao() {
		super(EntradaStock.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<EntradaStock> recuperarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(Restrictions.like("descripcion", "%"+filtro[0]+"%"));
		criteria.add(Restrictions.between("fecha", FormatoFecha.stringToDate(filtro[1]),FormatoFecha.stringToDate(filtro[2])));
		
		list = criteria.list();
		session.close();
		return list;
		
	}
	
}
