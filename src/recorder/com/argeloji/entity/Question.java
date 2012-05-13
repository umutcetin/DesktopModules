package com.argeloji.entity;

import java.io.*;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.argeloji.cons.Constants;
import com.argeloji.cons.Enums;
import com.argeloji.cons.Enums.AnswerType;
import com.argeloji.util.UniqueIdGenerator;

public class Question implements Serializable {
	private String _questionID;
	private AnswerType _answerType;
	private byte _numberOfChoices;
	private File _file;
	private int _fileLength;
	private String _fileName;
	private Calendar _duration;
	public Question(AnswerType answerType, byte numberOfChoices, Calendar duration, File questionFile)
	{
		_questionID = UniqueIdGenerator.getID();
		_answerType = answerType;
		_numberOfChoices = numberOfChoices;
		_duration = duration;
		_file = questionFile;
		_fileLength = (int)_file.length();
		_fileName = _file.getName();
		
		/*_time = new GregorianCalendar();
		_time.set(Calendar.HOUR, 1);
		_time.set(Calendar.MINUTE, 15);
		_time.set(Calendar.SECOND, 30);*/
		
		/*name = "deneme";
		answerType = 3;
		
		file = new File("H:\\0262013193Probabilistic.pdf");
		*/
	}
	
	public String getQuestionID()
	{
		return _questionID;
	}
	
	public AnswerType getAnswerType()
	{
		return _answerType;
	}
	
	public byte getNumberOfChoices()
	{
		return _numberOfChoices;
	}
	
	public File getFile()
	{
		return _file;
	}
	
	public Calendar getDuration()
	{
		return _duration;
	}
	
	public int getDurationHour()
	{
		return _duration.get(Calendar.HOUR);
	}
	
	public int getDurationMinute()
	{
		return _duration.get(Calendar.MINUTE);
	}
	
	public int getDurationSecond()
	{
		return _duration.get(Calendar.SECOND);
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
		oos.writeInt(_answerType.ordinal());
		oos.writeByte(_numberOfChoices);
		oos.writeObject(_duration);
		oos.writeInt(_fileLength);
		oos.writeObject(_fileName);
		//send file
		byte[] fileContent = new byte[(int) _file.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(_file)); 
        dis.read(fileContent, 0, fileContent.length);
        dis.close();
        oos.writeObject(fileContent);// -- (fileContent, 0, fileContent.length);
        oos.flush();
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
		_answerType = Enums.AnswerType.values()[ois.readInt()];
		_numberOfChoices = ois.readByte();
		_duration = (Calendar)ois.readObject();
		_fileLength = ois.readInt();
		_fileName = (String)ois.readObject();
		//save file to the disk
		byte[] mybytearray = new byte[_fileLength];
		File newFile = new File(Constants.SERVER_FILES_DIR + _fileName);
		newFile.setWritable(true);
        DataOutputStream bos = new DataOutputStream(new FileOutputStream(newFile));
        mybytearray = (byte[])ois.readObject();
        bos.write(mybytearray, 0, _fileLength);
        bos.close();
	}
	
	//test icin
	public void print()
	{
		System.out.println("answerType: " + getAnswerType() + 
				" numberOfChoices: " + getNumberOfChoices() +
				" duration: " + getDurationHour() + ":" + getDurationMinute() + ":" + getDurationSecond() + ":" + 
				" fileLength: " + _fileLength + 
				" fileName: " + _fileName);
	}
}
