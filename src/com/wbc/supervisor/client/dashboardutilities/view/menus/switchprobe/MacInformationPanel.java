package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.IpComparator;
import com.wbc.supervisor.client.dashboardutilities.utils.LiveGridViewExt;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.SwitchErrorData;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.ArpDataExtended;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.MacInfo;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.SymbolDTO;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.dom.client.Element;
import com.google.gwt.dom.client.NativeEvent;
import com.google.gwt.dom.client.Style;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.cell.core.client.ButtonCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.client.loader.RpcProxy;
import com.sencha.gxt.data.shared.*;
import com.sencha.gxt.data.shared.loader.*;
import com.sencha.gxt.state.client.GridFilterStateHandler;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.NumberPropertyEditor;
import com.sencha.gxt.widget.core.client.grid.*;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.grid.filters.NumericFilter;
import com.sencha.gxt.widget.core.client.grid.filters.StringFilter;
import com.sencha.gxt.widget.core.client.tips.QuickTip;
import com.sencha.gxt.widget.core.client.toolbar.ToolBar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@SuppressWarnings( "GwtInconsistentSerializableClass" )
public class MacInformationPanel extends SwitchProbePanel
{

	protected PagingLoader<FilterPagingLoadConfig, PagingLoadResult<MacInfo>> pagingLoader;
	private LiveGridViewExt<MacInfo> gridView;

	public MacInformationPanel()
	{
		add(filterWidget);
		add(grid());
		init();
		initGlobalSearch();
	}

	private void initGlobalSearch() {
		setColumnsToFilter("ip","mac", "port", "portDescription", "vlanNum", "name", "vendor");
		filterWidget.colSearch.setVisible(true);
		filterWidget.columnToFilter.setVisible(true);
		//filterWidget.labelSearch.getElement().getStyle().setPaddingLeft(50, Style.Unit.PX);
	}

	@Override
	protected ArrayList<String> getParams(BaseGrid baseGrid, String column) {
		MacInfo arp = (MacInfo) baseGrid;
		ArrayList<String> params = new ArrayList<String>();
		switch (column) {
			case "mac":
				params.add(arp.getMac());
				break;
			case "port":
				params.add(arp.getPort() + "");
				break;
			case "portDescription":
				params.add(arp.getPortDescription());
				break;
			case "vlanNum":
				params.add(arp.getVlanNum() + "");
				break;
			case "name":
				params.add(arp.getName() + "");
				break;
			case "vendor":
				params.add(arp.getVendor());
				break;
			case "ip":
				params.add(arp.getIp());
				break;
			case "All":
			case "":
				params.add(arp.getMac());
				params.add(arp.getPort() + "");
				params.add(arp.getPortDescription());
				params.add(arp.getVlanNum() + "");
				params.add(arp.getName() + "");
				params.add(arp.getVendor());
				break;
		}
		return params;
	}

	private static final MacProperties props = GWT.create( MacProperties.class);
	private ListStore<MacInfo> store =null;

	public interface MacProperties extends PropertyAccess<SwitchErrorData>
	{
		@Editor.Path( "key" )
		ModelKeyProvider<MacInfo> key();

		ValueProvider<MacInfo, String> mac();
		ValueProvider<MacInfo, Integer> port();
		ValueProvider<MacInfo, String> portDescription();
		ValueProvider<MacInfo, Integer> vlanNum();
		ValueProvider<MacInfo, Integer> descid();
		ValueProvider<MacInfo, String> ip();
		ValueProvider<MacInfo, String> name();
		ValueProvider<MacInfo, String> vendor();
		ValueProvider<MacInfo, SymbolDTO> symbolDTO();

	}

