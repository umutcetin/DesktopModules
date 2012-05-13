package com.argeloji.messaging;

import java.net.Socket;
import java.util.Calendar;

import com.argeloji.entity.Answer;
import com.argeloji.entity.FileContainer;
import com.argeloji.entity.Question;
import com.argeloji.entity.Student;

public interface MessageListener {

	public void messageReceived_StudentConnectionRequestProcessedByServer();

	public void messageReceived_SendStudentInfo(Student student, Socket connection);

	public void messageReceived_SendQuestion(Question question);

	public void messageReceived_SendQuestionDurationIncrease(Calendar duration);
	
	public void messageReceived_SendAnswer(Answer answer);

	public void messageReceived_SendFile(FileContainer fileContainer);
}
