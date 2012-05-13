package com.argeloji.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Student implements Serializable {

	private String _studentID;
	
	public Student(String studentID)
	{
		_studentID = studentID;
	}
	
	public String getStudentID()
	{
		return _studentID;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
		oos.writeObject(_studentID);
        oos.flush();
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
		_studentID = (String)ois.readObject();
	}

	public void printID()
	{
		System.out.println("Student ID: " + _studentID);
	}
}
