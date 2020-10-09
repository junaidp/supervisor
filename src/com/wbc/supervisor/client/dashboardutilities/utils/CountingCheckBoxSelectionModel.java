package com.wbc.supervisor.client.dashboardutilities.utils;

import com.wbc.supervisor.shared.dashboardutilities.switchprobe.MacInfo;
import com.sencha.gxt.widget.core.client.grid.GridView;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collections;
        import java.util.HashSet;
        import java.util.List;
        import java.util.Set;

        import com.google.gwt.cell.client.AbstractCell;
        import com.google.gwt.cell.client.Cell.Context;
        import com.google.gwt.cell.client.ValueUpdater;
        import com.google.gwt.core.client.GWT;
        import com.google.gwt.dom.client.BrowserEvents;
        import com.google.gwt.dom.client.Element;
        import com.google.gwt.dom.client.NativeEvent;
        import com.google.gwt.dom.client.Style.Display;
        import com.google.gwt.dom.client.Style.FontWeight;
        import com.google.gwt.dom.client.Style.TextAlign;
        import com.google.gwt.dom.client.Style.Unit;
        import com.google.gwt.event.logical.shared.BeforeSelectionEvent;
        import com.google.gwt.event.logical.shared.SelectionEvent;
        import com.google.gwt.event.shared.HandlerRegistration;
        import com.google.gwt.safehtml.shared.SafeHtml;
        import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
        import com.google.gwt.safehtml.shared.SafeHtmlUtils;
        import com.google.gwt.user.client.Event;
        import com.google.gwt.user.client.ui.FlowPanel;
        import com.google.gwt.user.client.ui.HTML;
        import com.google.gwt.user.client.ui.HasHorizontalAlignment;
        import com.google.gwt.user.client.ui.IsWidget;
        import com.google.gwt.user.client.ui.Widget;
        import com.sencha.gxt.core.client.IdentityValueProvider;
        import com.sencha.gxt.core.client.Style.SelectionMode;
        import com.sencha.gxt.core.client.ValueProvider;
        import com.sencha.gxt.core.client.dom.XElement;
        import com.sencha.gxt.core.client.gestures.PointerEvents;
        import com.sencha.gxt.core.client.gestures.PointerEventsSupport;
        import com.sencha.gxt.core.client.util.DelayedTask;
        import com.sencha.gxt.core.shared.event.GroupingHandlerRegistration;
        import com.sencha.gxt.data.shared.event.StoreClearEvent;
        import com.sencha.gxt.widget.core.client.event.HeaderClickEvent;
        import com.sencha.gxt.widget.core.client.event.HeaderClickEvent.HeaderClickHandler;
        import com.sencha.gxt.widget.core.client.event.LiveGridViewUpdateEvent;
        import com.sencha.gxt.widget.core.client.event.LiveGridViewUpdateEvent.LiveGridViewUpdateHandler;
        import com.sencha.gxt.widget.core.client.event.RefreshEvent;
        import com.sencha.gxt.widget.core.client.event.RefreshEvent.RefreshHandler;
        import com.sencha.gxt.widget.core.client.event.RowClickEvent;
        import com.sencha.gxt.widget.core.client.event.RowClickEvent.RowClickHandler;
        import com.sencha.gxt.widget.core.client.event.RowMouseDownEvent;
        import com.sencha.gxt.widget.core.client.event.RowMouseDownEvent.RowMouseDownHandler;
        import com.sencha.gxt.widget.core.client.event.ViewReadyEvent;
        import com.sencha.gxt.widget.core.client.event.ViewReadyEvent.ViewReadyHandler;
        import com.sencha.gxt.widget.core.client.event.XEvent;
        import com.sencha.gxt.widget.core.client.grid.CheckBoxSelectionModel.CheckBoxColumnAppearance;
        import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
        import com.sencha.gxt.widget.core.client.grid.Grid;
        import com.sencha.gxt.widget.core.client.grid.GridSelectionModel;
        import com.sencha.gxt.widget.core.client.grid.GridView;
        import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent;
        import com.sencha.gxt.widget.core.client.selection.SelectionChangedEvent.SelectionChangedHandler;


