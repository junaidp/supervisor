package com.wbc.supervisor.client.dashboard2.graphics.gwtchart;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.VisualizationUtils;
import com.google.gwt.visualization.client.visualizations.corechart.ColumnChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.LineChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.wbc.supervisor.client.dashboard2.events.GettingDetailedPingDataEvent;
import com.wbc.supervisor.client.dashboard2.graphics.chart.WbcSeriesInfo;
import com.wbc.supervisor.client.supervisor;
import com.wbc.supervisor.shared.dto.DataBasedChartSeriesDTO;
import com.wbc.supervisor.shared.dto.MultiSeriesTimebasedChartDTO;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * Created by Junaid on 2/11/2015.
 */
public class WbcGwtTimeseriesChart extends WbcGwtChart {

    private ArrayList<WbcSeriesInfo>  seriesInfo  ;
    private static Logger logger = Logger.getLogger("SummaryBandwidthData.class");

    public WbcGwtTimeseriesChart( ArrayList<WbcSeriesInfo> seriesList)
    {
        seriesInfo = seriesList;
    }

    public WbcGwtTimeseriesChart() {

        seriesInfo = new ArrayList<WbcSeriesInfo>();
    }

    public void drawMultiseriesChart(String chartType, final MultiSeriesTimebasedChartDTO dto, final VerticalPanel chartContainer, final boolean debugTable ) {
        Options options = createOptions();
        final Options finalOptions = setColorOptions(options);
        // TODO JUNAID fix error in setColorOptions, color string is not in the format that visualization wants, errpr = #ff0000,#0000ff is not a valid color string// Done
//        final Options defaultOptions = createOptions();
        if ( chartType.equalsIgnoreCase("LINE") ) {
            Runnable onLoadCallback = new Runnable() {
                public void run() {
                    AbstractDataTable dt = createDataTableFromMultiSeriesDTO(dto, debugTable );
                    final LineChart coreChart = new LineChart(dt, finalOptions );
                    chartContainer.add(coreChart);
                }
            };
            VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
        } else if ( chartType.equalsIgnoreCase("BAR") || chartType.equalsIgnoreCase("COLUMN") ) {
            Runnable onLoadCallback = new Runnable() {
                public void run() {
                    AbstractDataTable dt = createDataTableFromMultiSeriesDTO(dto, debugTable );
                    final ColumnChart coreChart = new ColumnChart(dt, finalOptions );
                    chartContainer.add(coreChart);
                }
            };
            VisualizationUtils.loadVisualizationApi(onLoadCallback, ColumnChart.PACKAGE);
        } else {
            logger.log(Level.SEVERE, "WbcGwtTimeseriesChart: drawMultiseriesChart unhandled chart type " + chartType );
        }
    }


    public void drawDatabasedChart(String chartType, final ArrayList<DataBasedChartSeriesDTO> dtoList, final VerticalPanel chartContainer,
                                   final boolean debugTable) {
        Options options = createOptions();
        /*
        //TODO this.seriesList's wbcSeriesInfo is not used, or must be set from the data inside each DataBasedChartSeriesDTO
        final Options finalOptions = setColorOptions(options);
         */
        final Options defaultOptions = createOptions();

        final Options finalOptions = setColorOptionsForPingDetails(options, dtoList);

        if ( chartType.equalsIgnoreCase("LINE") ) {
            Runnable onLoadCallback = new Runnable() {
                public void run() {
                    final AbstractDataTable dt = createTableFromDatabasedChartDTO(dtoList, debugTable);
                    final LineChart coreChart = new LineChart(dt, finalOptions );
                    CoreChart chart = (CoreChart) coreChart;
                    supervisor.eventBus.fireEvent(new GettingDetailedPingDataEvent(dtoList, chart, dt));
                     chartContainer.add(coreChart);
                }
            };
            VisualizationUtils.loadVisualizationApi(onLoadCallback, LineChart.PACKAGE);
        } else if ( chartType.equalsIgnoreCase("BAR") || chartType.equalsIgnoreCase("VBAR") || chartType.equalsIgnoreCase("COLUMN") ) {
            Runnable onLoadCallback = new Runnable() {
                public void run() {
                    AbstractDataTable dt = createTableFromDatabasedChartDTO(dtoList, debugTable);
                    final ColumnChart coreChart = new ColumnChart(dt, defaultOptions );
                    CoreChart chart = (CoreChart) coreChart;
                    supervisor.eventBus.fireEvent(new GettingDetailedPingDataEvent(dtoList, chart, dt));
                    chartContainer.add(coreChart);
                }
            };
            VisualizationUtils.loadVisualizationApi(onLoadCallback, ColumnChart.PACKAGE);
        } else {
            logger.log(Level.SEVERE, "WbcGwtTimeseriesChart: drawMultiseriesChart unhandled chart type " + chartType );
        }
    }

//    public  Options addOptions( ) {
//        Options options = Options.create();
//        options.setWidth(getWidth());
//        options.setHeight(getHeight());
//        options.setTitle(getChartTitle());
//        // options.setEnableTooltip(true);
//        options.setLegend(LegendPosition.NONE);
//        return options;
//    }

