package com.wbc.supervisor.client.dashboardutilities.view.menus.switchprobe;

import com.wbc.supervisor.client.dashboardutilities.images.Images;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.utils.FilterWidget;
import com.wbc.supervisor.client.dashboardutilities.utils.GxtStringUtils;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.ExportWidget;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.loader.*;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.event.SelectEvent;
import com.sencha.gxt.widget.core.client.form.StringComboBox;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.BooleanFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;

import java.util.ArrayList;
import java.util.List;

public abstract class SwitchProbePanel<T extends BaseGrid> extends VerticalLayoutContainer
{
    protected GridFilters<T> gridFilters;
    protected BooleanFilter<T> filterText = null;
    protected ArrayList<T> tempList =  new ArrayList<T>();
    protected List<FilterConfig> filters = null;
    protected ArrayList<T> list;
    protected Grid<T> grid = null;
	protected ListStore<T> store = null;
	protected FilterWidget filterWidget = new FilterWidget();

	protected void init(){

		if(gridFilters != null){
			gridFilters.initPlugin(grid);
			gridFilters.setAutoReload(true);
			gridFilters.setLocal(false);
		}
	}

	public SwitchProbePanel(){
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
		if(filterText == null && grid!=null) {
			ValueProvider<? super T, Boolean> vp = (ValueProvider) grid.getColumnModel().getColumns().get(1).getValueProvider();
			filterText = new BooleanFilter<T>(vp);

		}
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

	protected void restartFilterText()
	{
		filterText.setActive(false, false);
		if (!filterWidget.searchField.getText().isEmpty())
		{
			gridFilters.addFilter(filterText);
			filterText.setActive(true, false);
		}
		else
		{
			gridFilters.removeFilter(filterText);
			filterText.setActive(false, false);
		}
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

			valid = valid && filterTextSatisfies(filterTextValue, getParams(tempList.get(j),""));

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

	protected abstract ArrayList<String>  getParams(T t, String column);
	protected abstract void filter();

}

