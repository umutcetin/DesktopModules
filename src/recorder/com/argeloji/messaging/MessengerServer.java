package com.argeloji.messaging;

import static com.argeloji.cons.SocketMessengerConstants.SERVER_PORT;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.argeloji.cons.Enums;
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.Answer;
import com.argeloji.entity.FileContainer;
import com.argeloji.entity.Question;
import com.argeloji.entity.ServerDispatcher;
import com.argeloji.entity.Student;
import com.argeloji.entity.StudentConnectionNode;

public class MessengerServer implements Runnable, MessageListener {

	private ExecutorService serverExecutor;
	private LinkedList<StudentConnectionNode> _clientList;
	private ServerDispatcher _serverDispatcher;
	
	public MessengerServer(ServerDispatcher serverDispatcher)
	{
		_serverDispatcher = serverDispatcher;
	}
	
	public void run()
	{
		startServer();
	}
	
	// start server
	public void startServer()
	{
		// create executor for server runnables
		serverExecutor = Executors.newCachedThreadPool();
		_clientList = new LinkedList<StudentConnectionNode>();
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
				//_clientList.add(new StudentConnectionNode(clientSocket));
				//serverExecutor.execute(new MessageReceiver(this, clientSocket));
				serverExecutor.execute(new MessageReceiver(this, clientSocket));
				System.out.println("Connection received from " + clientSocket.getInetAddress());
				serverExecutor.execute(new MessageSender(clientSocket, MessageType.StudentConnectionRequestProcessedByServer, null));
			}
		}
		catch (BindException bex)
		{
			System.out.println("Sunucudaki " + SERVER_PORT + " baska bir uygulama tarafindan kullaniliyor");
		}
		catch (IOException ioex)
		{
			ioex.printStackTrace();
		}
	}
	
	public void sendAll()
	{
		for (int i = 0; i < _clientList.size(); i++)
		{
			System.out.println(i);
			serverExecutor.execute(new MessageSender(_clientList.get(i).getConnection()));
		}
	}
	
	public void sendAll(Enums.MessageType messageType, Object object)
	{
		for (int i = 0; i < _clientList.size(); i++)
		{
			if (!_clientList.get(i).connectionIsNull())
				serverExecutor.execute(new MessageSender(_clientList.get(i).getConnection(), messageType, object));
		}
	}
	
	//test icin
	private void listAllConnectedStudents()
	{
		for (int i = 0; i < _clientList.size(); i++)
		{
			System.out.println("conn ref: " + _clientList.get(i).getConnection().toString() + " studentID: " + _clientList.get(i).getStudentID());
		}		
	}
	
	public void messageReceived_SendStudentInfo(Student student, Socket connection)
	{
		System.out.println("_clientList.size: " + _clientList.size());
		for (int i = 0; i < _clientList.size(); i++)
		{
			//if (_clientList.get(i).getConnection().equals(connection))
			//{
			//	System.out.println("111");
			//	_clientList.get(i).setStudent(student);
			//	break;
			//}
			System.out.println("i: " + i);
			if (_clientList.get(i).getStudentID().compareTo(student.getStudentID()) == 0)
			{
				System.out.println("222");
				_clientList.get(i).setConnection(connection);
				_serverDispatcher.studentAlreadyConnected(student);
				return;
			}
		}		
		System.out.println("333");
		_clientList.add(new StudentConnectionNode(connection, student));

		listAllConnectedStudents();
		
		_serverDispatcher.messageReceived_StudentConnected(student);
	}
	
	public void messageReceived_StudentConnectionRequestProcessedByServer()
	{
		
	}

	public void messageReceived_SendQuestion(Question question)
	{
		_serverDispatcher.messageReceived_SendQuestion(question);
	}

	public void messageReceived_SendQuestionDurationIncrease(Calendar duration)
	{
		_serverDispatcher.messageReceived_SendQuestionDurationIncrease(duration);
	}

	public void messageReceived_SendAnswer(Answer answer)
	{
		_serverDispatcher.messageReceived_SendAnswer(answer);
	}

	public void messageReceived_SendFile(FileContainer fileContainer)
	{
		_serverDispatcher.messageReceived_SendFile(fileContainer);
	}
	
	public void close()
	{

	}
}