    public  Options createOptions( ) {
//        Options options = Options.create();
        int width = getWidth();
        int height = getHeight();
        String title = getChartTitle();
        Options options =addOptions(width,height,title);
        options.setBackgroundColor(getBackground());
        return options;
    }



    public native Options addOptions(int width, int height, String title) /*-{
        var wi = width;
        var he = height;
        var ti = title;
        var options = {

            'title': ti,
            'width':wi,
            'height':he,
            'legend':'none',
                explorer: { actions: ['dragToZoom', 'rightClickToReset'] }
            };

        return options;
    }-*/;


    public AbstractDataTable createTableFromDatabasedChartDTO(ArrayList<DataBasedChartSeriesDTO> dataBasedChartSeriesDTOs, boolean debugData  ) {
        DataTable dataTable = null;
        boolean debugTimes = false;
        try {
            if (debugData) logger.info("WbcGwtTimeseriesChart: createTableFromDatabasedChartDTO   before  created");
            dataTable = DataTable.create();
            if (debugData) logger.info("WbcGwtTimeseriesChart: createTableFromDatabasedChartDTO   dataTable created");

            if (debugData) logger.info("WbcGwtTimeseriesChart: dataBasedChartSeriesDTOs size " + dataBasedChartSeriesDTOs.size() );
            // find the time data and store the times
            boolean timeDataFound = false;

            ArrayList<Long> timesList = new ArrayList<Long>();
            // ONLY get the dto that has "timeData"
            for(int device=0; device< dataBasedChartSeriesDTOs.size(); device++){
                DataBasedChartSeriesDTO dto = dataBasedChartSeriesDTOs.get(device);
                WbcSeriesInfo wbcSeriesInfo = dto.getWbcSeriesInfo();
                if ( wbcSeriesInfo.getName().equalsIgnoreCase("timeData")) {
                    timeDataFound = true;
                    ArrayList<Number> numberList = dto.getDataList();
                    for (Number number : numberList) {
                        timesList.add( number.longValue() );
                    }
                    timeDataFound = true;
                    break;
                }
            }
            if (!timeDataFound) logger.log(Level.SEVERE, "NO time data found !!!!");
            DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy,MM,dd,HH,mm");
            dataTable.addColumn(AbstractDataTable.ColumnType.DATETIME, "time");  // col 0
            int row = 0;
            if (debugData || debugTimes) logger.info("WbcGwtTimeseriesChart: createTableFromDatabasedChartDTO   timesList size = " + timesList.size() );
            for (Long lTime : timesList) {
                dataTable.addRow();
                // put the series date in col 0 for this row
                String chartDate = fmt.format(new Date( lTime ));
                Date date = fmt.parse(chartDate);
                if (debugData || debugTimes) {
                    if ( lTime < 10000000L ) logger.info("WbcGwtTimeseriesChart: TEST set DATE for row " + row  + "  " + chartDate + "  ************ BAD DATE **************" );
                    else logger.info("WbcGwtTimeseriesChart: TEST set DATE for row " + row  + "  " + chartDate );
                }
                dataTable.setValue(row, 0, date);
                row++;
            }
            if (debugData || debugTimes) logger.info("WbcGwtTimeseriesChart: createTableFromDatabasedChartDTO   total rows of date data = " + row);

            /*
                    NOTE:  do not use this.seriesInfo !!!!
                    seriesInfo was not set before getting the data
                    each DataBasedChartSeriesDTO contains its own WbcSeriesInfo
             */
            for(int i=0; i< dataBasedChartSeriesDTOs.size(); i++){
                DataBasedChartSeriesDTO dto = dataBasedChartSeriesDTOs.get(i);
                WbcSeriesInfo wbcSeriesInfo = dto.getWbcSeriesInfo();
                if ( wbcSeriesInfo.getName().equalsIgnoreCase("timeData")) {
                    // SKIP the dto that has "timeData"
                    continue;  // do not graph the time data
                }
                if (debugData) logger.info("WbcGwtTimeseriesChart: TEST add column for next series, ");
                dataTable.addColumn(AbstractDataTable.ColumnType.NUMBER, wbcSeriesInfo.getName() );
                int dataColumn = dataTable.getNumberOfColumns() -1 ;
                ArrayList<Number> points = dto.getDataList();
                row = 0;
                if (debugData) logger.info("WbcGwtTimeseriesChart: createTableFromDatabasedChartDTO   points size for series " + wbcSeriesInfo.getName() + " = " + points.size() );
                for (Number point : points) {
                    Double data = point.doubleValue();
                    if (debugData) logger.info("WbcGwtTimeseriesChart: TEST set DATA in row= " + row + " , datacol " + dataColumn + " = " + data  );
                    dataTable.setValue(row, dataColumn, data );
                    row++;
                }
                if (debugData) logger.info("WbcGwtTimeseriesChart: createTableFromDatabasedChartDTO   total rows of data for series " + wbcSeriesInfo.getName() + " = " + row);
            }

        } catch (Exception ex) {
            //TODO use logger
            logger.log(Level.SEVERE, "WbcGwtTimeseriesChart: createTableFromDatabasedChartDTO Exception: " + ex.getMessage(), ex);
        }
        return dataTable;
    }

