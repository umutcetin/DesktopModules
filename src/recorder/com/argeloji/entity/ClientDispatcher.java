package com.argeloji.entity;

import java.util.Calendar;

import javax.swing.JOptionPane;

import com.argeloji.client.DecodeAndPlayVideo;
import com.argeloji.client.QuizClientDesktop;
import com.argeloji.client.ShowImageWindow;
import com.argeloji.cons.Enums;
import com.argeloji.cons.Enums.FileType;
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.cons.SocketMessengerConstants;
import com.argeloji.messaging.MessengerClient;
import com.sun.media.ui.MessageBox;

public class ClientDispatcher {

	private Student _student;
	private MessengerClient mc;
	
	private static ClientDispatcher theInstance;
	// Ýhtiyaç anýnda yaratýlacak nesne

	public static ClientDispatcher getInstance(Student student) {
		// Dýþarýdan nesne almak için bu metodu kullanacaðýz.
		// Eðer nesnemiz yaratýlmamýþ ise yaratalým.
		if (theInstance == null) {
			theInstance = new ClientDispatcher(student);
		}
		// Þu an ya da önceden yaratýlan nesneyi döndürelim.
		return theInstance;
	}
	
	public static ClientDispatcher getInstance() throws Exception {
		// Dýþarýdan nesne almak için bu metodu kullanacaðýz.
		// Eðer nesnemiz yaratýlmamýþ ise exception.
		if (theInstance == null) {
			throw new Exception("nesne yok"); 
		}
		// Þu an ya da önceden yaratýlan nesneyi döndürelim.
		return theInstance;
	}
	
	private ClientDispatcher(Student student) {		
		_student = student;
		mc = new MessengerClient(this, SocketMessengerConstants.SERVER_ADDRESS);
		Thread thread = new Thread(mc);
		thread.start();
		mc.connect();
		// this.send(MessageType.StudentConnectionRequest, _student);
	}
	
	public Student getStudent()
	{
		return _student;
	}

	public void send(Enums.MessageType messageType, Object object) {
		mc.send(messageType, object);
	}

	public void messageReceived_StudentConnected(Student student) {
		student.printID();
	}

	public void messageReceived_StudentConnectionRequestProcessedByServer() {
		System.out.println("client dispatcher");
		mc.send(MessageType.SendStudentInfo, _student);
	}

	public void messageReceived_SendQuestion(Question question) {
		question.print();

		QuizClientDesktop qc = new QuizClientDesktop(question);
		qc.setVisible(true);
	}

	public void messageReceived_SendQuestionDurationIncrease(Calendar duration) {
		System.out
				.println(duration.get(Calendar.HOUR)
						+ duration.get(Calendar.MINUTE)
						+ duration.get(Calendar.SECOND));

	}

	public void messageReceived_SendAnswer(Answer answer) {
		answer.print();
	}

	public void messageReceived_SendFile(FileContainer fileContainer) {
		if (fileContainer.getFileType().equals(FileType.Image))
		{
			System.out.println("Received: image");
			ShowImageWindow s= new ShowImageWindow(fileContainer.getFilePath());
			s.setVisible(true);
		}
		if (fileContainer.getFileType().equals(FileType.Video))
		{
			System.out.println("Received: Video");
			DecodeAndPlayVideo dc=new DecodeAndPlayVideo(fileContainer.getFilePath());
		}
		if (fileContainer.getFileType().equals(FileType.Other))
		{
			System.out.println("Received: Other");
			JOptionPane.showMessageDialog(null,"Dosya Alýndý: "+ fileContainer.print());
		}
		fileContainer.print();
	}

}
