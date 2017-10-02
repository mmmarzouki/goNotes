package goNotes.controller;

import java.io.File;
import goNotes.metier.Note;

public class Controller {
	
	public static final File NOTES_FOLDER =new File("./notes");
	
	public static int OPEN_FRAME =0;
	
	public Controller() {
		File[] noteFiles = NOTES_FOLDER.listFiles();
		for(File noteFile : noteFiles) {
			new Note(noteFile);
			OPEN_FRAME++;
		}
	}
	
	public static void main(String[] args) {
		new Controller();
	}
}
