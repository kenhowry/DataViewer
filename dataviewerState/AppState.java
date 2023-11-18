package dataviewerState;

public abstract class AppState {
	//variables
	protected DataViewerApp dataViewer;
	
	//constructor
	AppState(DataViewerApp dataViewer) {
		this.dataViewer = dataViewer;
	}
	
	//boolean to determine if the menu is displayed
	public abstract boolean isMenu();
	
	//method to change to the main menu
	public abstract AppState menu(DataViewerApp dataViewer);
	
	//method to show the data
	public abstract AppState data(DataViewerApp dataViewer);
}
