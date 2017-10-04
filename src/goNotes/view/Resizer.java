package goNotes.view;

import java.awt.Cursor;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

@SuppressWarnings("serial")
public class Resizer extends JButton {
	
	private int initialX;
	private int initialY;
	ViewFrame frame;
	
	public Resizer(ViewFrame frame) {
		this.setToolTipText("resize");
		this.frame=frame;
		this.setBorderPainted(false);
		this.setBorder(null);
		this.setContentAreaFilled(false);
		this.setOpaque(false);
		this.setSize(20, 20);
		try {
			this.setIcon(new ImageIcon(ImageIO.read(Resizer.class.getResourceAsStream("/resources/resizerIcon.png"))));
		}
		catch(Exception ex) {
			ex.printStackTrace();
		}
		this.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
		this.addMouseMotionListener(new MotionListener());
		this.addMouseListener(new ClickListener());
	}
	
	class ClickListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent arg0) {
			initialX=arg0.getX();
			initialY=arg0.getY();
		}

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			frame.getNote().setBounds(frame.getBounds());
		}
		
	}
	class MotionListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			if(e.getComponent() instanceof Resizer) {
				 int xMoved =  e.getX() - initialX;
				 int yMoved =  e.getY() - initialY;
				 int newWidth = frame.getWidth() + xMoved;
				 int newHeight = frame.getHeight() + yMoved;
				 //width minimal 150
				 if(newWidth<150)
					 newWidth=150;
				 //minimal Height 50
				 if(newHeight<100)
					 newHeight=100;
				 
				 frame.setBounds(new Rectangle((int)frame.getBounds().getX(), (int)frame.getBounds().getY(), newWidth, newHeight));
				 frame.rebound();
				 
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
