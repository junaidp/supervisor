package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.utils.*;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.ConnectionInfoData;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.MacInfo;
import com.wbc.supervisor.shared.dashboardutilities.threshold.*;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
import com.google.gwt.event.logical.shared.BeforeSelectionHandler;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.Response;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.data.shared.loader.FilterConfig;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.DoubleField;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.validator.EmptyValidator;
import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.editing.GridInlineEditing;
import com.sencha.gxt.widget.core.client.info.Info;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class MenuThresholdAnalysis<T extends ThresholdPresentationData> extends MenuBase implements IsWidget
{

	private static final MenuThresholdAnalysis.ThresholdProperties props = GWT.create( MenuThresholdAnalysis.ThresholdProperties.class );
	private ListStore<ThresholdPresentationData> store = null;
	private String json = "";
	private  Grid<ThresholdPresentationData> grid = null;
	private ThresholdPresentationInfo thresholdPresentationInfo = null;
	private ExportWidget exportWidget = new ExportWidget();
	IdentityValueProvider<ThresholdPresentationData> identity = new IdentityValueProvider<ThresholdPresentationData>();
	private AsyncCallback<String> asyncCallback;
	protected ArrayList<T> tempList =  new ArrayList<T>();
	protected List<T> list;
	private FilterWidget filterWidget = new FilterWidget();


	final CheckBoxSelectionModel<ThresholdPresentationData> selectionModel
			= new CheckBoxSelectionModel<ThresholdPresentationData>(identity);

	public MenuThresholdAnalysis(AsyncCallback<String> asyncCallback) {
		this.asyncCallback = asyncCallback;
	}

	@Override
	public Widget asWidget()
	{
		container.clear();
		container.setWidth( "100%" );
		container.add( layout() );
		doFilter();
		return super.asWidget();

	}

	private void getThresholdData()
	{
		StringBuilder param = new StringBuilder();
		DashboardUtils.callServer( Constants.THRESHOLD_ANALYSIS, Constants.THRESHOLD_ANALYSIS_MESSAGE, param, RequestBuilder.GET, Constants.URL_THRESHOLD_ANALYSIS, null, new AsyncCallback<Response>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox( Constants.THRESHOLD_ANALYSIS, caught.getMessage() );
			}

			@Override
			public void onSuccess( Response result )
			{
				json = result.getText();
				getThresholdData( ThresholdType.THRESHOLD_TYPE_ALL );
			}
		} );
	}

	private void getThresholdData( ThresholdType thresholdType )
	{
		supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
		rpcService.getThresholdData( json, thresholdType, new AsyncCallback<ThresholdPresentationInfo>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox( Constants.THRESHOLD_REPORT, caught.getLocalizedMessage() );
			}

			@Override
			public void onSuccess( ThresholdPresentationInfo result )
			{
				DashboardUtils.handleError( result.getErrorInfo(), Constants.THRESHOLD_REPORT, new AsyncCallback<Void>()
				{
					@Override
					public void onFailure( Throwable caught )
					{
						if(caught != null)
						logError( caught.getLocalizedMessage() );
					}

					@Override
					public void onSuccess( Void data )
					{
						populate( result );
					}
				} );

			}
		} );
	}

	public interface ThresholdProperties extends PropertyAccess<ThresholdPresentationData>
	{
		@Editor.Path( "key" )
		ModelKeyProvider<ThresholdPresentationData> key();

		ValueProvider<ThresholdPresentationData, String> ipaddress();

		ValueProvider<ThresholdPresentationData, String> devicename();

		ValueProvider<ThresholdPresentationData, Integer> threshType();

		ValueProvider<ThresholdPresentationData, StatData> statData();

		ValueProvider<ThresholdPresentationData, Double> cur_threshold();

		ValueProvider<ThresholdPresentationData, Double> recommend_threshold();

		ValueProvider<ThresholdPresentationData, Integer> descid();

		ValueProvider<ThresholdPresentationData, Integer> threshid();

		ValueProvider<ThresholdPresentationData, String> network();

		ValueProvider<ThresholdPresentationData, Integer> countOverRec();

	}

	public Grid<ThresholdPresentationData> grid()
	{

		ValueProvider<ThresholdPresentationData, ThresholdPresentationData> valueProviderIp = new ValueProvider<ThresholdPresentationData, ThresholdPresentationData>()
		{
			@Override
			public String getPath()
			{
				return "";
			}

			@Override
			public ThresholdPresentationData getValue(ThresholdPresentationData taskDTO)
			{
				return taskDTO;
			}

			@Override
			public void setValue(ThresholdPresentationData object, ThresholdPresentationData value)
			{
			}
		};

		ColumnConfig<ThresholdPresentationData, Integer> typeCol = new ColumnConfig<ThresholdPresentationData, Integer>( props.threshType(), 70, "Type" );
		ColumnConfig<ThresholdPresentationData, ThresholdPresentationData> ipAddressCol = new ColumnConfig<ThresholdPresentationData, ThresholdPresentationData>( valueProviderIp, 100, "IP Address" );
		ColumnConfig<ThresholdPresentationData, String> networkCol = new ColumnConfig<ThresholdPresentationData, String>( props.network(), 100, "Network" );
		ColumnConfig<ThresholdPresentationData, StatData> samplesCol = new ColumnConfig<ThresholdPresentationData, StatData>( props.statData(), 70, "Samples" );
		ColumnConfig<ThresholdPresentationData, StatData> statColMin = new ColumnConfig<ThresholdPresentationData, StatData>( props.statData(), 130, "Min" );
		ColumnConfig<ThresholdPresentationData, StatData> statColMax = new ColumnConfig<ThresholdPresentationData, StatData>( props.statData(), 70, "Max" );
		ColumnConfig<ThresholdPresentationData, StatData> statColAvg = new ColumnConfig<ThresholdPresentationData, StatData>( props.statData(), 70, "Avg" );
		ColumnConfig<ThresholdPresentationData, StatData> statColStdDev = new ColumnConfig<ThresholdPresentationData, StatData>( props.statData(), 70, "StdDev" );
		ColumnConfig<ThresholdPresentationData, StatData> stdDev_over_mixCol = new ColumnConfig<ThresholdPresentationData, StatData>( props.statData(), 120, "StdDev Over Mix" );
		ColumnConfig<ThresholdPresentationData, StatData> max_stdMaxDevsCol = new ColumnConfig<ThresholdPresentationData, StatData>( props.statData(), 70, "Max StdDevs" );
		ColumnConfig<ThresholdPresentationData, StatData> xOverCol = new ColumnConfig<ThresholdPresentationData, StatData>(props.statData(), 70, "5x Over");
		ColumnConfig<ThresholdPresentationData, Double> currentCol = new ColumnConfig<ThresholdPresentationData, Double>( props.cur_threshold(), 70, "Current" );
		ColumnConfig<ThresholdPresentationData, Double> recommendCol = new ColumnConfig<ThresholdPresentationData, Double>( props.recommend_threshold(), 130, "Recommend" );
		ColumnConfig<ThresholdPresentationData, Integer> ct_over_recCol = new ColumnConfig<ThresholdPresentationData, Integer>( props.countOverRec(), 130, "Ct over Rec" );

		List<ColumnConfig<ThresholdPresentationData, ?>> columns = new ArrayList<ColumnConfig<ThresholdPresentationData, ?>>();
		recommendCol.setFixed( true );
		ct_over_recCol.setFixed( true );
		statColMin.setFixed( true );
		addColumns( typeCol, ipAddressCol, networkCol, samplesCol, statColMin, statColMax, statColAvg, statColStdDev, stdDev_over_mixCol, xOverCol, currentCol, recommendCol, ct_over_recCol, columns );

		ipAddressCol.setComparator(new IpComparator() );
		ipAddressCol.setCell(getButtonCellIP());
		stdDev_over_mixCol.setToolTip( SafeHtmlUtils.fromTrustedString( "The number of responses that were over various levels of Standard Deviations over the Average are <br>- less than 1 <br>- between 1 and 2 <br>- between 2 and 3 <br>- between 3 and 5 <br>- between 5 and 10 <br>- over 10</i>" ));
		samplesCol.setToolTip( "The number of samples out of 360 with data" );
		xOverCol.setToolTip( "The value that is 5 Standard Deviations over the Average" );
		currentCol.setToolTip( "The current threshold set in IntraVUE" );
		recommendCol.setToolTip( "The calculated, recommended threshold value" );
		ct_over_recCol.setToolTip( "Of the samples used, how many would be over the recommended setting" );

		statColMin.setCell( CustomCell.getButtonCellStatMin() );
		statColMax.setCell( CustomCell.getButtonCellStatMax() );
		statColAvg.setCell( CustomCell.getButtonCellStatAvg() );
		statColStdDev.setCell( CustomCell.getButtonCellStatStdDev() );
		stdDev_over_mixCol.setCell( CustomCell.getButtonCellStatStdDevCounts() );
		xOverCol.setCell( CustomCell.getButtonCellXOver() );
		typeCol.setCell( CustomCell.getButtonCellThresholdType() );
		samplesCol.setCell( CustomCell.getButtonCellSamples() );

		statColMin.setComparator( new MinComparator() );
		statColMax.setComparator( new MaxComparator() );
		statColAvg.setComparator( new AvgComparator() );
		statColStdDev.setComparator( new StdDevComparator());
		samplesCol.setComparator( new SamplesComparator());
		stdDev_over_mixCol.setComparator( new StdDevMixComparator());
		xOverCol.setComparator( new XoverComparator() );

		statColMin.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		statColMax.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		statColAvg.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		statColStdDev.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		xOverCol.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		currentCol.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		recommendCol.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );
		ct_over_recCol.setHorizontalAlignment( HasHorizontalAlignment.ALIGN_RIGHT );


		ColumnModel<ThresholdPresentationData> cm = new ColumnModel<ThresholdPresentationData>( columns );

		store = new ListStore<ThresholdPresentationData>( props.key() );

		grid = new Grid<ThresholdPresentationData>( store, cm );
		DashboardUtils.setDefaultGridStyle( grid );

		final GridInlineEditing<ThresholdPresentationData> editing = new GridInlineEditing<ThresholdPresentationData>(grid);
		DoubleField recommendField = new DoubleField();
		editing.addEditor(recommendCol, recommendField);
		grid.setSelectionModel(selectionModel);

		recommendField.addValidator(new EmptyValidator<Double>());

		return grid;

	}

	private void addColumns( ColumnConfig<ThresholdPresentationData, Integer> typeCol, ColumnConfig<ThresholdPresentationData, ThresholdPresentationData> ipAddressCol, ColumnConfig<ThresholdPresentationData, String> networkCol, ColumnConfig<ThresholdPresentationData, StatData> samplesCol, ColumnConfig<ThresholdPresentationData, StatData> statColMin, ColumnConfig<ThresholdPresentationData, StatData> statColMax, ColumnConfig<ThresholdPresentationData, StatData> statColAvg,
			ColumnConfig<ThresholdPresentationData, StatData> statColStdDev, ColumnConfig<ThresholdPresentationData, StatData> stdDev_over_mixCol, ColumnConfig<ThresholdPresentationData, StatData> xOverCol, ColumnConfig<ThresholdPresentationData, Double> currentCol, ColumnConfig<ThresholdPresentationData, Double> recommendCol, ColumnConfig<ThresholdPresentationData, Integer> ct_over_recCol, List<ColumnConfig<ThresholdPresentationData, ?>> columns )
	{
		columns.add( typeCol );
		columns.add( ipAddressCol );
		columns.add( networkCol );
		columns.add( samplesCol );
		columns.add( statColMin );
		columns.add( statColMax );
		columns.add( statColAvg );
		columns.add( statColStdDev );

		columns.add( stdDev_over_mixCol );
		//columns.add( max_stdMaxDevsCol );
		columns.add(xOverCol);
		columns.add( currentCol );
		columns.add( recommendCol );
		columns.add( ct_over_recCol );
		columns.add(selectionModel.getColumn());
	}

	protected void populate( ThresholdPresentationInfo thresholdPresentationInfo )
	{
		try
		{
			this.thresholdPresentationInfo = thresholdPresentationInfo;
			// GWT.log( "POPULATING Threshold Report with size: " + thresholdPresentationInfo.getPresentationInfoMap().size());
			List<ThresholdPresentationData> thresholdPresentationDataList = new ArrayList<ThresholdPresentationData>();
			//	int count =0;
			for ( Map.Entry<String, ThresholdPresentationData> entry : thresholdPresentationInfo.getPresentationInfoMap().entrySet() )
			{
				thresholdPresentationDataList.add( entry.getValue() );

			}
			store.addAll( thresholdPresentationDataList );
			list = (List<T>) thresholdPresentationDataList;
			exportWidget.setVisible(true);
			exportWidget.export(null, thresholdPresentationInfo);
		}
		catch ( Exception ex )
		{
			new WarningMessageBox( Constants.THRESHOLD_REPORT, ex.getMessage() );
		}

	}

	private ThresholdPresentationInfo removeDescFromIp(ThresholdPresentationInfo data) {
		for ( Map.Entry<String, ThresholdPresentationData> entry : data.getPresentationInfoMap().entrySet()) {
			if(entry.getValue() == null || entry.getValue().getIpaddress() == null || entry.getValue().getIpaddress().isEmpty())
				continue;
			int decIdIndex = entry.getValue().getIpaddress().indexOf( " " );
			String ipAddress = entry.getValue().getIpaddress().substring( 0, decIdIndex );
			entry.getValue().setIpaddress(ipAddress);
		}
		return data;
	}

	private Widget layout()
	{
		VerticalLayoutContainer container = new VerticalLayoutContainer();
		HorizontalPanel hContainer = new HorizontalPanel();
		container.add( getUpperPanel() );
		container.add( hContainer  );
		hContainer.add( getFilters() );
		hContainer.add( getButtons() );
		hContainer.add(filterWidget);
		filterWidget.labelSearch.getElement().getStyle().setPaddingLeft(10, Style.Unit.PX);
		filterWidget.colSearch.getElement().getStyle().setPaddingLeft(30, Style.Unit.PX);
		hContainer.getElement().getStyle().setPaddingBottom( 10, Style.Unit.PX );

		container.add(exportWidget);
		container.add( grid());
		container.setWidth( "100%" );

		getThresholdData();
		return container;
	}

	private IsWidget getButtons()
	{
		TextButton selectAll = new TextButton( "Select All" );
		TextButton selectNone = new TextButton( "Select None" );
		TextButton updateSelected = new TextButton( "Update Selected Thresholds" );

		HorizontalLayoutContainer h = new HorizontalLayoutContainer();
		h.add( selectAll, new HorizontalLayoutContainer.HorizontalLayoutData(-1,1,new Margins(1,5,1,1) ) );
		h.add( selectNone ,new HorizontalLayoutContainer.HorizontalLayoutData(-1,1,new Margins(1,5,1,1) ) );
		h.add( updateSelected , new HorizontalLayoutContainer.HorizontalLayoutData(-1,1,new Margins(1,5,1,1) ) );
		h.setWidth( 400 );

		h.getElement().getStyle().setPaddingLeft( 10, Style.Unit.PX );
		h.getElement().getStyle().setPaddingBottom( 10, Style.Unit.PX );
		selectAll.addSelectHandler( new SelectEvent.SelectHandler()
		{
			@Override
			public void onSelect( SelectEvent event )
			{
				grid.getSelectionModel().selectAll();
			}
		} );

		selectNone.addSelectHandler( new SelectEvent.SelectHandler()
		{
			@Override
			public void onSelect( SelectEvent event )
			{
				grid.getSelectionModel().deselectAll();
			}
		} );

		updateSelected.addSelectHandler( new SelectEvent.SelectHandler()
		{
			@Override
			public void onSelect( SelectEvent event )
			{
			//	Collection<Store<ThresholdPresentationData>.Record> recordsModified = store.getModifiedRecords();
				grid.getStore().commitChanges();
				List<ThresholdPresentationData> selectedData = grid.getSelectionModel().getSelectedItems();
				if(selectedData.isEmpty())
				{
					new MessageBox( "Nothing selected !" );
				}
				else
				{
					List<ThresholdSetData> selectedThresholdSetData =  setThresholdSetData(selectedData);
					getRecommendedThresholdsJson( selectedThresholdSetData );
				}

			}
		} );

		return h;

	}

	private List<ThresholdSetData> setThresholdSetData( List<ThresholdPresentationData> selectedData )
	{

		List<ThresholdSetData> listThresholdSetData = new ArrayList<ThresholdSetData>();

		for(ThresholdPresentationData thresholdPresentationData: selectedData)
		{
			ThresholdSetData thresholdSetData = new ThresholdSetData();
			thresholdSetData.setThreshid( thresholdPresentationData.getThreshid() );
			thresholdSetData.setThreshType( thresholdPresentationData.getThreshType() );
			thresholdSetData.setNewSetting( (int) thresholdPresentationData.getRecommend_threshold() );
			listThresholdSetData.add( thresholdSetData );
		}

		return listThresholdSetData;
	}

	private void getRecommendedThresholdsJson( List<ThresholdSetData> list )
	{
		supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
		rpcService.getRecommendedThresholdsJson( list, new AsyncCallback<String>()
		{
			@Override
			public void onFailure( Throwable caught )
			{
				new WarningMessageBox( "getting list json", caught.getLocalizedMessage() );
			}

			@Override
			public void onSuccess( String result )
			{
				saveRecommendedThresholds(result);
			}
		} );
	}

	private void saveRecommendedThresholds( String selectedData )
	{
		StringBuilder param = new StringBuilder();
		param.append( "?recommended=" ).append( selectedData );


		DashboardUtils.callServer( Constants.THRESHOLD_REPORT, Constants.SAVE_THRESHOLD_RECOMMENDED, param, RequestBuilder.POST, Constants.URL_THRESHOLD_SAVE_RECOMMENDED, null, new AsyncCallback<Response>()
		{
			@Override
			public void onFailure( Throwable caught )
			{

				new WarningMessageBox( "Report", caught.getLocalizedMessage() );
			}

			@Override
			public void onSuccess( Response response )
			{
				DashboardUtils.convertErrorTextJson(response.getText(), new AsyncCallback<ErrorInfo>() {
					@Override
					public void onFailure(Throwable caught) {

					}

					@Override
					public void onSuccess(ErrorInfo result) {
						MessageBox box = new MessageBox("Recommended Threshold", result.getErrorText());
						box.show();
					}
				});
				asyncCallback.onSuccess("saved");
			}
		} );
	}

	private StringComboBox getFilters()
	{
		StringComboBox filters = new StringComboBox();
		filters.setEmptyText( "All Thresholds" );
		filters.add( ThresholdType.getAllValues() );
		filters.setTriggerAction( ComboBoxCell.TriggerAction.ALL );

		filters.addBeforeSelectionHandler( new BeforeSelectionHandler<String>()
		{
			@Override
			public void onBeforeSelection( BeforeSelectionEvent<String> event )
			{
				DashboardUtils.logInfo("Selected ThresholdType: "+ event.getItem());
				ThresholdType selected = ThresholdType.getSelected( event.getItem());
				store.clear();
				getThresholdData( selected );
			}
		} );
		return filters;
	}

	private VerticalLayoutContainer getUpperPanel()
	{
		VerticalLayoutContainer v = new VerticalLayoutContainer();
		v.add( new HTML("The Threshold Analysis and Configuration utility does an analysis of the ping and bandwidth data collected <b>during the last 6 hours</b> when 1 minute resolution data is available.<br>"
				+ "<br>"
				+ "<b>THE GOAL</b> is to create realistic thresholds for devices like PLCs and Servers which could be having problems but which would never exceed the IntraVUE default value of 30.<br>"
				+ "<br>"
				+ "The thresholds recommended are NOT values that will never be exceeded, they will be exceeded infrequently and will be a better indication of devices in marginal states.  If many devices exceed their recommended values in the same minute, it will be an indication of something happening on the network. "
				+ "<br>"
				) );
		//v.setHeight( 80 );
		v.setWidth( "100%" );

		DashboardUtils.resize( v );
		return v;
	}

	public class MinComparator implements Comparator
	{
		@Override
		public int compare( Object p1, Object p2 )
		{
			StatData data1 = (StatData) p1;
			StatData data2 = (StatData) p2;
			return Double.compare(data1.getMin(), data2.getMin());
		}
	}

	public class MaxComparator implements Comparator
	{
		@Override
		public int compare( Object p1, Object p2 )
		{
			StatData data1 = (StatData) p1;
			StatData data2 = (StatData) p2;
			return Double.compare(data1.getMax(), data2.getMax());
		}
	}

	public class AvgComparator implements Comparator
	{
		@Override
		public int compare( Object p1, Object p2 )
		{
			StatData data1 = (StatData) p1;
			StatData data2 = (StatData) p2;
			return Double.compare(data1.getAvg(), data2.getAvg());
		}
	}

	public class XoverComparator implements Comparator
	{
		@Override
		public int compare( Object p1, Object p2 )
		{
			StatData data1 = (StatData) p1;
			StatData data2 = (StatData) p2;
			return Double.compare(data1.getAvg() + (5 * data1.getStddev()), data2.getAvg() + (5 * data2.getStddev()));
		}
	}

	public class StdDevComparator implements Comparator
	{
		@Override
		public int compare( Object p1, Object p2 )
		{
			StatData data1 = (StatData) p1;
			StatData data2 = (StatData) p2;
			return Double.compare(data1.getStddev(), data2.getStddev());
		}
	}

	public class StdDevMixComparator implements Comparator
	{
		@Override
		public int compare( Object p1, Object p2 )
		{
			StatData data1 = (StatData) p1;
			StatData data2 = (StatData) p2;
			return Double.compare(data1.getStdOverCounts().get( 0 ), data2.getStdOverCounts().get( 1 ));
		}
	}

	public class SamplesComparator implements Comparator
	{
		@Override
		public int compare( Object p1, Object p2 )
		{
			StatData data1 = (StatData) p1;
			StatData data2 = (StatData) p2;
			return Double.compare(data1.getCount(), data2.getCount());
		}
	}

	public static Cell<ThresholdPresentationData> getButtonCellIP()
	{
		ButtonCell<ThresholdPresentationData> symbolCell = new ButtonCell<ThresholdPresentationData>()
		{
			@Override
			public void render(Context context, ThresholdPresentationData ip, SafeHtmlBuilder sb)
			{
				if(ip!= null )
				{
					if(ip.getDescid()!=0)
						sb.appendHtmlConstant( "<span style='background-color:white; color:blue; cursor: pointer;  text-decoration: underline;'>" + ip.getIpaddress() + "</span>" );
					else
						sb.appendHtmlConstant( "<span style='background-color:white;'>" + ip.getIpaddress() + "</span>" );

				}
				else
				{
					sb.appendHtmlConstant("<span> &nbsp; </span>");
				}
			}

			@Override
			public void onBrowserEvent(Context context, Element parent, ThresholdPresentationData ip, NativeEvent event,
									   ValueUpdater<ThresholdPresentationData> valueUpdater)
			{
				super.onBrowserEvent( context, parent, ip, event, valueUpdater );

				String eventType = event.getType();
				if(ip!= null && ip.getDescid() != 0)
				{
					if ("click".equals( eventType ))
					{
						DashboardUtils.logInfo("Opening Ip :"+ ip.getIpaddress() +" with DescId: "+ ip.getDescid());
						DashboardUtils.onIpSelection( ip.getIpaddress(),  ip.getDescid());
					}
				}

			}
		};
		return symbolCell;
	}

	public class IpComparator implements Comparator
	{
		@Override
		public int compare(Object obj1, Object obj2) {
			try
			{
				ThresholdPresentationData ob1 = (ThresholdPresentationData) obj1;
				ThresholdPresentationData ob2 = (ThresholdPresentationData) obj2;
				String adr1 = ob1.getIpaddress();
				String adr2 = ob2.getIpaddress();

				if(adr1 == null || adr1.toString().isEmpty()) return -1;
				if(adr2 == null || adr2.toString().isEmpty()) return 1;

				String ad2 = adr2.toString();
				String[] ba1 = adr1.toString().split( "\\." );
				String[] ba2 = ad2.split( "\\." );

				for ( int i = 0; i < ba1.length; i++ )
				{
					int b1 = Integer.parseInt( ba1[ i ] );
					int b2 = Integer.parseInt( ba2[ i ] );

					if (b1 == b2)
						continue;
					if (b1 < b2)
						return -1;
					else
						return 1;
				}
				return 0;
			}
			catch ( Exception ex )
			{
				return 0;
			}

		}
	}

	///////////FILTER
	public void doFilter()
	{
		setColumnsToFilter("Type", "IP Address", "Network", "Samples", "Min", "Max", "Avg", "StdDev", "StdDev Over Mix", "5x Over", "Current", "Recommend", "Ct over Rec");
		filterWidget.colSearch.setVisible(true);
		filterWidget.columnToFilter.setVisible(true);

		filterWidget.buttonSearch.addSelectHandler(Event -> search());

		filterWidget.searchField.addKeyUpHandler(new KeyUpHandler()
		{

			@Override
			public void onKeyUp(KeyUpEvent event)
			{
				if(filterWidget.searchField.getText().isEmpty()  || (event.getNativeKeyCode() == 13 && !filterWidget.searchField.getText().isEmpty()))
					search();
			}
		});

		filterWidget.clearField.addSelectHandler(new SelectEvent.SelectHandler() {
			@Override
			public void onSelect(SelectEvent event) {
				filterWidget.searchField.setText("");
				search();
			}
		});

	}

	private void search() {
		activeFilterHeading();
		DashboardUtils.logInfo("Searching Grid for:" + filterWidget.searchField.getText());

		filter();
	}

	private void activeFilterHeading()
	{
		if(!filterWidget.searchField.getText().isEmpty()) filterWidget.filteredGridHeading.setVisible(true);
		else filterWidget.filteredGridHeading.setVisible(false);
	}

	private boolean filterTextSatisfies(String filterTextValue, ArrayList<String> params)
	{
		boolean satisfies = false;
		if (filterTextValue.contains(" "))
		{
			String[] parts = filterTextValue.split(" ");
			for (int i = 0; i < parts.length; i++)
			{

				satisfies = filterText(parts[i], params);
				if (!satisfies) break;
			}

			return satisfies;

		}
		else
		{
			return filterText(filterTextValue, params);
		}

	}

	private boolean filterText(String filterTextValue, ArrayList<String> params)
	{
		for(String param : params) {
			String p = param.replaceAll("\\s+", "");
			filterTextValue = filterTextValue.replaceAll("\\s+", "");

			if (GxtStringUtils.containsIgnoreCase(p, filterTextValue))
				return true;
		}
		return false;
	}



	public void filter(List<FilterConfig> filters, String filterTextValue)
	{
		if(tempList.isEmpty()) {
			for (T arp : list) {
				tempList.add(arp);
			}
		}
		list.clear();

		for (int j = 0; j < tempList.size(); j++)
		{
			boolean valid = true;

			if (filters != null)
			{
				for (int i = 0; i < filters.size(); i++)
				{
					valid = valid && filterTextSatisfies(filterTextValue, getParams(tempList.get(j), filterWidget.columnToFilter.getText()));

				}
			}
			if (valid) list.add(tempList.get(j));

		}

	}

	public void filter( String filterTextValue)
	{
		if(tempList.isEmpty()) {
			for (T arp : list) {
				tempList.add(arp);
			}
		}
		list.clear();
		store.clear();
		for (int j = 0; j < tempList.size(); j++)
		{
			boolean valid = true;

			valid = valid && filterTextSatisfies(filterTextValue, getParams(tempList.get(j),filterWidget.columnToFilter.getText()));

			if (valid) list.add(tempList.get(j));

		}
		store.addAll(list);

	}

	protected  void setColumnsToFilter(String... columns)
	{
		filterWidget.columnToFilter.clear();
		for(String column : columns)
			filterWidget.columnToFilter.add(column);
	}

	protected void filter() {
		filter(filterWidget.searchField.getText());

	}

	protected ArrayList<String> getParams(T connection, String column) {
		T param = connection;
		ArrayList<String> params = new ArrayList<String>();
		switch (column) {
			case "IP Address":
				params.add(param.getIpaddress());
				break;
			case "Type":
				params.add(param.getThreshType() + "");
				break;
			case "Network":
				params.add(param.getNetwork() + "");
				break;
			case "Samples":
				params.add(param.getStatData().getCount() + "");
				break;
			case "Min":
				params.add(param.getStatData().getMin() + "");
				break;
			case "Max":
				params.add(param.getStatData().getMax() + "");
				break;
			case "Avg":
				params.add(param.getStatData().getAvg() + "");
				break;
			case "StdDev":
				params.add(param.getStatData().getStddev() + "");
				break;
			case "StdDev Over Mix":
				params.add(param.getStatData().getStdOverCounts() + "");
				break;
			case "5x Over":
				params.add(param.getStatData().getStdOverToString() + "");
				break;
			case "Current":
				params.add(param.getStatData().getCur_threshold() + "");
				break;
			case "Recommend":
				params.add(param.getStatData().getRecommended() + "");
				break;
			case "Ct over Rec":
				params.add(param.getCountOverRec() + "");
				break;
			case "":
			case "All":
				params.add(param.getIpaddress());
				params.add(param.getThreshType() + "");
				params.add(param.getNetwork());
				params.add(param.getStatData().getCount() + "");
				params.add(param.getStatData().getMin() + "");
				params.add(param.getStatData().getMax() + "");
				params.add(param.getStatData().getAvg() + "");
				params.add(param.getStatData().getStddev() + "");
				params.add(param.getStatData().getStdOverCounts() + "");
				params.add(param.getStatData().getStdOverToString() + "");
				params.add(param.getStatData().getCur_threshold() + "");
				params.add(param.getStatData().getRecommended() + "");
				params.add(param.getCountOverRec() + "");
				break;

		}
				return params;
	}

}
