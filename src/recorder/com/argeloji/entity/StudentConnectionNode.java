package com.argeloji.entity;

import java.net.Socket;

public class StudentConnectionNode {

	private Socket _connection;
	private Student _student;
	
	public StudentConnectionNode(Socket connection)
	{
		_connection = connection;
	}
	
	public StudentConnectionNode(Socket connection, Student student)
	{
		_connection = connection;
		_student = student;
	}
	
	public Socket getConnection()
	{
		return _connection;
	}
	
	public void setConnection(Socket connection)
	{
		_connection = connection;
	}
	
	public void setStudent(Student student)
	{
		_student = student;
	}
	
	/*
	public Student getStudent()
	{
		return _student;
	}
	*/
	
	public String getStudentID()
	{
		if (_student == null)
			return "null";
		else
			return _student.getStudentID();
	}
	
	public boolean connectionIsNull()
	{
		if (_connection == null)
			return true;
		else
			return false;
	}
	
	public boolean studentIsNull()
	{
		if (_student == null)
			return true;
		else
			return false;
	}
}
