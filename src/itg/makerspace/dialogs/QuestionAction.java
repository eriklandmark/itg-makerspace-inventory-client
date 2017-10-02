package itg.makerspace.dialogs;

public interface QuestionAction {
	
	public enum ActionType {
		YES, NO
	}
	
	public void onAnswerEvent(ActionType actionType);
}
