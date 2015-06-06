package py.com.hotelsys.componentes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
@SuppressWarnings("serial")
public class FormatoTabla extends DefaultTableCellRenderer{
	private int c;
	private String t;
	public FormatoTabla(int c, String t){
		this.c = c;
		this.t = t;
	}
	public Component getTableCellRendererComponent
	 (JTable table, Object value, boolean selected, boolean focused, int row, int column)
	 {
	  
		
	  if(String.valueOf(table.getValueAt(row,c)).equals(t)){ 
		  setBackground(Color.green);
		  
	  }
	                  
	 else  setBackground(Color.white);
	    
	 super.getTableCellRendererComponent(table, value, selected, focused, row, column);
	 return this;
	 }	
	
 }