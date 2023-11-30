import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Point;
import java.awt.MouseInfo;
import java.awt.Robot;

class View extends JPanel
{
	JButton b1;
	BufferedImage[] images = new BufferedImage[10];
	Model model;
	int imageNum;
	JButton save_button;
	JButton load_button;
	int time;
	static int horizontal = 0;
	static int vertical = 0;

	View(Controller c, Model m, Game game)
	{
		// Link up to other objects
		c.setView(this);
		model = m;

		//A2 Step3
		save_button = new JButton("Save!");
		save_button.setFocusable(false);
		save_button.addActionListener(c);
		this.add(save_button);

		load_button = new JButton("Load!");
		load_button.setFocusable(false);
		load_button.addActionListener(c);
		this.add(load_button);
		this.addMouseListener(c);

		for(imageNum = 0; imageNum < game.THINGS.length; imageNum++) {
			String fileName = "images/" + game.THINGS[imageNum] + ".png";
			try {
				this.images[imageNum] = ImageIO.read(new File(fileName));

			} catch(Exception e) {
				e.printStackTrace(System.err);
				System.exit(1);
			}
		}
	}

	public void update()
	{
		int boxX = getWidth()/2 - 100;
		int boxY  = 0;
		int height = 130;
		int width = 200;

		Point p = MouseInfo.getPointerInfo().getLocation();

		if(p.x >= 0 && p.x <= 200 && p.y >= 0 && p.y <= 200){
			// System.out.println("Mouse is in the purple box");
		}

		else if(p.x >= boxX && p.x <= boxX+width && p.y >= boxY && p.y <= boxY + height) {
			// System.out.println("Mouse is in the save/load area");
		}

		else {
			try {
				Robot robot = new Robot();

				if((p.x >= (getWidth()-100)) && (p.x <= getWidth())) {
					robot.mouseMove(getWidth() - 100, p.y);
					View.horizontal = View.horizontal + 10;
				}
				
				else if((p.x >= 0) && (p.x <= 100)) {
					robot.mouseMove(100, p.y);
					View.horizontal = View.horizontal - 10;
				}

				else if((p.y >= (getHeight() - 100)) && (p.y <= getHeight())) {
					robot.mouseMove(p.x, getHeight() - 100);
					View.vertical = View.vertical + 10;
				}

				else if((p.y >= 0) && (p.y <= 100)) {
					robot.mouseMove(p.x, 100);
					View.vertical = View.vertical - 10;
				}
			} catch(Exception ex) {
				ex.printStackTrace(System.err);
				System.exit(1);
		  	}
		}
	}

	public void paintComponent(Graphics g)
	{
		// Clear the background
		g.setColor(new Color(0, 204, 0));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(new Color(128, 0, 128));
		g.fillRect(0, 0, 200, 200);
		g.drawImage(this.images[model.itemNum], 0 , 0, null);
		this.update();
		for(int i = 0; i < model.things.size(); i++) {
			Thing thing = model.things.get(i);
			Point p = thing.pos(time);
			int kind = thing.kind;
			BufferedImage image = images[kind];
			g.drawImage(image, p.x - horizontal, p.y - vertical, null);
		}
		time = time + 1;
	}
	
	void removeButton()
	{
		this.remove(this.b1);
		this.repaint();
	}
}