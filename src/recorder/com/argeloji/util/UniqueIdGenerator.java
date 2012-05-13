package com.argeloji.util;

import java.util.UUID;

public class UniqueIdGenerator {

	public static String getID()
	{
		try
		{
			return UUID.randomUUID().toString();
		}
		catch (Exception ex)
		{
			System.out.println("Unique id olusturulurken hata olustu.");
			return null;
		}
	}
	
	public static final void main(String... aArgs){
		System.out.println(getID());
	}
}
