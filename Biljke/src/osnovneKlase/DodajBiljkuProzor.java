package osnovneKlase;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Date;
import java.util.Base64;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import com.toedter.calendar.JDateChooser;


/**
 * Created by: Andrea
 * 
 * This class is used to add a new plant to the application database
 */


public class DodajBiljkuProzor extends JFrame implements MouseListener,KeyListener {


	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField dodajNzivBiljkeTxt;
	private JTextField unesiBrojDanaNaKojiSeZalivaBiljka;
	private JLabel dodajNazivlbl;
	private JLabel dodajFotografijulbl;
	private JLabel dodajUputstvolbl;
	private JLabel pocetniDatumZalivanjalbl;
	private JLabel brDanaNaKojiSeZalivalbl;
	private JLabel dugmeDodajFotografiju;
	private JLabel dugmeDodajUputstvo;
	private JDateChooser pocetniDatumZalivanjaDateChooser =null;
	private JTextField pocetniDatumZalivanjaDateField;
	private JLabel dugmeSacuvaj;
	private String putanjaDoSlike;
	private String base64StringSlika;
	String fotografija = "";
	PreparedStatement statement;
	Connection conn;
	private boolean duplaBiljka = false;
	 private static Point initialClick;
	 private JLabel btnX;
	 protected static DodajBiljkuProzor dodajBiljkuProzorInstanca;
	private ConnectionString cs = new ConnectionString();

	
	public DodajBiljkuProzor() {
		
		dodajBiljkuProzorInstanca = this;
		
		setBounds(0, 0,  429, 378);
		contentPane = new JPanel();
		setUndecorated(true);  // No default JFrame border/frame, frame removed
		contentPane.setLayout(null);
		setBackground(new Color(37, 193, 183,80));
		contentPane.setBackground(new Color(37, 193, 183,80));
		setLocationRelativeTo(GlavniProzor.glavniProzorInstanca);
		setResizable(false);  // window cant be resized 
		setContentPane(contentPane);
		
		//Adjusting opacity - I could have extracted this method into a separate class to avoid repeating code, but I didn't have time
		 addMouseListener(new MouseAdapter() {
		        public void mousePressed(MouseEvent e) {
		          initialClick = e.getPoint();
		          getComponentAt(initialClick);
		        }
		      });
		    
		    addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				setOpacity((float) (1.0));
				contentPane.setBackground(new Color(37, 193, 183,200));
				
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
				btnX.setBackground(new Color(0,0,0,0));
				btnX.setOpaque(true);
				btnX.setHorizontalAlignment(JLabel.CENTER);
				btnX.setVerticalAlignment(JLabel.CENTER);
				btnX.setBorder(null);
				btnX.setFocusable(false);
				btnX.setIcon(new ImageIcon(DodajBiljkuProzor.class.getResource("/img/dugmeX15pikselaCrno.png")));
				btnX.setBounds(388, 20, 23, 23);
				btnX.addMouseListener(this);
				contentPane.add(btnX);


		
		dodajNazivlbl = new JLabel("Dodajte naziv biljke:");   
		dodajNazivlbl.setBounds(24, 72, 158, 14);
		contentPane.add(dodajNazivlbl);
		
		dodajFotografijulbl = new JLabel("Dodajte fotografiju biljke:");
		dodajFotografijulbl.setBounds(24, 119, 200, 14);
		contentPane.add(dodajFotografijulbl);
		
		dodajUputstvolbl = new JLabel("Dodajte uputstvo:");
		dodajUputstvolbl.setBounds(24, 164, 200, 20);
		contentPane.add(dodajUputstvolbl);
		
		pocetniDatumZalivanjalbl = new JLabel("Izaberite početni datum zalivanja:");
		pocetniDatumZalivanjalbl.setBounds(24, 213, 213, 14);
		contentPane.add(pocetniDatumZalivanjalbl);
		
		brDanaNaKojiSeZalivalbl = new JLabel("Unesite broj dana na koji se zaliva biljka:");
		brDanaNaKojiSeZalivalbl.setBounds(24, 257, 245, 14);
		contentPane.add(brDanaNaKojiSeZalivalbl);
		
		dodajNzivBiljkeTxt = new JTextField();
		dodajNzivBiljkeTxt.setBounds(167, 70, 220, 20);
		dodajNzivBiljkeTxt.setBackground(new Color(240,240,240));
		contentPane.add(dodajNzivBiljkeTxt);
		
		dugmeDodajFotografiju = new JLabel("DODAJTE");
		dugmeDodajFotografiju.setBounds(277, 115, 110, 23);
		dugmeDodajFotografiju.setOpaque(true);
		dugmeDodajFotografiju.setHorizontalAlignment(JLabel.CENTER);
		dugmeDodajFotografiju.setVerticalAlignment(JLabel.CENTER);
		dugmeDodajFotografiju.setBackground(new Color(189, 128, 136));
		contentPane.add(dugmeDodajFotografiju);
		dugmeDodajFotografiju.addMouseListener(this);
		
		dugmeDodajUputstvo = new JLabel("DODAJTE");
		dugmeDodajUputstvo.setBounds(277, 163, 110, 23);
		dugmeDodajUputstvo.setOpaque(true);
		dugmeDodajUputstvo.setHorizontalAlignment(JLabel.CENTER); // horizontal centering of text
		dugmeDodajUputstvo.setVerticalAlignment(JLabel.CENTER);   // vertical centering of text
		dugmeDodajUputstvo.setBackground(new Color(189, 128, 136));
		contentPane.add(dugmeDodajUputstvo);
		dugmeDodajUputstvo.addMouseListener(this);
		
		unesiBrojDanaNaKojiSeZalivaBiljka = new JTextField();
		unesiBrojDanaNaKojiSeZalivaBiljka.setBackground(new Color(240,240,240));
		unesiBrojDanaNaKojiSeZalivaBiljka.setBounds(277, 254, 110, 20);
		contentPane.add(unesiBrojDanaNaKojiSeZalivaBiljka);
		unesiBrojDanaNaKojiSeZalivaBiljka.addKeyListener(this);
		
		pocetniDatumZalivanjaDateChooser = new JDateChooser();
		pocetniDatumZalivanjaDateChooser.setBounds(277, 210, 110, 20);
		//changes dates to Serbian language and format
		pocetniDatumZalivanjaDateChooser.setDateFormatString("dd.MM.yyyy");
        Locale serbianLocale = new Locale.Builder().setLanguage("sr").setScript("Latn").build();
       
        pocetniDatumZalivanjaDateChooser.setLocale(serbianLocale);
		contentPane.add(pocetniDatumZalivanjaDateChooser);
		
		pocetniDatumZalivanjaDateField = ((JTextField) pocetniDatumZalivanjaDateChooser.getComponent(1));
		pocetniDatumZalivanjaDateField.setBackground(new Color(240,240,240));
		pocetniDatumZalivanjaDateField.addKeyListener(new KeyListener() {
			
			// This key listener prevents anything from being entered into the field, for security reasons to avoid errors when retrieving data from the field
			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {
					e.consume();
				}
				if (!Character.isAlphabetic(c)) {
					e.consume();
				}
			}
			@Override
			public void keyPressed(KeyEvent e) {}
			@Override
			public void keyReleased(KeyEvent e) {}		
		});
		
