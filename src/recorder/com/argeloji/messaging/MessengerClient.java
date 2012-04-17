package com.argeloji.messaging;

import static com.argeloji.cons.SocketMessengerConstants.SERVER_PORT;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.argeloji.cons.Enums;
import com.argeloji.entity.Student;

public class MessengerClient implements Runnable {

	private Socket clientSocket; // Socket for outgoing messages
	private String serverAddress; // MessengerServer address
	private boolean connected = false; // connection status
	private ExecutorService serverExecutor; // executor for server
	
	public MessengerClient(String address)
	{
		serverAddress = address; // store server address
		serverExecutor = Executors.newCachedThreadPool();
	}
	
	public void run()
	{
		
	}
	
	// connect to server and send messages to given MessageListener
	public void connect()
	{
		if (connected)
			return; // if already connected return immediately
		
		try
		{
			// open Socket connection to MessengerServer 
			clientSocket = new Socket(InetAddress.getByName(serverAddress), SERVER_PORT);
			// create Runnable for receiving incoming messages
			serverExecutor.execute(new MessageReceiver(clientSocket));
			// update connected flag
			connected = true;
		}
		catch (ConnectException ce)
		{
			System.out.println("Sunucu acik degil ya da sunucuya baglanan istemci sayisi ust siniri asilmis.");
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
		}
	}

	public void send()
	{
		if (clientSocket != null)
			serverExecutor.execute(new MessageSender(clientSocket));
		else
			System.out.println("Sunucu ile baglanti saglanamadigi icin bilgi gonderilemiyor.");
	}

	public void send(Enums.MessageType messageType, Student student)
	{
		if (clientSocket != null)
			serverExecutor.execute(new MessageSender(clientSocket, messageType, student));
		else
			System.out.println("Sunucu ile baglanti saglanamadigi icin bilgi gonderilemiyor.");
	}
	
	protected void finalize () {
		System.out.println("FINALIZER");
	}
}
