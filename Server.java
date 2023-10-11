package server;

import java.io.*;
import java.net.*;

public class Server extends Thread{
	
	final static int SERVERPORT = 6789;
	
	public static void main(String[] args){
		
		try {
            ServerSocket welcomeSocket = new ServerSocket(SERVERPORT);
            System.out.println("Server is listening on port " + SERVERPORT);

            while (true) {
                System.out.println("Waiting for connection to socket...");
                Socket connectionSocket = welcomeSocket.accept();
                System.out.println("Successfully connected to " + connectionSocket.getInetAddress());

                // Start a new thread to handle client communication
                Thread clientThread = new Thread(new Socketrun(connectionSocket));
                clientThread.start();
            }
            
        } 
		catch (IOException e) {
            e.printStackTrace();
        }
		
	}
}

class Socketrun implements Runnable{
	
	private Socket socket = null;
	
	Socketrun(Socket s){
		this.socket = s;
	}
	
	public void run() {
		try {
			// Variables that receive sentence from clients and variables that send responses to clients
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outToClient = new DataOutputStream(socket.getOutputStream());

            String clientSentence;
            String modifiedSentence;

            while (true) {
            	
            	// Receive a sentence from Client
                clientSentence = inFromClient.readLine();
                
                // Disconnect if input is lost
                if (clientSentence == null) {
                    System.out.println("Client disconnected");
                    break;
                }
                
                // Send the client a sentence with the first letter changed to upper case and the rest to lower case.
                char firstChar = Character.toUpperCase(clientSentence.charAt(0));
                String restOfChars = clientSentence.substring(1).toLowerCase();
                modifiedSentence = firstChar + restOfChars;
                outToClient.writeBytes(modifiedSentence + '\n');
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}