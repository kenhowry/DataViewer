package dataViewerFinal;

import java.awt.Color;
import java.util.SortedMap;

public class DataVisualState extends AppState {
	//variables
	public static DataVisualState instance;
	private final static double 	DATA_WINDOW_BORDER = 50.0;
	private final static boolean	DO_DEBUG = true;
	private final static boolean	DO_TRACE = false;
	private final static double 	EXTREMA_PCT = 0.1;
	private final static String[] 	MONTH_NAMES = { "", // 1-based
			"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec" };
	private final static double		TEMPERATURE_MAX_C = 30.0;
	private final static double		TEMPERATURE_MIN_C = -10.0;
	private final static double		TEMPERATURE_RANGE = TEMPERATURE_MAX_C - TEMPERATURE_MIN_C;
	private final static int		VISUALIZATION_EXTREMA_IDX = 1;

    // plot-related data

	
	//constructor
	private DataVisualState(DataViewerApp dataViewer) {
		super(dataViewer);
	}

	//method that creates and instance of DataVisualState
	public static DataVisualState create(DataViewerApp dataViewer) {
		if (instance == null) {
			instance = new DataVisualState(dataViewer);
		}
		return instance;
	}

	@Override
	public boolean isMenu() {
		return false;
	}

	@Override
	public AppState menu(DataViewerApp dataViewer) {
		return MainMenuState.create(dataViewer);
	}

	@Override
	public AppState data(DataViewerApp dataViewer) {
		drawData();
		return this;
	}
	
	private void drawData() {
    	// Give a buffer around the plot window
        dataViewer.m_window.setXscale(-DATA_WINDOW_BORDER, DataViewerApp.WINDOW_WIDTH+DATA_WINDOW_BORDER);
		dataViewer.m_window.setYscale(-DATA_WINDOW_BORDER, DataViewerApp.WINDOW_HEIGHT+DATA_WINDOW_BORDER);

    	// gray background
    	dataViewer.m_window.clear(Color.LIGHT_GRAY);

    	// white plot area
		dataViewer.m_window.setPenColor(Color.WHITE);
		dataViewer.m_window.filledRectangle(DataViewerApp.WINDOW_WIDTH/2.0, DataViewerApp.WINDOW_HEIGHT/2.0, DataViewerApp.WINDOW_WIDTH/2.0, DataViewerApp.WINDOW_HEIGHT/2.0);  

    	dataViewer.m_window.setPenColor(Color.BLACK);
    	
    	double nCols = 12; // one for each month
    	double nRows = dataViewer.m_selectedEndYear - dataViewer.m_selectedStartYear + 1; // for the years
    	
    	debug("nCols = %f, nRows = %f", nCols, nRows);
 		
        double cellWidth = DataViewerApp.WINDOW_WIDTH / nCols;
        double cellHeight = DataViewerApp.WINDOW_HEIGHT / nRows;
        
        debug("cellWidth = %f, cellHeight = %f", cellWidth, cellHeight);
        
        boolean extremaVisualization = dataViewer.m_selectedVisualization.equals(DataViewerApp.VISUALIZATION_MODES[VISUALIZATION_EXTREMA_IDX]);
        info("visualization: %s (extrema == %b)", dataViewer.m_selectedVisualization, extremaVisualization);
        
        for(int month = 1; month <= 12; month++) {
            double fullRange = dataViewer.m_plotMonthlyMaxValue.get(month) - dataViewer.m_plotMonthlyMinValue.get(month);
            double extremaMinBound = dataViewer.m_plotMonthlyMinValue.get(month) + EXTREMA_PCT * fullRange;
            double extremaMaxBound = dataViewer.m_plotMonthlyMaxValue.get(month) - EXTREMA_PCT * fullRange;


            // draw the line separating the months and the month label
        	dataViewer.m_window.setPenColor(Color.BLACK);
        	double lineX = (month-1.0)*cellWidth;
        	dataViewer.m_window.line(lineX, 0.0, lineX, DataViewerApp.WINDOW_HEIGHT);
        	dataViewer.m_window.text(lineX+cellWidth/2.0, -DATA_WINDOW_BORDER/2.0, MONTH_NAMES[month]);
        	
        	// there should always be a map for the month
        	SortedMap<Integer,Double> monthData = dataViewer.m_plotData.get(month);
        	
        	for(int year = dataViewer.m_selectedStartYear; year <= dataViewer.m_selectedEndYear; year++) {

        		// month data structure might not have every year
        		if(monthData.containsKey(year)) {
        			Double value = monthData.get(year);
        			
        			double x = (month-1.0)*cellWidth + 0.5 * cellWidth;
        			double y = (year-dataViewer.m_selectedStartYear)*cellHeight + 0.5 * cellHeight;
        			
        			Color cellColor = null;
        			
        			// get either color or grayscale depending on visualization mode
        			if(extremaVisualization && value > extremaMinBound && value < extremaMaxBound) {
        				cellColor = getDataColor(value, true);
        			}
        			else if(extremaVisualization) {
        				// doing extrema visualization, show "high" values in red "low" values in blue.
        				if(value >= extremaMaxBound) {
        					cellColor = Color.RED;
        				}
        				else {
        					cellColor = Color.BLUE;
        				}
        			}
        			else {
        				cellColor = getDataColor(value, false);
        			}
        			
        			// draw the rectangle for this data point
        			dataViewer.m_window.setPenColor(cellColor);
        			trace("month = %d, year = %d -> (%f, %f) with %s", month, year, x, y, cellColor.toString());
        			dataViewer.m_window.filledRectangle(x, y, cellWidth/2.0, cellHeight/2.0);
        		}
        	}
        }
        
        // draw the labels for the y-axis
        dataViewer.m_window.setPenColor(Color.BLACK);

        double labelYearSpacing = (dataViewer.m_selectedEndYear - dataViewer.m_selectedStartYear) / 5.0;
        double labelYSpacing = DataViewerApp.WINDOW_HEIGHT/5.0;
        // spaced out by 5, but need both the first and last label, so iterate 6
        for(int i=0; i<6; i++) {
        	int year = (int)Math.round(i * labelYearSpacing + dataViewer.m_selectedStartYear);
        	String text = String.format("%4d", year);
        	
        	dataViewer.m_window.textRight(0.0, i*labelYSpacing, text);
        	dataViewer.m_window.textLeft(DataViewerApp.WINDOW_WIDTH, i*labelYSpacing, text);
        }
     
        // draw rectangle around the whole data plot window
        dataViewer.m_window.rectangle(DataViewerApp.WINDOW_WIDTH/2.0, DataViewerApp.WINDOW_HEIGHT/2.0, DataViewerApp.WINDOW_WIDTH/2.0, DataViewerApp.WINDOW_HEIGHT/2.0);
        
        // put in the title
        String title = String.format("%s, %s from %d to %d. Press 'M' for Main Menu.  Press 'Q' to Quit.",
        		dataViewer.m_selectedState, dataViewer.m_selectedCountry, dataViewer.m_selectedStartYear, dataViewer.m_selectedEndYear);
        dataViewer.m_window.text(DataViewerApp.WINDOW_WIDTH/2.0, DataViewerApp.WINDOW_HEIGHT+DATA_WINDOW_BORDER/2.0, title);
	}
    
