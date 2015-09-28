package escape.ui;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import escape.gameworld.Container;
import escape.gameworld.Item;
import escape.gameworld.Player;
import escape.gameworld.Player.Direction;
import escape.gameworld.Room;

public class GameCanvas extends JPanel {
	private static final Image wall = loadImage("/images/wall.png");
	private static final Image darkWall = loadImage("/images/darkWall.png");
	private static final Image hallMain = loadImage("/images/hallMain.png");
	private static final Image hallLeftStudy = loadImage("/images/hallLeftStudy.png");
	private static final Image hallRightBedroom = loadImage("/images/hallRightBedroom.png");
	private static final Image hallLeftKitchen = loadImage("/images/hallLeftKitchen.png");
	private static final Image hallRightLivingRoom = loadImage("/images/hallRightLivingRoom.png");
	private static final Image mainMenu = loadImage("/images/mainMenu.png");
	private Player player;
	private Room currentRoom;
	private Player.Direction currentDirection;
	private int state;
	private static final double WINDOW_HEIGHT_SCALE = 0.6;
	private static final double WINDOW_WIDTH_SCALE = 0.375;
	private static final double BACKGROUND_WIDTH_SCALE = 0.8;
	private static final double BACKGROUND_HEIGHT_SCALE = 0.814;
	private Toolkit t = Toolkit.getDefaultToolkit();;
	private Dimension d = t.getScreenSize();
	private int h = (int) (d.height * WINDOW_HEIGHT_SCALE);
	private int w = (int) (d.width * WINDOW_WIDTH_SCALE);

	public GameCanvas(Player p) {
		player = p;
		getPreferredSize();

	}

	public void paint(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		state = GameFrame.state();
		// System.out.println("State: " + state);
		// Draws backgrounds
		if (state == 0) {
			g.drawImage(mainMenu, 0, 0, scaleImgWidth(mainMenu),
					scaleImgHeight(mainMenu), null);
		} else if (state == 1) {
			if (player.getRoom() != null) {
				currentRoom = player.getRoom();
				currentDirection = player.getDirection();

				switch (currentRoom.getName()) {
				case "Main Hall":
					g.drawImage(hallMain, 0, 0, scaleImgWidth(hallMain),
							scaleImgHeight(hallMain), null);
					break;
				case "Hall - Study":
					g.drawImage(hallLeftStudy, 0, 0,
							scaleImgWidth(hallLeftStudy),
							scaleImgHeight(hallLeftStudy), null);
					break;
				case "Hall - Bedroom":
					g.drawImage(hallRightBedroom, 0, 0,
							scaleImgWidth(hallRightBedroom),
							scaleImgHeight(hallRightBedroom), null);
					break;
				case "Hall - Kitchen":
					g.drawImage(hallLeftKitchen, 0, 0,
							scaleImgWidth(hallLeftStudy),
							scaleImgHeight(hallLeftStudy), null);
					break;
				case "Hall - Living Room":
					g.drawImage(hallRightLivingRoom, 0, 0,
							scaleImgWidth(hallRightLivingRoom),
							scaleImgHeight(hallRightLivingRoom), null);
					break;
				case "Bedroom":
					g.drawImage(darkWall, 0, 0, null);
					break;
				case "Living Room":
				case "Kitchen":
				case "Study":
					g.drawImage(wall, 0, 0, scaleImgWidth(wall),
							scaleImgHeight(wall), null);
					break;
				}
				// currentRoom.getBin().draw(g, currentRoom);
				for (Item i : currentRoom.getItems()) {
					i.draw(g, currentRoom, currentDirection);
				}

				//
				// for (Container c : currentRoom.getContainer()){
				// c.draw(g);
				// }
			}
		}
		repaint();
	}

	public Dimension getPreferredSize() {

		d.setSize(w, h);
		System.out.println("Width: " + w);
		System.out.println("Height: " + h);
		return d;
		// return new Dimension(600, 600);
	}

	public void setResizable(boolean b) {
		getPreferredSize();
	}

	// public int getW() {
	// return w;
	// }
	//
	// public int getH() {
	// return h;
	// }
	private static int scaleImgWidth(Image img) {
		return (int) (img.getWidth(null) * BACKGROUND_WIDTH_SCALE);
	}

	private static int scaleImgHeight(Image img) {
		return (int) (img.getHeight(null) * BACKGROUND_HEIGHT_SCALE);
	}

	public static Image loadImage(String source) {
		try {
			Image image = ImageIO.read(new FileInputStream("src/" + source));
			return image;
		} catch (IOException e) {
			throw new RuntimeException("Unable to load image");
		}
	}

}
