package osnovneKlase;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;

public class ProzorImijeniDodajProcitajUputstvo extends JPanel {

	/**
	 * Created by: Andrea
	 * 
	 *  Window used as needed for modification, adding new instructions, or reading watering instructions
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Statement stmt = null;
	private Connection conn;
	private JLabel sacuvaj;
	private String uputstvo;
	private JTextArea textArea;
	private ResultSet rs;
	public static ProzorImijeniDodajProcitajUputstvo prozorImijeniDodajProcitajUputstvoInstanca;
	private JLabel btnX;
	protected JScrollPane scrollPane;
	private ConnectionString cs = new ConnectionString();
	
	// constructor for applying properties depending on the need for reading, editing, or adding instructions
	public ProzorImijeniDodajProcitajUputstvo(String nazivProzora, boolean dodajDugmeNaPanel , boolean procitajUputstvo, boolean uputstvoEditable) {
		
		prozorImijeniDodajProcitajUputstvoInstanca=this; // an instance that does not invoke the constructor
		
		setBounds(0, 0, GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getWidth(), GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getHeight());
		setLayout(null);
		
		JLabel lblNewLabel1 = new JLabel();
		add(lblNewLabel1);
		lblNewLabel1.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/RoseNaDrvetu.png")));
		lblNewLabel1.setHorizontalAlignment(JLabel.LEFT);
		lblNewLabel1.setBounds(0, 0, 792, 619);
		lblNewLabel1.setFocusable(false);
		lblNewLabel1.setVerticalAlignment(JLabel.TOP);
		
		btnX = new JLabel(""); //closes the panel
		btnX.setOpaque(true);
		btnX.setHorizontalAlignment(JLabel.CENTER);
		btnX.setVerticalAlignment(JLabel.CENTER);
		btnX.setBackground(new Color(0,0,0,0));
		btnX.setBorder(null);
		btnX.setFocusable(false);
		btnX.setIcon(new ImageIcon(DodajBiljkuProzor.class.getResource("/img/dugmeX15pikselaCrno.png")));
		btnX.setBounds(729, 13, 15, 15);
		btnX.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				
				
				if(DodajBiljkuProzor.dodajBiljkuProzorInstanca!=null) {
					setVisible(false);
					DodajBiljkuProzor.dodajBiljkuProzorInstanca.setVisible(true);
					GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();
					GlavniProzor.glavniProzorInstanca.bicikloPozadina();
					DugmeUputstvo.dugmeUputstvoInstanca=null; // This sets the window add plant and everything below to null; this way prevents 
					// the window from being opened more than once to avoid errors 
					//  multiple windows opened for entering data into the database 
					// for example, during modification or adding a new plant
					}	
				
				if(ProzorIzmijeniBiljku.prozorIzmijeniBiljkuInstanca!=null) { 
					setVisible(false);
					ProzorIzmijeniBiljku.prozorIzmijeniBiljkuInstanca.setVisible(true);
					GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();
					GlavniProzor.glavniProzorInstanca.bicikloPozadina();
					DugmeUputstvo.dugmeUputstvoInstanca=null;
					
					}	
				if(DugmeUputstvo.dugmeUputstvoInstanca!=null) {
					setVisible(false);
					GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();
					
					}

				
	
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
	    
		lblNewLabel1.add(btnX);
		
		JLabel label = new JLabel(nazivProzora);
		label.setOpaque(true);
		Font font = new Font("Dialog", Font.BOLD, 20);
		label.setFont(font);	
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setBackground(new Color(0,0,0,0));
		label.setForeground(new Color(0,0,0));
		label.setBounds(0, 15, this.getWidth(),60);
		lblNewLabel1.add(label);
		
		
		JPanel panelText = new JPanel();
		panelText.setBounds(40,80, this.getWidth()-80,this.getHeight()-160);
		panelText.setBackground(new Color(255,255,255,0));  
		lblNewLabel1.add(panelText);
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setLocation(0, 0);
		scrollPane.setBackground(new Color(255,255,255,0));  
		scrollPane.setMinimumSize(new Dimension(panelText.getWidth()-2,panelText.getHeight()-4));
		scrollPane.setMaximumSize(new Dimension(panelText.getWidth()-2,panelText.getHeight()-4));
		scrollPane.setPreferredSize(new Dimension(panelText.getWidth()-2,panelText.getHeight()-4));
		scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());
	    panelText.add(scrollPane);
	    panelText.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setBorder(null);
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		if(uputstvoEditable==true) {
		textArea.setEditable(true);
		}
		else {
			textArea.setEditable(false);
		}
		if(procitajUputstvo == true) {
		try{
	  		  
			
				conn = DriverManager.getConnection(cs.connectionString);
  				stmt = conn.createStatement();  				
  				pozivanjeUputstvaDugmeUputstvo();
  				if(ProzorIzmijeniBiljku.prozorIzmijeniBiljkuInstanca!=null) {
  				pozivanjeUputstvaDugmeIzmijeniUputstvo();
  				}
  				  				
  				
  				if(rs.next()) {
  
  			
  				 String uputstvo = rs.getString("uputstvo");
  				 
  				textArea.setText(uputstvo);
		    
  				}
		
	}catch(Exception ex) {
		ex.printStackTrace();
	}finally {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        // closes connection 
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        System.out.println("Konekcija zatvorena ProzorIzmijeniDodajProcitajUputstvo.");
    }
		}
		textArea.setBounds(0,0, scrollPane.getWidth()+5,scrollPane.getHeight());
		scrollPane.setViewportView(textArea);
		
		sacuvaj = new JLabel("SAČUVAJTE");
		sacuvaj.setBounds(this.getWidth()-144,this.getHeight()-64, 105, 23);
		sacuvaj.setBackground(new Color(189, 128, 136));  
		sacuvaj.setVerticalAlignment(SwingConstants.CENTER);
		sacuvaj.setHorizontalAlignment(SwingConstants.CENTER);
		sacuvaj.setOpaque(true);
		sacuvaj.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
	
				sacuvaj.setBackground(new java.awt.Color(238, 203, 184));

				if(textArea.getText()==""|| textArea.getText().equals(null) || textArea.getText().isBlank() || textArea.getText().isEmpty()) {
					new ProzorObavjestenje("Niste dodali uputstvo!"); 

				}else {
					 textArea.getText();
						setVisible(false);

						GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();
						GlavniProzor.glavniProzorInstanca.bicikloPozadina();
					
					if(DodajBiljkuProzor.dodajBiljkuProzorInstanca!=null) {
						DodajBiljkuProzor.dodajBiljkuProzorInstanca.setVisible(true);
						}
					if(ProzorIzmijeniBiljku.prozorIzmijeniBiljkuInstanca!=null) {
						ProzorIzmijeniBiljku.prozorIzmijeniBiljkuInstanca.setVisible(true);
						}
				}
			
			
			}

			@Override
			public void mousePressed(MouseEvent e) {
				sacuvaj.setBackground(new java.awt.Color(238, 203, 184));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				sacuvaj.setBackground(new Color(189, 128, 136));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				sacuvaj.setBackground(new java.awt.Color(238, 203, 184));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				sacuvaj.setBackground(new Color(189, 128, 136));
			}
		});
		
		if(dodajDugmeNaPanel == true) {
		lblNewLabel1.add(sacuvaj);
		}
		
		scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.add(this);
}



	public JTextArea getTextArea() {
		return textArea;
	}


	public void setTextArea(JTextArea textArea) {
		this.textArea = textArea;
	}
	
	

	public String getUputstvo() {
		uputstvo = textArea.getText();

		return uputstvo;
	}



	public void pozivanjeUputstvaDugmeUputstvo() throws SQLException {
		 rs = stmt.executeQuery("SELECT * FROM table_biljke WHERE RB='"+GlavniProzor.brojRedaTabele.get(DugmeUputstvo.getBrKliknutogDugmetaUputstvo())+"';");

	}
	public void pozivanjeUputstvaDugmeIzmijeniUputstvo() throws SQLException {
		rs = stmt.executeQuery("SELECT * FROM table_biljke WHERE RB='"+GlavniProzor.brojRedaTabele.get(ProzorIzmijeniBiljku.prozorIzmijeniBiljkuInstanca.getIndex()-1)+"';");

	}
	
}