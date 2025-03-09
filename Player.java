/**
	This is a template for a Java file.
	
	@author TAGASA, Meishelle (226081)
    @author MARINO, Lawrence (224103)
	@version 20 May 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

/*Class Description
 * This class is a helper class in charge of importing the image for a player's character.
 * It also contains all methods to manage and render the images based on the player's actions.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class Player extends JComponent {

    private int positionX, positionY;
    private int width, height;
    private BufferedImage characVisual;
    private boolean imageLoaded = false;

    public Player(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public void renderCharac(Graphics2D g2d) {
        loadObjImage(); // Ensure the image is loaded before rendering
        int a = positionX;
        int b = positionY;
        g2d.drawImage(characVisual, a, b, null);
    }

    private void loadObjImage() {
        if (!imageLoaded) {
            try {
                InputStream charImports = getClass().getResourceAsStream("Player_Seedling_Visuals.png");
                characVisual = ImageIO.read(charImports);
                width = characVisual.getWidth();
                height = characVisual.getHeight();
                imageLoaded = true;
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the IOException here
            }
        }
    }

    // Movement method
    public void movePlayer(int moveX, int moveY, int panelWidth, int panelHeight) {
		// Calculate the new position
		int newX = positionX + moveX;
		int newY = positionY + moveY;

		// Ensure the new position is within the bounds of the panel
			if (newX >= 0 && newX + getVisualWidth() <= panelWidth) {
				positionX = newX;
		}
			if (newY >= 0 && newY + getVisualHeight() <= panelHeight) {
				positionY = newY;
		}
	}

    // Collision detection methods
    public Rectangle getBounds() {
        return new Rectangle(positionX, positionY, getVisualWidth(), getVisualHeight());
    }

    public boolean collidesWith(Rectangle rect) {
        return rect.intersects(positionX, positionY, getVisualWidth(), getVisualHeight());
    }

    // Position getters
    public int getPositionX() {
        return positionX;
    }

    public int getPositionY() {
        return positionY;
    }
    
    // Width and height getters
    public int getVisualWidth() {
        loadObjImage(); // Ensure the image is loaded before accessing width
        return width;
    }

    public int getVisualHeight() {
        loadObjImage(); // Ensure the image is loaded before accessing height
        return height;
    }

    //METHODS FOR PASSING COORS TO OTHER PLAYER
    public void setX(int n) {
        positionX = n;
    }

    public void setY(int n) {
        positionY = n;
    }
}
