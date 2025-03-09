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
 * This class is in charge of allowing a player to join the game and creating a Jframe for the player.
*/

public class GameStarter{
    
    public static void main(String[] args){
        
        GameFrame mainGF = new GameFrame();
        mainGF.connectToServer();
        mainGF.setUpGUI();

        
    }
}