package dataviewerCommand;

public interface AppState {
	//boolean to determine if the menu is displayed
	public boolean isMenu();
	
	//method to change to the main menu
	public AppState menu();
	
	//method to show the data
	public AppState data();
}
