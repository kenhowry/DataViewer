package dataviewerCommand;

public class DataVisualState implements AppState {
	//variables
	public static DataVisualState instance;

	//constructor
	private DataVisualState() {}

	//method that creates and instance of DataVisualState
	public static DataVisualState create() {
		if (instance == null) {
			instance = new DataVisualState();
		}
		return instance;
	}

	@Override
	public boolean isMenu() {
		return false;
	}

	@Override
	public AppState menu() {
		return MainMenuState.create();
	}

	@Override
	public AppState data() {
		return this;
	}

}
