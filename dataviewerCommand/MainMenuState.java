package dataviewerCommand;

public class MainMenuState implements AppState {
	//variables
	public static MainMenuState instance;

	//constructor
	private MainMenuState() {}

	//method that creates and instance of MainMenuState
	public static MainMenuState create() {
		if (instance == null) {
			instance = new MainMenuState();
		}
		return instance;
	}

	@Override
	public boolean isMenu() {
		return true;
	}

	@Override
	public AppState menu() {
		return this;
	}

	@Override
	public AppState data() {
		return DataVisualState.create();
	}

}
