package com.argeloji.entity;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;

import com.argeloji.cons.Constants;
import com.argeloji.cons.Enums;
import com.argeloji.cons.Enums.AnswerType;

public class Answer implements Serializable {

	private String _questionID;
	private String _studentID;
	private AnswerType _answerType;
	LinkedList<Object> _answer;
	
	public Answer(String questionID, String studentID, AnswerType answerType, LinkedList<Object> answer)
	{
		_questionID = questionID;
		_studentID = studentID;
		_answerType = answerType;
		_answer = answer;
	}
	
	public String getQuestionID()
	{
		return _questionID;
	}
	
	public String getStudentID()
	{
		return _studentID;
	}
	
	public AnswerType getAnswerType()
	{
		return _answerType;
	}
	
	public LinkedList<Object> getAnswer()
	{
		return _answer;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
		oos.writeObject(_questionID);
		oos.writeObject(_studentID);
		oos.writeInt(_answerType.ordinal());
		oos.writeObject(_answer);
        oos.flush();
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
		_questionID = (String)ois.readObject();
		_studentID = (String)ois.readObject();
		_answerType = Enums.AnswerType.values()[ois.readInt()];
		_answer = (LinkedList<Object>)ois.readObject();
	}
	
	//test icin
	public void print()
	{
		System.out.println("QuestionID: " + getQuestionID() +
				" StudentID: " + getStudentID() +
				" AnswerType: " + getAnswerType());
	}
}
