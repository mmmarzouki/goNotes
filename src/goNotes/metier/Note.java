package goNotes.metier;

import java.awt.Rectangle;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import goNotes.controller.Controller;
import goNotes.view.ViewFrame;

public class Note {

	private static final String CARRIAGE_RETURN = "\\n";
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
	private JSONObject root;
	private String title;
	private Rectangle bounds;
	private ColorSet colorSet;
	private String htmlText;
	
	//getters
	public File getNoteFile() { return noteFile; }
	public String getTitle() { return title; }
	public Rectangle getBounds() { return bounds; }
	public ColorSet getColorSet() { return colorSet; }
	public String getHtmlText() { return htmlText; }
	
	//settters
	@SuppressWarnings("unchecked")
	public void setTitle(String title){ 
		this.title=title;
		root.put("title", title);
		noteFile.delete();
		noteFile = new File(Controller.NOTES_FOLDER,title+".json");
		writeFile();
	}
	@SuppressWarnings("unchecked")
	public void setBounds (Rectangle bounds) { 
		this.bounds=bounds;
		JSONObject boundsObject = (JSONObject) root.get("bounds");
		boundsObject.put("X", String.valueOf((int)bounds.getX()));
		boundsObject.put("Y", String.valueOf((int)bounds.getY()));
		boundsObject.put("width", String.valueOf((int)bounds.getWidth()));
		boundsObject.put("height", String.valueOf((int)bounds.getHeight()));
		root.put("bounds", boundsObject);
		writeFile();
	}
	@SuppressWarnings("unchecked")
	public void setColorSet(String colorName) {
		boolean hasChanged=false;
		for (ColorSet colorSet : colorSets) {
			if(colorSet.getColorName().equals(colorName)) {
				this.colorSet=colorSet;
				hasChanged=true;
			}
		}
		if(!hasChanged)
			//if couldnt find the color set the default color ( blue )
			this.colorSet=colorSets[0];
		root.put("color", colorSet.getColorName());
		writeFile();
	}
	@SuppressWarnings("unchecked")
	public void setHtmlText(String s) {
		this.htmlText=s.replaceAll(CARRIAGE_RETURN, BR);
		root.put("html", htmlText);
		writeFile();
	}
	
	//methods
	private void writeFile() {
        try {
    		FileWriter fileWriter = new FileWriter(noteFile);
        	fileWriter.write(root.toJSONString());
        	fileWriter.flush();
        	fileWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        } 
	}
	private void initNoteAttributes(){
		//init parser
		JSONParser parser = new JSONParser();
		try {
			//file reader
			FileReader fr = new FileReader(noteFile);
			//parse file
			root = (JSONObject) parser.parse(fr);
			//get attributes
			title = (String) root.get("title");
			colorSet = chooseColor((String)root.get("color"));
			htmlText = ((String) root.get("html"));
			//bounds
			JSONObject boundsObject = (JSONObject)root.get("bounds"); 
			int x = Integer.parseInt((String)boundsObject.get("X"));
			int y = Integer.parseInt((String)boundsObject.get("Y"));
			int width = Integer.parseInt((String)boundsObject.get("width"));
			int height = Integer.parseInt((String)boundsObject.get("height"));
			bounds = new Rectangle(x, y, width, height);
			//close reader
			fr.close();
		}
		catch(Exception ex) {
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
		noteFile.delete();
	}
	
	//constructors
	public Note(File noteFile) {
		this.noteFile=noteFile;
		initNoteAttributes();
		//view
		new ViewFrame(this);
	}
}
