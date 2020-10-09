package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.MacInfo;
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
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;

public class PortInformationPanel extends SwitchProbePanel
{
	public PortInformationPanel()
	{
		add(filterWidget);
		add( grid() );
		init();
	}

	@Override
	protected ArrayList<String> getParams(BaseGrid baseGrid, String columnToFilter) {
		PortDataExtended param = (PortDataExtended) baseGrid;
		ArrayList<String> params = new ArrayList<String>();
		params.add(param.getPortIf()+"");
		params.add(param.getType()+"");
		params.add(param.getPortNumber()+"");
		params.add(param.getDescription());
		params.add(param.getSpeed()+"");
		return params;
	}

	@Override
	protected void filter() {
		filter(filterWidget.searchField.getText());
	}

	private static final PortInformationPanel.VProperties props = GWT.create( PortInformationPanel.VProperties.class );

	private int portNumber;
	private int portIf;
	private String description;
	private int type;
	private long speed;

	public interface VProperties extends PropertyAccess<SwitchErrorData>
	{
		@Editor.Path( "key" )
		ModelKeyProvider<PortDataExtended> key();

		ValueProvider<PortDataExtended, Integer> portNumber();

		ValueProvider<PortDataExtended, Integer> portIf();

		ValueProvider<PortDataExtended, Integer> type();

		ValueProvider<PortDataExtended, Long> speed();

		ValueProvider<PortDataExtended, String> description();

	}

	public VerticalLayoutContainer grid()
	{
		ColumnConfig<PortDataExtended, Integer> portNumberCol = new ColumnConfig<PortDataExtended, Integer>( props.portNumber(), 150, "Port Number" );
		ColumnConfig<PortDataExtended, Integer> portIfCol = new ColumnConfig<PortDataExtended, Integer>( props.portIf(), 150, "Port Interface" );
		ColumnConfig<PortDataExtended, String> descCol = new ColumnConfig<PortDataExtended, String>( props.description(), 150, "Description" );
		ColumnConfig<PortDataExtended, Integer> typeCol = new ColumnConfig<PortDataExtended, Integer>( props.type(), 150, "Type" );
		ColumnConfig<PortDataExtended, Long> speedCol = new ColumnConfig<PortDataExtended, Long>( props.speed(), 150, "Speed" );

		List<ColumnConfig<PortDataExtended, ?>> columns = new ArrayList<ColumnConfig<PortDataExtended, ?>>();

		columns.add( portNumberCol );
		columns.add( portIfCol );
		columns.add( descCol );
		columns.add( typeCol );
		columns.add( speedCol );

		ColumnModel<PortDataExtended> cm = new ColumnModel<PortDataExtended>( columns );

		store = new ListStore<PortDataExtended>( props.key() );

		final Grid<PortDataExtended> grid = new Grid<PortDataExtended>( store, cm );
		DashboardUtils.setDefaultGridStyle( grid );

		//FILTERS
		NumericFilter<PortDataExtended, Integer> portNumberFilter = new NumericFilter<PortDataExtended, Integer>( props.portNumber(), new NumberPropertyEditor.IntegerPropertyEditor() );
		NumericFilter<PortDataExtended, Integer> portIfFilter = new NumericFilter<PortDataExtended, Integer>( props.portIf(), new NumberPropertyEditor.IntegerPropertyEditor() );
		StringFilter<PortDataExtended> descFilter = new StringFilter<PortDataExtended>(props.description());
		NumericFilter<PortDataExtended, Integer> typeFilter = new NumericFilter<PortDataExtended, Integer>( props.type(), new NumberPropertyEditor.IntegerPropertyEditor() );
		NumericFilter<PortDataExtended, Long> speedFilter = new NumericFilter<PortDataExtended, Long>( props.speed(), new NumberPropertyEditor.LongPropertyEditor() );


		GridFilters<PortDataExtended> filters = new GridFilters<PortDataExtended>();
		filters.initPlugin( grid );
		filters.setLocal( true );
		filters.addFilter( portNumberFilter );
		filters.addFilter( portIfFilter );
		filters.addFilter( descFilter );
		filters.addFilter( typeFilter );
		filters.addFilter( speedFilter );

		// Stage manager, load the previous state
		GridFilterStateHandler<PortDataExtended> handler = new GridFilterStateHandler<PortDataExtended>( grid, filters );
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

	protected void populate( ArrayList<PortDataExtended> result )
	{
		try
		{
			// GWT.log( "POPULATING Port PANEL, with size: " + result.size());
			store.clear();
			this.list = result;
			store.addAll( list );
			UtilGrid utilGrid = new UtilGrid();
			utilGrid.setListPortData(list);
			filterWidget.exportWidget.export(null, utilGrid);
		}
		catch ( Exception ex )
		{
			new WarningMessageBox( "PORT PANEL", ex.getMessage() );
		}

	}
}