    public DataTable createDataTableFromMultiSeriesDTO(MultiSeriesTimebasedChartDTO dto, boolean debugData  ) {

        /*
        There will be 1 column for "dates" plus one column for each line / series in this chart
        There will be one row in summary ping chart, for every datetime
        There will be three columns in the bandwidht chart = date, XMIT, RECV
        There will be 2 to 20 columns in the ping details chart
         */
        DataTable dataTable = null;
        try {
            if (debugData) logger.info("WbcGwtTimeseriesChart: createDataTableFromMultiSeriesDTO   before  created");
            dataTable = DataTable.create();
            if (debugData) logger.info("WbcGwtTimeseriesChart: createDataTableFromMultiSeriesDTO   dataTable created");



            LinkedHashMap<String, ArrayList<Number>> seriesGraphData = dto.getData();
            if (debugData) logger.info("WbcGwtTimeseriesChart: createDataTableFromMultiSeriesDTO seriesGraphData size " + seriesGraphData.size() );

            // DISCARD UNLESS needed to format the date differently depending on time period
            /*
            if ( hoursToGraph == 8) {
            } else if ( hoursToGraph == 24) {
            } else if ( hoursToGraph == 48) {
            }
            */

            DateTimeFormat fmt = DateTimeFormat.getFormat("yyyy,MM,dd,HH,mm");
            if (debugData) logger.info("WbcGwtTimeseriesChart: seriesList size " + seriesInfo.size() );

            if (debugData) logger.info("createDataTableFromMultiSeriesDTO TEST current TEST");
            for (int col = 0; col < seriesInfo.size(); col ++) {  // one column per series plus column 0 for dates
                WbcSeriesInfo wbcSeriesInfo = seriesInfo.get(col);
                if (col == 0) {
                    if (debugData) logger.info("createDataTableFromMultiSeriesDTO TEST add column 0 for dates");
                    dataTable.addColumn(AbstractDataTable.ColumnType.DATETIME, "time");  // col 0
                }
                if (debugData) logger.info("createDataTableFromMultiSeriesDTO TEST add column for next series");
                dataTable.addColumn(AbstractDataTable.ColumnType.NUMBER, wbcSeriesInfo.getName() );  // col + 1
                int dataColumn = col + 1;
                /*
                NOTE: currently we are collecting data very inefficently, for every series, there is only one set of data
                        it should be that for each date there are n sets of data where n is the number of lines
                        will need to relate the series to the data in the array
                 */
                int row = 0;
                int arrayIndex = wbcSeriesInfo.getArrayIndex();
                for (String sDate : seriesGraphData.keySet()) {
                    ArrayList<Number> xvalues = seriesGraphData.get(sDate);
                    // each xvalues data is a new row
                    // note, in current scheme there will only ever be one row
                    dataTable.addRow();
                    // put the series date in col 0 for this row
                    Long lDate = Long.valueOf(sDate);
                    String chartDate = fmt.format(new Date(lDate));
                    Date date = fmt.parse(chartDate);
                    if (debugData) logger.info("createDataTableFromMultiSeriesDTO TEST col=" + dataColumn + " , set DATE for arrayIndex " + arrayIndex  + "  " + chartDate );
                    dataTable.setValue(row, 0, date);
                    Double data = xvalues.get( arrayIndex ).doubleValue();
                    if (debugData) logger.info("createDataTableFromMultiSeriesDTO TEST set DATA in row= " + row + " , datacol " + dataColumn + " = " + data  );
                    dataTable.setValue(row, dataColumn, data );
                    row++;
                }
            } // end of processing all series
            // anything to be done about the whole chart ??


            // guess not.....

            if (debugData) {
                logger.info("dataTable num rows " + dataTable.getNumberOfRows());
                logger.info("dataTable num cols " + dataTable.getNumberOfColumns());
                for (int i=0; i< dataTable.getNumberOfColumns(); i++) {
                    logger.info("Col " + i + "  " + dataTable.getColumnLabel(i));
                    /*
                    for (int j=0; j< dataTable.getNumberOfRows(); j++) {
                        logger.info("Row " + j + "  " + dataTable.getValueDate(j, 0) + "  " + dataTable.getValueDouble(j, i));
                    }
                    */
                }
            }

        } catch (Exception ex) {
            //TODO use logger
            logger.log(Level.SEVERE, "WbcGwtTimeseriesChart: drawMultiseriesChart Exception: " + ex.getMessage(), ex);
        }
        return dataTable;
    }

