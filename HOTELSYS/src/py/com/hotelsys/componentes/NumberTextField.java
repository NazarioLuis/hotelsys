package py.com.hotelsys.componentes;

import java.awt.Color;

import javax.swing.JFormattedTextField;

import py.com.hotelsys.util.Util;

@SuppressWarnings("serial")
public class NumberTextField extends JFormattedTextField {


    public NumberTextField() {
    	setDisabledTextColor(Color.gray);
    	setFormatterFactory(Util.getDff());
    }

    

}
