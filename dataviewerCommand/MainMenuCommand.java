package dataviewerCommand;

public class MainMenuCommand extends Command {
	//constructor
	public MainMenuCommand(DataViewerApp dataViewer) {
		super(dataViewer);
	}
    
	@Override
	public void execute() {
		dataViewer.state = dataViewer.state.menu();
	}
}
