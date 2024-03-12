package osnovneKlase;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JLabel;

/**
 *  Created by: Andrea
 *  
 *  This class displays picture of the plant when button 'Slika' is pressed 
 */

public class DugmeSlika extends JButton implements MouseListener{

	private static final long serialVersionUID = 1L;
	static ArrayList<JLabel> dugmeSlikaLista = new ArrayList<>();
	static DugmeSlika dugmeSlikaInstanca;
	private static int brKliknutogDugmeta;
	private JLabel dugmeSlika;

	public DugmeSlika(){
		dugmeSlikaInstanca = this;
		dugmeSlika = new JLabel("Slika");
		dugmeSlika.setOpaque(true);
		dugmeSlika.setHorizontalAlignment(JLabel.CENTER);
		dugmeSlika.setVerticalAlignment(JLabel.CENTER);
		dugmeSlika.setForeground(new java.awt.Color(0, 0, 0));
		dugmeSlika.setBackground(new Color(189, 128, 136));
		dugmeSlika.setBounds(20, 56+GlavniProzor.glavniProzorInstanca.razmakIzmedjuBiljke, 95, 23);
        GlavniProzor.glavniProzorInstanca.panel.add(dugmeSlika);
        dugmeSlikaLista.add(dugmeSlika);
        dugmeSlika.addMouseListener(this);
        
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
		dugmeSlika.setBackground(new java.awt.Color(238, 203, 184));
		GlavniProzor.glavniProzorInstanca.razmakIzmedjuBiljke=0;

		for(int i =0;i<dugmeSlikaLista.size();i++) {
		if(e.getSource()==dugmeSlikaLista.get(i)) {
			setBrKliknutogDugmeta(i);
			GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.addSlikaDugme();	
		}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dugmeSlika.setBackground(new java.awt.Color(238, 203, 184));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dugmeSlika.setBackground(new Color(189, 128, 136));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		dugmeSlika.setBackground(new java.awt.Color(238, 203, 184));
	}

	@Override
	public void mouseExited(MouseEvent e) {
		dugmeSlika.setBackground(new Color(189, 128, 136));
	}

	/**
	 * @return the brKliknutogDugmeta
	 */
	public static int getBrKliknutogDugmeta() {
		return brKliknutogDugmeta;
	}

	/**
	 * @param brKliknutogDugmeta the brKliknutogDugmeta to set
	 */
	public static void setBrKliknutogDugmeta(int brKliknutogDugmeta) {
		DugmeSlika.brKliknutogDugmeta = brKliknutogDugmeta;
	}
	
		
}
