package dataViewerFly;

import javax.swing.JOptionPane;

public class ChooseStateCommand extends Command {
	//constructor
	public ChooseStateCommand(DataViewerApp dataViewer) {
		super(dataViewer);
	}


	@Override
	public void execute() {
		Object selectedValue = JOptionPane.showInputDialog(
				null,
				"Choose a State", "Input",
				JOptionPane.INFORMATION_MESSAGE, null,
				dataViewer.m_dataStates.toArray(), dataViewer.m_selectedState);

		if (selectedValue != null) {
			info("User selected: '%s'", selectedValue);
			if (!selectedValue.equals(dataViewer.m_selectedState)) {
				// change in data
				dataViewer.m_selectedState = (String) selectedValue;
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

