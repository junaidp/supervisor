package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.IpComparator;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.IpData;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.PortDataExtended;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.sencha.gxt.cell.core.client.ButtonCell;
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
import java.util.Comparator;
import java.util.List;

public class IpInformationPanel extends SwitchProbePanel
{

	public IpInformationPanel()
	{
		add(filterWidget);
		add( grid() );
		init();

	}

	@Override
	protected ArrayList<String> getParams(BaseGrid baseGrid, String columnToFilter) {
		IpData param = (IpData) baseGrid;
		ArrayList<String> params = new ArrayList<String>();
		params.add(param.getIp());
		params.add(param.getInterfaceNum()+"");
		params.add(param.getNetMask());
		return params;
	}

	@Override
	protected void filter() {
		filter(filterWidget.searchField.getText());
	}

	private static final IpInformationPanel.VProperties props = GWT.create( IpInformationPanel.VProperties.class );

	public interface VProperties extends PropertyAccess<SwitchErrorData>
	{
		@Editor.Path( "key" )
		ModelKeyProvider<IpData> key();

		ValueProvider<IpData, String> ip();

		ValueProvider<IpData, Integer> interfaceNum();

		ValueProvider<IpData, String> netMask();

	}

	public VerticalLayoutContainer grid()
	{
		ValueProvider<IpData, IpData> valueProviderIp = new ValueProvider<IpData, IpData>()
		{
			@Override
			public String getPath()
			{
				return "";
			}

			@Override
			public IpData getValue(IpData taskDTO)
			{
				return taskDTO;
			}

			@Override
			public void setValue(IpData object, IpData value)
			{
			}
		};


		ColumnConfig<IpData, IpData> ipCol = new ColumnConfig<IpData, IpData>( valueProviderIp, 150, "IP Address" );
		ColumnConfig<IpData, Integer> interfaceNumCol = new ColumnConfig<IpData, Integer>( props.interfaceNum(), 150, "Interface Number" );
		ColumnConfig<IpData, String> netMaskCol = new ColumnConfig<IpData, String>( props.netMask(), 150, "Netmask" );

		List<ColumnConfig<IpData, ?>> columns = new ArrayList<ColumnConfig<IpData, ?>>();

		columns.add( ipCol );
		columns.add( interfaceNumCol );
		columns.add( netMaskCol );

		ColumnModel<IpData> cm = new ColumnModel<IpData>( columns );

		store = new ListStore<IpData>( props.key() );

		final Grid<IpData> grid = new Grid<IpData>( store, cm );
		DashboardUtils.setDefaultGridStyle( grid );

		ipCol.setComparator(new IpComparator() );
		ipCol.setCell( getButtonCellIP() );

		//FILTERS
		StringFilter<IpData> ipFilter = new StringFilter<IpData>(props.ip());
		NumericFilter<IpData, Integer> interfaceNumFilter = new NumericFilter<IpData, Integer>( props.interfaceNum(), new NumberPropertyEditor.IntegerPropertyEditor() );
		StringFilter<IpData> netMaskFilter = new StringFilter<IpData>(props.netMask());

		GridFilters<IpData> filters = new GridFilters<IpData>();
		filters.initPlugin( grid );
		filters.setLocal( true );
		filters.addFilter( ipFilter );
		filters.addFilter( interfaceNumFilter );
		filters.addFilter( netMaskFilter );

		// Stage manager, load the previous state
		GridFilterStateHandler<IpData> handler = new GridFilterStateHandler<IpData>( grid, filters );
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

	protected void populate( ArrayList<IpData> result )
	{
		try
		{
			// DashboardUtils.logInfo( "POPULATING IP PANEL with size: " + result.size()  );
			store.clear();
			this.list = result;
			store.addAll( list );
			UtilGrid utilGrid = new UtilGrid();
			utilGrid.setListIpInformation(list);
			filterWidget.exportWidget.export(null, utilGrid);

		}
		catch ( Exception ex )
		{
			new WarningMessageBox( "IP PANEL", ex.getMessage() );
		}

	}

	public static Cell<IpData> getButtonCellIP()
	{
		ButtonCell<IpData> symbolCell = new ButtonCell<IpData>()
		{
			@Override
			public void render(Context context, IpData ip, SafeHtmlBuilder sb)
			{
				if(ip!= null )
				{
					sb.appendHtmlConstant( "<span style='background-color:white;'>" + ip.getIp() + "</span>" );

				}
				else
				{
					sb.appendHtmlConstant("<span> &nbsp; </span>");
				}
			}

			@Override
			public void onBrowserEvent(Context context, Element parent, IpData ip, NativeEvent event,
									   ValueUpdater<IpData> valueUpdater)
			{
				super.onBrowserEvent( context, parent, ip, event, valueUpdater );

				String eventType = event.getType();


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
				IpData ob1 = (IpData) obj1;
				IpData ob2 = (IpData) obj2;
				String adr1 = ob1.getIp();
				String adr2 = ob2.getIp();

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
}