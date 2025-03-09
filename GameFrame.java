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
 * This class is in charge of the JFrame to which the game will be displayed.
 * It is in charge of connecting the player to the server when they join the game.
*/

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;


public class GameFrame extends JFrame{
    
    private JFrame mainFrame;
    public GameCanvas mainGC;
    private Socket playerSocket;
    public int playerID;
    private Player p1, p2;
    public ReadFromServer rfsRunnable;
    public WriteToServer wtsRunnable;

    
    public GameFrame(){
        mainFrame = new JFrame();
        mainGC = new GameCanvas();
    }

    public void setUpGUI(){
        Container contentPane = mainFrame.getContentPane();
        mainGC.setPreferredSize(new Dimension(800,600));

        contentPane.add(mainGC,"Center");
        
        mainFrame.setTitle("Speedy Seedlings");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

    

    public class ReadFromServer implements Runnable{
        private DataInputStream dataIn;

        public ReadFromServer(DataInputStream in){
            dataIn = in;
            System.out.println("RFS Runnable created");
        }

        public void run(){
            try{
                while(true){
                    int p2x = dataIn.readInt();
                    int p2y = dataIn.readInt();
                    if(p2 != null){
                        p2.setX(p2x);
                        p2.setY(p2y);
                    }
                }
            }
            catch(IOException e){
                System.out.println("IOEX from rfs run");
            }
        }


        public void waitForStart(){
            try{
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);
                Thread readThread = new Thread (rfsRunnable);
                Thread writeThread = new Thread (wtsRunnable);
                readThread.start();
                writeThread.start();
            }
            catch(IOException e){
                System.out.println("IOex from waitstart");
            }

        }
    }

    public class WriteToServer implements Runnable{
        private DataOutputStream dataOut;

        public WriteToServer(DataOutputStream out){
            dataOut = out;
            System.out.println("WTS Runnable created");
        }

        public void run(){
            try{
                while(true){
                    if (p1 != null){
                        dataOut.writeInt(p1.getPositionX());
                        dataOut.writeInt(p1.getPositionY());
                        dataOut.flush();
                    }
                    try{
                        Thread.sleep(25);
                    }
                    catch(InterruptedException ex){
                        System.out.println("Interrupted");
                    }
                }
            }
            catch(IOException e){
                System.out.println("IOEx from run");
            }
            
        }
    }

    public void connectToServer(){
        try{
            playerSocket = new Socket("localhost",7890);
            DataInputStream in = new DataInputStream(playerSocket.getInputStream());
            DataOutputStream out = new DataOutputStream(playerSocket.getOutputStream());
            playerID = in.readInt();
            System.out.println("Welcome Player #" + playerID + " !");
            if (playerID == 1){
                System.out.println("Waiting for other players to join...");
            }
            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStart();
        
        }
        catch(IOException e){
            System.out.println("IOEx from connect");
        }
    }
}