    /*
                NOTE :    THIS  METHOD MUST BE FIXED TO GET THE SECOND+ COLOR CORRECTLY INTO THE STRING
     */

    private Options setColorOptions(Options options) {
        // set the colors
//        StringBuilder colorString = null;
        boolean debug = false;
        ArrayList<String> colorList = new ArrayList<String>();
        for (int col = 0; col < seriesInfo.size(); col ++) {  // one column per series plus column 0 for dates
            StringBuilder  colorString= new StringBuilder();

            WbcSeriesInfo wbcSeriesInfo = seriesInfo.get(col);
            if (debug) logger.info("WbcGwtTimeseriesChart: wbcSeriesInfo " + wbcSeriesInfo.toString() );
            colorString.append("#");

            String value = Integer.toHexString(wbcSeriesInfo.getR());
            if ( value.length() == 1) value = "0" + value;
            colorString.append( value );

            value = Integer.toHexString(wbcSeriesInfo.getG());
            if ( value.length() == 1) value = "0" + value;
            colorString.append( value );

            value = Integer.toHexString(wbcSeriesInfo.getB());
            if ( value.length() == 1) value = "0" + value;
            colorString.append( value );
            if (debug) logger.info("WbcGwtTimeseriesChart: colorString BEFORE " + colorString.toString());
            colorList.add(colorString.toString());
        }
        String[] colorArray = new String[colorList.size()];
        for(int i=0; i< colorList.size(); i++){
            colorArray[i]=colorList.get(i);
        }
        options.setColors(colorArray);
        return options;
    }



    private Options setColorOptionsForPingDetails(Options options, ArrayList<DataBasedChartSeriesDTO> dtoList) {
         boolean debug = false;
        ArrayList<String> colorList = new ArrayList<String>();
        for (int col = 1; col < dtoList.size(); col ++) {

            int r = dtoList.get(col).getWbcSeriesInfo().getR();
            int g =  dtoList.get(col).getWbcSeriesInfo().getG();
            int b =  dtoList.get(col).getWbcSeriesInfo().getB();
            StringBuilder  colorString= new StringBuilder();

            colorString.append("#");

            String value = Integer.toHexString(r);
            if ( value.length() == 1) value = "0" + value;
            colorString.append( value );

            value = Integer.toHexString(g);
            if ( value.length() == 1) value = "0" + value;
            colorString.append( value );

            value = Integer.toHexString(b);
            if ( value.length() == 1) value = "0" + value;
            colorString.append( value );
            if (debug) logger.info("WbcGwtTimeseriesChart: colorString BEFORE " + colorString.toString());
            colorList.add(colorString.toString());
        }
        String[] colorArray = new String[colorList.size()];
        for(int i=0; i< colorList.size(); i++){
            colorArray[i]=colorList.get(i);
        }
        options.setColors(colorArray);
        return options;
    }




}
