package itg.makerspace.dialogs;

public class ErrorDialog extends DefaultDialog {

	private static final long serialVersionUID = 1L;

	public ErrorDialog() {
		super();
	}
	
	public ErrorDialog(String msg) {
		super();
		setText(msg);
	}
	
	@Override
	public void setText(String text) {
		super.setText(text);
	}
	
	@Override
	public void open(String msg) {
		super.setText("Error: \n" + msg);
		super.open();
	}
}