

import com.argeloji.entity.ServerDispatcher;
import com.argeloji.messaging.MessengerServer;

public class Server1 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ServerDispatcher sd = ServerDispatcher.getInstance();
		ServerDispatcher sd2 = ServerDispatcher.getInstance();
		System.out.println(sd.equals(sd2));
		
		//MessengerServer ms = new MessengerServer();
		//Thread thread = new Thread(ms);
		//thread.start();
		/*try {
			Thread.sleep(30000);
			ms.sendAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

}
