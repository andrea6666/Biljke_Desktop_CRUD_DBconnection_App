package osnovneKlase;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import net.coobird.thumbnailator.Thumbnails;

import javax.swing.JLabel;

public class ProzorSlika extends JPanel {

	/**
	 * Created by: Andrea
	 * 
	 *  Window that displays an image of a plant from the database when the "slika" button on the main window is pressed
	 */
	private static final long serialVersionUID = 1L;
	Statement stmt = null;
	Connection conn;
	ResultSet rs = null;
	ImageIcon img;
	BufferedImage image;
	private JLabel slikaLabel;
	private ImageIcon icon;
	private JLabel btnX;
	private JLabel imeBiljkeLabel;
	private ConnectionString cs = new ConnectionString();

	public ProzorSlika() {

		setBounds(0, 0, GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getWidth(), GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.getHeight());
		setBackground(new Color(189, 128, 136));
		setLayout(null);

		imeBiljkeLabel = new JLabel();
		imeBiljkeLabel.setBounds(0,0,getWidth(),40);
		imeBiljkeLabel.setBackground(new Color(189, 128, 136));
		imeBiljkeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		imeBiljkeLabel.setVerticalAlignment(SwingConstants.BOTTOM);

		slikaLabel = new JLabel();
		slikaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		slikaLabel.setVerticalAlignment(SwingConstants.CENTER);
		

		setVisible(true);

		try {

			conn = DriverManager.getConnection(cs.connectionString);
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM table_biljke WHERE RB='"
					+ GlavniProzor.brojRedaTabele.get(DugmeSlika.getBrKliknutogDugmeta()) + "';");

			if (rs.next()) {

				String base64String = rs.getString("slika");
				String ime_biljke = rs.getString("ime_biljke");

				byte[] decodedBytes = Base64.getDecoder().decode(base64String);

				try {
					// Create an InputStream from the decoded byte array
					InputStream inputStream = new ByteArrayInputStream(decodedBytes);

					// Create a ByteArrayOutputStream to write the thumbnail to
					ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

					// Use Thumbnailator to create a thumbnail of the image
					Thumbnails.of(inputStream).size(500, 500).outputFormat("png").toOutputStream(outputStream);

					// Convert the OutputStream to a byte array
					byte[] thumbnailBytes = outputStream.toByteArray();

					// Create a new ImageIcon object using the byte array
					icon = new ImageIcon(thumbnailBytes);

					// Create a new JLabel object and set its icon

					imeBiljkeLabel.setText(ime_biljke);
					Font font = new Font("Dialog", Font.BOLD, 20);
					imeBiljkeLabel.setFont(font);
					imeBiljkeLabel.setOpaque(true);
					add(imeBiljkeLabel);

					slikaLabel.setBounds(0,20,getWidth(),getHeight()-20);
					slikaLabel.setBackground(new Color(189, 128, 136));
					slikaLabel.setOpaque(true);
					slikaLabel.setIcon(icon);
					add(slikaLabel);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException ex) {
			System.err.println(ex);
		} finally {
			try {
				if (stmt != null) {
					stmt.close();
				}
				if (conn != null) {
					conn.close();
				}
			} catch (SQLException ex) {
				ex.printStackTrace();
			}

		}

		btnX = new JLabel("");
		btnX.setOpaque(true);
		btnX.setHorizontalAlignment(JLabel.CENTER);
		btnX.setVerticalAlignment(JLabel.CENTER);
		btnX.setBackground(new Color(0, 0, 0, 0));
		btnX.setBorder(null);
		btnX.setFocusable(false);
		btnX.setIcon(new ImageIcon(DodajBiljkuProzor.class.getResource("/img/dugmeX15pikselaCrno.png")));
		btnX.setBounds(729, 13, 15, 15);
		btnX.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				setVisible(false);
			 GlavniProzorBiljkeFrame.glavniProzorBiljkeFrameInstanca.deleteUpdate();
			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

		});
		imeBiljkeLabel.add(btnX);

	}

}
