/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package assessor.util;

import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author Toryang
 */
public class CurrencyRenderer extends DefaultTableCellRenderer {
    private final NumberFormat currencyFormat;

    public CurrencyRenderer() {
        setHorizontalAlignment(SwingConstants.RIGHT);
        Locale phLocale = new Locale("en", "PH");
        currencyFormat = NumberFormat.getCurrencyInstance(phLocale);
        
        DecimalFormatSymbols symbols = ((java.text.DecimalFormat)currencyFormat).getDecimalFormatSymbols();
        symbols.setCurrencySymbol("₱");
        ((java.text.DecimalFormat)currencyFormat).setDecimalFormatSymbols(symbols);
    }

    @Override
    protected void setValue(Object value) {
        if (value instanceof Number) {
            setText(currencyFormat.format(value).replace("PHP", "₱")); // Extra safeguard
        } else {
            super.setValue(value);
        }
    }
}
