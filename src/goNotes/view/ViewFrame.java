package goNotes.view;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import goNotes.controller.Controller;
import goNotes.metier.Note;

@SuppressWarnings("serial")
public class ViewFrame extends JFrame{

	private final Note note;
	private JTextPane textPane;
	private TitleBar titleBar;
	private JScrollPane textScrollPane;
	
	//getters & setters
	public Note getNote() { return note; }
	
	//methods
	private void initFrame() {
		//init frame properties
		this.setBounds(note.getBounds());
		this.setTitle(note.getTitle());
		this.setLayout(null);
		this.setUndecorated(true);
	}
	private void initComponents() {
		//init components
		//textPane
		textPane = new JTextPane();
		textPane.setContentType("text/html");
		textPane.setEditable(true);
		textPane.setBackground(note.getColorSet().getMainColor());
		textPane.setText(note.getHtmlText());
		textScrollPane = new JScrollPane(textPane);
		textScrollPane.setBounds(0, 30, (int)note.getBounds().getWidth(), (int)note.getBounds().getHeight()-30);
		this.add(textScrollPane,BorderLayout.CENTER);
		//titleBar
		titleBar = new TitleBar(this);
		titleBar.setBounds(0, 0, (int)note.getBounds().getWidth(), 30);
		this.add(titleBar);
	}
	public void close() {
		this.dispose();
		Controller.OPEN_FRAME--;
		if(Controller.OPEN_FRAME==0)
			System.exit(0);
	}
	public void rebound() {
        setBounds(note.getBounds());
		titleBar.setBounds(0, 0, (int)note.getBounds().getWidth(), 30);
		textScrollPane.setBounds(0, 30, (int)note.getBounds().getWidth(), (int)note.getBounds().getHeight()-50);
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