	public VerticalLayoutContainer grid()
	{


		ValueProvider<MacInfo, String> valueProviderIp = new ValueProvider<MacInfo, String>()
		{
			@Override
			public String getPath()
			{
				return "";
			}

			@Override
			public String getValue(MacInfo taskDTO)
			{
				return taskDTO.getIp();
			}

			@Override
			public void setValue(MacInfo object, String value)
			{
			}
		};

		ColumnConfig<MacInfo, String> macCol = new ColumnConfig<MacInfo, String>(props.mac(), 150, "Mac");
		ColumnConfig<MacInfo, Integer> portCol = new ColumnConfig<MacInfo, Integer>(props.port(), 150, "Port");
		ColumnConfig<MacInfo, String> descCol = new ColumnConfig<MacInfo, String>(props.portDescription(), 150, "Port Desc");
		ColumnConfig<MacInfo, Integer> vlanNumCol = new ColumnConfig<MacInfo, Integer>(props.vlanNum(), 150, "Vlan Num");
		ColumnConfig<MacInfo, Integer> descIdCol = new ColumnConfig<MacInfo, Integer>(props.descid(), 150, "Desc Id");

		ColumnConfig<MacInfo, String> ipCol = new ColumnConfig<MacInfo, String>(props.ip(), 150, "IP");
		ColumnConfig<MacInfo, String> nameCol = new ColumnConfig<MacInfo, String>(props.name(), 150, "Name");
		ColumnConfig<MacInfo, String> ivendorCol = new ColumnConfig<MacInfo, String>(props.vendor(), 150, "Vendor");

		ipCol.setComparator(new IpComparator() );
		ipCol.setCell( getButtonCellIP() );

    	List<ColumnConfig<MacInfo, ?>> columns = new ArrayList<ColumnConfig<MacInfo, ?>>();

		columns.add(ipCol);
		columns.add(macCol);
		columns.add(portCol);
		columns.add(descCol);
		columns.add(vlanNumCol);
	//	columns.add(descIdCol);
		columns.add(nameCol);
		columns.add(ivendorCol);

		ColumnModel<MacInfo> cm = new ColumnModel<MacInfo>(columns);

		store = new ListStore<MacInfo>(props.key());
		pagingLoader = getLoader(store);
		gridView = initialiseLiveGrid();


		grid = new Grid<MacInfo>(store, cm) {
			@Override
			protected void onAfterFirstAttach() {
				super.onAfterFirstAttach();
				Scheduler.get().scheduleDeferred(new Scheduler.ScheduledCommand() {
					@Override
					public void execute() {
						pagingLoader.load(0, gridView.getCacheSize());

					}
				});
			}
		};

		DashboardUtils.setDefaultGridStyle( grid );
		grid.setLoader(pagingLoader);
		grid.setLoadMask(true);
		grid.setView(gridView);

		ToolBar toolBar = new ToolBar();
		toolBar.setBorders(false);
		toolBar.add(new LiveToolItem(grid));
		VerticalLayoutContainer verticalLayoutContainer = new VerticalLayoutContainer();
		verticalLayoutContainer.add(toolBar, new VerticalLayoutData(1, 25));
		verticalLayoutContainer.add(grid, new VerticalLayoutData(1, 500));

		grid.setWidth("100%");
		return verticalLayoutContainer;

	}

	protected void populate( List<MacInfo> list )
	{
		try
		{
			// DashboardUtils.logInfo( "POPULATING MAC INFO with size " + list.size());
			for(MacInfo lis : list){
				if(lis.getIp() == null){
					lis.setIp("");
				}
			}
			this.list = (ArrayList<MacInfo>) list;

			UtilGrid utilGrid = new UtilGrid();
			utilGrid.setListMacInfo((ArrayList<MacInfo>) list);

			filterWidget.exportWidget.export(null, utilGrid);
			// DashboardUtils.logInfo( "POPULATING MAC INFO DONE with size: " + list.size()  );
		}catch(Exception ex)
		{
			new WarningMessageBox( "MAC INFORMATIONPANEL", ex.getMessage() );
		}

	}

	protected PagingLoader<FilterPagingLoadConfig, PagingLoadResult<MacInfo>> getLoader(ListStore<MacInfo> store)
	{
		RpcProxy<FilterPagingLoadConfig, PagingLoadResult<MacInfo>> proxy = new RpcProxy<FilterPagingLoadConfig, PagingLoadResult<MacInfo>>()
		{
			@Override
			public void load(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<MacInfo>> callback)
			{

				getDatas(loadConfig, callback);
			}
		};

		PagingLoader<FilterPagingLoadConfig, PagingLoadResult<MacInfo>> loader = new PagingLoader<FilterPagingLoadConfig, PagingLoadResult<MacInfo>>(proxy);

		loader.useLoadConfig(new FilterPagingLoadConfigBean());
		loader.addLoadHandler(new LoadResultListStoreBinding<FilterPagingLoadConfig, MacInfo, PagingLoadResult<MacInfo>>(store));
		loader.setRemoteSort(true);
		gridFilters = new GridFilters<ArpDataExtended>(loader);
		return loader;
	}

	private void getDatas(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<MacInfo>> callback)
	{
		try
		{
			if (list == null)
			{
				callback.onFailure(new Exception("empty analyseData"));
				return;
			}

			final int offset = loadConfig.getOffset();
			int limit = loadConfig.getLimit();
			final List<MacInfo> datas = new ArrayList<MacInfo>();
			List<? extends SortInfo> sortInfo = loadConfig.getSortInfo();
			filters = loadConfig.getFilters();
			filter(filters, filterWidget.searchField.getText());
			sortFilteredData(sortInfo, grid);

			int end = offset + limit;
			if(end > list.size()) end = list.size();
			datas.clear();

			for (int i = offset; i < end; i++)
			{
				datas.add((MacInfo) list.get( i ));
			}


			PagingLoadResult<MacInfo> result = new PagingLoadResult<MacInfo>()
			{

				private static final long serialVersionUID = 1L;

				@Override
				public List<MacInfo> getData()
				{

					return datas;
				}

				@Override
				public void setTotalLength(int totalLength)
				{

				}

				@Override
				public void setOffset(int offset)
				{
				}

				@Override
				public int getTotalLength()
				{
					return list.size();
				}

				@Override
				public int getOffset()
				{
					return offset;
				}
			};
			callback.onSuccess(result);
		}
		catch (

				Exception ex)
		{
			System.out.println("err");
		}
	}


