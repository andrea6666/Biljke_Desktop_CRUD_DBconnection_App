package osnovneKlase;
import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MojRenderer extends DefaultTableCellRenderer { 
	
	/**
	 * 
	 *  Created by: Andrea
	 *  
	 * The class changes the colors of the next watering dates on the main window panel within a watering table.
	 * When the date is delayed by 1 day or more, the color of the date in the table is black.
	 * When the date is today, the color is red.
	 * When the date is the day before watering, the color is orange.
	 * This system serves as a notification, represented by colors instead of messages, notifications or piping windows.
	 * Other days and dates are displayed in the default table color.
	 */
  
  private static final long serialVersionUID = 1L;
  
  public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
    Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
    setHorizontalAlignment(SwingConstants.CENTER);
    setBackground(new Color(100, 67, 62));
    setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
    setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(25, 14, 22)));
    if (row == 0 && col == 1) {
    	String aktuelniDatum = value.toString();
      int r  =  RacunanjeBrojaDanaZaBoju.razdvajanjeDatumaNaDMG(aktuelniDatum);
      if (r == 0) {  // the plant should be watered today
        c.setBackground(Color.RED);
        c.setForeground(Color.BLACK);
       	((JComponent) c).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

      } 
      if (r <= -1) {
        c.setBackground(Color.BLACK);
        c.setForeground(Color.WHITE);  // late with watering by a day or more
       	((JComponent) c).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

      } 
      if (r == 1) {
        c.setBackground(Color.ORANGE); // one day left until watering
        c.setForeground(Color.BLACK);
       	((JComponent) c).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

      } 
    } else {
     c.setBackground(new Color(100, 67, 62)); // default color when there is more than one day until watering
   	 c.setForeground(new Color(225, 180, 36));
   	((JComponent) c).setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
    } 

    return c;
  
}
  
 
 

  }
      

  



