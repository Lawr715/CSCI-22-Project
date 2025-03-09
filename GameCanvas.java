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
 * This class is in charge of all graphics related functionalities of the game which includes rendering the assets & characters to be displayed on the JFrame.
 * It also contains all methods associated with gameplay including movement, tracking of the scores, and declaring the winner.
*/


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.util.List;


public class GameCanvas extends JPanel implements ActionListener{

    private final int MOVE_AMOUNT = 5;
    private final Set<Integer> keysPressed = new HashSet<>();
    private final Timer timer;
    private Player p1, p2;
    private GameBG mainBG;
    private GameWinOverlay p1Win;
    private GameWinOverlay p2Win;
    private List<WaterSprite> waterSprites;
    private List<SunSprite> sunSprites;
    private int playerID;
    private int maxWater = 4;
    private int maxSun = 4;
    private int p1Water = 0, p1Sun = 0, p2Water = 0, p2Sun = 0;
    private boolean player1Win = false;
    private boolean player2Win = false;

    public GameCanvas(){
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keysPressed.add(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keysPressed.remove(e.getKeyCode());
            }
        });
        timer = new Timer(16, this);
        timer.start();
        try {
            initializeGameWinScreen();
            initializeGameBG();
            initializePlayers();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing players: " + e.getMessage());
        }
        initializeSprites();
    }

    private void initializeGameWinScreen() throws IOException {
        p1Win = new GameWinOverlay(0,0);
        p2Win = new GameWinOverlay(0,0);

    }

    private void initializeGameBG() throws IOException {
        mainBG = new GameBG(0, 0);
    }

    private void initializePlayers() throws IOException {

        if(playerID == 1){
            p1 = new Player(50, 50);
            p2 = new Player(150, 150);
        }
        else{
            p1 = new Player(50, 50);
            p2 = new Player(150, 150);
        }
    }

    private void initializeSprites(){
        waterSprites = new ArrayList<>();   
        sunSprites = new ArrayList<>();

        waterSprites.add(new WaterSprite(100, 100, 2, 2));
        waterSprites.add(new WaterSprite(200, 200, -2, 2));
        waterSprites.add(new WaterSprite(300, 300, 2, -2));

        sunSprites.add(new SunSprite(150, 110, 2, 2));
        sunSprites.add(new SunSprite(250, 210, -2, 2));
        sunSprites.add(new SunSprite(350, 310, 2, -2));
    }

    @Override
    public void addNotify() {
        super.addNotify();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
            updateGameState(e);
    }

    public void updateGameState(ActionEvent e) {
        int moveX1 = 0;
        int moveY1 = 0;
        for (int keyCode : keysPressed) {
            switch (keyCode) {
                case KeyEvent.VK_UP:
                    moveY1 -= MOVE_AMOUNT;
                    break;
                case KeyEvent.VK_DOWN:
                    moveY1 += MOVE_AMOUNT;
                    break;
                case KeyEvent.VK_LEFT:
                    moveX1 -= MOVE_AMOUNT;
                    break;
                case KeyEvent.VK_RIGHT:
                    moveX1 += MOVE_AMOUNT;
                    break;
            }
        }

        int moveX2 = 0;
        int moveY2 = 0;
        for (int keyCode : keysPressed) {
            switch (keyCode) {
                case KeyEvent.VK_W:
                    moveY2 -= MOVE_AMOUNT;
                    break;
                case KeyEvent.VK_S:
                    moveY2 += MOVE_AMOUNT;
                    break;
                case KeyEvent.VK_A:
                    moveX2 -= MOVE_AMOUNT;
                    break;
                case KeyEvent.VK_D:
                    moveX2 += MOVE_AMOUNT;
                    break;
            }
        }

        // Calculate the new positions for p1 and p2
        int newP1X = p1.getPositionX() + moveX1;
        int newP1Y = p1.getPositionY() + moveY1;
        int newP2X = p2.getPositionX() + moveX2;
        int newP2Y = p2.getPositionY() + moveY2;

        // Check for collision between p1 and p2
        Rectangle p1Bounds = new Rectangle(newP1X, newP1Y, p1.getVisualWidth(), p1.getVisualHeight());
        Rectangle p2Bounds = new Rectangle(newP2X, newP2Y, p2.getVisualWidth(), p2.getVisualHeight());
        
        if (!p1Bounds.intersects(p2Bounds)) {
            // Move p1 only if it doesn't collide with p2
            p1.movePlayer(moveX1, moveY1, getWidth(), getHeight());
        }
        if (!p2Bounds.intersects(p1Bounds)) {
            // Move p2 only if it doesn't collide with p1
            p2.movePlayer(moveX2, moveY2, getWidth(), getHeight());
        }


       
            // Move water sprites and check for collisions
            for (WaterSprite waterSprite : waterSprites) {
                waterSprite.moveSprite(getWidth(), getHeight());

                
                if (waterSprite.collidesWith(p1.getBounds())) {
                    p1Water++;
                    waterSprite.placeSpriteAtEdge(getWidth(), getHeight());
                }

                if (waterSprite.collidesWith(p2.getBounds())) {
                    p2Water++;
                    waterSprite.placeSpriteAtEdge(getWidth(), getHeight());
                }
            }
    
            // Move sun sprites and check for collisions
            for (SunSprite sunSprite : sunSprites) {
                sunSprite.moveSprite(getWidth(), getHeight());

                if (sunSprite.collidesWith(p1.getBounds())) {
                    p1Sun++;
                    sunSprite.placeSpriteAtEdge(getWidth(), getHeight());
                }

                if (sunSprite.collidesWith(p2.getBounds())) {
                    p2Sun++;
                    sunSprite.placeSpriteAtEdge(getWidth(), getHeight());
                }
            }

            if(p1Sun == maxSun && p1Water == maxWater){
                player1Win = true;
                System.out.println("Player 1 wins");
            }

            if(p2Sun == maxSun && p2Water == maxWater){
                player2Win = true;
                System.out.println("Player 2 wins");
            }


        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        mainBG.renderBG(g2d);

        p1.renderCharac(g2d);
        p2.renderCharac(g2d);

        // Render water sprites
        for (WaterSprite waterSprite : waterSprites) {
            waterSprite.renderWater(g2d);
        }
 
        // Render sun sprites
        for (SunSprite sunSprite : sunSprites) {
            sunSprite.renderSun(g2d);
        }

        // Display collsision counters
        Font displayFont = new Font("Upheaval", Font.BOLD,15);
        g2d.setColor(Color.BLACK);
        g2d.setFont(displayFont);
        g2d.drawString("Player 1  | " + p1Water + " Water Sprites and " + p1Sun + " Sun Sprites Collected", 10, 20);
        g2d.drawString("Player 2  | " + p2Water + " Water Sprites and " + p2Sun + " Sun Sprites Collected", 10, 40);


            //P1 WINS
            if (player1Win == true){
                System.out.println("Player 1 wins");
                p1Win.renderP1Win(g2d);
                repaint();
            }

            //P2 WINS
            if (player2Win == true){
                System.out.println("Player 2 wins");
                p2Win.renderP2Win(g2d);
                repaint();
            }

    }

}