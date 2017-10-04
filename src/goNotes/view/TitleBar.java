package goNotes.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import goNotes.controller.Controller;

@SuppressWarnings("serial")
public class TitleBar extends JPanel {
	//attributes
	private ViewFrame viewFrame;
	private JLabel titleLabel = new JLabel();
	private JTextField titleField = new JTextField();
	
	//methods
	private void initPanel() {
		this.setBackground(viewFrame.getNote().getColorSet().getTitleBarColor());
		this.setLayout(new BorderLayout());
	}
	private void initComponents() {
		//exit
		ExitButton exit = new ExitButton(viewFrame);
		this.add(exit,BorderLayout.EAST);
		//titleLabel
		titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setOpaque(false);
		titleLabel.setText(viewFrame.getNote().getTitle());
		this.add(titleLabel,BorderLayout.CENTER);
		titleLabel.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				if(arg0.getClickCount()==2) {
					titleLabel.setVisible(false);
					add(titleField,BorderLayout.CENTER);
					titleField.grabFocus();
					titleField.setSelectionStart(0);
					titleField.setSelectionEnd(titleField.getText().length());
				}
			}
		});
		ClickListener cl = new ClickListener();
		titleLabel.addMouseListener(cl);
		titleLabel.addMouseMotionListener(cl);
		titleLabel.setToolTipText("note title, double click to change");
		//titleField
		titleField.setText(viewFrame.getNote().getTitle());
		titleField.setOpaque(false);
		titleField.setBackground(viewFrame.getNote().getColorSet().getTitleBarColor());
		titleField.setBorder(null);
		TitleListener tl = new TitleListener();
		titleField.addActionListener(tl);
		titleField.addFocusListener(tl);
		//add
		JPanel container = new JPanel();
		container.setLayout(new BorderLayout());
		container.setOpaque(false);
			AddButton add = new AddButton();
			container.add(add, BorderLayout.WEST);
		//edit
			Edit edit= new Edit(viewFrame);
			container.add(edit, BorderLayout.EAST);
		this.add(container, BorderLayout.WEST);
		
	}
	
	//constructors
	public TitleBar(ViewFrame viewFrame) {
		this.viewFrame=viewFrame;
		initPanel();
		initComponents();
		//listeners
		ClickListener cl = new ClickListener();
		this.addMouseListener(cl);
		this.addMouseMotionListener(cl);
	}

	//class
	class ClickListener implements MouseListener, MouseMotionListener{

		private int initialX;
		private int initialY;
		
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
		
		@Override
		public void mouseDragged(MouseEvent e) {
			if((e.getComponent() instanceof TitleBar)||(e.getComponent() instanceof JLabel)) {
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
	class TitleListener implements ActionListener, FocusListener{
		private boolean acceptable(String title) {
			File f = new File(Controller.NOTES_FOLDER,title+".json");
			if(!f.exists())
				return true;
			if(f.getAbsolutePath().equals(viewFrame.getNote().getNoteFile().getAbsolutePath()))
				return true;
			return false;
		}
		private void saveTitle() {
			String newTitle=titleField.getText();
			if(acceptable(newTitle)) {
				titleLabel.setText(newTitle);
				viewFrame.getNote().setTitle(newTitle);
				titleField.setVisible(true);
				remove(titleField);
				add(titleLabel,BorderLayout.CENTER);
				titleLabel.setVisible(true);
			}
			else {
				JOptionPane.showMessageDialog(null, "this title already exists");
				titleField.setText(viewFrame.getNote().getTitle());
			}
			
		}
		@Override
		public void focusGained(FocusEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void focusLost(FocusEvent e) {
			saveTitle();
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			saveTitle();
			
		}
		
	}
	

		
		
}
