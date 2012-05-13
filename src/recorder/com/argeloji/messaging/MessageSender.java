package com.argeloji.messaging;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Calendar;

import com.argeloji.cons.Enums;
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.*;

public class MessageSender implements Runnable {

	private Socket clientSocket; // Socket over which to send message
	private MessageType _messageType;
	private Object _object;
	
	public MessageSender(Socket socket)
	{
		clientSocket = socket; // store Socket for client 
	}
	
	public MessageSender(Socket socket, Enums.MessageType messageType, Object object)
	{
		clientSocket = socket; // store Socket for client
		_messageType = messageType;
		_object = object;
	}
	
	public void run()
	{
		send();
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
	
	private void send()
	{
		OutputStream os;
		try 
		{
				os = clientSocket.getOutputStream();
	            ObjectOutputStream objectStream = new ObjectOutputStream(os);
	            objectStream.writeInt(_messageType.ordinal());
	            System.out.println("Gönderilen: " + _messageType.ordinal());

	    		switch (_messageType)
	    		{
	    		//case StudentConnectionRequest:
		        //    objectStream.writeObject((Student)_object);
	    		//	break;
	    		case StudentConnectionRequestProcessedByServer:
	    			System.out.println("sender case StudentConnectionRequestProcessedByServer:");
	    			break;
	    		case SendStudentInfo:
		            objectStream.writeObject((Student)_object);
	    			break;
	    		case SendQuestion:
		            objectStream.writeObject((Question)_object);
	    			break;
	    		case SendQuestionDurationIncrease:
		            objectStream.writeObject((Calendar)_object);
	    			break;
	    		case SendAnswer:
		            objectStream.writeObject((Answer)_object);
	    			break;
	    		case SendFile:
		            objectStream.writeObject((FileContainer)_object);
	    			break;
	    		}
	            objectStream.flush();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
