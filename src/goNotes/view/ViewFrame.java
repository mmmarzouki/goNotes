package goNotes.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.net.URL;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import javax.imageio.ImageIO;

import goNotes.controller.Controller;
import goNotes.metier.ColorSet;
import goNotes.metier.Note;

@SuppressWarnings("serial")
public class ViewFrame extends JFrame{

	private final Note note;
	private JTextPane textPane;
	private TitleBar titleBar;
	private JScrollPane textScrollPane;
	private Resizer resizer;
	private EditPanel editPanel;
	
	//getters & setters
	public Note getNote() { return note; }
	public EditPanel getEditPanel() { return editPanel; }
	
	//methods
	public void recolor(int index) {
		ColorSet colorSet = Note.colorSets[index];
		textPane.setBackground(colorSet.getMainColor());
		titleBar.setBackground(colorSet.getTitleBarColor());
		note.setColorSet(colorSet.getColorName());
		this.repaint();
	}
	private void initFrame() {
		//init frame properties
		this.setBounds(note.getBounds());
		this.setTitle(note.getTitle());
		this.setLayout(null);
		try {
			URL url = ViewFrame.class.getResource("/resources/icon.png");
			this.setIconImage(ImageIO.read(url));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		this.setUndecorated(true);
	}
	private void initComponents() {
		//EditPanel
		editPanel = new EditPanel(this);
		editPanel.setVisible(false);
		editPanel.setBounds(0, 30, (int)note.getBounds().getWidth(), 40);
		this.add(editPanel);
		//titleBar
		titleBar = new TitleBar(this);
		titleBar.setBounds(0, 0, (int)note.getBounds().getWidth(), 30);
		this.add(titleBar);
		//Resizer
		this.resizer = new Resizer(this);
		resizer.setBounds(this.getWidth()-20, this.getHeight()-20, 20, 20);
		this.add(resizer);
		//textPane
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(true);
		textPane.setBackground(note.getColorSet().getMainColor());
		textPane.setText(note.getHtmlText());
		textPane.setSelectedTextColor(Color.white);
		textPane.setSelectionColor(Color.BLACK);
		textScrollPane = new JScrollPane(textPane);
		textScrollPane.setBounds(0, 30, (int)note.getBounds().getWidth(), (int)note.getBounds().getHeight()-30);
		this.add(textScrollPane,BorderLayout.CENTER);
		textPane.getDocument().addDocumentListener(new DocumentListener() {
			
			private void save() {
				String fullText = textPane.getText();
				String text = fullText.substring(fullText.indexOf("<body>")+"<body>".length(), fullText.indexOf("</body>"));
				//remove all spaces at the begging
				while(text.charAt(0)==' '||text.charAt(0)=='\t'||text.charAt(0)=='\n'||text.charAt(0)=='\r' ) {
					text=text.substring(1);
				}
				note.setHtmlText(text);
				//update frame
				repaint();
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				save();
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				save();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				save();
				
			}
		});
		
	}
	public void close() {
		this.dispose();
		Controller.OPEN_FRAME--;
		if(Controller.OPEN_FRAME==0)
			System.exit(0);
	}
	public void rebound() {
        resizer.setBounds((int)this.getBounds().getWidth()-20, (int)this.getBounds().getHeight()-20, 20, 20);
		titleBar.setBounds(0, 0, (int)this.getBounds().getWidth(), 30);
		if(editPanel.isShown()) {
			textScrollPane.setBounds(0, 70, (int)this.getBounds().getWidth(), (int)this.getBounds().getHeight()-70);
		}
		else {
			textScrollPane.setBounds(0, 30, (int)this.getBounds().getWidth(), (int)this.getBounds().getHeight()-30);
		}
		editPanel.setBounds(0, 30, (int)this.getBounds().getWidth(), 40);
	}
	public void showEditPanel() {
		editPanel.setShown(true);
		editPanel.setVisible(true);
		textScrollPane.setBounds(0, 70, (int)this.getBounds().getWidth(), (int)this.getBounds().getHeight()-70);
	}
	public void hideEditPanel() {
		editPanel.setShown(false);
		editPanel.setVisible(false);
		textScrollPane.setBounds(0, 30, (int)this.getBounds().getWidth(), (int)this.getBounds().getHeight()-30);
	}
	
	//constructors
	public ViewFrame(Note note) {
		//note
		this.note=note;
		//frame & components
		initFrame();
		initComponents();
		//visible
		this.setVisible(true);
	}
}
