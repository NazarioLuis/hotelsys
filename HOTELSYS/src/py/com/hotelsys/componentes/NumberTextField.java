package py.com.hotelsys.componentes;

import java.awt.Color;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFormattedTextField;

import py.com.hotelsys.util.Util;

@SuppressWarnings("serial")
public class NumberTextField extends JFormattedTextField {


    public NumberTextField() {
    	setDisabledTextColor(Color.darkGray);
    	setFormatterFactory(Util.getDff());
    	addKeyListener(new KeyAdapter() {
    		@Override
    		public void keyTyped(KeyEvent e) {
    			Util.validarNumero(e);
    		}
		});
    }

    

}
