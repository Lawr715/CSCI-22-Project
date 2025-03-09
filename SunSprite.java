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
 * This class is a helper class in charge of importing the image for one of the elemental sprites (Sun).
 * It also contains all methods to render the Sun Sprite based on what is happening in the game.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.util.Random;

public class SunSprite extends JComponent {

    public int positionX, positionY, dx, dy, width, height;
    private BufferedImage sunSpriteVisual;
    private Random random = new Random();
    private boolean imageLoaded = false;

    public SunSprite(int x, int y, int dx, int dy) {
        positionX = x;
        positionY = y;
        this.dx = dx;
        this.dy = dy;
        loadObjImage();
        width = sunSpriteVisual.getWidth();
        height = sunSpriteVisual.getHeight();
    }

    private void loadObjImage() {
        if (!imageLoaded) {
            try {
                InputStream sunSpImports = getClass().getResourceAsStream("Sprite_Sun_Visuals.png");
                sunSpriteVisual = ImageIO.read(sunSpImports);
                imageLoaded = true;
            } catch (IOException e) {
                System.out.println("Error importing");
                e.printStackTrace();
            }
        }
    }

    public void renderSun(Graphics2D g2d) {
        g2d.drawImage(sunSpriteVisual, positionX, positionY, null);
    }

    public void moveSprite(int panelWidth, int panelHeight) {
        positionX += dx;
        positionY += dy;

        if (positionX < 0 || positionX > panelWidth - width || positionY < 0 || positionY > panelHeight - height) {
            placeSpriteAtEdge(panelWidth, panelHeight);
        }
    }

    public void placeSpriteAtEdge(int panelWidth, int panelHeight) {
        int edge = random.nextInt(4);
        switch (edge) {
            case 0: // Top edge
                positionX = random.nextInt(Math.max(panelWidth - width, 1));
                positionY = 0;
                dx = random.nextInt(2 * 5 + 1) - 5;
                dy = 5;
                break;
            case 1: // Bottom edge
                positionX = random.nextInt(Math.max(panelWidth - width, 1));
                positionY = panelHeight - height;
                dx = random.nextInt(2 * 5 + 1) - 5;
                dy = -5;
                break;
            case 2: // Left edge
                positionX = 0;
                positionY = random.nextInt(Math.max(panelHeight - height, 1));
                dx = 5;
                dy = random.nextInt(2 * 5 + 1) - 5;
                break;
            case 3: // Right edge
                positionX = panelWidth - width;
                positionY = random.nextInt(Math.max(panelHeight - height, 1));
                dx = -5;
                dy = random.nextInt(2 * 5 + 1) - 5;
                break;
        }
    }

    public boolean collidesWith(Rectangle rect) {
        return rect.intersects(positionX, positionY, width, height);
    }
}
