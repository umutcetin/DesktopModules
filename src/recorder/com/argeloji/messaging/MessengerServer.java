package com.argeloji.messaging;

import static com.argeloji.cons.SocketMessengerConstants.SERVER_PORT;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MessengerServer implements Runnable {

	private ExecutorService serverExecutor;
	private LinkedList<Socket> clientList;
	
	public void run()
	{
		startServer();
	}
	
	// start server
	public void startServer()
	{
		// create executor for server runnables
		serverExecutor = Executors.newCachedThreadPool();
		clientList = new LinkedList<Socket>();
		try
		{
			// create ServerSocket for incoming connections 
			ServerSocket serverSocket = new ServerSocket(SERVER_PORT, 100);
			System.out.println("Server listening on port 12345");
			// listen for clients constantly
			while (true)
			{
				// accept new client connections
				Socket clientSocket = serverSocket.accept();
				// create MessageReceiver for receiving messages from client
				clientList.add(clientSocket);
				//serverExecutor.execute(new MessageReceiver(this, clientSocket));
				serverExecutor.execute(new MessageReceiver(clientSocket));
				System.out.println("Connection received from " + clientSocket.getInetAddress());
			}
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
		}
	}
	
	public void sendAll()
	{
		for (int i = 0; i < clientList.size(); i++)
		{
			System.out.println(i);
			serverExecutor.execute(new MessageSender(clientList.get(i)));
		}
	}
}
