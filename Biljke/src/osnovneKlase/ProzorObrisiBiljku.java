package osnovneKlase;
import java.awt.Color;
import java.awt.Point;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.awt.event.ActionEvent;

public class ProzorObrisiBiljku extends JFrame implements ActionListener {

	/**
	 * Created by: Andrea
	 * 
	 * window used to delete existing plant
	 * if plant isn't chosen from dropdown list it shows warning window
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	protected JComboBox<String> comboBox;
	protected int index;
	private JLabel obrisi;
	private static Point initialClick;
	protected static ArrayList<String> izborBiljke = new ArrayList<>();
	protected static ArrayList<Integer> redniBroj = new ArrayList<>();
	private JLabel btnX;
	private int brojZaBrisanjeBiljke;
	private static Connection conn ;
	private static Statement stmt ;



	protected static ProzorObrisiBiljku obrisiBiljkuInstanca;

	public ProzorObrisiBiljku() {
		
		obrisiBiljkuInstanca = this;
		setBounds(100, 100, 425, 255);
		setUndecorated(true);
		setBounds(100, 100, 425, 255);
		setBackground(new Color(37, 193, 183,80));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(null);
		contentPane.setBackground(new Color(37, 193, 183,80));
		setLocationRelativeTo(GlavniProzor.glavniProzorInstanca);
		setResizable(false);
		setContentPane(contentPane);
		
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
				btnX.setIcon(new ImageIcon(DodajBiljkuProzor.class.getResource("/img/dugmeX15pikselaCrno.png")));
				btnX.setBounds(388, 20, 23, 23);
				btnX.addMouseListener(new MouseListener() {

					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getSource()==btnX) {
							dispose();
					GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();

						}
					}

					@Override
					public void mousePressed(MouseEvent e) {		}
					@Override
					public void mouseReleased(MouseEvent e) {}
					@Override
					public void mouseEntered(MouseEvent e) {}
					@Override
					public void mouseExited(MouseEvent e) {	}
				});
				contentPane.add(btnX);
		
		JLabel lblNewLabel = new JLabel("Iz padajućeg menija izaberite biljku koju želite da obrišete:");
		lblNewLabel.setBounds(35, 83, 390, 14);
		contentPane.add(lblNewLabel);
		
	
		comboBox = new JComboBox<>();
		comboBox.addActionListener(this);
		comboBox.setFocusable(false);
		comboBox.setBounds(35, 136, 350, 22);
		comboBox.setBackground(new Color(240,240,240));
		comboBox.addItem("IZABERITE BILJKU ZA BRISANJE");
		contentPane.add(comboBox);
		
		obrisi = new JLabel("Obrišite");
		obrisi.setBackground(new Color(189, 128, 136));
		obrisi.setOpaque(true);
		obrisi.setHorizontalAlignment(JLabel.CENTER);
		obrisi.setVerticalAlignment(JLabel.CENTER);
		obrisi.setBounds(296, 195, 89, 23);
		obrisi.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				obrisi.setBackground(new java.awt.Color(238, 203, 184));

						if(index==0) {
							new ProzorObavjestenje("Izaberite biljku za brisanje.");
							setVisible(false);

						}else {
					ucitajListuBiljaka();
					 brojZaBrisanjeBiljke = redniBroj.get(index-1);
					dispose();
					new DaLiSteSigurniDaZeliteDaObriseteBiljku();
						}
			
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
		contentPane.add(obrisi);
		setVisible(true);
		
		ucitajListuBiljaka();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == comboBox) {
			izborBiljke.clear();
			ucitajListuBiljaka();
			  
		      index = comboBox.getSelectedIndex();
		      comboBox.removeAllItems();
		      comboBox.addItem("IZABERITE BILJKU ZA BRISANJE");
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

	
	public static void ucitajListuBiljaka() { //The method, upon each ComboBox start, reloads plants from the database to avoid errors, 
		//whether it's deleted or not, and to appear in the list
		  try {

				 ConnectionString cs = new ConnectionString();
				conn = DriverManager.getConnection(cs.connectionString);
				stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery("SELECT * FROM table_biljke;");
				while (rs.next()) {
					String ime_biljke = rs.getString("ime_biljke");
					int RB = rs.getInt("RB");
					izborBiljke.add(ime_biljke);
					redniBroj.add(RB);
					
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
	                
	                System.out.println("Konekcija zatvorena ProzorObrisiBiljku.");
	            }

	}





}
