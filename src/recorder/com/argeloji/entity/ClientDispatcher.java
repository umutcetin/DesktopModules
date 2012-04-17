package com.argeloji.entity;

import com.argeloji.cons.Enums;
import com.argeloji.messaging.MessengerClient;

public class ClientDispatcher {
	
	MessengerClient mc;
	
	public ClientDispatcher()
	{
		mc = new MessengerClient("127.0.0.1");
		mc.connect();
	}
	
	private void send(Enums.MessageType messageType, Object object)
	{
		
	}
}
