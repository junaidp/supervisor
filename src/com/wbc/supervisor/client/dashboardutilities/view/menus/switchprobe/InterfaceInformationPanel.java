package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.InterfaceData;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.PortDataExtended;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
import com.sencha.gxt.widget.core.client.grid.ColumnModel;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.LiveToolItem;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;

public class InterfaceInformationPanel extends SwitchProbePanel
{
	public InterfaceInformationPanel()
	{
		add(filterWidget);
		add( grid() );
		init();

	}

	@Override
	protected ArrayList<String> getParams(BaseGrid baseGrid, String columnToFilter) {
		InterfaceData param = (InterfaceData) baseGrid;
		ArrayList<String> params = new ArrayList<String>();
		params.add(param.getIfID()+"");
		params.add(param.getType()+"");
		params.add(param.getDescription());
		params.add(param.getMac());
		params.add(param.getSpeedBps()+"");
		params.add(param.getSpeedMps()+"");
		return params;
	}

	@Override
	protected void filter() {
		filter(filterWidget.searchField.getText());
	}

	private static final InterfaceInformationPanel.VProperties props = GWT.create( InterfaceInformationPanel.VProperties.class );

	public interface VProperties extends PropertyAccess<SwitchErrorData>
	{
		@Editor.Path( "key" )
		ModelKeyProvider<InterfaceData> key();

		ValueProvider<InterfaceData, Integer> ifID();
		ValueProvider<InterfaceData, Integer> type();
		ValueProvider<InterfaceData, String> description();
		ValueProvider<InterfaceData, String> mac();
		ValueProvider<InterfaceData, Long> speedBps();
		ValueProvider<InterfaceData, Long> speedMps();

	}

	public VerticalLayoutContainer grid()
	{
		ColumnConfig<InterfaceData, Integer> ifIdCol = new ColumnConfig<InterfaceData, Integer>( props.ifID(), 150, "Interface" );
		ColumnConfig<InterfaceData, Integer> typeCol = new ColumnConfig<InterfaceData, Integer>( props.type(), 150, "TYPE" );
		ColumnConfig<InterfaceData, String> descriptionCol = new ColumnConfig<InterfaceData, String>( props.description(), 150, "Description" );
		ColumnConfig<InterfaceData, String> macCol = new ColumnConfig<InterfaceData, String>( props.mac(), 150, "MAC Address" );
		ColumnConfig<InterfaceData, Long> speedCol = new ColumnConfig<InterfaceData, Long>( props.speedBps(), 150, "Speed (bps)" );
		ColumnConfig<InterfaceData, Long> speedColMps = new ColumnConfig<InterfaceData, Long>( props.speedMps(), 150, "Speed (Mps)" );

		List<ColumnConfig<InterfaceData, ?>> columns = new ArrayList<ColumnConfig<InterfaceData, ?>>();

		columns.add( ifIdCol );
		columns.add( macCol );
		columns.add( descriptionCol );
		columns.add( typeCol );
		columns.add( speedCol );
		columns.add( speedColMps );


		ColumnModel<InterfaceData> cm = new ColumnModel<InterfaceData>( columns );

		store = new ListStore<InterfaceData>( props.key() );

		final Grid<InterfaceData> grid = new Grid<InterfaceData>( store, cm );
		DashboardUtils.setDefaultGridStyle( grid );

		//FILTERS
		NumericFilter<InterfaceData, Integer> ifIDFilter = new NumericFilter<InterfaceData, Integer>( props.ifID(), new NumberPropertyEditor.IntegerPropertyEditor() );
		NumericFilter<InterfaceData, Integer> typeFilter = new NumericFilter<InterfaceData, Integer>( props.type(), new NumberPropertyEditor.IntegerPropertyEditor() );
		NumericFilter<InterfaceData, Long> speedMpsFilter = new NumericFilter<InterfaceData, Long>( props.speedMps(), new NumberPropertyEditor.LongPropertyEditor(  ) );
		NumericFilter<InterfaceData, Long> speedBpsFilter = new NumericFilter<InterfaceData, Long>( props.speedMps(), new NumberPropertyEditor.LongPropertyEditor(  ) );

		StringFilter<InterfaceData> descFilter = new StringFilter<InterfaceData>(props.description());
		StringFilter<InterfaceData> macFilter = new StringFilter<InterfaceData>(props.mac());


		GridFilters<InterfaceData> filters = new GridFilters<InterfaceData>();
		filters.initPlugin( grid );
		filters.setLocal( true );
		filters.addFilter( ifIDFilter );
		filters.addFilter( typeFilter );
		filters.addFilter( speedMpsFilter );
		filters.addFilter( speedBpsFilter );
		filters.addFilter( descFilter );
		filters.addFilter( macFilter );

		// Stage manager, load the previous state
		GridFilterStateHandler<InterfaceData> handler = new GridFilterStateHandler<InterfaceData>( grid, filters );
		handler.loadState();

		ToolBar toolBar = new ToolBar();
		toolBar.setBorders(false);
		toolBar.add(grid);
		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, 25));
		verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 270));

		grid.setWidth("100%");
		return verticalLayoutContainer;

	}

	protected void populate( ArrayList<InterfaceData> result )
	{
		try
		{
			// DashboardUtils.logInfo( "POPULATING InterfaceInformation Panel with size: "+result.size() );
			store.clear();
			this.list = result;
			store.addAll( list );
			UtilGrid utilGrid = new UtilGrid();
			utilGrid.setListInterfaceData(list);
			filterWidget.exportWidget.export(null, utilGrid);
		}
		catch ( Exception ex )
		{
			new WarningMessageBox( "INTERFACE INFORMATION PANEL", ex.getMessage() );
		}

	}
}