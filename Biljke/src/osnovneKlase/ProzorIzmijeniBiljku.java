package osnovneKlase;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Locale;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import com.toedter.calendar.JDateChooser;


public class ProzorIzmijeniBiljku extends JFrame implements ActionListener, MouseListener {

	/**
	 * Created by: Andrea
	 * 
	 * This window is used to change the information for existing plant, all fields can be changed independently,
	 * there is no requirement for all to be filled 
	 * if the plant isn't chosen from the dropdown list it will open window as a warning  
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JComboBox<String> comboBox;
	protected int index;
	private JTextField textFieldNazivBiljke;
	private JLabel labelNazivBiljke;
	private JLabel lblIzmijeniUputstvoBiljke;
	private JLabel lblZamijeniPostojeuFotografiju;
	private JLabel dugmeDodajFotografiju;
	private JLabel dugmeDodajUputstvo;
	private JLabel lblIzmijeniPoetniDatum;
	private JLabel lblIzmijeniBrojDana;
	private JTextField textFieldBrDanaZalivanja;
	private JLabel dugmeSacuvaj;
	private JDateChooser dateChooserPocetnoZalivanje;
	private JLabel lblIzmijeniDatumNarednog;
	private JDateChooser dateChooserNarednoZalivanje;
	private JTextField narednoZalivanjeTextField;
	private JTextField pocetnoZalivanjeTextField;
	private Connection conn;
	private Statement stmt;
	private ArrayList<String> izborBiljke = new ArrayList<>();
	private ArrayList<Integer> redniBroj = new ArrayList<>();
	private String izmijenjeniNazivBiljke;
	private int brDanaZaZalivanje;
	private ResultSet rs;
	protected static ProzorIzmijeniBiljku prozorIzmijeniBiljkuInstanca;
	private String postojeceUputstvo;
	private String base64StringSlika2;
	private String putanjaDoSlike2;
	private String fotografija2;
	static Point initialClick;
	private JLabel btnX;
	public static boolean duplaBiljka = false;
	private static ArrayList<String> listaBiljaka = new ArrayList<>();
	private ConnectionString cs = new ConnectionString();


	/*
	 * prozor za izmjenu biljke ovdje je moguce izmijeniti samo jedan od podataka, nema uslova da svi moraju da se promijene
	 * 
	 */
	
