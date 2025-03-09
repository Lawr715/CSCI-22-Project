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
 * This class is a helper class in charge of importing the image for the game's background.
 * It also contains the method to render the background once the game begins.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class GameBG extends JComponent {

    private int positionX, positionY;
    private BufferedImage bgVisual;
    private boolean imageLoaded = false;

	public GameBG(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public void renderBG(Graphics2D g2d) {
        loadImage(); // Ensure the image is loaded before rendering
        int a = positionX;
        int b = positionY;
        g2d.drawImage(bgVisual, a, b, null);
    }

    private void loadImage() {
        if (!imageLoaded) {
            try {
                InputStream bgImports = getClass().getResourceAsStream("Game_BackDrop_Visuals.png");
                bgVisual = ImageIO.read(bgImports);
                imageLoaded = true;
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the IOException here
            }
        }
    }
}
