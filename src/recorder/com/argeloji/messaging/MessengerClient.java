package com.argeloji.messaging;

import static com.argeloji.cons.SocketMessengerConstants.SERVER_PORT;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.argeloji.cons.Enums;
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.Answer;
import com.argeloji.entity.ClientDispatcher;
import com.argeloji.entity.FileContainer;
import com.argeloji.entity.Question;
import com.argeloji.entity.Student;

public class MessengerClient implements Runnable, MessageListener {

	private Socket _clientSocket; // Socket for outgoing messages
	private String _serverAddress; // MessengerServer address
	private boolean _connected = false; // connection status
	private ExecutorService _serverExecutor; // executor for server
	private ClientDispatcher _clientDispatcher;
	
	public MessengerClient(ClientDispatcher clientDispatcher, String address)
	{
		_clientDispatcher = clientDispatcher;
		_serverAddress = address; // store server address
		_serverExecutor = Executors.newCachedThreadPool();
	}
	
	public void run()
	{
		//this.connect();
	}
	
	// connect to server and send messages to given MessageListener
	public void connect()
	{
		if (_connected)
			return; // if already connected return immediately
		
		try
		{
			// open Socket connection to MessengerServer 
			_clientSocket = new Socket(InetAddress.getByName(_serverAddress), SERVER_PORT);
			// create Runnable for receiving incoming messages
			_serverExecutor.execute(new MessageReceiver(this, _clientSocket));
			// update connected flag
			_connected = true;
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
		if (_clientSocket != null)
			_serverExecutor.execute(new MessageSender(_clientSocket));
		else
			System.out.println("Sunucu ile baglanti saglanamadigi icin bilgi gonderilemiyor.");
	}

	public void send(Enums.MessageType messageType, Object object)
	{
		if (_clientSocket != null)
			_serverExecutor.execute(new MessageSender(_clientSocket, messageType, object));
		else
			System.out.println("Sunucu ile baglanti saglanamadigi icin bilgi gonderilemiyor.");
	}
	
	public void messageReceived_StudentConnectionRequest(Student student, Socket connection)
	{
		_clientDispatcher.messageReceived_StudentConnected(student);
	}
	
	public void messageReceived_StudentConnectionRequestProcessedByServer()
	{
		System.out.println("mes client");
		_clientDispatcher.messageReceived_StudentConnectionRequestProcessedByServer();
	}
	
	public void messageReceived_SendStudentInfo(Student student, Socket connection)
	{
		
	}

	public void messageReceived_SendQuestion(Question question)
	{
		_clientDispatcher.messageReceived_SendQuestion(question);
	}
	
	public void messageReceived_SendQuestionDurationIncrease(Calendar duration)
	{
		_clientDispatcher.messageReceived_SendQuestionDurationIncrease(duration);
	}

	public void messageReceived_SendAnswer(Answer answer)
	{
		_clientDispatcher.messageReceived_SendAnswer(answer);
	}
	
	public void messageReceived_SendFile(FileContainer fileContainer)
	{
		_clientDispatcher.messageReceived_SendFile(fileContainer);
	}
	
	public void close()
	{
		try {
			if ((_clientSocket != null) && (_connected))
			{
				_connected = false;
				_clientSocket.close();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
