package dataViewerFly;

public class QuitCommand extends Command {
	//constructor
	public QuitCommand(DataViewerApp dataViewer) {
		super(dataViewer);
	}
    @Override
    public void execute() {
        System.out.println("Bye");
        System.exit(0);
    }
}
