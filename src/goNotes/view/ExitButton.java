package goNotes.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;

@SuppressWarnings("serial")
public class ExitButton extends JButton {
	@SuppressWarnings("unused")
	private final ViewFrame viewFrame;
	public ExitButton(ViewFrame viewFrame) {
		this.viewFrame=viewFrame;
		this.setBorderPainted(false);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setOpaque(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		try {
			this.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/resources/exit.png"))));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		this.setPreferredSize(new Dimension(30, 30));
		
		this.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				int choix = JOptionPane.showConfirmDialog(viewFrame, "you are going to delete this note, continue?", "confirmation", JOptionPane.WARNING_MESSAGE);
				if (choix!=JOptionPane.YES_OPTION)
					return;
				viewFrame.getNote().delete();
				viewFrame.close();
				
			}
		});
	}
}
