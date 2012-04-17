package com.argeloji.messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;

import com.argeloji.cons.Enums;
import com.argeloji.entity.*;

public class MessageReceiver implements Runnable {

	protected Socket connection;
	private boolean keepListening = true; // when false ends runnable
	
	public MessageReceiver(Socket clientSocket)
	{
		connection = clientSocket;
	}
	
	public void run()
	{
		receive();
	}
	
	private void receive()
	{
		Enums.MessageType mt;
		while (keepListening)
		{
			try {
				ObjectInputStream instream = new ObjectInputStream(connection.getInputStream());
				mt = Enums.MessageType.values()[instream.readInt()];
				switch (mt)
				{
				case StudentConnected:
					Student s = (Student)instream.readObject();
					s.printID();
				}
			} 
			catch (SocketException se)
			{
				this.stopListening();
				System.out.println("Baglanti sona erdi");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("MessageReceiver, IOEx");
				this.stopListening();
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("MessageReceiver, ClassNotFoundEx");
				e.printStackTrace();
			}
		}
	}
	
	/*
	private void receive()
	{
		Question q;
		while (keepListening)
		{
			try {
				ObjectInputStream instream = new ObjectInputStream(connection.getInputStream());
				//System.out.println("byte: " + instream.readByte());
				q = (Question)instream.readObject();
				System.out.println("Alinan: " + q.toString());
				//q.printfile();
			} 
			catch (SocketException se)
			{
				this.stopListening();
				System.out.println("Baglanti sona erdi");
			}
			catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("MessageReceiver, IOEx");
				this.stopListening();
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println("MessageReceiver, ClassNotFoundEx");
				e.printStackTrace();
			}
		}
	}
	*/
	
	// stop listening for incoming messages
	public void stopListening()
	{
		keepListening = false;
	}
}
