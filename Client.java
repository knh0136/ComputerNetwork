package client;

import java.net.*;
import java.io.*;

public class Client {
	
	final static String SERVERIP = "127.0.0.1";
	final static int SERVERPORT = 6789;
	
	
	public static void main(String[] args){
		try {
			
			String sentence;
			String modifiedSentence;
			
			// Starting socket communication
			Socket clientSocket = new Socket(SERVERIP, SERVERPORT);
			System.out.println("Socket is connected.");
			
			// Variables to receive user(client) input
			BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
			
			// Variables for sending sentence to server
			DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
			
			// Variables to accept response from Server
			BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
			
			while (true) {
				System.out.println("Enter a sentence.");
                // Receive sentences from the user and send them to the server
                sentence = inFromUser.readLine();
                if (sentence == null || sentence.isEmpty()) {
                    // Exit the loop if the user enters an empty line
                    break;
                }
                outToServer.writeBytes(sentence + '\n');

                // Receive a response from the server and print it out
                modifiedSentence = inFromServer.readLine();
                System.out.println("FROM SERVER: " + modifiedSentence);
            }
			
			clientSocket.close();
		}
		catch(UnknownHostException e) {
			e.printStackTrace();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
}
