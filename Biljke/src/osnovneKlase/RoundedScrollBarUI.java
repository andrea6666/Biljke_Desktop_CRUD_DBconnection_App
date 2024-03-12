package osnovneKlase;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import javax.swing.DefaultButtonModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicScrollBarUI;

import net.coobird.thumbnailator.Thumbnails;

public class RoundedScrollBarUI extends BasicScrollBarUI {
	
	/**
	 * Created by: Andrea
	 * 
	 * Class that defines the appearance of the scrollbar on the main panel or window
	 * when more than 2 plants are displayed or when there are 2 or more plants in the database,
	 * triggering the appearance of the scrollbar. This class defines the appearance of the scrollbar.
	 */

    @Override
    protected JButton createDecreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
            	
            	
          	ByteArrayOutputStream stream = new ByteArrayOutputStream();
            	
            	try {
					Thumbnails.of(GlavniProzor.class.getResource("/img/DugmeStrelicaGore.png")).size(15, 15).outputQuality(1).toOutputStream(stream);
				} catch (IOException e) {
					e.printStackTrace();
				}            	
            	
 
            	
            	 setIcon(new ImageIcon(stream.toByteArray()));
            	 
                setBounds(0, 0, 15, 15);
            	
                setFocusable(false);
            	setBorder(null);
            	setBackground(new java.awt.Color(225, 177, 173));
            	
            	
            	setModel(new DefaultButtonModel() {
            		  public void setPressed(boolean b) {
            		    if (!isPressed()) {
            		      // do not paint the shaded, gray-blue color
            		      super.setArmed(false);
            		      super.setPressed(false);
            		    }
            		    super.setPressed(b);
            		  }
            		});

            	return new Dimension(15, 30);
            }
        };
    }

    @Override
    protected JButton createIncreaseButton(int orientation) {
        return new JButton() {
            @Override
            public Dimension getPreferredSize() {
            	
            	
            	ByteArrayOutputStream stream = new ByteArrayOutputStream();
            	
            	try {
					Thumbnails.of(GlavniProzor.class.getResource("/img/DugmeStrelicaDole.png")).size(15, 15).outputQuality(1).toOutputStream(stream);
				} catch (IOException e) {
					e.printStackTrace();
				}            	
            	
            	 setIcon(new ImageIcon(stream.toByteArray()));
                setBounds(0, 0, 15, 15);
            	
            	setBackground(new java.awt.Color(225, 177, 173));
            	setFocusable(false);
            	setBorder(null);
            	
            	setModel(new DefaultButtonModel() {
          		  public void setPressed(boolean b) {
          		    if (!isPressed()) {
          		      // do not paint the shaded, gray-blue color
          		      super.setArmed(false);
          		      super.setPressed(false);
          		    }
          		    super.setPressed(b);
          		  }
          		});
            	
                return new Dimension(15, 30);
            }

        };
        
    }

    @Override
    protected void paintThumb(Graphics g, JComponent c, Rectangle thumbBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new java.awt.Color(189, 128, 136));
        g2.fillRoundRect(thumbBounds.x, thumbBounds.y, thumbBounds.width, thumbBounds.height, 0, 0);
        g2.dispose();
    }

    @Override
    protected void paintTrack(Graphics g, JComponent c, Rectangle trackBounds) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new java.awt.Color(225, 177, 173));
        g2.fillRoundRect(trackBounds.x, trackBounds.y, trackBounds.width, trackBounds.height, 0, 0);
        g2.dispose();
    }

}