	public ProzorIzmijeniBiljku() {
		
		prozorIzmijeniBiljkuInstanca=this;
		
		setBounds(0, 0, 425, 469);
		setUndecorated(true);
		setBackground(new Color(37, 193, 183,80));  
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		setLocationRelativeTo(GlavniProzor.glavniProzorInstanca);
		setResizable(false);
		setContentPane(contentPane);
		contentPane.setBackground(new Color(37, 193, 183,80));
		
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
		      
		      btnX = new JLabel("");
				btnX.setBackground(new Color(0,0,0,0));
				btnX.setOpaque(true);
				btnX.setHorizontalAlignment(JLabel.CENTER);
				btnX.setVerticalAlignment(JLabel.CENTER);
				btnX.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0,0)));
				btnX.setFocusable(false);
				btnX.setIcon(new ImageIcon(GlavniProzor.class.getResource("/img/dugmeX15pikselaCrno.png")));
				btnX.setBounds(388, 20, 23, 23);
				btnX.addMouseListener(this);
				contentPane.add(btnX);

		

		
		
		
		comboBox = new JComboBox<>(); // dropdown list with plant names
		comboBox.addActionListener(this); 
		comboBox.setFocusable(false);
		comboBox.setBounds(24, 23, 236, 22);
		comboBox.addItem("IZABERITE BILJKU ZA IZMJENU");
		comboBox.setBackground(new Color(240,240,240));
		contentPane.add(comboBox);
		
		
		
		labelNazivBiljke = new JLabel("Izmijenite naziv biljke:");
		labelNazivBiljke.setBounds(24, 73, 206, 14);

		contentPane.add(labelNazivBiljke);
		
		textFieldNazivBiljke = new JTextField();
		textFieldNazivBiljke.setBackground(new Color(240,240,240));
		textFieldNazivBiljke.setBounds(184, 70, 200, 20);
		contentPane.add(textFieldNazivBiljke);
		
		lblIzmijeniUputstvoBiljke = new JLabel("Izmijenite postojeće uputstvo biljke:");
		lblIzmijeniUputstvoBiljke.setBounds(24, 158, 257, 20);
		contentPane.add(lblIzmijeniUputstvoBiljke);
		
		lblZamijeniPostojeuFotografiju = new JLabel("Zamijenite postojeću fotografiju biljke:");
		lblZamijeniPostojeuFotografiju.setBounds(24, 113, 257, 14);
		contentPane.add(lblZamijeniPostojeuFotografiju);
		
		dugmeDodajFotografiju = new JLabel("ZAMIJENITE");
		dugmeDodajFotografiju.setOpaque(true);
		dugmeDodajFotografiju.setHorizontalAlignment(JLabel.CENTER);
		dugmeDodajFotografiju.setVerticalAlignment(JLabel.CENTER);
		dugmeDodajFotografiju.setBackground(new Color(189, 128, 136));
		dugmeDodajFotografiju.setBounds(288, 109, 96, 23);
		dugmeDodajFotografiju.addMouseListener(this);
		contentPane.add(dugmeDodajFotografiju);
		
		dugmeDodajUputstvo = new JLabel("IZMIJENITE");
		dugmeDodajUputstvo.setBackground(new Color(189, 128, 136));
		dugmeDodajUputstvo.setOpaque(true);
		dugmeDodajUputstvo.setBounds(288, 157, 96, 23);
		dugmeDodajUputstvo.addMouseListener(this);
		dugmeDodajUputstvo.setHorizontalAlignment(JLabel.CENTER);
		dugmeDodajUputstvo.setVerticalAlignment(JLabel.CENTER);
		contentPane.add(dugmeDodajUputstvo);
		
		lblIzmijeniPoetniDatum = new JLabel("Izmijenite početni datum zalivanja:");   
		lblIzmijeniPoetniDatum.setBounds(24, 209, 257, 14);
		contentPane.add(lblIzmijeniPoetniDatum);
		
		lblIzmijeniBrojDana = new JLabel("Izmijenite broj dana na koji se zaliva biljka:");
		lblIzmijeniBrojDana.setBounds(24, 301, 272, 14);
		contentPane.add(lblIzmijeniBrojDana);
		
		textFieldBrDanaZalivanja = new JTextField();
		textFieldBrDanaZalivanja.setBounds(288, 298, 96, 20);
		textFieldBrDanaZalivanja.setBackground(new Color(240,240,240));
		textFieldBrDanaZalivanja.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent e) {
				char c = e.getKeyChar();
				if (!Character.isDigit(c)) {   // opet disallowing the entry of non-digit characters
					e.consume();
				}
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
		});
		contentPane.add(textFieldBrDanaZalivanja);
		
		dugmeSacuvaj = new JLabel("SAČUVAJTE");
		dugmeSacuvaj.setOpaque(true);
		dugmeSacuvaj.setHorizontalAlignment(JLabel.CENTER);
		dugmeSacuvaj.setVerticalAlignment(JLabel.CENTER);
		dugmeSacuvaj.setBounds(288, 381, 96, 23);
		dugmeSacuvaj.setBackground(new Color(189, 128, 136));
		dugmeSacuvaj.addMouseListener(this);
		contentPane.add(dugmeSacuvaj);
		
		dateChooserPocetnoZalivanje = new JDateChooser();
		dateChooserPocetnoZalivanje.setBounds(288, 203, 96, 20);
	//changes dates to Serbian language using Latin script and adjusts the date format accordingly
		dateChooserPocetnoZalivanje.setDateFormatString("dd.MM.yyyy");
        Locale serbianLocale = new Locale.Builder().setLanguage("sr").setScript("Latn").build();
        dateChooserPocetnoZalivanje.setLocale(serbianLocale);
        
		pocetnoZalivanjeTextField = ((JTextField) dateChooserPocetnoZalivanje.getComponent(1));
		pocetnoZalivanjeTextField.setBackground(new Color(240,240,240));
		pocetnoZalivanjeTextField.addKeyListener(new KeyListener() {
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
		contentPane.add(dateChooserPocetnoZalivanje);
		
		lblIzmijeniDatumNarednog = new JLabel("Izmijenite datum narednog zalivanja:");
		lblIzmijeniDatumNarednog.setBounds(24, 259, 257, 14);
		contentPane.add(lblIzmijeniDatumNarednog);
		
		dateChooserNarednoZalivanje = new JDateChooser(); // selects a date
		dateChooserNarednoZalivanje.setBounds(288, 253, 96, 20);
		//changes dates to Serbian language and format
		dateChooserNarednoZalivanje.setDateFormatString("dd.MM.yyyy"); // formating the date 
        Locale serbianLocale2 = new Locale.Builder().setLanguage("sr").setScript("Latn").build(); // formatting to Latin script Serbian language
        dateChooserNarednoZalivanje.setLocale(serbianLocale); // formatting to Latin script Serbian language
        narednoZalivanjeTextField = ((JTextField) dateChooserNarednoZalivanje.getComponent(1));
        narednoZalivanjeTextField.setBackground(new Color(240,240,240)); //setting the text field to white color
        narednoZalivanjeTextField.addKeyListener(new KeyListener() {
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
			
		contentPane.add(dateChooserNarednoZalivanje);
		
		ucitajListuBiljaka();
		
		setVisible(true);

		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comboBox) {
			izborBiljke.clear();
			ucitajListuBiljaka();
		      index = comboBox.getSelectedIndex();
		      comboBox.removeAllItems();
		      comboBox.addItem("IZABERITE BILJKU ZA IZMJENU");
		      for (String biljkaNaziv : izborBiljke) {
		        comboBox.addItem(biljkaNaziv); 
		      if (index == 0) {
		      } else {
		    	 comboBox.setSelectedItem(comboBox.getItemAt(index));
		      } 
		      }
		      izborBiljke.clear();
		    } 
		
	}
	
	public void ucitajListuBiljaka() {
		  try {

				conn = DriverManager.getConnection(cs.connectionString);
				stmt = conn.createStatement();
				rs = stmt.executeQuery("SELECT * FROM table_biljke;");
				while (rs.next()) {
					String ime_biljke = rs.getString("ime_biljke");
					
					izborBiljke.add(ime_biljke);
					
				}
	        	} catch (SQLException e) {
	                e.printStackTrace();
	            } finally {
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
	                
	                System.out.println("Konekcija zatvorena ProzorIzmijeniBiljku.");
	            }

	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
		if(e.getSource()==btnX) {
			dispose();
			GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();
	 		GlavniProzor.glavniProzorInstanca.scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());
	 		prozorIzmijeniBiljkuInstanca=null;
		}
		//-----------------------------------------------------SAVE ADD INSTRUCTION --- mouseClicked --- beginning--------------------------------------------------------------

		if(e.getSource()==dugmeDodajUputstvo) {
			dugmeDodajUputstvo.setBackground(new java.awt.Color(238, 203, 184));

			if(index==0) {
				new ProzorObavjestenje("Izaberite biljku za izmjenu.");
				setVisible(false);

			}else {
				
				GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.addIzmijeniUputstvo();
				setVisible(false);						
			}
		}
		//-----------------------------------------------------ADD PHOTO BUTTON---mouseClicked----start--------------------------------------------------------------

		if(e.getSource()==dugmeDodajFotografiju) { 
			dugmeDodajFotografiju.setBackground(new java.awt.Color(238, 203, 184));

			if(index==0) {
				new ProzorObavjestenje("Izaberite biljku za izmjenu.");
				setVisible(false);

			}else {
				fotografija2 = dodajFotografijuBiljke();
			}
		}

		//-----------------------------------------------------SAVE BUTTON---mouseClicked----start--------------------------------------------------------------
	
		if(e.getSource()==dugmeSacuvaj) {
			dugmeSacuvaj.setBackground(new java.awt.Color(238, 203, 184));

			if(index==0) {

				new ProzorObavjestenje("Izaberite biljku za izmjenu.");
				setVisible(false);
				
			}else {
				              
				konektovanjeSaBazomSQL(); 
				if(duplaBiljka ==  true) {
					new ProzorObavjestenje("Ime biljke već postoji, izaberite drugačije ime."); 
					setVisible(false);
					listaBiljaka.clear();
					
					duplaBiljka=false;
				}
				else if(duplaBiljka==false) {

					new ProzorObavjestenje("Podaci su usješno izmijenjeni.");
					dispose();
					ProzorIzmijeniBiljku.prozorIzmijeniBiljkuInstanca = null;
					GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();
				}			
		}
		}
	}
		
	//-----------------------------------------------------SAVE BUTTON---mouseClicked----end--------------------------------------------------------------


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

	//---------------------------------------------------methods for modification -----------------start------------------------------------------------

	public void izmijeniNazivBiljke() {
		if(!textFieldNazivBiljke.getText().isBlank() && textFieldNazivBiljke.getText()!=null) {
			 izmijenjeniNazivBiljke = textFieldNazivBiljke.getText();
			try {
				stmt.executeUpdate("UPDATE `table_biljke` SET `ime_biljke` = '" + izmijenjeniNazivBiljke + "' WHERE `RB` = '" +redniBroj.get(index-1)  + "';");
			if(listaBiljaka.contains(izmijenjeniNazivBiljke)) {
				duplaBiljka = true;
			}
			}catch (SQLIntegrityConstraintViolationException   e1 ) {	
				if(e1.getMessage().contains("Duplicate")){

				duplaBiljka = true;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				} 
		         

		}
		
	}
		
	
	public void izmijeniUputsvoUbaziSQL() {
		System.out.println(" UPUTSTVO PROLAZI LI KOD OVDJE");
		
		 postojeceUputstvo = ProzorImijeniDodajProcitajUputstvo.prozorImijeniDodajProcitajUputstvoInstanca.getTextArea().getText();
		 System.out.println(postojeceUputstvo + " UPUTSTVO PROVJERA HVATA LI NESTO");
			if(!postojeceUputstvo.isBlank()) {
				try {
					stmt.executeUpdate("UPDATE `table_biljke` SET `uputstvo` = '" + postojeceUputstvo + "' WHERE `RB` = '" +redniBroj.get(index-1)  + "';");

				} catch (SQLException e) {
					e.printStackTrace();
	}		         
	}
	}
	
	
	public String dodajFotografijuBiljke() { // window - add plant photo
		setVisible(false);
	      JFileChooser fileChooser2 = new JFileChooser();
	      fileChooser2.setDialogTitle("Izaberite sliku biljke");
	      fileChooser2.setCurrentDirectory(new File(System.getProperty("user.home")));
		FileNameExtensionFilter filter = new FileNameExtensionFilter("*.SLIKE", new String[] { "JPG", "PNG", "JPEG" });
		fileChooser2.addChoosableFileFilter(filter);
		int result = fileChooser2.showSaveDialog(null);
		if (result == 0) {
		  File selectedFile = fileChooser2.getSelectedFile();
		  putanjaDoSlike2 = selectedFile.getAbsolutePath().toString();
		  
		  try {
			  boolean dalJePNG = putanjaDoSlike2.toLowerCase().endsWith(".png"); // checks if type of file is png, jpg, jpeg
		      boolean dalJeJPG = putanjaDoSlike2.toLowerCase().endsWith(".jpg");
		      boolean dalJeJPEG = putanjaDoSlike2.toLowerCase().endsWith(".jpeg");

		      
		      if (dalJePNG || dalJeJPG || dalJeJPEG ) { 

		          try {
		          byte[] bytes = Files.readAllBytes(Paths.get(putanjaDoSlike2, new String[0]));
			        base64StringSlika2 = Base64.getEncoder().encodeToString(bytes);
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
		return base64StringSlika2; // returns all as base 64  for sql as a text
	}
	
	
	public void izmijeniFotografijuUSQL() {
		if(fotografija2!= null) {
			 try {
				stmt.executeUpdate("UPDATE `table_biljke` SET `slika` = '" + fotografija2 + "' WHERE `RB` = '" +redniBroj.get(index-1)  + "';");
			} catch (SQLException e) {
				e.printStackTrace();
					
				}
			}

		}
		
	
	
	
	public void izmijeniBrDanaZaZalivanje() {
		if(!textFieldBrDanaZalivanja.getText().isBlank()) {
			 brDanaZaZalivanje = Integer.parseInt(textFieldBrDanaZalivanja.getText());
			 try {
				stmt.executeUpdate("UPDATE `table_biljke` SET `br_dana_na_koji_se_zaliva` = '" + brDanaZaZalivanje + "' WHERE `RB` = '" +redniBroj.get(index-1)  + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}		
	}
	
	
	public void izmijeniPocetnoZalivanjeDatum() {
		if(pocetnoZalivanjeTextField.getText()!="" && datumZaSQL(dateChooserPocetnoZalivanje)!=null) {
			
			try {
				stmt.executeUpdate("UPDATE `table_biljke` SET `datum_poslednjeg_zalivanja` = '" + datumZaSQL(dateChooserPocetnoZalivanje) + "' WHERE `RB` = '" +redniBroj.get(index-1)  + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} 
	}
	
	
	public void izmijeniNarednoZalivanjeDatum() {
if(narednoZalivanjeTextField.getText()!="" && datumZaSQL(dateChooserNarednoZalivanje)!=null) {
			
			try {
				stmt.executeUpdate("UPDATE `table_biljke` SET `datum_sledeceg_zalivanja` = '" + datumZaSQL(dateChooserNarednoZalivanje) + "' WHERE `RB` = '" +redniBroj.get(index-1)  + "';");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public  java.sql.Date datumZaSQL(JDateChooser dateChooser) {
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

	
	
	public void konektovanjeSaBazomSQL() {
		  try {
			  izborBiljke.clear();
              duplaBiljka = false;



				conn = DriverManager.getConnection(cs.connectionString);
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM table_biljke;");
				while (rs.next()) {
					int RB = rs.getInt("RB");
					redniBroj.add(RB);
					String naziv_biljke = rs.getString("ime_biljke");
					listaBiljaka.add(naziv_biljke);

				}
						if(textFieldNazivBiljke.getText()!="") {izmijeniNazivBiljke();} 
						if(textFieldBrDanaZalivanja.getText()!="") {izmijeniBrDanaZaZalivanje();}
						if(pocetnoZalivanjeTextField.getText()!=null) {izmijeniPocetnoZalivanjeDatum();}
						if(narednoZalivanjeTextField.getText()!=null) {izmijeniNarednoZalivanjeDatum();}
						if(ProzorImijeniDodajProcitajUputstvo.prozorImijeniDodajProcitajUputstvoInstanca!=null) {izmijeniUputsvoUbaziSQL();}
						if(base64StringSlika2!="") {izmijeniFotografijuUSQL();}
						

	        	} catch (SQLException e) {
	                e.printStackTrace();
	            } finally {
	                if (stmt != null) {
	                    try {
	                        stmt.close();
	                    } catch (SQLException e) {
	                        e.printStackTrace();
	                    }
	                }
	                
	                //closes connection
	                if (conn != null) {
	                    try {
	                        conn.close();
	                    } catch (SQLException e) {
	                        e.printStackTrace();
	                    }
	                }
	                
	                System.out.println("Konekcija zatvorena ProzorIzmijeni Biljku.");
	            }
	}
	//---------------------------------------------------methods for modification-----------------end------------------------------------------------
	
	public  int getIndex() {
		return index;
	}
	
	


}
