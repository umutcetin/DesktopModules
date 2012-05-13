

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.Random;

import com.argeloji.cons.Enums.AnswerType;
import com.argeloji.cons.Enums.FileType;
import com.argeloji.cons.Enums.MessageType;
import com.argeloji.entity.Answer;
import com.argeloji.entity.ClientDispatcher;
import com.argeloji.entity.FileContainer;
import com.argeloji.entity.Question;
import com.argeloji.entity.Student;
import com.argeloji.messaging.MessengerClient;

public class Client1 {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		//test icin
		Random r = new Random();
		int sID = r.nextInt(100);
		//test icin bitti
		ClientDispatcher cd= ClientDispatcher.getInstance(new Student(Integer.toString(sID)));
		ClientDispatcher cd2= ClientDispatcher.getInstance(new Student(Integer.toString(sID)));
		
		System.out.println(cd.equals(cd2));
		
		//cd.send(MessageType.StudentConnected, new Student("123"));
		
		Calendar _time = new GregorianCalendar();
		_time.set(Calendar.HOUR, 1);
		_time.set(Calendar.MINUTE, 15);
		_time.set(Calendar.SECOND, 30);

		//cd.send(MessageType.SendQuestion, new Question(AnswerType.MultipleChoice, (byte) 5, _time, new File("H:\\getPDF.pdf")));
		
		//cd.send(MessageType.SendQuestionDurationIncrease, _time);
		
		//cd.send(MessageType.SendAnswer, new Answer("1231234123512", "112", AnswerType.MultipleChoice, null));
		
		Thread.sleep(10000);
		cd.send(MessageType.SendFile, new FileContainer(new File("D:\\source1.pdf"), FileType.Audio));
	}

}