    /**
     * Return a Color object based on the value passed in.
     * @param value - controls the color
     * @param doGrayscale - if true, return a grayscale value (r, g, b are all equal);
     * 	otherwise return a range of red to green.
     * @return null is value is null, otherwise return a Color object
     */
    private Color getDataColor(Double value, boolean doGrayscale) {
    	if(null == value) {
    		return null;
    	}
    	double pct = (value - TEMPERATURE_MIN_C) / TEMPERATURE_RANGE;
    	trace("converted %f raw value to %f %%", value, pct);
    
    	if (pct > 1.0) {
            pct = 1.0;
        }
        else if (pct < 0.0) {
            pct = 0.0;
        }
        int r, g, b;
        // Replace the color scheme with my own
        if (!doGrayscale) {
        	r = (int)(255.0 * pct);
        	g = 0;
        	b = (int)(255.0 * (1.0-pct));
        	
        } else {
        	// Grayscale for the middle extema
        	r = g = b = (int)(255.0 * pct);
        }
        
        trace("converting %f to [%d, %d, %d]", value, r, g, b);

		return new Color(r, g, b);
	}
    
    /**
     * For debugging output.  Output is controlled by the DO_DEBUG constant.
     * @param format
     * @param args
     */
    private void debug(String format, Object... args) {
    	if(DO_DEBUG) {
    		System.out.print("DEBUG: ");
    		System.out.println(String.format(format, args));
    	}
    }
    
    /**
     * For debugging.  Use 'trace' for older debugging messages that you don't want to see.
     * 
     * Output is shown based on the M_DO_TRACE constant.
     */
    private void trace(String format, Object...args) {
    	if(DO_TRACE) {
    		System.out.print("TRACE: ");
    		System.out.println(String.format(format, args));
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
