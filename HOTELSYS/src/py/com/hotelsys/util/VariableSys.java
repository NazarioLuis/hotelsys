package py.com.hotelsys.util;

import java.util.List;

import py.com.hotelsys.modelo.Caja;
import py.com.hotelsys.modelo.Usuario;


public class VariableSys {
	private final String SUPERNAME = "Super Usuario";
	private final String SUPERUSER = "ROOT";
	private final String SUPERACCES = "HOSYS2015";
	
	public static Usuario user;
	public static Caja caja;
	
	public static boolean isSUPERUSER(String su, String ac) {
		VariableSys vs = new VariableSys();
		if (vs.SUPERUSER.equals(su)&&vs.SUPERACCES.equals(ac)){
			user = new Usuario();
			user.setId(9999);
			user.setNombre(vs.SUPERNAME);
			user.setAlias(vs.SUPERUSER);
			return true;
		}
		return false;
	}
	
	
	public String getSUPERUSER() {
		return SUPERUSER;
	}
	
	public static List<String> getPermisos() {
		return Util.stringAArray(user.getRol().getPermiso());
	}
	
}
