package com.argeloji.cons;

public class Enums {
	
	public enum MessageType
	{
		ConnectionStatusCheck,
		//StudentConnectionRequest,
		StudentConnectionRequestProcessedByServer,
		SendStudentInfo,
		//StudentConnected,
		StudentDisconnected,
		SendQuestion,
		SendQuestionDurationIncrease,
		SendAnswer,
		SendFile
	}
	
	public enum AnswerType
	{
		MultipleChoice,
		Number
	}
	
	public enum FileType
	{
		Video,
		Audio,
		Image,
		Other
	}
}
