package dataViewerFinal;

import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

public class ChooseCountryCommand extends Command {
	//constructor
	public ChooseCountryCommand(DataViewerApp dataViewer) {
		super(dataViewer);
	}

	@Override
	public void execute() {
		Object selectedValue = JOptionPane.showInputDialog(
				null,
				"Choose a Country", "Input",
				JOptionPane.INFORMATION_MESSAGE, null,
				dataViewer.m_dataCountries.toArray(), dataViewer.m_selectedCountry);

		if (selectedValue != null) {
			info("User selected: '%s'", selectedValue);
			if (!selectedValue.equals(dataViewer.m_selectedCountry)) {
				// change in data
				dataViewer.m_selectedCountry = (String) selectedValue;
				loadData();
				dataViewer.needsUpdate = true;
				dataViewer.needsUpdatePlotData = true;
			}
		}		
	}

	private void loadData() {
		try {
			// Assuming loadData method in DataLoader loads data for the given country
			dataViewer.loadData();
		} catch (FileNotFoundException e) {
			// Handle the exception appropriately, e.g., log or throw a runtime exception
			throw new RuntimeException(e);
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
