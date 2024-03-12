package osnovneKlase;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;

/**
 * Created by: Andrea
 * 
 * This is main panel that shows all the data in the main Frame
 */

public class GlavniProzor extends JPanel implements MouseListener {

	/**
	 * Ovo je glavni prozor koji se prvi pojavljuje prilikom pokretanja aplikacije, ustvari panel koji ide na glavni prozor
	 */
	private static final long serialVersionUID = 1L;
	static Connection conn;
	private Statement stmt;
	protected static ArrayList<Integer> brojRedaTabele = new ArrayList<>();
	private int RB;
	private int brojBiljke;
	private String ime_biljke;
	protected static ArrayList<String> izborBiljke= new ArrayList<>();
	private String datum_poslednjeg_zalivanja;
	protected String datum_sledeceg_zalivanja;
	private int br_dana_na_koji_se_zaliva;
	private ArrayList<String>poslednje_zalivanjeLista = new ArrayList<>();
	private ArrayList<String> aktuelno_zalivanjeLista= new ArrayList<>();
	protected static ArrayList<Integer> br_dana_na_koji_se_zalivaLista= new ArrayList<>();
	protected int razmakIzmedjuBiljke;
	protected static GlavniProzor glavniProzorInstanca;
	private JLabel minimiziraj;
	private JLabel dodajBiljku;
	private JLabel ugasi;
	private JLabel obrisi;
	private JLabel izmijeni;
	private JLabel lblNewLabel;
	protected JPanel panel;
	private JPanel label6;
	private JLabel lblNewLabel1; 				
	protected JScrollPane scrollPane;
	private static JScrollBar scrollBar;
	private ConnectionString cs = new ConnectionString();


	
	 
	
	public GlavniProzor() {
		lblNewLabel1 = new JLabel(); // background image label

		if( conn ==null) {  
		
		//Clears everything from the window and resets, not necessary during the application startup, only when resetting the window
		
		brojRedaTabele.clear();
		izborBiljke.clear();
		aktuelno_zalivanjeLista.clear();
		poslednje_zalivanjeLista.clear();
		br_dana_na_koji_se_zalivaLista.clear();
		razmakIzmedjuBiljke = 0;
		
		 DugmeUputstvo.dugmeUputstvoLista.clear();
		 DugmeSlika.dugmeSlikaLista.clear();
		 DugmeZaliveno.dugmeZalivenoLista.clear();

		 repaint();
		 revalidate();
		
		
		glavniProzorInstanca = this;
		
		setSize(792, 590);
	    setBackground(new Color(0, 0, 0));
		setLayout(null);
		
		lblNewLabel = new JLabel();    // The main label to which everything is added
		lblNewLabel.setBounds(0, 0, 792, 590);
		lblNewLabel.setFocusable(false);
		lblNewLabel.setOpaque(true);
	    add(lblNewLabel);
	    
        
        label6 = new JPanel();  // Panel for displaying plants, tables, buttons with a scrollbar
        label6.setBackground(new Color(225, 177, 173));
        label6.setBounds(201, 100, 565, 430);
        label6.setLayout(null);
        lblNewLabel.add(label6);
		
        izmijeni = new JLabel();  // Button for modifying the plant - launching the modification window
        izmijeni.setOpaque(true);
        izmijeni.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/pencilSmanjena.png")));
        izmijeni.setBackground(new Color(189, 128, 136));
        izmijeni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 128, 136)));
        izmijeni.setFocusable(false);
        izmijeni.setBounds(635, 43, 30, 30);
        izmijeni.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				izmijeni.setBackground(new java.awt.Color(238, 203, 184));
				izmijeni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(238, 203, 184)));
				panel.setVisible(false);
				if (scrollBar != null) {
					scrollBar.setVisible(false);
				}
				new ProzorIzmijeniBiljku();
				bicikloPozadina();
				
			}
			public void mousePressed(MouseEvent e) {
				izmijeni.setBackground(new java.awt.Color(238, 203, 184));
				izmijeni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(238, 203, 184)));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				izmijeni.setBackground(new Color(189, 128, 136));
				izmijeni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 128, 136)));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				izmijeni.setBackground(new java.awt.Color(238, 203, 184));
				izmijeni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(238, 203, 184)));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				izmijeni.setBackground(new Color(189, 128, 136));
				izmijeni.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 128, 136)));
			}
		});
		lblNewLabel.add(izmijeni);
        
        dodajBiljku  = new JLabel(); // Button for adding a plant - opening the window
        dodajBiljku.setOpaque(true);
        dodajBiljku.setHorizontalAlignment(JLabel.CENTER);
        dodajBiljku.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/plus.png")));
        dodajBiljku.setBounds(675, 43, 30, 30);
        dodajBiljku.setFocusable(false);
        dodajBiljku.setBackground(new Color(189, 128, 136));
        dodajBiljku.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 128, 136)));
        dodajBiljku.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				dodajBiljku.setBackground(new java.awt.Color(238, 203, 184));
				
				new DodajBiljkuProzor();
				bicikloPozadina();
			}
			public void mousePressed(MouseEvent e) {
				dodajBiljku.setBackground(new java.awt.Color(238, 203, 184));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				dodajBiljku.setBackground(new Color(189, 128, 136));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				dodajBiljku.setBackground(new java.awt.Color(238, 203, 184));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				dodajBiljku.setBackground(new Color(189, 128, 136));
			}
		});
        lblNewLabel.add(dodajBiljku);
        
        obrisi = new JLabel();  // Button for deleting a plant - opening the window
        obrisi.setOpaque(true);
        obrisi.setFocusable(false);
        obrisi.setBackground(new Color(189, 128, 136));
        obrisi.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(189, 128, 136)));
        obrisi.setHorizontalAlignment(JLabel.CENTER);
        obrisi.setVerticalAlignment(JLabel.CENTER);
        obrisi.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/kanta.png")));
        obrisi.setBounds(715, 43, 30, 30);
        obrisi.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				obrisi.setBackground(new java.awt.Color(238, 203, 184));
			
				bicikloPozadina();
		        new ProzorObrisiBiljku();
				}
			
			@Override
			public void mousePressed(MouseEvent e) {
				obrisi.setBackground(new java.awt.Color(238, 203, 184));
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				obrisi.setBackground(new Color(189, 128, 136));
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				obrisi.setBackground(new java.awt.Color(238, 203, 184));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				obrisi.setBackground(new Color(189, 128, 136));
			}
		});
		lblNewLabel.add(obrisi);
        
        minimiziraj = new JLabel();  // Button for minimizing the window
        minimiziraj.setHorizontalAlignment(JLabel.CENTER);
        minimiziraj.setVerticalAlignment(JLabel.CENTER);
        minimiziraj.setFocusable(false);
        minimiziraj.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/minimize15pikselaCrno.png")));
        minimiziraj.setBounds(698, 13, 15, 15);
        minimiziraj.setBackground(new java.awt.Color(0, 0, 0,0));
        minimiziraj.setBorder(null);
        minimiziraj.addMouseListener(this);
        minimiziraj.setFocusable(false);
        lblNewLabel.add(minimiziraj);
        
        ugasi = new JLabel();  // Button for closing the window - as well as the entire application
        ugasi.setFocusable(false);
        ugasi.setHorizontalAlignment(JLabel.CENTER);
        ugasi.setVerticalAlignment(JLabel.CENTER);
        ugasi.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/dugmeX15pikselaCrno.png")));
        ugasi.setBounds(729, 13, 15, 15);
        ugasi.setBackground(new java.awt.Color(0, 0, 0,0));
        ugasi.setBorder(null);
        ugasi.addMouseListener(this);
        ugasi.setFocusable(false);
        lblNewLabel.add(ugasi);
        
		

        
        
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBackground(new java.awt.Color(225, 177, 173));
		


	        setVisible(true); 
	        
	        // Connecting to the database until the connection is established
	        
	        while (conn==null) {
	        	
	        try {
	        

	        	//Loading data from the database necessary for the main window
	        	
				
				conn = DriverManager.getConnection(cs.connectionString);
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM table_biljke;");
				while (rs.next()) {
					RB = rs.getInt("RB");
					ime_biljke = rs.getString("ime_biljke");
					datum_poslednjeg_zalivanja = rs.getString("datum_poslednjeg_zalivanja");
					datum_sledeceg_zalivanja = rs.getString("datum_sledeceg_zalivanja");
					br_dana_na_koji_se_zaliva = rs.getInt("br_dana_na_koji_se_zaliva");
					
					
					
					brojRedaTabele.add(RB);
					izborBiljke.add(ime_biljke);
					poslednje_zalivanjeLista.add(datum_poslednjeg_zalivanja);
					aktuelno_zalivanjeLista.add(datum_sledeceg_zalivanja);
					br_dana_na_koji_se_zalivaLista.add(br_dana_na_koji_se_zaliva);
					
					
					novaBiljka();
					

					brojBiljke += 1; 


				}
				
				
				
				lblNewLabel1.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/RoseNaDrvetu.png")));
				lblNewLabel1.setHorizontalAlignment(JLabel.LEFT);
				lblNewLabel1.setBackground(new Color(255, 153, 204));
				lblNewLabel1.setBounds(0, 0, 792, 619);
				lblNewLabel1.setFocusable(false);
				lblNewLabel1.setOpaque(true);
				lblNewLabel1.setVerticalAlignment(JLabel.TOP);
				lblNewLabel.add(lblNewLabel1);
				
	        	}  catch (SQLException e) {
	        	
	        	//window that opens instead of main window in case that there is no database connection 
	        		new NoConnectionWindow("<html>Nije uspostavljena veza sa bazom podataka!<br>Program ne može da se pokrene!<html>");
	        		
	                e.printStackTrace();
	            } finally {
	                if (stmt != null) {
	                    try {
	                        stmt.close();
	                    } catch (SQLException e) {
	                        e.printStackTrace();
	                    }
	                }
	                
	                // Close the connection
	                if (conn != null) {
	                    try {
	                        conn.close();
	                    } catch (SQLException e) {
	                        e.printStackTrace();

	                        // Wait for a certain duration before attempting the connection again
	                        try {
	                            Thread.sleep(2000); // Wait for 2 seconds before retrying
	                        } catch (InterruptedException ex) {
	                            ex.printStackTrace();
	                        }
	                    
	                    }
	                }
	            }
	                
	                System.out.println("Konekcija zatvorena GlavniProzor.");
	                
	              
	            } 
	        
	        conn =null;
	        
	    	panel.setPreferredSize(new Dimension(545, brojRedaTabele.size()*200));
	    	panel.setBounds(0, 0, 545, brojRedaTabele.size()*200);
	    	repaint();
	    	revalidate();
	    	 scrollPane = new JScrollPane();
	    	    scrollPane.setBounds(0, 0, 565, 430);
	    	    scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(15, 0));
	    	    scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 15));
	    	    scrollPane.setBorder(BorderFactory.createLineBorder(new Color(225, 177, 173, 150), 1));
	    	    scrollPane.setBackground(new Color(225, 177, 173));
	    	    scrollPane.setHorizontalScrollBarPolicy(31);
	    	    scrollPane.setVerticalScrollBarPolicy(20);
	    	    scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());
	    	    scrollPane.setViewportView(panel);
	    	    scrollPane.repaint();
	    	    scrollPane.revalidate();
	    	    label6.add(scrollPane);   //scroll pane for all plants
	    	    
		}
		
	}
	
	//end of the constructor 
	



	private void novaBiljka() { // function for adding each new plant
		
        JLabel lblNewLabel = new JLabel(ime_biljke); // name of the plant label
        lblNewLabel.setOpaque(true);
        lblNewLabel.setBackground(new Color(100, 67, 62));
        lblNewLabel.setHorizontalAlignment(JLabel.CENTER);
        lblNewLabel.setVerticalAlignment(JLabel.CENTER);
		lblNewLabel.setForeground(new Color(225, 180, 36));
		Font font = new Font("Dialog", Font.BOLD, 16);
		lblNewLabel.setFont(font);		
		lblNewLabel.setBounds(20, 26 + razmakIzmedjuBiljke,508, 23);  
		panel.add(lblNewLabel);
			

		Object[][] data = {  // table for displaying dates
	            {datumIzSQLformataUformatZaTabelu(datum_poslednjeg_zalivanja), datumIzSQLformataUformatZaTabelu(datum_sledeceg_zalivanja)}
	        };

	        String[] columnNames = {"Poslednje zalivanje:", "Naredno zalivanje:"}; // column names that are not headers because I couldn't adjust them properly
	        // after numerous attempts to make them look right; 
	        //there was always some white line between cells, so I set them as labels
	        
	        JLabel hederPoslednjeZalivanje = new JLabel("Poslednje zalivanje:");
	        hederPoslednjeZalivanje.setOpaque(true);
	        hederPoslednjeZalivanje.setBackground(new Color(100, 67, 62)); // header improvised from labels
	        hederPoslednjeZalivanje.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
	        hederPoslednjeZalivanje.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(25, 14, 22)));
	        hederPoslednjeZalivanje.setBounds(237, 56 + razmakIzmedjuBiljke, 146, 24); 
	        hederPoslednjeZalivanje.setForeground(new Color(225, 180, 36));
	        hederPoslednjeZalivanje.setHorizontalAlignment(JLabel.CENTER);
	        hederPoslednjeZalivanje.setVerticalAlignment(JLabel.CENTER);
			 panel.add(hederPoslednjeZalivanje);
			 
			 JLabel hederNarednoZalivanje = new JLabel("Naredno zalivanje:");
			 hederNarednoZalivanje.setOpaque(true);
			 hederNarednoZalivanje.setBackground(new Color(100, 67, 62));// header improvised from labels
			 hederNarednoZalivanje.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
			 hederNarednoZalivanje.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(25, 14, 22)));
			 hederNarednoZalivanje.setBounds(384, 56 + razmakIzmedjuBiljke, 145, 24); 
			 hederNarednoZalivanje.setForeground(new Color(225, 180, 36));
			 hederNarednoZalivanje.setHorizontalAlignment(JLabel.CENTER);
			 hederNarednoZalivanje.setVerticalAlignment(JLabel.CENTER);
			 panel.add(hederNarednoZalivanje);

	        JTable table = new JTable(data, columnNames);  // table with dates of the last and next watering
	        table.setRowSelectionAllowed(false);
	        table.setEnabled(false);
	        table.setBounds(238,86+razmakIzmedjuBiljke,291,23);  
	        table.setRowHeight(23);
	        table.setForeground(new Color(225, 180, 36));
	        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
	        table.setDefaultRenderer(Object.class, centerRenderer);
	        table.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
	        table.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(25, 14, 22)));
	        table.setShowHorizontalLines(true);
	        table.setShowVerticalLines(true);
	        table.setBackground(new Color(0,0,0,0));
	        table.getColumnModel().setColumnMargin(2);
	        table.setDefaultRenderer(Object.class, new MojRenderer());
			panel.add(table);



			new DugmeZaliveno();  // Adding buttons to the main window for instructions, image, and changing the watering date
			new DugmeUputstvo();
			new DugmeSlika();
	        
	        razmakIzmedjuBiljke += 150; // spacing between components of one plant and the next plant
	        repaint(); // window refresh
	        revalidate();  // window refresh

	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==ugasi) {
			System.exit(0); // Closes the main window, and the application stops running
		}


		if(e.getSource()==minimiziraj) {
			GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.setState(Frame.ICONIFIED); // minimizes the application
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



	public String datumIzSQLformataUformatZaTabelu(String datumSQLformat) {
	        
	        // turning SQL date into LocalDate
	        LocalDate datumZaTabelu = LocalDate.parse(datumSQLformat);
	        
	        //  formats the date in order day-month-year
	        DateTimeFormatter formatDatuma = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	        
	        // formats the final date for the user table of the application
	        String formiranDatumPoslednjegZalivanja = datumZaTabelu.format(formatDatuma);
	        
			return formiranDatumPoslednjegZalivanja;
	}
	
	public void bicikloPozadina() {  // background for secondary screens
		
		lblNewLabel.remove(izmijeni);
		lblNewLabel.remove(dodajBiljku);
		lblNewLabel.remove(obrisi);
		lblNewLabel.remove(label6);
     	lblNewLabel1.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/biciklo1.png")));
	
		
		
		repaint();
		revalidate();

	}
    


	
}
