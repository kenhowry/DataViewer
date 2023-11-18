package dataViewerFinal;

public class CommandFactory {
    public Command createCommand(int key, DataViewerApp dataViewer) {
        switch (key) {
            case 'Q':
                return new QuitCommand(dataViewer);
            case 'P':
                return new PlotDataCommand(dataViewer);
            case 'C':
                return new ChooseCountryCommand(dataViewer);
            case 'T':
                return new ChooseStateCommand(dataViewer);
            case 'S':
                return new ChooseStartYearCommand(dataViewer);
            case 'E':
                return new ChooseEndYearCommand(dataViewer);
            case 'V':
                return new ChooseVisualizationCommand(dataViewer);
            case 'M':
            	return new MainMenuCommand(dataViewer);
            default:
                throw new IllegalArgumentException("Invalid key: " + key);
        }
    }
}
