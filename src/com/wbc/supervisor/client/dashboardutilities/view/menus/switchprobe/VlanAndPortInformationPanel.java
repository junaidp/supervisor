package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.CiscoVlanData;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.InterfaceData;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.core.client.dom.ScrollSupport;
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
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.List;

public class VlanAndPortInformationPanel extends SwitchProbePanel
{
	public VlanAndPortInformationPanel()
	{
		add(filterWidget);
		add( grid() );
		init();
	}

	@Override
	protected ArrayList<String> getParams(BaseGrid baseGrid, String column) {
		CiscoVlanData param = (CiscoVlanData) baseGrid;
		ArrayList<String> params = new ArrayList<String>();
		params.add(param.getType()+"");
		params.add(param.getMgt()+"");
		params.add(param.getVlan()+"");
		return params;
	}

	@Override
	protected void filter() {
		filter(filterWidget.searchField.getText());
	}

	private static final VlanProperties props = GWT.create( VlanProperties.class );

	public interface VlanProperties extends PropertyAccess<SwitchErrorData>
	{
		@Editor.Path( "key" )
		ModelKeyProvider<CiscoVlanData> key();

		ValueProvider<CiscoVlanData, Integer> mgt();

		ValueProvider<CiscoVlanData, Integer> vlan();

		ValueProvider<CiscoVlanData, Integer> type();

	}

	public VerticalLayoutContainer grid()
	{
		VerticalLayoutContainer d = new VerticalLayoutContainer();
		d.setScrollMode( ScrollSupport.ScrollMode.AUTOY );

		ColumnConfig<CiscoVlanData, Integer> mgtCol = new ColumnConfig<CiscoVlanData, Integer>( props.mgt(), 150, "MGT" );
		ColumnConfig<CiscoVlanData, Integer> vlanCol = new ColumnConfig<CiscoVlanData, Integer>( props.vlan(), 150, "VLAN" );
		ColumnConfig<CiscoVlanData, Integer> typeCol = new ColumnConfig<CiscoVlanData, Integer>( props.type(), 150, "TYPE" );

		List<ColumnConfig<CiscoVlanData, ?>> columns = new ArrayList<ColumnConfig<CiscoVlanData, ?>>();

		columns.add( mgtCol );
		columns.add( vlanCol );
		columns.add( typeCol );

		ColumnModel<CiscoVlanData> cm = new ColumnModel<CiscoVlanData>( columns );

		store = new ListStore<CiscoVlanData>( props.key() );

		final Grid<CiscoVlanData> grid = new Grid<CiscoVlanData>( store, cm );
		DashboardUtils.setDefaultGridStyle( grid );

		//FILTERS
		NumericFilter<CiscoVlanData, Integer> mgtFilter = new NumericFilter<CiscoVlanData, Integer>( props.mgt(), new NumberPropertyEditor.IntegerPropertyEditor() );
		NumericFilter<CiscoVlanData, Integer> vlanFilter = new NumericFilter<CiscoVlanData, Integer>( props.vlan(), new NumberPropertyEditor.IntegerPropertyEditor() );
		NumericFilter<CiscoVlanData, Integer> typFilter = new NumericFilter<CiscoVlanData, Integer>( props.type(), new NumberPropertyEditor.IntegerPropertyEditor() );

		GridFilters<CiscoVlanData> filters = new GridFilters<CiscoVlanData>();
		filters.initPlugin( grid );
		filters.setLocal( true );
		filters.addFilter( mgtFilter );
		filters.addFilter( vlanFilter );
		filters.addFilter( typFilter );

		// Stage manager, load the previous state
		GridFilterStateHandler<CiscoVlanData> handler = new GridFilterStateHandler<CiscoVlanData>( grid, filters );
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

	protected void populate( ArrayList<CiscoVlanData> result )
	{
		try
		{
			//GWT.log( "POPULATING VLAN INFO Panel with size: " + result.size()  );
			store.clear();
			this.list = result;
			store.addAll( list );
			UtilGrid utilGrid = new UtilGrid();
			utilGrid.setListCiscoVlanData(list);
			filterWidget.exportWidget.export(null, utilGrid);
		}
		catch ( Exception ex )
		{
			new WarningMessageBox( "VLAN INFORMATION PANEL", ex.getMessage() );
		}

	}
}