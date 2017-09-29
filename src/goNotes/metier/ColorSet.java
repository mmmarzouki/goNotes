package goNotes.metier;

import java.awt.Color;

public class ColorSet {
	private Color mainColor;
	private Color titleBarColor;
	private String name;
	
	//getters & setters
	public Color getMainColor() { return mainColor; }
	public void setMainColor(Color mainColor) { this.mainColor = mainColor; }
	public Color getTitleBarColor() { return titleBarColor; }
	public void setTitleBarColor(Color titleBarColor) { this.titleBarColor = titleBarColor; }
	public String getColorName() { return name;}
	public void setName(String name) { this.name=name; }
	

	//constructors
	public ColorSet(int r1, int g1, int b1, int r2, int g2, int b2, String name) {
		this.mainColor= new Color(r1,g1,b1);
		this.titleBarColor = new Color(r2,g2,b2);
		this.name=name;
	}
}
