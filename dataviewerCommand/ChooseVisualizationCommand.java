package dataviewerCommand;

import javax.swing.JOptionPane;

public class ChooseVisualizationCommand extends Command {
	//constructor
	public ChooseVisualizationCommand(DataViewerApp dataViewer) {
		super(dataViewer);
	}

	@Override
	public void execute() {
		Object selectedValue = JOptionPane.showInputDialog(
				null,
				"Choose a Visualization", "Input",
				JOptionPane.INFORMATION_MESSAGE, null,
				DataViewerApp.VISUALIZATION_MODES, dataViewer.m_selectedVisualization);

		if (selectedValue != null) {
			info("User selected: '%s'", selectedValue);
			if (!selectedValue.equals(dataViewer.m_selectedVisualization)) {
				// change in data
				dataViewer.m_selectedVisualization = (String) selectedValue;
				dataViewer.needsUpdate = true;
				dataViewer.needsUpdatePlotData = true;
			}
		}		
	}

	/**
	 * For informational output.
	 * @param format
	 * @param args
	 */
	private void info(String format, Object... args) {
		System.out.print("INFO: ");
		System.out.println(String.format(format, args));
	}
}
