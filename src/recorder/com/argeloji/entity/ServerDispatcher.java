package com.argeloji.entity;

import java.io.IOException;
import java.util.Calendar;

import com.argeloji.cons.Enums;
import com.argeloji.messaging.MessengerServer;
import com.argeloji.server.AttendanceFrame;
import com.argeloji.server.BarChartDemo4;
import com.argeloji.server.SwingExample;

public class ServerDispatcher {

	MessengerServer ms;
	
	AttendanceFrame af;
	
	BarChartDemo4 bc;
	
	private static ServerDispatcher theInstance;
	// �htiya� an�nda yarat�lacak nesne

	public static ServerDispatcher getInstance() {
		// D��ar�dan nesne almak i�in bu metodu kullanaca��z.
		// E�er nesnemiz yarat�lmam�� ise yaratal�m.
		if (theInstance == null) {
			theInstance = new ServerDispatcher();
		}
		// �u an ya da �nceden yarat�lan nesneyi d�nd�relim.
		return theInstance;
	}
	
	private ServerDispatcher()
	{
		ms = new MessengerServer(this);
		Thread thread = new Thread(ms);
		thread.start();
	}

	public void send(Enums.MessageType messageType, Object object)
	{
		ms.sendAll(messageType, object);
	}

	public void messageReceived_StudentConnected(Student student)
	{
		student.printID();	
		af.isHere(student.getStudentID());		
	}
	
	public void messageReceived_SendQuestion(Question question)
	{
		question.print();
	}
	
	public void messageReceived_SendQuestionDurationIncrease(Calendar duration)
	{
		System.out.println(duration.get(Calendar.HOUR) + ":" + duration.get(Calendar.MINUTE) + ":" + duration.get(Calendar.SECOND));
	}
	
	public void messageReceived_SendAnswer(Answer answer)
	{
		answer.print();
		String secenek=answer.getAnswer().get(0).toString();
		System.out.println(secenek);
		bc.updateGraphics(secenek);
		
//		try {
//			SwingExample se= new SwingExample();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	public void studentAlreadyConnected(Student student)
	{
		System.out.println("Student with ID " + student.getStudentID() + " is already connected");
	}
	
	public void messageReceived_SendFile(FileContainer fileContainer)
	{
		fileContainer.print();
	}
	
	public void register(AttendanceFrame af)
	{
		this.af= af;
	}
	
	public void registerGraphics(BarChartDemo4 bc)
	{
		this.bc = bc;
	}
}
