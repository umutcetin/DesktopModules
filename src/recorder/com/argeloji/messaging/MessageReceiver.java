package com.argeloji.messaging;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.net.SocketException;
import java.util.Calendar;

import com.argeloji.cons.Enums;
import com.argeloji.entity.*;

public class MessageReceiver implements Runnable {

	protected Socket _connection;
	private boolean _keepListening = true; // when false ends runnable
	private MessageListener _messageListener;
	
	public MessageReceiver(MessageListener messageListener, Socket clientSocket)
	{
		_connection = clientSocket;
		_messageListener = messageListener;
	}
	
	public void run()
	{
		receive();
	}
	
	private void receive()
	{
		Enums.MessageType mt;
		while (_keepListening)
		{
			try {
				ObjectInputStream instream = new ObjectInputStream(_connection.getInputStream());
				mt = Enums.MessageType.values()[instream.readInt()];
				switch (mt)
				{
				//case StudentConnectionRequest:
				//	Student s = (Student)instream.readObject();
				//	_messageListener.messageReceived_StudentConnectionRequest(s, _connection);
				//	break;
				case StudentConnectionRequestProcessedByServer:
					System.out.println("receiver case StudentConnectionRequestProcessedByServer:");
					_messageListener.messageReceived_StudentConnectionRequestProcessedByServer();
					break;
				case SendStudentInfo:
					Student s = (Student)instream.readObject();
					_messageListener.messageReceived_SendStudentInfo(s, _connection);
					break;
				case SendQuestion:
					Question q = (Question)instream.readObject();
					_messageListener.messageReceived_SendQuestion(q);
					break;
				case SendQuestionDurationIncrease:
					Calendar c = (Calendar)instream.readObject();
					_messageListener.messageReceived_SendQuestionDurationIncrease(c);
					break;
				case SendAnswer:
					Answer a = (Answer)instream.readObject();
					_messageListener.messageReceived_SendAnswer(a);
					break;
				case SendFile:
					FileContainer fc = (FileContainer)instream.readObject();
					_messageListener.messageReceived_SendFile(fc);
					break;
				}
			} 
			catch (SocketException se)
			{
				this.stopListening();
				if (_messageListener.getClass() == MessengerServer.class)
					System.out.println("ClassType: server");
				else if (_messageListener.getClass() == MessengerClient.class)
					System.out.println("ClassType: client");
				else
					System.out.println("eslesmedi");

				System.out.println(_connection.getInetAddress() + " Baglanti sona erdi");
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
		_keepListening = false;
	}
}
