package osnovneKlase;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class GlavniProzorBiljkeFrame extends JFrame {

	/**
	 * Created by: Andrea
	 * 
	 * This is the main frame - window to which a panel is added, actually named GlavniProzor (and in fact GlavniProzor is a panel)
	 */
	private static final long serialVersionUID = 1L;
	protected JPanel contentPane;
	private static Point initialClick;
	protected static GlavniProzorBiljkeFrame glavniProzorBiljkeFrameInstanca;
	GlavniProzor glavniProzor;

	public GlavniProzorBiljkeFrame() {
		glavniProzorBiljkeFrameInstanca = this;
		setUndecorated(true);
		this.setBounds(0, 0, 792, 590);
		contentPane = new JPanel();
		setResizable(false);
		setLayout(null);
		setLocationRelativeTo(null); // The location is relative to the center of the computer screen;
				// others are set relative to the main window, although it causes a problem when the main window is minimized
		add(contentPane);

		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				initialClick = e.getPoint();
				getComponentAt(initialClick);
			}
		});

		addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				setOpacity((float) (1.0));

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

		glavniProzor = new GlavniProzor();
		glavniProzor.setLocation(0, 0);
		add(glavniProzor);

		setVisible(true);
	}

	public void deleteUpdate() { // A method that removes everything from this frame and sets things up from the beginning
		//I had to reset everything this way because repaint() and revalidate() didn't do the job even for windows, and values had to be reset
		
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		SwingUtilities.updateComponentTreeUI(this);

		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.remove(glavniProzor);
		GlavniProzor.brojRedaTabele.clear();
		GlavniProzor.izborBiljke.clear();
		GlavniProzor.glavniProzorInstanca.razmakIzmedjuBiljke = 0;
		DugmeUputstvo.dugmeUputstvoLista.clear();
		DugmeSlika.dugmeSlikaLista.clear();
		DugmeZaliveno.dugmeZalivenoLista.clear();
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.repaint();
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.revalidate();

		glavniProzor = new GlavniProzor();
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getContentPane().add(glavniProzor);
		SwingUtilities.updateComponentTreeUI(glavniProzor);
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.repaint();
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.revalidate();
		GlavniProzor.glavniProzorInstanca.scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());

		SwingUtilities.updateComponentTreeUI(this);

		GlavniProzor.glavniProzorInstanca.scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());

	}

	public void addDodajUputstvo() { // adds an instruction panel to this window when the method is called
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		SwingUtilities.updateComponentTreeUI(this);

		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.remove(glavniProzor);

		ProzorImijeniDodajProcitajUputstvo prozorImijeniDodajProcitajUputstvo = new ProzorImijeniDodajProcitajUputstvo(
				"DODAJTE UPUTSTVO:", true, false, true);
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getContentPane()
				.add(prozorImijeniDodajProcitajUputstvo);
		SwingUtilities.updateComponentTreeUI(prozorImijeniDodajProcitajUputstvo);
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.repaint();
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.revalidate();
		SwingUtilities.updateComponentTreeUI(this);
		prozorImijeniDodajProcitajUputstvo.scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());

	}
	public void addIzmijeniUputstvo() { // adds a panel for changing instructions
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		SwingUtilities.updateComponentTreeUI(this);

		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.remove(glavniProzor);

		ProzorImijeniDodajProcitajUputstvo prozorImijeniDodajProcitajUputstvo = new ProzorImijeniDodajProcitajUputstvo("IZMIJENITE UPUTSTVO ZA BILJKU: "+
				(GlavniProzor.izborBiljke.get(ProzorIzmijeniBiljku.prozorIzmijeniBiljkuInstanca.index-1)).toString(),true,true,true);

		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getContentPane()
				.add(prozorImijeniDodajProcitajUputstvo);
		SwingUtilities.updateComponentTreeUI(prozorImijeniDodajProcitajUputstvo);
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.repaint();
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.revalidate();
		SwingUtilities.updateComponentTreeUI(this);
		prozorImijeniDodajProcitajUputstvo.scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());

	}
	
	public void addUputstvoDugme() { // adds reading instructions on the button press of the instruction from the main screen
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		SwingUtilities.updateComponentTreeUI(this);

		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.remove(glavniProzor);

		ProzorImijeniDodajProcitajUputstvo prozorImijeniDodajProcitajUputstvo
		=new ProzorImijeniDodajProcitajUputstvo("UPUTSTVO: " +(GlavniProzor.izborBiljke.get(DugmeUputstvo.getBrKliknutogDugmetaUputstvo())).toString(), false, true, false);
		

		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getContentPane()
				.add(prozorImijeniDodajProcitajUputstvo);
		SwingUtilities.updateComponentTreeUI(prozorImijeniDodajProcitajUputstvo);
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.repaint();
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.revalidate();
		SwingUtilities.updateComponentTreeUI(this);
		prozorImijeniDodajProcitajUputstvo.scrollPane.getVerticalScrollBar().setUI(new RoundedScrollBarUI());

	}

	
	public void addSlikaDugme() { // calls the panel that displays the plant image on the button press 'slika' from the main screen or panel
		contentPane.removeAll();
		contentPane.repaint();
		contentPane.revalidate();
		SwingUtilities.updateComponentTreeUI(this);

		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.remove(glavniProzor);

		ProzorSlika prozorSlika = new ProzorSlika();

		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getContentPane()
				.add(prozorSlika);
		SwingUtilities.updateComponentTreeUI(prozorSlika);
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.repaint();
		GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.revalidate();
		SwingUtilities.updateComponentTreeUI(this);

	}
	
	
}
