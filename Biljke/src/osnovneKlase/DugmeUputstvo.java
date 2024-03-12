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
 *  This class displays instructions for the plant when button 'Uputstvo' is pressed 
 */

public class DugmeUputstvo extends JButton implements MouseListener{


	private static final long serialVersionUID = 1L;
		static ArrayList<JLabel> dugmeUputstvoLista = new ArrayList<>();
		private static int brKliknutogDugmetaUputstvo;
		private JLabel dugmeUputstvo;
		protected static DugmeUputstvo dugmeUputstvoInstanca;

		
		public DugmeUputstvo(){
			
			dugmeUputstvoInstanca = this;
			
			dugmeUputstvo = new JLabel("Uputstvo");
			dugmeUputstvo.setOpaque(true);
			dugmeUputstvo.setHorizontalAlignment(JLabel.CENTER);
			dugmeUputstvo.setVerticalAlignment(JLabel.CENTER);
			dugmeUputstvo.setForeground(new java.awt.Color(0, 0, 0));
			dugmeUputstvo.setBackground(new Color(189, 128, 136));
			dugmeUputstvo.setBounds(124, 56+GlavniProzor.glavniProzorInstanca.razmakIzmedjuBiljke, 95, 23);
	        GlavniProzor.glavniProzorInstanca.panel.add(dugmeUputstvo);
	        dugmeUputstvoLista.add(dugmeUputstvo);
	        dugmeUputstvo.addMouseListener(this);
	        
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			
			dugmeUputstvo.setBackground(new java.awt.Color(238, 203, 184));
			GlavniProzor.glavniProzorInstanca.razmakIzmedjuBiljke=0;

			for(int i =0;i<dugmeUputstvoLista.size();i++) {
			if(e.getSource()==dugmeUputstvoLista.get(i)) {
				setBrKliknutogDugmetaUputstvo(i);
			GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.addUputstvoDugme();	
			}
			}
		}

		@Override
		public void mousePressed(MouseEvent e) {
			dugmeUputstvo.setBackground(new java.awt.Color(238, 203, 184));
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			dugmeUputstvo.setBackground(new Color(189, 128, 136));
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			dugmeUputstvo.setBackground(new java.awt.Color(238, 203, 184));
		}

		@Override
		public void mouseExited(MouseEvent e) {
			dugmeUputstvo.setBackground(new Color(189, 128, 136));
		}

		public static int getBrKliknutogDugmetaUputstvo() {
			return brKliknutogDugmetaUputstvo;
		}

		public static void setBrKliknutogDugmetaUputstvo(int brKliknutogDugmetaUputstvo) {
			DugmeUputstvo.brKliknutogDugmetaUputstvo = brKliknutogDugmetaUputstvo;
		}
		
			
	}
