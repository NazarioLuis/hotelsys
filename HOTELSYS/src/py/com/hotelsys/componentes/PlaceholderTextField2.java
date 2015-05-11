package py.com.hotelsys.componentes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.*;
import javax.swing.text.Document;

@SuppressWarnings("serial")
public class PlaceholderTextField2 extends JTextField {

    
    private String placeholder;

    public PlaceholderTextField2() {
    	setForeground(new Color(220, 20, 60));
    	setFont(new Font("Tahoma", Font.BOLD, 17));
    	setHorizontalAlignment(SwingConstants.CENTER);
    	this.placeholder="placeholder";
    	setDisabledTextColor(new Color(220, 20, 60));
    }

    public PlaceholderTextField2(
        final Document pDoc,
        final String pText,
        final int pColumns)
    {
        super(pDoc, pText, pColumns);
    }

    public PlaceholderTextField2(final int pColumns) {
        super(pColumns);
    }

    public PlaceholderTextField2(final String pText) {
        super(pText);
    }

    public PlaceholderTextField2(final String pText, final int pColumns) {
        super(pText, pColumns);
    }

    public String getPlaceholder() {
        return placeholder;
    }

    @Override
    protected void paintComponent(final Graphics pG) {
        super.paintComponent(pG);

        if (placeholder.length() == 0 || getText().length() > 0) {
            return;
        }

        final Graphics2D g = (Graphics2D) pG;
        g.setRenderingHint(
            RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(getDisabledTextColor());
        g.setFont(getFont().deriveFont(Font.BOLD));
        g.drawString(placeholder, getInsets().left, pG.getFontMetrics()
            .getMaxAscent() + getInsets().top);
    }

    public void setPlaceholder(final String s) {
        placeholder = s;
    }

}
