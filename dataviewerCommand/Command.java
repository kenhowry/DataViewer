package dataviewerCommand;

public abstract class Command {
	//variables
	protected DataViewerApp dataViewer;
	
	//constructor
	Command(DataViewerApp dataViewer) {
		this.dataViewer = dataViewer;
	} 
	
	public abstract void execute();
}
