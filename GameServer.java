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
 * This class is in charge of the game server to which players will connect to in order to play the game.
 * It keeps track of the number of players joining and manages the input and output from the server to the player.
*/

import java.io.*;
import java.net.*;

public class GameServer{
    
    private ServerSocket gameSS;
    private Socket playerSocketA, playerSocketB;
    private ReadFromClient p1ReadRunnable, p2ReadRunnable;
    private WriteToClient p1WriteRunnable, p2WriteRunnable;
    private int curNumPlayers, maxNumPlayers;
    private int p1x, p1y, p2x, p2y;


    public GameServer(){
        System.out.println("Game Server Initiating");
        curNumPlayers = 0;
        maxNumPlayers = 2;

        p1x = 50;
        p1y = 50;
        p2x = 150;
        p2x = 150;

        try{
            gameSS = new ServerSocket(7890);
        }
        catch(IOException e){
            System.out.println("IOEx from gameserv");
        }
    }

    public void acceptConnections(){
        try{
            System.out.println("Waiting for players...");

            while(curNumPlayers < maxNumPlayers){
                Socket s = gameSS.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                
                curNumPlayers++;
                out.writeInt(curNumPlayers);
                System.out.println("Player #" + curNumPlayers + "has joined!");

                ReadFromClient rfc = new ReadFromClient(curNumPlayers, in);
                WriteToClient wtc = new WriteToClient(curNumPlayers, out);
            
                if(curNumPlayers == 1){
                    playerSocketA = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                }
                else{
                    playerSocketB = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    p1WriteRunnable.sendStart();
                    p2WriteRunnable.sendStart();

                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();
                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }
            }
        }
        catch(IOException e){
            System.out.println("IOEx from cons");
        }
    }

    private class ReadFromClient implements Runnable{
        private int playerID;
        private DataInputStream dataInput;

        public ReadFromClient(int pid, DataInputStream in){
            playerID = pid;
            dataInput = in;
            System.out.println("RFC " + playerID + " Runnable active.");

        }

        public void run(){
            try{
                while(true){
                    if(playerID == 1){
                        p1x = dataInput.readInt();
                        p1y = dataInput.readInt();
                    }
                    else{
                        p2x = dataInput.readInt();
                        p2y = dataInput.readInt();
                    }
                }
            }
            catch(IOException e){
                System.out.println("IOEx from rfc");
            }
        }

    }

    private class WriteToClient implements Runnable{
        private int playerID;
        private DataOutputStream dataOutput;

        public WriteToClient(int pid, DataOutputStream out){
            playerID = pid;
            dataOutput= out;
            System.out.println("WTC " + playerID + " Runnable active.");
        }

        public void run(){
            try{
                while(true){
                    if(playerID == 1) {
                        dataOutput.writeInt(p2x);
                        dataOutput.writeInt(p2x);
                        dataOutput.flush();
                    }
                    else {
                        dataOutput.writeInt(p1x);
                        dataOutput.writeInt(p1x);
                        dataOutput.flush();
                    }
                    try{
                        Thread.sleep(50);
                    }
                    catch(InterruptedException ex){
                        System.out.println("IntEx from wtc run");
                    }
                }
            }
            catch(IOException e){
                System.out.println("IOEx from wtc run");
            }
        }

        public void sendStart(){
            try{
                dataOutput.writeUTF("Players Complete. Ready!");
            }
            catch(IOException e){
                System.out.println("IOEx from start msg");
            }
        }


    }


    public static void main(String[] args){
        
        GameServer mainGameServer = new GameServer();
        mainGameServer.acceptConnections();
    }
}