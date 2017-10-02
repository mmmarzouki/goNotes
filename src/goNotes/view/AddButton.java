package goNotes.view;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import goNotes.controller.Controller;
import goNotes.metier.Note;

@SuppressWarnings("serial")
public class AddButton extends JButton {
	
	public AddButton() {
		this.setBorderPainted(false);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setOpaque(false);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		try {
			this.setIcon(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("/resources/add.png"))));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		this.setPreferredSize(new Dimension(30, 30));
		
		this.addActionListener(new ActionListener() {
			private File createFile() {
				File f = new File(Controller.NOTES_FOLDER,"newNote.json");
				int counter=1;
				while(true) {
					if(!f.exists())
						break;
					f = new File(Controller.NOTES_FOLDER,"newNote("+String.valueOf(counter)+").json");
					counter++;
				}
				return f;
			}
			private void writeInsideFile(File f) {
				String name=f.getName();
				name=name.substring(0,name.lastIndexOf('.'));
				String content="{\"color\":\"blue\",\"bounds\":{\"X\":\"100\",\"Y\":\"100\",\"width\":\"240\",\"height\":\"360\"},\"html\":\"INSERT TEXT\",\"title\":\""+name+"\"}";
				try {
					FileWriter fw = new FileWriter(f);
					fw.write(content);
					fw.close();
				}
				catch(Exception ex) {
					ex.printStackTrace();
				}
			}
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				File file = createFile();
				writeInsideFile(file);
				new Note(file);
				Controller.OPEN_FRAME++;
			}
		});
	}
}