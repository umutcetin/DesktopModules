package com.argeloji.cons;

public class SocketMessengerConstants {
	// address for multicast datagrams
	public static final String MULTICAST_ADDRESS = "224.0.0.1";
	// port for listening for multicast datagrams
	public static final int MULTICAST_LISTENING_PORT = 5555;
	// port for sending multicast datagrams
	public static final int MULTICAST_SENDING_PORT = 5554;
	// port for socket connections to MessengerServer
	public static final int SERVER_PORT = 12345;
	// String that indicates disconnect
	public static final String DISCONNECT_STRING = "DISCONNECT";
	// message size (in bytes)
	public static final int MESSAGE_SIZE = 512;
}
