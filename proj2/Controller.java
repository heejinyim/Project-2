import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.lang.Math;

//A2 Step3
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
//A2 Step4
import java.awt.event.MouseMotionListener;
//import java.awt.Robot;
import java.awt.*;

class Controller implements ActionListener, MouseListener, KeyListener
{
	View view;
	Model model;
	boolean keyLeft;
	boolean keyRight;
	boolean keyUp;
	boolean keyDown;
	

	Controller(Model m)
	{
		model = m;
	}

	void setView(View v)
	{
		view = v;
	}

	public void actionPerformed(ActionEvent e)
	{
		//A2 Step3 (save button 눌렀을 때)
		if(e.getSource() == view.save_button){
		try {
			FileWriter writer = new FileWriter("map.json");
			writer.write(this.model.marshal().toString());
			writer.close();
			System.out.println("Test");
		  } catch (IOException exception) {
			exception.printStackTrace();
			System.exit(1);
		  }
		}

		else{
			model.load();
		}

	}
	
	public void mousePressed(MouseEvent e)
	{
		model.setDestination(e.getX(), e.getY());

		if((e.getX() >= 0 && e.getX() <= 200) && (e.getY() >= 0 && e.getY() <= 200)) {
			model.itemNum += 1;
			model.itemNum %= 10; //Makes the itemNum less than 10
			System.out.println(model.itemNum);
		}
  
		if((e.getX() > 200 || e.getY() > 200) && e.getButton() == 1) {
			model.addThing(e.getX()+ View.horizontal, e.getY()+ View.vertical);
		}

	  	if((e.getX() > 200 || e.getY() > 200) && e.getButton() == 3) {
			model.removeThing(e.getX() , e.getY() );
		}	

	}

	public void mouseReleased(MouseEvent e) 
	{	}
	
	public void mouseEntered(MouseEvent e) 
	{	}
	
	public void mouseExited(MouseEvent e) 
	{	}
	
	public void mouseClicked(MouseEvent e) 
	{	}

	public void mouseMotionListener(MouseEvent e) 
	{	}
	
	public void keyPressed(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = true; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = true; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = true; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = true; 
				break;
		}
	}

	public void keyReleased(KeyEvent e)
	{
		switch(e.getKeyCode())
		{
			case KeyEvent.VK_RIGHT: 
				keyRight = false; 
				break;
			case KeyEvent.VK_LEFT: 
				keyLeft = false; 
				break;
			case KeyEvent.VK_UP: 
				keyUp = false; 
				break;
			case KeyEvent.VK_DOWN: 
				keyDown = false; 
				break;
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
		}
		char c = Character.toLowerCase(e.getKeyChar());
		if(c == 'q')
			System.exit(0);
        if(c == 'r')
            model.reset();
	}

	public void keyTyped(KeyEvent e)
	{	}

	void update()
	{
		// if(keyRight) 
        //     model.dest_x += Model.speed;
		// if(keyLeft) 
    	// 	model.dest_x -= Model.speed;
		// if(keyDown) 
        //     model.dest_y += Model.speed;
		// if(keyUp)
        //     model.dest_y -= Model.speed;
	}
}
