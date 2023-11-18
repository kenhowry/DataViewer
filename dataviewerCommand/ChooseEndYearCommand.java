package dataviewerCommand;

import javax.swing.JOptionPane;

public class ChooseEndYearCommand extends Command {
	//constructor
	public ChooseEndYearCommand(DataViewerApp dataViewer) {
		super(dataViewer);
	}

	@Override
	public void execute() {
		Object selectedValue = JOptionPane.showInputDialog(
				null,
				"Choose a Year", "Input",
				JOptionPane.INFORMATION_MESSAGE, null,
				dataViewer.m_dataYears.toArray(), dataViewer.m_selectedEndYear);

		if (selectedValue != null) {
			info("User selected: '%s'", selectedValue);
			if (!selectedValue.equals(dataViewer.m_selectedEndYear)) {
				// change in data
				dataViewer.m_selectedEndYear = (Integer) selectedValue;
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

