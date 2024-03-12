package osnovneKlase;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.sql.Statement;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


/**
 * 
 * Created by: Andrea
 * 
 * The window that appears when deleting a plant and confirms whether you are sure that you want to delete the plant
 */

public class DaLiSteSigurniDaZeliteDaObriseteBiljku extends JFrame implements MouseListener{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JLabel dugmeDA;
	private JLabel dugmeNE; 
	private Statement stmt;
	private  Connection conn;
	private JLabel btnX;
	private static Point initialClick;
	protected static DaLiSteSigurniDaZeliteDaObriseteBiljku daLiSteSigurniDaZeliteDaObriseteBiljkuInstanca;
	private ConnectionString cs = new ConnectionString();


	public DaLiSteSigurniDaZeliteDaObriseteBiljku() {
		
		//An instance of a class that does not invoke the constructor that would, in that case, open the window, which is not the goal
		daLiSteSigurniDaZeliteDaObriseteBiljkuInstanca =this;
		
		contentPane = new JPanel();
		contentPane.setBackground(new Color(189, 128, 136,100));
		setBounds(0, 0, 358, 196);
		contentPane.setLayout(null); 
		setUndecorated(true);
		setLocationRelativeTo(GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca);
		setResizable(false);
		
		//Mouse listener that enables a transparent screen color, and when the screen is moved, it becomes more intense in color
		addMouseListener(new MouseAdapter() {
	        public void mousePressed(MouseEvent e) {
	          initialClick = e.getPoint();
	          getComponentAt(initialClick);
	        }
	      });
	    
	    addMouseListener(new MouseAdapter() {
		public void mouseReleased(MouseEvent e) {
			//returns the color to opaque
			setOpacity((float) (1.0));
			contentPane.setBackground(new Color(189, 128, 136,200));
			
		}
	    });
	      addMouseMotionListener(new MouseAdapter() {
	        public void mouseDragged(MouseEvent e) {
	          // opacity 0.8 and window location, when moved - opacity is 80 percent.
	        	setOpacity((float) (0.8));
	          int thisX = getLocation().x;
	          int thisY = getLocation().y;

	          // Determines the location of the first click
	          int xMoved = e.getX() - initialClick.x;
	          int yMoved = e.getY() - initialClick.y;

	          // Move window to this position
	          int X = thisX + xMoved;
	          int Y = thisY + yMoved;
	          setLocation(X, Y);
	        }
	      });
		
	      //A button that closes the window - which is actually a label. It was easier for me to adjust the label instead of the button class
	       //because of the grey color when pressed that button class has whit similar settings
			btnX = new JLabel();
			btnX.setBackground(new Color(0,0,0,0));
			btnX.setOpaque(true);
			btnX.setHorizontalAlignment(JLabel.CENTER);
			btnX.setVerticalAlignment(JLabel.CENTER);
			btnX.setBorder(null);
			btnX.setFocusable(false);
			btnX.setIcon(new ImageIcon(DodajBiljkuProzor.class.getResource("/img/dugmeX15pikselaCrno.png")));
			btnX.setBounds(getWidth()-33,10, 23, 23);
			btnX.addMouseListener(new MouseListener() {

				@Override
				public void mouseClicked(MouseEvent e) {
					dispose();
					new ProzorObrisiBiljku();
				}
				@Override
				public void mousePressed(MouseEvent e) {}
				@Override
				public void mouseReleased(MouseEvent e) {}
				@Override
				public void mouseEntered(MouseEvent e) {}
				@Override
				public void mouseExited(MouseEvent e) {	}		
			});
			contentPane.add(btnX);

		
		JLabel lblNewLabel = new JLabel("Da li ste sigurni da želite da obrišete biljku?");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(0, 66, 358, 14);
		contentPane.add(lblNewLabel);
		
		dugmeDA = new JLabel("DA");
		dugmeDA.setBounds(76, 110, 89, 23);
		dugmeDA.setOpaque(true);
		dugmeDA.setHorizontalAlignment(JLabel.CENTER);
		dugmeDA.setVerticalAlignment(JLabel.CENTER); 
		dugmeDA.setBackground(new Color(37, 193, 183));
		dugmeDA.addMouseListener(this);
		contentPane.add(dugmeDA);
		
		dugmeNE = new JLabel("NE");
		dugmeNE.setBounds(189, 110, 89, 23);
		dugmeNE.setOpaque(true);
		dugmeNE.setHorizontalAlignment(JLabel.CENTER);
		dugmeNE.setVerticalAlignment(JLabel.CENTER); 
		dugmeNE.setBackground(new Color(37, 193, 183));
		contentPane.add(dugmeNE);
		dugmeNE.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
				dugmeNE.setBackground(new Color(205, 237, 232));
				dispose();
				new ProzorObrisiBiljku();
			}
			@Override
			public void mousePressed(MouseEvent e) {
				dugmeNE.setBackground(new Color(205, 237, 232));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dugmeNE.setBackground(new Color(37, 193, 183));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				dugmeNE.setBackground(new Color(205, 237, 232));							
			}
			@Override
			public void mouseExited(MouseEvent e) {
				dugmeNE.setBackground(new Color(37, 193, 183));
			}	
		});

		
		getContentPane().add(contentPane);
		setVisible(true);
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==dugmeDA) {
			dugmeDA.setBackground(new Color(205, 237, 232));
			
			 if(ProzorObrisiBiljku.obrisiBiljkuInstanca.index!=0) {
				
			
	            try {
	               conn = DriverManager.getConnection(cs.connectionString);
	               stmt = conn.createStatement();

	              stmt.executeUpdate("DELETE FROM `table_biljke` WHERE `RB` = '" +ProzorObrisiBiljku.redniBroj.get(ProzorObrisiBiljku.obrisiBiljkuInstanca.index-1)  + "';");

	              ProzorObrisiBiljku.obrisiBiljkuInstanca.comboBox.setSelectedItem("IZABERITE BILJKU ZA BRISANJE");


	             
		}catch(Exception ex) {
			ex.printStackTrace();
		} finally {
	          try {
		            if (stmt != null) {
		            	stmt.close(); 
		            }
		            if (conn != null)
		              this.conn.close(); 
		          } catch (SQLException ex) {	 
		        	  ex.printStackTrace();
		          } 
			
			
		        }
	            dispose();
	            new ProzorObavjestenje("Biljka je obrisana!");
		
	              }else {
	            	  
	            	  	dispose();  // window disposed
	            		 new ProzorObavjestenje("Niste izabrali biljku!"); // The notification window for all notifications is the same, only the messages are different
	            		
	            	 }
	            	  }
	
		
	}
	
	// Adjusting colors for the button when the mouse hovers over, clicks, presses, etc.
	@Override
	public void mousePressed(MouseEvent e) {
		dugmeDA.setBackground(new Color(205, 237, 232));
		dugmeDA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(205, 237, 232)));					
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dugmeDA.setBackground(new Color(37, 193, 183));
		dugmeDA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 193, 183)));				
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		
		dugmeDA.setBackground(new Color(205, 237, 232));
		dugmeDA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(205, 237, 232)));	
	}

	@Override
	public void mouseExited(MouseEvent e) {
		dugmeDA.setBackground(new Color(37, 193, 183));
		dugmeDA.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(37, 193, 183)));				
	}
	


}
