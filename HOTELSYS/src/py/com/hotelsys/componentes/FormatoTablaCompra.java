package py.com.hotelsys.componentes;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
@SuppressWarnings("serial")
public class FormatoTablaCompra extends DefaultTableCellRenderer{
	
	public Component getTableCellRendererComponent
	 (JTable table, Object value, boolean selected, boolean focused, int row, int column)
	 {
	         
	  if(String.valueOf(table.getValueAt(row,6)).equals("Confirmado")){ 
		  setBackground(Color.green);
		  
	  }
	                  
	 else  setBackground(Color.white);
	    
	 super.getTableCellRendererComponent(table, value, selected, focused, row, column);
	 return this;
	 }	
 }