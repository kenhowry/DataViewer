package dataViewerFly;

public class PlotDataCommand extends Command {
	//constructor
	public PlotDataCommand(DataViewerApp dataViewer) {
		super(dataViewer);
	}
    
	@Override
	public void execute() {
		dataViewer.state = dataViewer.state.data(dataViewer);
		if(dataViewer.m_plotData == null) {
			// first time going to render data need to generate the plot data
			dataViewer.needsUpdatePlotData = true;
		}
		dataViewer.needsUpdate = true;
	}
}
