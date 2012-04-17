package com.argeloji.entity;

import java.io.*;

public class Question implements Serializable {
	private String name;
	private byte type;
	private File file;
	private int fileLength;
	private String fileName;
	
	public Question()
	{
		name = "deneme";
		type = 3;
		file = new File("H:\\0262013193Probabilistic.pdf");
		fileLength = (int)file.length();
		fileName = file.getName();
	}
	
	public void print()
	{
		System.out.println(name);
		System.out.println(type);
		//System.out.println(file.toString());
	}
	
	private void writeObject(ObjectOutputStream oos) throws IOException
	{
		oos.defaultWriteObject();
		oos.writeByte(type);
		oos.writeObject(name);
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
		type = ois.readByte();
		name = (String)ois.readObject();
		fileLength = ois.readInt();
		fileName = (String)ois.readObject();
		//file = (File)ois.readObject();
		//save file to the disk
		byte[] mybytearray = new byte[fileLength];
		File newFile = new File("C:\\test\\" + fileName);
		newFile.setWritable(true);
        DataOutputStream bos = new DataOutputStream(new FileOutputStream(newFile));
        mybytearray = (byte[])ois.readObject();
        bos.write(mybytearray, 0, fileLength);
        bos.close();
	}
	
	public String toString()
	{
		return "name: " + name + " type: " + type;
	}
	
	public void printfile()
	{
		StringBuilder contents = new StringBuilder();
	    
	    try {
	      //use buffering, reading one line at a time
	      //FileReader always assumes default encoding is OK!
	      BufferedReader input =  new BufferedReader(new FileReader(file));
	      try {
	        String line = null; //not declared within while loop
	        /*
	        * readLine is a bit quirky :
	        * it returns the content of a line MINUS the newline.
	        * it returns null only for the END of the stream.
	        * it returns an empty String if two newlines appear in a row.
	        */
	        while (( line = input.readLine()) != null){
	          contents.append(line);
	          contents.append(System.getProperty("line.separator"));
	        }
	      }
	      finally {
	        input.close();
	      }
	    }
	    catch (IOException ex){
	      ex.printStackTrace();
	    }
	    
	    System.out.print("file: " + contents.toString());
	}
}
