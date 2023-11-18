package dataViewerFly;

import java.awt.Color;

public class MainMenuState extends AppState {
	//variables
	public static MainMenuState instance;
	
    private final static double		MENU_STARTING_X = 40.0;
	private final static double 	MENU_STARTING_Y = 90.0;
	private final static double 	MENU_ITEM_SPACING = 5.0;

	//constructor
	private MainMenuState(DataViewerApp dataViewer) {
		super(dataViewer);
	}

	//method that creates and instance of MainMenuState
	public static MainMenuState create(DataViewerApp dataViewer) {
		if (instance == null) {
			instance = new MainMenuState(dataViewer);
		}
		return instance;
	}

	@Override
	public boolean isMenu() {
		return true;
	}

	@Override
	public AppState menu(DataViewerApp dataViewer) {
		drawMainMenu();
		return this;
	}

	@Override
	public AppState data(DataViewerApp dataViewer) {
		return DataVisualState.create(dataViewer);
	}

	private void drawMainMenu() {
		dataViewer.m_window.clear(Color.WHITE);

		String[] menuItems = {
				"Type the menu number to select that option:",
				"",
				String.format("C     Set country: [%s]", dataViewer.m_selectedCountry),
				String.format("T     Set state: [%s]", dataViewer.m_selectedState),
				String.format("S     Set start year [%d]", dataViewer.m_selectedStartYear),
				String.format("E     Set end year [%d]", dataViewer.m_selectedEndYear),
				String.format("V     Set visualization [%s]", dataViewer.m_selectedVisualization),
				String.format("P     Plot data"),
				String.format("Q     Quit"),
		};

		// enable drawing by "percentage" with the menu drawing
		dataViewer.m_window.setXscale(0, 100);
		dataViewer.m_window.setYscale(0, 100);

		// draw the menu
		dataViewer.m_window.setPenColor(Color.BLACK);

		drawMenuItems(menuItems);
	}

	private void drawMenuItems(String[] menuItems) {
		double yCoord = MENU_STARTING_Y;

		for(int i=0; i<menuItems.length; i++) {
			dataViewer.m_window.textLeft(MENU_STARTING_X, yCoord, menuItems[i]);
			yCoord -= MENU_ITEM_SPACING;
		}
	}

}
