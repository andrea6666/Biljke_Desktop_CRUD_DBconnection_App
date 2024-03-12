package osnovneKlase;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;

/** Created by: Andrea
 * 
 * This class changes the dates for the plant, when it was watered and when it needs to be done in the future,
 *  when button 'Zaliveno' is pressed 
 */

public class DugmeZaliveno extends JButton implements MouseListener{
	
	
	/**
	 *  Created by: Andrea
	 * Sva dugmad su napravljena po istom principu, label koji mijenja bolje kada se napravi event i bira vrijednost po rednom broju iz arrayList
	 */
	private static final long serialVersionUID = 1L;
	private JLabel dugmeZaliveno;
	static ArrayList<JLabel> dugmeZalivenoLista = new ArrayList<>();
	static ArrayList<Integer> brojDanaZaZalivanjeLista = new ArrayList<>();
	private Connection conn;
	private Statement stmt;
	private int brojZaliveneBiljke;
	private static int brDanaZaZalivanjeIndex;
	private ConnectionString cs = new ConnectionString();
	
	public DugmeZaliveno() {
		
		

	dugmeZaliveno = new JLabel("Zaliveno");
	dugmeZaliveno.setOpaque(true);
	dugmeZaliveno.setHorizontalAlignment(JLabel.CENTER);
	dugmeZaliveno.setVerticalAlignment(JLabel.CENTER);
	dugmeZaliveno.setForeground(new java.awt.Color(0, 0, 0));
	dugmeZaliveno.setBackground(new Color(189, 128, 136));
	dugmeZaliveno.setBounds(20, 86 + GlavniProzor.glavniProzorInstanca.razmakIzmedjuBiljke,198, 23);  
	dugmeZalivenoLista.add(dugmeZaliveno);
	dugmeZaliveno.addMouseListener(this);
	GlavniProzor.glavniProzorInstanca.panel.add(dugmeZaliveno); // dodavanje dugmeta, bilo kojeg od slicnih na glavni prozor odnosno panel
	
	
	}


	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getSource()==dugmeZaliveno) {
			
			dugmeZaliveno.setBackground(new java.awt.Color(238, 203, 184));
			
			GlavniProzor.glavniProzorInstanca.razmakIzmedjuBiljke=0;
	
			for( int  i =0;i<dugmeZalivenoLista.size();i++) {
				
			if(e.getSource()==dugmeZalivenoLista.get(i)) {
				 brojZaliveneBiljke = GlavniProzor.brojRedaTabele.get(i);
				 brDanaZaZalivanjeIndex = i;
				
					pomjeriDatume(); // poziva funkciju da pomjeri datume kada se klikne zaliveno na datum dana kada je kliknuto dugme


					
			}


			}
			GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();  // vreca na glavni prozor - update



		}
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		dugmeZaliveno.setBackground(new java.awt.Color(238, 203, 184));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dugmeZaliveno.setBackground(new Color(189, 128, 136));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		dugmeZaliveno.setBackground(new java.awt.Color(238, 203, 184));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		dugmeZaliveno.setBackground(new Color(189, 128, 136));
	}
	
	
	
	
	public void pomjeriDatume() {
		  while (conn==null) {   // u nekim povezivanjima sa bazom sam uradila da se veza uspostavlja sve dok se ne uspostavi, ostale sam ostavila na jednom pokusaju, nisam stigla toliko da mijenjam stanje aplikacije, na par mjesta sam samo pokazala kao primjer kako se radi i cemu sluzi
		  try {

				
				conn = DriverManager.getConnection(cs.connectionString);
				stmt = conn.createStatement();

				
				stmt.executeUpdate("UPDATE `table_biljke` SET `datum_poslednjeg_zalivanja` = '" + noviDatumPoslednjegZalivanja() + "' WHERE `RB` = '" +brojZaliveneBiljke + "';");
				stmt.executeUpdate("UPDATE `table_biljke` SET `datum_sledeceg_zalivanja` = '" + noviNaredniDatumZalivanja(GlavniProzor.br_dana_na_koji_se_zalivaLista.get(brDanaZaZalivanjeIndex)) + "' WHERE `RB` = '" +brojZaliveneBiljke + "';");


	        	} catch (SQLException e) {
                    e.printStackTrace();
                    System.err.println("Failed to connect to the database: " + e.getMessage());
                    System.out.println("Retrying connection...");
                    // Wait for a certain duration before attempting the connection again
                    try {
                        Thread.sleep(2000); // Wait for 2 seconds before retrying
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                
                }
            }
		  System.out.println("Konekcija zatvorena DugmeZaliveno.");
        }
            
         
        
	
	 public Date noviDatumPoslednjegZalivanja() {
		 	//danasnji datum
	        LocalDate danas = LocalDate.now();

	        // LocalDate konvertovan u sqlDate
	        Date sqlDatum = Date.valueOf(danas);
	        return sqlDatum;
	 }
	 
	 public  Date noviNaredniDatumZalivanja(int brojDanaZaZalivanje) {
		 
		  LocalDate danas = LocalDate.now();
	      LocalDate naredniDatumZaZalivanje = danas.plusDays(brojDanaZaZalivanje);
	      Date sqlDatum = Date.valueOf(naredniDatumZaZalivanje);
	      
	      return sqlDatum;
	 }
	 


}
