package dataViewerFly;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {
	private Map<Character, Command> commandCache = new HashMap<>();
	private DataViewerApp dataViewer;

	public CommandFactory(DataViewerApp dataViewer) {
		this.dataViewer = dataViewer;
	}

	public Command createCommand(char key) {
		if (commandCache.containsKey(key)) {
			// If the command for the given key exists in the cache, return it
			return commandCache.get(key);
		} else {
			//if the command for the given key doesn't exist, put one in the cache
			Command command;

			switch (key) {
				case 'Q':
					command = new QuitCommand(dataViewer);
					break;
				case 'P':
					command = new PlotDataCommand(dataViewer);
					break;
				case 'C':
					command = new ChooseCountryCommand(dataViewer);
					break;
				case 'T':
					command = new ChooseStateCommand(dataViewer);
					break;
				case 'S':
					command = new ChooseStartYearCommand(dataViewer);
					break;
				case 'E':
					command = new ChooseEndYearCommand(dataViewer);
					break;
				case 'V':
					command = new ChooseVisualizationCommand(dataViewer);
					break;
				case 'M':
					command = new MainMenuCommand(dataViewer);
					break;
				default:
					throw new IllegalArgumentException("Invalid key: " + key);
			}
            // Store the newly created command in the cache for reuse
            commandCache.put(key, command);
            return command;
		}
	}
}