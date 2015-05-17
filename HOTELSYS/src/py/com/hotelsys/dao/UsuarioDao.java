package py.com.hotelsys.dao;

import java.util.List;

import org.hibernate.criterion.Restrictions;

import py.com.hotelsys.modelo.Usuario;
import py.com.hotelsys.util.JPass;

public class UsuarioDao extends GenericDao<Usuario>{

	public UsuarioDao() {
		super(Usuario.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> recuperarPorFiltros(String [] filtro) {
		criteria = session.createCriteria(entity);
		criteria.add(Restrictions.or(Restrictions.like("nombre", "%"+filtro[0]+"%").ignoreCase(),
				Restrictions.like("alias", "%"+filtro[0]+"%").ignoreCase()));
		
		list = criteria.list();
		cerrar();
		return list;
	}

	public Usuario logerUsuario(String alias, String pass) {
		JPass.encrypt(pass);
		criteria = session.createCriteria(entity);
		criteria.add(Restrictions.eq("alias", alias));
		criteria.add(Restrictions.eq("pass", JPass.getEncryptedString()));
		
		Usuario usu = (Usuario) criteria.uniqueResult();
		cerrar();
		return usu;
	}
	
}
