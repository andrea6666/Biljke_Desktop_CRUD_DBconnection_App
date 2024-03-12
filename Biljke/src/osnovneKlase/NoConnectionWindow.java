package osnovneKlase;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class NoConnectionWindow extends JFrame {

	/**
	 *  Created by: Andrea
	 *  
	 * Window used in case that there is no connection to the database, window opens when application starts and the main 
	 * application window wont run after this window, when closed the application stops running
	 * if there is connection to the database application will work regularly and this window wont show up 
	 */

	private static final long serialVersionUID = 1L;
	private String labelText;
	private JPanel contentPane;
	private JLabel btnX;
	private Point initialClick;
	protected static NoConnectionWindow noConnectionWindow;

	public NoConnectionWindow(String labelText) {
		if(noConnectionWindow == null) {
		noConnectionWindow = this;
		this.labelText = labelText;
		setUndecorated(true);
		setBounds(100, 100, 358, 196);
		setBackground(new Color(0,0,0,0));
		contentPane = new JPanel();
		contentPane.setBackground(new Color(189, 128, 136));		
		contentPane.setLayout(null);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		setLocationRelativeTo(GlavniProzor.glavniProzorInstanca);
		setResizable(false);
		
		addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	          initialClick = e.getPoint();
	          getComponentAt(initialClick);
	        }
	      });
	    
	    addMouseListener(new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			setOpacity((float) (1.0));
			contentPane.setBackground(new Color(189, 128, 136));
			
		}
	    });
	      addMouseMotionListener(new MouseAdapter() {
	        public void mouseDragged(MouseEvent e) {
	          // get location of Window
	        	setOpacity((float) (0.8));
	          int thisX = getLocation().x;
	          int thisY = getLocation().y;

	          // Determine how much the mouse moved since the initial click
	          int xMoved = e.getX() - initialClick.x;
	          int yMoved = e.getY() - initialClick.y;

	          // Move window to this position
	          int X = thisX + xMoved;
	          int Y = thisY + yMoved;
	          setLocation(X, Y);
	        }
	      });
		
			btnX = new JLabel();
			btnX.setHorizontalAlignment(JLabel.CENTER);
			btnX.setVerticalAlignment(JLabel.CENTER);
			btnX.setBackground(new Color(0,0,0,0));
			btnX.setFocusable(false);
			btnX.setIcon(new ImageIcon(DodajBiljkuProzor.class.getResource("/img/dugmeX15pikselaCrno.png")));
			btnX.setBounds(getWidth()-33,10, 23, 23);
			btnX.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0,0,0,0)));

			btnX.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {					

					System.exit(0);
				}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {}	
			});
			contentPane.add(btnX);

		JLabel lblNewLabel = new JLabel(labelText);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER); 
		lblNewLabel.setBounds(0, 70, 358, 46);
		contentPane.add(lblNewLabel);
		setVisible(true);
	}
	}
}
