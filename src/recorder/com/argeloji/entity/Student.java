package com.argeloji.entity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Student implements Serializable {

	private String sID;
	
	public Student(String studentID)
	{
		sID = studentID;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
		oos.writeObject(sID);
        oos.flush();
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
		sID = (String)ois.readObject();
	}

	public void printID()
	{
		System.out.println("Student ID: " + sID);
	}
}
