package com.argeloji.messaging;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import com.argeloji.cons.Enums;
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.*;

public class MessageSender implements Runnable {

	private Socket clientSocket; // Socket over which to send message
	private MessageType mt;
	private Student s;
	
	public MessageSender(Socket socket)
	{
		clientSocket = socket; // store Socket for client 
	}
	
	public MessageSender(Socket socket, Enums.MessageType messageType, Object object)
	{
		clientSocket = socket; // store Socket for client
		mt = messageType;
		switch (mt)
		{
		case StudentConnected:
			s = (Student)object;
		}
		
	}
	
	public void run()
	{
		send();
	}
	
	private void send()
	{
		switch (mt)
		{
		case StudentConnected:
			if (s != null)
				this.send(mt, s);
		}
	}
	
	/*
	private void send()
	{
		Question q = new Question();
		OutputStream os;
		byte b = 5;
		try {
			os = clientSocket.getOutputStream();
            ObjectOutputStream objectStream = new ObjectOutputStream(os);
            //objectStream.writeByte(b);// -- (fileContent, 0, fileContent.length);
            
            System.out.println("Gönderilen: " + q.toString());
            objectStream.writeObject(q);
            
            objectStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	*/
	
	private void send(Enums.MessageType messageType, Student student)
	{
		OutputStream os;
		try 
		{
				os = clientSocket.getOutputStream();
	            ObjectOutputStream objectStream = new ObjectOutputStream(os);
	            objectStream.writeInt(messageType.ordinal());
	            System.out.println("Gönderilen: " + messageType.ordinal());
	            objectStream.writeObject(student);
	            objectStream.flush();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
