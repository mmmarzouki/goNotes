package goNotes.view;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class TitleBar extends JPanel {
	//attributes
	private int initialX;
	private int initialY;
	private ViewFrame viewFrame;
	
	//methods
	private void initPanel() {
		this.setBackground(viewFrame.getNote().getColorSet().getTitleBarColor());
		this.setLayout(new BorderLayout());
	}
	private void initComponents() {
		ExitButton exit = new ExitButton(viewFrame);
		this.add(exit,BorderLayout.EAST);
	}
	
	//constructors
	public TitleBar(ViewFrame viewFrame) {
		this.viewFrame=viewFrame;
		initPanel();
		initComponents();
		//listeners
		this.addMouseListener(new ClickListener());
		this.addMouseMotionListener(new MotionListener());
	}

	//class
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
			initialX=arg0.getX();
			initialY=arg0.getY();
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			viewFrame.getNote().setBounds(viewFrame.getBounds());
		}
		
	}
	
	class MotionListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent e) {
			if(e.getComponent() instanceof TitleBar) {
				int xMoved =  e.getX() - initialX;
				int yMoved =  e.getY() - initialY;
				int newPosX = (int) viewFrame.getBounds().getX() + xMoved;
				int newPosY = (int) viewFrame.getBounds().getY() + yMoved;
				viewFrame.setBounds(newPosX,newPosY,viewFrame.getWidth(),viewFrame.getHeight());
			}
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
