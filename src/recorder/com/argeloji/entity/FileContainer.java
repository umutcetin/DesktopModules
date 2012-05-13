package com.argeloji.entity;

import java.io.*;

import com.argeloji.cons.Enums;
import com.argeloji.cons.Enums.FileType;

public class FileContainer implements Serializable {

	private File _file;
	private int _fileLength;
	private String _fileName;
	private FileType _fileType;
	
	public FileContainer(File newFile, FileType fileType)
	{
		_file = newFile;
		_fileLength = (int)_file.length();
		_fileName = _file.getName();
		_fileType = fileType;
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
		oos.writeInt(_fileLength);
		oos.writeObject(_fileName);
		oos.writeInt(_fileType.ordinal());
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
		_fileLength = ois.readInt();
		_fileName = (String)ois.readObject();
		_fileType = Enums.FileType.values()[ois.readInt()];
		//save file to the disk
		byte[] mybytearray = new byte[_fileLength];
		File newFile = new File("C:\\test\\" + _fileName);
		newFile.setWritable(true);
        DataOutputStream bos = new DataOutputStream(new FileOutputStream(newFile));
        mybytearray = (byte[])ois.readObject();
        bos.write(mybytearray, 0, _fileLength);
        bos.close();
	}
	
	public FileType getFileType()
	{
		return _fileType;
	}
	
	public String getFileName()
	{
		return _fileName;
	}
	
	public String getFilePath()
	{
		return "C:\\test\\" + _fileName;
	}
	
	//test icin
	public String print()
	{
		return _fileName + " - " + _fileLength + " - " + _fileType;
	}
}