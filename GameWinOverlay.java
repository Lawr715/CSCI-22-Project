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
 * This class is a helper class in charge of importing the images to be displayed when a player wins.
 * It also contains all methods to manage and render the images.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;

public class GameWinOverlay extends JComponent {

    private int positionX, positionY;
    private BufferedImage p1WinVisual;
    private BufferedImage p2WinVisual;
    private boolean imageLoaded = false;

	public GameWinOverlay(int x, int y) {
        positionX = x;
        positionY = y;
    }

    public void renderP1Win(Graphics2D g2d) {
        loadImageP1(); // Ensure the image is loaded before rendering
        int a = positionX;
        int b = positionY;
        g2d.drawImage(p1WinVisual, a, b, null);
    }

    public void renderP2Win(Graphics2D g2d) {
        loadImageP2(); // Ensure the image is loaded before rendering
        int a = positionX;
        int b = positionY;
        g2d.drawImage(p2WinVisual, a, b, null);
    }

    private void loadImageP1() {
        if (!imageLoaded) {
            try {
                InputStream p1WinImports = getClass().getResourceAsStream("Game_P1WIN_Visuals.png");
                p1WinVisual = ImageIO.read(p1WinImports);
                imageLoaded = true;
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the IOException here
            }
        }
    }

    private void loadImageP2() {
        if (!imageLoaded) {
            try {
                InputStream p2WinImports = getClass().getResourceAsStream("Game_P2WIN_Visuals.png");
                p2WinVisual = ImageIO.read(p2WinImports);
                imageLoaded = true;
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the IOException here
            }
        }
    }
}