/**
 * Clone CheckBoxSelectionModel
 * <p>
 * CssColumnHeader.gss modified in theme
 */
        public class CountingCheckBoxSelectionModel<T extends MacInfo> extends GridSelectionModel<T>
        {

        /**
         * copying the super handler format to bind right grid
         */
        private class Handler2 implements RowMouseDownHandler, RowClickHandler, ViewReadyHandler, RefreshHandler
        {
        @Override
        public void onRefresh(RefreshEvent event)
        {
        refresh();
        if (getLastFocused() != null)
        {
        LiveGridViewExt<T> gridView = (LiveGridViewExt<T>) grid.getView();
        gridView.onHighlightRow(listStore.indexOf(getLastFocused()), true);
        }
        }

        @Override
        public void onRowClick(RowClickEvent event)
        {
        CountingCheckBoxSelectionModel.this.onRowClick(event);
        }

        @Override
        public void onRowMouseDown(RowMouseDownEvent event)
        {
        CountingCheckBoxSelectionModel.this.onRowMouseDown(event);
        }

        @Override
        public void onViewReady(ViewReadyEvent event)
        {
        refresh();
        }
        }

        public class HeaderWidget implements IsWidget
        {

        private FlowPanel widget;
        private HTML html;

        @Override
        public Widget asWidget()
        {
        if (widget == null)
        {
        html = new HTML("");
        html.setVisible(false);
        widget = new FlowPanel();
        widget.add(html);

        // TODO classname
        widget.getElement().getStyle().setColor("#65656a");
        widget.getElement().getStyle().setFontWeight(FontWeight.BOLD);
        widget.getElement().getStyle().setProperty("backgroundColor", "rgba(255, 255, 255, 0.75)");
        widget.getElement().getStyle().setTextAlign(TextAlign.CENTER);
        widget.getElement().getStyle().setDisplay(Display.INLINE_BLOCK);
        widget.getElement().getStyle().setPaddingRight(3, Unit.PX);
        widget.getElement().getStyle().setPaddingLeft(3, Unit.PX);
        // widget.getElement().getStyle().setPadding(2, Unit.PX);
        }
        return widget;
        }

        public void setCount(int count)
        {
        html.setHTML(Integer.toString(count));
        }

        }

        // ~~~ removed to use default
        // public interface CheckBoxColumnAppearance {
        //
        // String getCellClassName();
        //
        // String getCellInnerClassName();
        //
        // boolean isHeaderChecked(XElement header);
        //
        // void onHeaderChecked(XElement header, boolean checked);
        //
        // SafeHtml renderHeadCheckBox();
        //
        // void renderCellCheckBox(Context context, Object value, SafeHtmlBuilder
        // sb);
        //
        // boolean isRowChecker(XElement target);
        //
        // }

        protected ColumnConfig<T, T> config;

        private final CheckBoxColumnAppearance appearance;

        private GroupingHandlerRegistration handlerRegistration = new GroupingHandlerRegistration();
        private boolean showSelectAll = true;

        private HeaderWidget headerWidget;

        /**
         * Intercept the events and process them in this subclass
         */
        private Handler2 handler2 = new Handler2();

        /**
         * Creates a CheckBoxSelectionModel that will operate on the row itself. To
         * customize the row it is acting on, use a constructor that lets you
         * specify a ValueProvider, to customize how each row is drawn, use a
         * constructor that lets you specify an appearance instance.
         *
         * @param gridView
         */
        public CountingCheckBoxSelectionModel(LiveGridViewExt<T> gridView)
        {
        this(new IdentityValueProvider<T>(), GWT.<CheckBoxColumnAppearance> create(CheckBoxColumnAppearance.class));

        gridView.addLiveGridViewUpdateHandler(new LiveGridViewUpdateHandler()
        {
        @Override
        public void onUpdate(LiveGridViewUpdateEvent event)
        {
        setSelection(getSelectedItems());
        }
        });
        }

        public CountingCheckBoxSelectionModel(LiveGridViewExt<T> gridView, ValueProvider<T, T> valueProvider)
        {
        this(valueProvider, GWT.<CheckBoxColumnAppearance> create(CheckBoxColumnAppearance.class));

        gridView.addLiveGridViewUpdateHandler(new LiveGridViewUpdateHandler()
        {
        @Override
        public void onUpdate(LiveGridViewUpdateEvent event)
        {
        setSelection(getSelectedItems());
        }
        });
        }

        /**
         * Creates a CheckBoxSelectionModel with a custom ValueProvider instance.
         *
         * @param valueProvider
         *            the ValueProvider to use when constructing a ColumnConfig
         */
        public CountingCheckBoxSelectionModel(ValueProvider<T, T> valueProvider)
        {
        this(valueProvider, GWT.<CheckBoxColumnAppearance> create(CheckBoxColumnAppearance.class));
        }

        /**
         * Creates a CheckBoxSelectionModel with a custom appearance instance.
         *
         * @param appearance
         *            the appearance that should be used to render and update the
         *            checkbox
         */
        public CountingCheckBoxSelectionModel(CheckBoxColumnAppearance appearance)
        {
        this(new IdentityValueProvider<T>(), appearance);
        }

        /**
         * Creates a CheckBoxSelectionModel with a custom ValueProvider and
         * appearance.
         *
         * @param valueProvider
         *            the ValueProvider to use when constructing a ColumnConfig
         * @param appearance
         *            the appearance that should be used to render and update the
         *            checkbox
         */
        public CountingCheckBoxSelectionModel(ValueProvider<T, T> valueProvider, final CheckBoxColumnAppearance appearance)
        {
        this.appearance = appearance;

        // the widget that counts
        headerWidget = new HeaderWidget();

        config = newColumnConfig(valueProvider);
        config.setCellPadding(false);
        config.setWidth(40);
        config.setSortable(false);
        config.setHideable(false);
        config.setResizable(false);
        config.setFixed(true);
        config.setMenuDisabled(true);
        config.setWidget(headerWidget.asWidget(), (SafeHtml) null);
        config.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
        // config.setCellClassName(appearance.getCellClassName());
        // config.setColumnHeaderClassName(appearance.getCellInnerClassName());
        // config.setHeader(appearance.renderHeadCheckBox());

        // allow touch events in
        Set<String> consumedEvents = new HashSet<String>(Arrays.asList(BrowserEvents.TOUCHSTART, BrowserEvents.TOUCHMOVE, BrowserEvents.TOUCHCANCEL, BrowserEvents.TOUCHEND));
        if (PointerEventsSupport.impl.isSupported())
        {
        consumedEvents.add(PointerEvents.POINTERDOWN.getEventName());
        }
        config.setCell(new AbstractCell<T>()
        {
        @Override
        public void onBrowserEvent(Context context, Element parent, T value, NativeEvent event, ValueUpdater<T> valueUpdater)
        {
        super.onBrowserEvent(context, parent, value, event, valueUpdater);

        // If the incoming event is touch, then selection is already
        // taken care of
        // Don't let mouse events to pass through
        String eventType = event.getType();
        if (BrowserEvents.TOUCHSTART.equals(eventType) || BrowserEvents.TOUCHMOVE.equals(eventType) || BrowserEvents.TOUCHCANCEL.equals(eventType) || BrowserEvents.TOUCHEND.equals(eventType)
        || PointerEventsSupport.impl.isPointerEvent(event))
        {
        // ~~~ workaround for edge, having onTouch disabled
        // event.preventDefault();
        }
        }

        @Override
        public void render(Context context, T value, SafeHtmlBuilder sb)
        {
        CountingCheckBoxSelectionModel.this.render(context, value, sb);
        }
        });

        deselectOnSimpleClick = false;

        // Update header total count
        addSelectionChangedHandler(new SelectionChangedHandler<T>()
        {
        @Override
        public void onSelectionChanged(SelectionChangedEvent<T> event)
        {
        setCount(selected.size());
        }
        });

        setSelectionMode(SelectionMode.MULTI);

        // TODO remove
        // addResetSelectionHandler(new SelectionResetHandler() {
        // @Override
        // public void onReset(SelectionResetEvent event) {
        // clearSelection();
        // }
        // });
        }

        /**
         * ~~~ added
         */
        private void setCount(int count)
        {
        headerWidget.setCount(count);
        }

        /**
         * Returns the check box column appearance.
         *
         * @return appearance
         */
        public CheckBoxColumnAppearance getAppearance()
        {
        return appearance;
        }

        @Override
        public void bindGrid(Grid<T> grid)
        {
        if (this.grid != null)
        {
        handlerRegistration.removeHandler();
        }

        // super.bindGrid(grid); // ~~~~ workaround - process handling in this
        // class

        if (this.grid != null)
        {
        if (handlerRegistration != null)
        {
        handlerRegistration.removeHandler();
        handlerRegistration = null;
        }

        keyNav.bind(null);
        bind(null);
        }
        this.grid = grid;
        if (grid != null)
        {
        if (handlerRegistration == null)
        {
        handlerRegistration = new GroupingHandlerRegistration();
        }
        handlerRegistration.add(grid.addRowMouseDownHandler(handler2));
        handlerRegistration.add(grid.addRowClickHandler(handler2));
        handlerRegistration.add(grid.addViewReadyHandler(handler2));
        handlerRegistration.add(grid.addRefreshHandler(handler2));
        keyNav.bind(grid);
        bind(grid.getStore());
        }

        if (grid != null)
        {
        handlerRegistration.add(grid.addHeaderClickHandler(new HeaderClickHandler()
        {
        @Override
        public void onHeaderClick(HeaderClickEvent event)
        {
        handleHeaderClick(event);
        }
        }));

        handlerRegistration.add(grid.addRefreshHandler(new RefreshHandler()
        {
        @Override
        public void onRefresh(RefreshEvent event)
        {
        CountingCheckBoxSelectionModel.this.onRefresh(event);
        }
        }));
        }
        }

        /**
         * Only bind the right grid on init.
         */
        public void bindRightGrid(Grid<T> rightGrid)
        {
        handlerRegistration.add(rightGrid.addRowMouseDownHandler(handler2));
        handlerRegistration.add(rightGrid.addRowClickHandler(handler2));
        handlerRegistration.add(rightGrid.addViewReadyHandler(handler2));
        handlerRegistration.add(rightGrid.addRefreshHandler(handler2));
        }

        /**
         * Returns the column config.
         *
         * @return the column config
         */
        public ColumnConfig<T, T> getColumn()
        {
        return config;
        }

        /**
         * Returns true if the header checkbox is rendered and selected.
         *
         * @return true if selected
         */
        public boolean isSelectAllChecked()
        {
        if (isShowSelectAll() && grid != null && grid.isViewReady())
        {
        XElement hd = grid.getView().getHeader().getHead(grid.getColumnModel().getColumns().indexOf(getColumn())).getElement();
        return appearance.isHeaderChecked(hd);
        }
        return false;
        }

        /**
         * Returns true if this column header contains a checkbox that the user can
         * interact with.
         *
         * @return true if the column header should contain a checkbox to select all
         *         items
         */
        public boolean isShowSelectAll()
        {
        return showSelectAll;
        }

        /**
         * Sets the select all checkbox in the grid header and selects / deselects
         * all rows. If the header checkbox is not visible
         * ({@link #setShowSelectAll(boolean)}), this will only update the visible
         * row checkboxes.
         *
         * @param select
         *            true to select all
         */
        public void setSelectAllChecked(boolean select)
        {
        assert grid.isViewReady() : "cannot call this method before grid has been rendered";
        if (!select)
        {
        deselectAll();
        }
        else
        {
        // selectAll();
        deselectAll();
        }
        }

        /**
         * <p>
         * True to show the select all checkbox in the grid header, false to hide it
         * and prevent select all behavior. Defaults to true.
         * </p>
         * <p>
         * Must either be called before the grid header is rendered, or calling code
         * must force the header to be rerendered (for example, via
         * {@link GridView#refresh(boolean)}, passing {@code true} to get the header
         * to refresh).
         * </p>
         *
         * @param showSelectAll
         *            true to show a header checkbox, false to hide it
         */
        public void setShowSelectAll(boolean showSelectAll)
        {
        this.showSelectAll = showSelectAll;
        config.setHeader(showSelectAll ? appearance.renderHeadCheckBox() : SafeHtmlUtils.EMPTY_SAFE_HTML);
        }

        protected void handleHeaderClick(HeaderClickEvent event)
        {
        if (!isShowSelectAll()) { return; }
        ColumnConfig<T, ?> c = grid.getColumnModel().getColumn(event.getColumnIndex());
        if (c == config)
        {
        XElement hd = grid.getView().getHeader().getHead(grid.getColumnModel().getColumns().indexOf(getColumn())).getElement();
        boolean isChecked = appearance.isHeaderChecked(hd);
        if (isChecked)
        {
        deselectAll();
        }
        else
        {
        // selectAll();
        deselectAll();
        }
        }

        // tell the handler to clear
        fireEvent(new SelectionResetEvent());
        }

        protected ColumnConfig<T, T> newColumnConfig(ValueProvider<T, T> valueProvider)
        {
        return new ColumnConfig<T, T>(valueProvider);
        }

        @Override
        protected void onAdd(List<? extends T> models)
        {
        super.onAdd(models);
        updateHeaderCheckBox();
        }

        @Override
        protected void onClear(StoreClearEvent<T> event)
        {
        // super.onClear(event);
        updateHeaderCheckBox();
        }

        protected void onRefresh(RefreshEvent event)
        {
        updateHeaderCheckBox();
        }

        @Override
        protected void onRemove(T model)
        {
        // GWT.debugger();
        // super.onRemove(model);
        updateHeaderCheckBox();
        }

        protected void render(Context context, T value, SafeHtmlBuilder sb)
        {
        appearance.renderCellCheckBox(context, value, sb);
        }

        protected void updateHeaderCheckBox()
        {
        setCount(selected.size());
        }

        /**
         * Set selection, but keep the existing set
         */
        @Override
        public void setSelection(List<T> selection)
        {
        select(selection, true);
        }

        /**
         * Fix behavior
         */
        @Override
        protected void doMultiSelect(List<T> models, boolean keepExisting, boolean suppressEvent)
        {
        // GWT.log("doMultiSelect models.size()=" + models.size() + "
        // keepExisting=" + keepExisting + " supressEvent=" +
        // suppressEvent);

        if (locked) return;

        boolean change = false;
        if (!keepExisting && selected.size() > 0)
        {
        change = true;
        doDeselect(new ArrayList<T>(selected), true);
        }

        for (T m : models)
        {
        boolean isSelected = isSelected(m);
        if (!suppressEvent && !isSelected)
        {
        BeforeSelectionEvent<T> evt = BeforeSelectionEvent.fire(this, m);
        if (evt != null && evt.isCanceled())
        {
        continue;
        }
        }

        change = true;
        lastSelected = m;

        if (!selected.contains(m))
        { // ~~~ workaround - only add it if it doesn't exist in list
        selected.add(m);
        }
        setLastFocused(lastSelected);


        onSelectChange(m, true);
        if (!suppressEvent)
        {
        SelectionEvent.fire(this, m);
        }
        // }
        }

        if (change && !suppressEvent)
        {
        fireSelectionChange();
        }
        }

        /**
         * Track if the selection has been reset
         */
        @Override
        protected void doSelect(List<T> models, boolean keepExisting, boolean suppressEvent)
        {
        if (!keepExisting)
        {
        // tell the handler a reset occurred - This will reset the count in
        // header
        fireEvent(new SelectionResetEvent());
        }
        super.doSelect(models, keepExisting, suppressEvent);
        }

        /**
         * Observe selection reset events.
         */
        public HandlerRegistration addResetSelectionHandler(SelectionResetEvent.SelectionResetHandler handler)
        {
        return ensureHandlers().addHandler(SelectionResetEvent.getType(), handler);
        }

        /**
         * Reset the selection
         */
        @Override
        public void deselectAll()
        {
        super.deselectAll();

        // TODO remove ?
        // fireEvent(new SelectionResetEvent());

        setCount(0);
        selected = new ArrayList<>();
        }

        /**
         * This defers out the select 3 event frames so that the grid view has
         * rendered. Otherwise returning selection to exsiting models won't work.
         */
        @Override
        public void refresh()
        {
        List<T> sel = new ArrayList<T>();
        boolean change = false;
        for (T m : selected)
        {
        // T storeModel = store.findModel(m); // ~~~ it was, this but page
        // loading causes issue
        // match references with store
        T storeModel = listStore.findModelWithKey(store.getKeyProvider().getKey(m));
        if (storeModel != null)
        {
        sel.add(storeModel);
        }
        else
        {
        sel.add(m);
        }
        }
        if (sel.size() != selected.size())
        {
        change = true;
        }
        // selected.clear(); // ~~~ workaround - keep existing
        selected = sel;

        lastSelected = null;
        setLastFocused(null);

        doSelect(selected, true, true); // ~~~ workaround turn on keep existing
        // on paging

        if (change)
        {
        fireSelectionChange();
        }
        }

        @Override
        protected void onUpdate(T model)
        {
        if (locked) return;
        for (int i = 0; i < selected.size(); i++)
        {
        T m = selected.get(i);
        if (store.hasMatchingKey(model, m))
        {
        if (m != model)
        {
        selected.remove(m);
        if (!selected.contains(m))
        { // ~~~ workaround - only add new items
        selected.add(i, model);
        }
        }
        if (lastSelected == m)
        {
        lastSelected = model;
        }
        break;
        }
        }
        if (getLastFocused() != null && model != getLastFocused() && store.hasMatchingKey(model, getLastFocused()))
        {
        setLastFocused(model);
        }
        }

        /**
         * Change the model row selection.
         **/
        @Override
        protected void onSelectChange(T model, boolean select)
        {
        int idx = listStore.indexOf(model);

        if (idx != -1)
        {
        // lock(); * TODO:Need to improve( this method solves the Tablet CheckBox selection issue , But make problem when fee is rendering in LV)

        if (select)
        {
        LiveGridViewExt<T> gridView = (LiveGridViewExt<T>) grid.getView();
        gridView.onRowSelect(idx);
        }
        else
        {
        LiveGridViewExt<T> gridView = (LiveGridViewExt<T>) grid.getView();
        gridView.onRowDeselect(idx);
        }
        }

        updateHeaderCheckBox();
        }

        // FIX FOR TABLET CHECKBOX SELECTION
        private void lock()
        {
        locked = true;
        DelayedTask delay = new DelayedTask()
        {

        @Override
        public void onExecute()
        {
        locked = false;
        GWT.log("Un locked");
        }
        };
        delay.delay(300);
        }

        @Override
        public void select(int start, int end, boolean keepExisting)
        {
        keepExisting = true; // keep it on shift selection
        super.select(start, end, keepExisting);
        }

        @Override
        protected void onRowClick(RowClickEvent event)
        {
        if (event.getColumnIndex() == grid.getColumnModel().getColumns().indexOf(getColumn())) { return; }

        if (Element.is(event.getEvent().getEventTarget()) && !grid.getView().isSelectableTarget(Element.as(event.getEvent().getEventTarget()))) { return; }

        if (isLocked()) { return; }

        if (fireSelectionChangeOnClick)
        {
        fireSelectionChange();
        fireSelectionChangeOnClick = false;
        }

        XEvent xe = event.getEvent().<XEvent> cast();

        int rowIndex = event.getRowIndex();
        int colIndex = event.getColumnIndex();
        if (rowIndex == -1)
        {
        deselectAll();
        return;
        }

        T sel = listStore.get(rowIndex);

        boolean isSelected = isSelected(sel);
        boolean isControl = xe.getCtrlOrMetaKey();
        boolean isShift = xe.getShiftKey();

        // we only handle multi select with control key here
        if (selectionMode == SelectionMode.MULTI)
        {
        if (isSelected && isControl)
        {
        grid.getView().focusCell(rowIndex, colIndex, false);
        // focusCellCalled = true; // ~~~ workaround
        setFocusCellCalled(true);
        // reset the starting location of the click
        // indexOnSelectNoShift = rowIndex; // ~~~ workaround
        setIndexOnSelectNoShift(rowIndex);
        deselect(Collections.singletonList(sel)); // ~~~ workaround
        // change to
        // deselect
        }
        else if (isControl)
        {
        grid.getView().focusCell(rowIndex, colIndex, false);
        // focusCellCalled = true; // ~~~ workaround
        setFocusCellCalled(true);
        // reset the starting location of the click
        // indexOnSelectNoShift = rowIndex; // ~~~ workaround
        setIndexOnSelectNoShift(rowIndex);
        doSelect(Collections.singletonList(sel), true, false);
        }
        else if (isSelected && !isControl && !isShift && selected.size() > 1)
        {
        doSelect(Collections.singletonList(sel), false, false);
        }

        if (!getFocusCellCalled())
        {
        grid.getView().focusCell(rowIndex, colIndex, false);
        }
        }
        }

        private native void setFocusCellCalled(boolean b) /*-{
		this.@com.sencha.gxt.widget.core.client.grid.GridSelectionModel::focusCellCalled = b;
	}-*/;

        private native boolean getFocusCellCalled() /*-{
		return this.@com.sencha.gxt.widget.core.client.grid.GridSelectionModel::focusCellCalled;
	}-*/;

        private native void setIndexOnSelectNoShift(int i) /*-{
		this.@com.sencha.gxt.widget.core.client.grid.GridSelectionModel::indexOnSelectNoShift = i;
	}-*/;

        @Override
        protected void onRowMouseDown(RowMouseDownEvent event)
        {
        XEvent e = event.getEvent().<XEvent> cast();
        int rowIndex = event.getRowIndex();
        T sel = listStore.get(rowIndex);

        boolean left = event.getEvent().getButton() == Event.BUTTON_LEFT;

        if (left && event.getColumnIndex() == grid.getColumnModel().getColumns().indexOf(getColumn()))
        {
        T model = listStore.get(event.getRowIndex());
        if (model != null)
        {
        if (isSelected(model))
        {
        deselect(model);
        }
        else
        {
        doSelect(Collections.singletonList(sel), true, false);
        }
        }
        return;
        }

        super.onRowMouseDown(event);
        }

        }