		dugmeSacuvaj = new JLabel("SAČUVAJTE");
		
		dugmeSacuvaj.setBounds(277, 314, 110, 23);
		dugmeSacuvaj.setOpaque(true);
		dugmeSacuvaj.setHorizontalAlignment(JLabel.CENTER);
		dugmeSacuvaj.setVerticalAlignment(JLabel.CENTER);
		dugmeSacuvaj.setBackground(new Color(189, 128, 136));
		contentPane.add(dugmeSacuvaj);


		dugmeSacuvaj.addMouseListener(this);
		setVisible(true);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource()==btnX) {
			dispose();
			GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();  // removes all components and returns those necessary for updating the main window
			GlavniProzor.glavniProzorInstanca.scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI()); //scrollbar customized with colors and shape as desired
			dodajBiljkuProzorInstanca=null;

		}
		if(e.getSource()==dugmeSacuvaj) {
			
			
			if (svaPoljaPopunjena() ==false){
			new ProzorObavjestenje("Niste popunili sva polja!");
		


		}
			else {
				
				//database connection - I could have done a common method for connection, but I didn't have time
			try {
				
				
		          
		           conn = DriverManager.getConnection(cs.connectionString);
		           
		           Date narednoZalivanjeSQLDatum = narednoZalivanjeDatum(datumZaSQL(pocetniDatumZalivanjaDateChooser), brojDanaNaKojiSeZaliva());

		          String sql = "INSERT INTO `table_biljke` (`ime_biljke`, `slika`, `uputstvo`, `datum_poslednjeg_zalivanja`, `datum_sledeceg_zalivanja`, `br_dana_na_koji_se_zaliva`)"
		          		+ " VALUES ('"+ dodajNazivbiljke() +"', '"+fotografija+"','"+dodoajUputstvo()+"','"+datumZaSQL(pocetniDatumZalivanjaDateChooser) +"' , '"+ narednoZalivanjeSQLDatum +"','"+ brojDanaNaKojiSeZaliva()+"');";
		        
		          statement = conn.prepareStatement(sql);
		           statement.executeUpdate();
			} catch (SQLIntegrityConstraintViolationException   e1 ) {		    
		          if(e1.getMessage().contains("Duplicate entry")) {
		        	   duplaBiljka = true;
		   			
		        	  new ProzorObavjestenje("Biljka već postoji, unesite drugačije ime biljke!");

		        	  
		          }
		          }
		        
		         catch (SQLException   e2 ) {  
		        	 e2.printStackTrace();
		        } finally {
		          try {
		            if (statement != null) {
		              statement.close(); 
		            }
		            if (conn != null)
		              conn.close(); 
		          } catch (SQLException ex) {	 
		        	  ex.printStackTrace();
		          } 
			
			
		        }
			if(duplaBiljka == false) {   // A duplicate plant means that the plant name is repeated and already exists in the database, so it prevents it from being entered
			new ProzorObavjestenje("Uspješno ste dodali biljku!");
			dispose();
			DodajBiljkuProzor.dodajBiljkuProzorInstanca=null;
			GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();
			}
			duplaBiljka = false;
		}
		}
		if(e.getSource()==dugmeDodajUputstvo) {
			GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.addDodajUputstvo();
			setVisible(false);
			
		}
		if(e.getSource()==dugmeDodajFotografiju) {
			setVisible(false);
			 fotografija = dodajFotografijuBiljke();
			  }
			
}

	// Changing colors depending on the action for all buttons on the window
	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getSource()==dugmeSacuvaj) {dugmeSacuvaj.setBackground(new java.awt.Color(238, 203, 184));}
		if(e.getSource()==dugmeDodajFotografiju) {dugmeDodajFotografiju.setBackground(new java.awt.Color(238, 203, 184));}	
		if(e.getSource()==dugmeDodajUputstvo) {dugmeDodajUputstvo.setBackground(new java.awt.Color(238, 203, 184));}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getSource()==dugmeSacuvaj) {	dugmeSacuvaj.setBackground(new Color(189, 128, 136));}
		if(e.getSource()==dugmeDodajFotografiju) {dugmeDodajFotografiju.setBackground(new Color(189, 128, 136));}
		if(e.getSource()==dugmeDodajUputstvo) {dugmeDodajUputstvo.setBackground(new Color(189, 128, 136));}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(e.getSource()==dugmeSacuvaj) {	dugmeSacuvaj.setBackground(new java.awt.Color(238, 203, 184));}
		if(e.getSource()==dugmeDodajFotografiju) {dugmeDodajFotografiju.setBackground(new java.awt.Color(238, 203, 184));}
		if(e.getSource()==dugmeDodajUputstvo) {dugmeDodajUputstvo.setBackground(new java.awt.Color(238, 203, 184));}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(e.getSource()==dugmeSacuvaj) {dugmeSacuvaj.setBackground(new Color(189, 128, 136));}
		if(e.getSource()==dugmeDodajFotografiju) {dugmeDodajFotografiju.setBackground(new Color(189, 128, 136));}
		if(e.getSource()==dugmeDodajUputstvo) {dugmeDodajUputstvo.setBackground(new Color(189, 128, 136));}
	}
	
	//Prevents the input of text of any type, only allows digits
	@Override
	public void keyTyped(KeyEvent e) {
		char c = e.getKeyChar();
		if (!Character.isDigit(c)) {
			e.consume();
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {}
	@Override
	public void keyReleased(KeyEvent e) {}
	
	//--------------------------------------------------------------FUNCTIONS----------------------------------------------
	
	String dodajNazivbiljke(){
		return dodajNzivBiljkeTxt.getText();
		
	}
	
	
	// JFileChooser window for adding a photo; I didn't have time to create it from scratch, so it deviates from the design of the application
	String dodajFotografijuBiljke() {
	      JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Izaberite sliku biljke");
		fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
		// Filters images by type: jpg, png, jpeg when extension images are selected
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.SLIKE", new String[] { "JPG", "PNG", "JPEG" }); 
		fileChooser.addChoosableFileFilter(filter);
		int result = fileChooser.showSaveDialog(null);
		if (result == 0) {
		  File selectedFile = fileChooser.getSelectedFile();
		  putanjaDoSlike = selectedFile.getAbsolutePath().toString();
		  
		  try {
			  //Filters the image extension png, jpg, jpeg
		      boolean dalJePNG = putanjaDoSlike.toLowerCase().endsWith(".png");
		      boolean dalJeJPG = putanjaDoSlike.toLowerCase().endsWith(".jpg");
		      boolean dalJeJPEG = putanjaDoSlike.toLowerCase().endsWith(".jpeg");

		      
		      if (dalJePNG || dalJeJPG || dalJeJPEG ) { 

		          try {
		          byte[] bytes = Files.readAllBytes(Paths.get(putanjaDoSlike, new String[0]));
			        base64StringSlika = Base64.getEncoder().encodeToString(bytes);
		          }catch(NullPointerException e) {
		        	  e.printStackTrace();
		        	  
		          }
		          
		      } else {
		          new ProzorObavjestenje("Fotografija mora biti JPG,PNG ili JPEG");
					setVisible(false);

		      }
		  } catch (IOException e) {
		      e.printStackTrace();
		  }

		} else if (result == 1) {
			  System.out.println("Nije uploadovana slika - otkazana");
		  
		  
		} 
		setVisible(true);
		return base64StringSlika;
	}
	
	public String dodoajUputstvo() {
		try {
		return ProzorImijeniDodajProcitajUputstvo.prozorImijeniDodajProcitajUputstvoInstanca.getUputstvo();
		}catch(NullPointerException e) {
			if(e.getMessage().contains("Cannot invoke")) {
				return "";
			}
		}
		return "";
	}
	
	
	public  java.sql.Date datumZaSQL(JDateChooser dateChooser) { //selecting a date
		java.sql.Date sqlDate;
        java.util.Date selectedDate = dateChooser.getDate();

        // converts dataChooser date into sql date
        if(selectedDate!=null) {
         sqlDate = new java.sql.Date(selectedDate.getTime());
        } else {
        	sqlDate=null;
        }
        return sqlDate;
	}
	

	
	int brojDanaNaKojiSeZaliva() { // checks whether the number of days for watering is a filled field
		int brDana = -1;
		if(unesiBrojDanaNaKojiSeZalivaBiljka.getText()!="" && !unesiBrojDanaNaKojiSeZalivaBiljka.getText().isEmpty()&& !unesiBrojDanaNaKojiSeZalivaBiljka.getText().isBlank()) {
		 brDana = Integer.parseInt(unesiBrojDanaNaKojiSeZalivaBiljka.getText());  
		}
		return brDana;
	}
	

	private boolean svaPoljaPopunjena() { // The method checks whether all fields are filled; this way, I made a requirement that nothing remains empty
		boolean popunjeno = false;
		
		
	
		if(dodajNazivbiljke()!=null && dodajNazivbiljke()!="" && !dodajNazivbiljke().isBlank() && !dodajNazivbiljke().isEmpty()
				&& fotografija != ""
				&& dodoajUputstvo()!=null && dodoajUputstvo()!="" && !dodoajUputstvo().equals(null) && !dodoajUputstvo().equals("")
				&&  pocetniDatumZalivanjaDateField.getText()!="" && datumZaSQL(pocetniDatumZalivanjaDateChooser)!=null   
				&& brojDanaNaKojiSeZaliva()!=-1){
			
					
			popunjeno= true;
			
			
		} 
		
		return popunjeno;
	}
	
	//----------------------NEXT WATERING DATE ADDING NUMBER OF DAYS FOR WATERING start------------------------------------------
	
     public Date narednoZalivanjeDatum(Date pocetnoZalivanje, int brDanaZaZalivanje) {   
    	 Date date = Date.valueOf(pocetnoZalivanje.toString());

         // converts sqlDate into utilDate
         java.util.Date utilDate = new java.util.Date(date.getTime());

         // adds the number of the days for watering
         utilDate.setTime(utilDate.getTime() + (brDanaZaZalivanje * 24 * 60 * 60 * 1000));

         // converts utilDate into sqlDate
         Date buduciDatum = new Date(utilDate.getTime());

         
         return buduciDatum;
     }


	//----------------------NEXT WATERING DATE ADDING NUMBER OF DAYS FOR WATERING end------------------------------------------

	


}
