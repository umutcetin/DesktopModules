
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.Student;
import com.argeloji.messaging.MessengerClient;

public class Client1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MessengerClient mc = new MessengerClient("127.0.0.1");
		Thread thread = new Thread(mc);
		thread.start();
		mc.connect();
		mc.send(MessageType.StudentConnected, new Student("123"));
	}

}
