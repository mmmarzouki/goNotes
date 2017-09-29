package goNotes.metier;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Vector;
import java.io.InputStreamReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import goNotes.view.ViewFrame;

public class Note {

	//carriageReturn
	private static final String CARRIAGE_RETURN_INCRYPTED = "`§##§`";
	private static final String CARRIAGE_RETURN_DECRYPTED = "\n";
	private static final String BR="<br>";
	//colorSet
    public static ColorSet[] colorSets = new ColorSet[13];
    
    //load colors only once where create the first note
    static { 
        //parsing
        try {
            //get xmlFile content
            String xmlContent="";
            InputStream in = Note.class.getResourceAsStream("/resources//colors.xml");
            BufferedReader input = new BufferedReader(new InputStreamReader(in));
            String line;
	    while ((line = input.readLine()) != null) {
	    	xmlContent+=line;
	    }
            //parse
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlContent));		
            final Document document= builder.parse(is);
            //racine
            Element racine = document.getDocumentElement();
            //colors
            NodeList nodeColors = racine.getChildNodes();
            //counter for the colorSet table
            int counter=0;
            
            for(int i=0;i<nodeColors.getLength();i++){
                //foreach color get the RGB values
                Node color = nodeColors.item(i);
                if(color.getNodeType()==Node.ELEMENT_NODE){
                    //get the element color
                    Element colorElement = (Element) color;
                    Element main = (Element) colorElement.getElementsByTagName("main").item(0);
                    Element titleBar = (Element) colorElement.getElementsByTagName("titleBar").item(0);
                    //get the values
                    int mainR =Integer.parseInt(main.getElementsByTagName("R").item(0).getTextContent().replaceAll("\\s", ""));
                    int mainG =Integer.parseInt(main.getElementsByTagName("G").item(0).getTextContent().replaceAll("\\s", ""));
                    int mainB =Integer.parseInt(main.getElementsByTagName("B").item(0).getTextContent().replaceAll("\\s", ""));
                    int titleBarR =Integer.parseInt(titleBar.getElementsByTagName("R").item(0).getTextContent().replaceAll("\\s", ""));
                    int titleBarG =Integer.parseInt(titleBar.getElementsByTagName("G").item(0).getTextContent().replaceAll("\\s", ""));
                    int titleBarB =Integer.parseInt(titleBar.getElementsByTagName("B").item(0).getTextContent().replaceAll("\\s", ""));
                    //create the color Set
                    colorSets[counter]= new ColorSet(mainR, mainG, mainB, titleBarR, titleBarG, titleBarB,colorElement.getNodeName());
                    //increment the counter
                    counter++;
                }
            }
            
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        
    }
    
    
    //note attributes
	private File noteFile;
	private String title;
	private Rectangle bounds;
	private ColorSet colorSet;
	private String htmlText;
	
	//getters & setters
	public void setTitle(String title) { this.title=title; }
	public String getTitle() { return title; }
	public void setBounds (Rectangle bounds) { this.bounds=bounds; }
	public Rectangle getBounds() { return bounds; }
	public ColorSet getColorSet() { return colorSet; }
	public void setColorSet(String colorName) {
		for (ColorSet colorSet : colorSets) {
			if(colorSet.getColorName().equals(colorName)) {
				this.colorSet=colorSet;
				return;
			}
		}
		this.colorSet=colorSets[0];
	}
	public String getHtmlText() { return htmlText; }
	public void setHtmlText(String s)/*to complete*/ {
		//to Complete
	}
	
	//methods
	private void initNoteAttributes(){
        //parsing
        try{
            final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            final DocumentBuilder builder = factory.newDocumentBuilder();		
            final Document document= builder.parse(noteFile);
            //racine
            Element racine = document.getDocumentElement();
            //title
            Element titleElement = (Element) racine.getElementsByTagName("title").item(0);
            title=titleElement.getTextContent().replaceAll("\\s","");
            //posX
            Element posXElement = (Element) racine.getElementsByTagName("X").item(0);
            int posX=Integer.parseInt(posXElement.getTextContent().replaceAll("\\s",""));
            //posY
            Element posYElement = (Element) racine.getElementsByTagName("Y").item(0);
            int posY=Integer.parseInt(posYElement.getTextContent().replaceAll("\\s",""));
            //width
            Element widthElement = (Element) racine.getElementsByTagName("width").item(0);
            int width=Integer.parseInt(widthElement.getTextContent().replaceAll("\\s",""));
            //height
            Element heightElement = (Element) racine.getElementsByTagName("height").item(0);
            int height=Integer.parseInt(heightElement.getTextContent().replaceAll("\\s",""));
            //bounds
            bounds = new Rectangle(posX, posY, width, height);
            //color
            Element colorElement = (Element) racine.getElementsByTagName("color").item(0);
            colorSet=chooseColor(colorElement.getTextContent().replaceAll("\\s",""));
            //html
            Element htmlElement = (Element) racine.getElementsByTagName("html").item(0);
            htmlText=htmlElement.getTextContent().replaceAll(CARRIAGE_RETURN_INCRYPTED, BR);
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }
	private ColorSet chooseColor(String color){
        Vector<String> colorNames = new Vector<String>();
        for(int i=0;i<colorSets.length;i++){
            colorNames.add(colorSets[i].getColorName());
        }
        int colorOrder = colorNames.indexOf(color);
        return colorSets[colorOrder];
    }
	public void delete() {
		
	}
	
	//constructors
	public Note(File noteFile) {
		this.noteFile=noteFile;
		initNoteAttributes();
		//view
		new ViewFrame(this);
	}
}
