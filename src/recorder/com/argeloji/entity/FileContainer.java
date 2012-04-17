package com.argeloji.entity;

import java.io.*;

public class FileContainer implements Serializable {

	private File file;
	private int fileLength;
	private String fileName;
	
	public FileContainer(File newFile)
	{
		file = newFile;
		fileLength = (int)file.length();
		fileName = file.getName();
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
		oos.writeInt(fileLength);
		oos.writeObject(fileName);
		//send file
		byte[] fileContent = new byte[(int) file.length()];
        DataInputStream dis = new DataInputStream(new FileInputStream(file)); 
        dis.read(fileContent, 0, fileContent.length);
        dis.close();
        oos.writeObject(fileContent);// -- (fileContent, 0, fileContent.length);
        oos.flush();
	}
	
	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException
	{
		ois.defaultReadObject();
		fileLength = ois.readInt();
		fileName = (String)ois.readObject();
		//save file to the disk
		byte[] mybytearray = new byte[fileLength];
		File newFile = new File("C:\\test\\" + fileName);
		newFile.setWritable(true);
        DataOutputStream bos = new DataOutputStream(new FileOutputStream(newFile));
        mybytearray = (byte[])ois.readObject();
        bos.write(mybytearray, 0, fileLength);
        bos.close();
	}
}