	public  Cell<String> getButtonCellIP()
	{
		ButtonCell<String> symbolCell = new ButtonCell<String>()
		{
			@Override
			public void render(Context context, String ips, SafeHtmlBuilder sb)
			{

					String key = context.getKey().toString();
					MacInfo ip = (MacInfo) grid.getStore().findModelWithKey(key);
				if(ip!= null)
				{
					if(ip.getDescid()!=0)
						sb.appendHtmlConstant( "<span style='background-color:white; color:blue; cursor: pointer;  text-decoration: underline;'>" + ip.getIp() + "</span>" );
					else
						sb.appendHtmlConstant( "<span style='background-color:white;'>" + ip.getIp() + "</span>" );

				}
				else
				{
					sb.appendHtmlConstant("<span> &nbsp; </span>");
				}
			}

			@Override
			public void onBrowserEvent(Context context, Element parent, String ips, NativeEvent event,
									   ValueUpdater<String> valueUpdater)
			{
				super.onBrowserEvent( context, parent, ips, event, valueUpdater );

				String eventType = event.getType();
				String key = context.getKey().toString();
				MacInfo ip = (MacInfo) grid.getStore().findModelWithKey(key);
				if(ip!= null && ip.getDescid() != 0)
				{
					if ("click".equals( eventType ))
					{
						DashboardUtils.logInfo("Opening Ip :"+ ip.getIp() +" with DescId: "+ ip.getDescid());
						DashboardUtils.onIpSelection( ip.getIp(),  ip.getDescid());
					}
				}

			}
		};
		return symbolCell;
	}

	private LiveGridViewExt<MacInfo> initialiseLiveGrid()
	{
		return new LiveGridViewExt<MacInfo>()
		{
			@Override
			protected void initHeader()
			{
				super.initHeader();

				// remove tooltip
				ColumnHeader<MacInfo> header = getHeader();
				header.removeToolTip();
				header.disableEvents(); // Disable default tooltip on header
			}

			private native QuickTip getQuickTip(ColumnHeader<MacInfo> header)
				/*-{
					return header.@com.sencha.gxt.widget.core.client.grid.ColumnHeader::quickTip;
				}-*/;
		};
	}

	public void sortFilteredData(List<? extends SortInfo> sortInfo, Grid<MacInfo> grid)
	{
		if (!sortInfo.isEmpty())
		{
			SortDir sortDir = sortInfo.get(0).getSortDir();
			String s = sortInfo.get(0).getSortField();
			int index = getColumnIndex(s);
			DashboardUtils.logInfo("Sorting MacInfo, Column: "+ s +"Order: "+ sortDir.toString());
			ValueProvider<MacInfo, Comparable> vp = (ValueProvider) grid.getColumnModel().getColumns().get(index).getValueProvider();
			Comparator comparator = getComparator(vp, sortDir);

			Collections.sort(list, comparator);

		}
		else
		{
			//ValueProvider<MacInfo, Comparable> vp = (ValueProvider) grid.getColumnModel().getColumns().get(0).getValueProvider();
			//Comparator comparator = getComparator(vp, SortDir.DESC);
			//Collections.sort(list, comparator);
		}

	}

	private int getColumnIndex(String s) {

		switch (s){
			case "mac":
				return 1;
			case "port":
				return 2;
			case "portDescription":
				return 3;
			case "vlanNum":
				return 4;
			case "name":
				return 5;
			case "vendor":
				return 6;
				default:
				return 0;
		}
	}


	public <V extends Comparable<V>> Comparator<MacInfo> getComparator(final ValueProvider<? super MacInfo, V> property, SortDir direction)
	{

		Comparator<MacInfo> comp = new Comparator<MacInfo>()
		{
			@Override
			public int compare(MacInfo o1, MacInfo o2)
			{
				V v1 = property.getValue(o1);
				V v2 = property.getValue(o2);
				if ((v1 == null & v2 != null) || (v1 != null && v2 == null)) { return v1 == null ? -1 : 1; }
				if (v1 == null & v2 == null) { return 0; }
				if (direction == SortDir.ASC) return v1.compareTo(v2);
				else
					return v2.compareTo(v1);
			}
		};
		return comp;
	}

	@Override
	protected void filter() {
		restartFilterText();
		filter(filters, filterWidget.searchField.getText());
	}
}
