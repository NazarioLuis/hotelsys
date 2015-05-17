package py.com.hotelsys.util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import py.com.hotelsys.dao.MenuDao;
import py.com.hotelsys.dao.MenuItemDao;
import py.com.hotelsys.modelo.Menu;
import py.com.hotelsys.modelo.MenuItem;

public class OpcionesDeUsuario {
	private static Menu menu;
	private static MenuDao menuDao;
	private static List<Menu> listMenu;
	private static List<Integer> mArray;
	private static List<Integer> iArray;
	private static boolean menuVacio;
	private static MenuItem menuItem;
	private static List<MenuItem> menuItems;
	private static MenuItemDao menuItemDao;
	
	
	public static void agregarMenuAOpcionesDeUsuario(JMenuBar mb) {
		comprobarCantidadDeOpcionesDeUsuario(mb);
		menuVacio = true;
		if (listMenu.size()>0) {
			menuVacio = false;
		}
		
		
		if (menuVacio) {
			menuDao = new MenuDao();
			menuDao.truncar();
			int c = 1;
			for (int i = 0; i < mb.getMenuCount()-1; i++) {
				JMenu m = mb.getMenu(i);
				menuItems = new ArrayList<>();
				
				menu = new Menu();
				menu.setId(i+1);
				menu.setDescri(m.getText());
				menu.setIndex(i);
				for (int j = 0; j < m.getItemCount(); j++) {
					JMenuItem mi = m.getItem(j);
					
					menuItem = new MenuItem();
					menuItem.setId(c);
					menuItem.setDescri(mi.getText());
					menuItem.setIndex(j);
					menuItem.setMenu(menu);
					menuItems.add(menuItem);
					c++;
				}
				menu.setMenuItems(menuItems);
				
				menuDao = new MenuDao();
				try {
					menuDao.insertar(menu);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}else{
			mArray = new ArrayList<>();
			for (int i = 0; i < mb.getMenuCount()-1; i++) {
				JMenu m = mb.getMenu(i);
				if (coprobarOpcion(m,i)) {
					menuItems = new ArrayList<>();
					
					menu = new Menu();
					menuDao = new MenuDao();
					menu.setId(menuDao.recuperMaxId()+1);
					menu.setDescri(m.getText());
					menu.setIndex(i);
					
					menuItemDao = new MenuItemDao();
					int c = menuItemDao.recuperMaxId()+1;
					for (int j = 0; j < m.getItemCount(); j++) {
						JMenuItem mi = m.getItem(j);
						menuItem = new MenuItem();
						menuItem.setId(c);
						menuItem.setDescri(mi.getText());
						menuItem.setIndex(j);
						menuItem.setMenu(menu);
						menuItems.add(menuItem);
						c++;
					}
					menu.setMenuItems(menuItems);
					
					menuDao = new MenuDao();
					try {
						menuDao.insertar(menu);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			quitarMenusBorrados(listMenu);
		}
		
	}

	private static boolean coprobarOpcion(JMenu me, int idx) {
		iArray = new ArrayList<>();
		for (Menu m:listMenu) {
			if (m.getDescri().equals(me.getText())) {
				if (m.getIndex()!=idx) {
					m.setIndex(idx);
					menuDao = new MenuDao();
					try {
						menuDao.actualizar(m);
					} catch (Exception e) {
						menuDao.rollback();
					}
				}
				mArray.add(m.getId());
				
				
				for (int i = 0; i < me.getItemCount(); i++) {
					JMenuItem mit = me.getItem(i);
					if (coprobarItem(m.getMenuItems(), mit, i)) {
						menuItem = new MenuItem();
						menuItemDao = new MenuItemDao();
						menuItem.setId(menuItemDao.recuperMaxId()+1);
						menuItem.setDescri(mit.getText());
						menuItem.setIndex(i);
						menuItem.setMenu(m);
						menuItemDao = new MenuItemDao();
						try {
							menuItemDao.insertar(menuItem);
						} catch (Exception e) {
							menuItemDao.rollback();
						}
					}
				}
				
				
				quitarItemsBorrados(m);
				
				
				return false;
			}
		}
		return true;
	}
	

	private static void quitarItemsBorrados(Menu m) {
		for (int i = 0; i < m.getMenuItems().size(); i++) {
			MenuItem mi = m.getMenuItems().get(i);
			if (!iArray.contains(mi.getId())) {
				menuDao = new MenuDao();
				try {
					m.getMenuItems().remove(i);
					menuDao.actualizar(m);
				} catch (Exception e) {
					e.printStackTrace();
					menuDao.rollback();
				}
			}
		}
		
	}
	
	
	private static void quitarMenusBorrados(List<Menu> menus) {
		for (Menu m:menus) {
			if (!mArray.contains(m.getId())) {
				menuDao = new MenuDao();
				try {
					menuDao.eliminar(m.getId());
				} catch (Exception e) {
					menuDao.rollback();
				}
			}
		}
	}

	private static boolean coprobarItem(List<MenuItem> menuItems, JMenuItem mit, int idx) {
		for (MenuItem mi:menuItems) {
			if (mit.getText().endsWith(mi.getDescri())) {
				if (idx!=mi.getIndex()) {
					mi.setIndex(idx);
					menuItemDao = new MenuItemDao();
					try {
						menuItemDao.actualizar(mi);
					} catch (Exception e) {
						menuItemDao.rollback();
					}
				}
				iArray.add(mi.getId());
				
				return false;
			}
		}
		return true;
	}
	

	private static void comprobarCantidadDeOpcionesDeUsuario(JMenuBar mb) {
		menuDao = new MenuDao();
		listMenu = menuDao.recuperarTodo();
	}
	
	public static List<MenuItem> getMenuItems() {
		return menuItems;
	}

	public static void recuperarOpcionesDeUsuario(JMenuBar mb) {
		menuItemDao = new MenuItemDao();
		menuItems = menuItemDao.recuperarTodo();
		VariableSys vs = new VariableSys();
		if (!VariableSys.user.getAlias().equals(vs.getSUPERUSER())) 
			configurarMenu(mb);
	}

	
	private static void configurarMenu(JMenuBar mb) {
		for (int i = 0; i < mb.getMenuCount()-1; i++) {
			mb.getMenu(i).setVisible(false);
		}
		for (MenuItem mi : menuItems) {
			if (VariableSys.getPermisos().contains(mi.getId()+"")) {
				if (!mb.getMenu(mi.getMenu().getIndex()).isVisible())
					mb.getMenu(mi.getMenu().getIndex()).setVisible(true);
				mb.getMenu(mi.getMenu().getIndex()).getItem(mi.getIndex()).setVisible(true);
			}else{
				mb.getMenu(mi.getMenu().getIndex()).getItem(mi.getIndex()).setVisible(false);
			}
			
		}
	}
	
	
}
