package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.images.Images;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.UtilGrid;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.ArpDataExtended;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.sencha.gxt.core.client.ValueProvider;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.form.TextField;
import com.sencha.gxt.widget.core.client.grid.Grid;
import com.sencha.gxt.widget.core.client.grid.filters.BooleanFilter;
import com.sencha.gxt.widget.core.client.grid.filters.GridFilters;
import com.sencha.gxt.widget.core.client.info.Info;

public class ExportWidget extends TextButton {

    private supervisorServiceAsync rpcService = GWT.create( supervisorService.class );

    private AsyncCallback<String> asyncCallback = null;

    public ExportWidget()
    {
        setText("Export");
        getElement().getStyle().setCursor(Style.Cursor.POINTER);
        setIcon(Images.INSTANCE.upload());
        setToolTip("Export data to csv file ");
        setHeight(45);
        setVisible(false);
    }

    public void export(AsyncCallback<String> asyncCallback, UtilGrid utilGrid)
    {
        this.asyncCallback = asyncCallback;
          setVisible(true);

        addSelectHandler(Event -> DashboardUtils.export(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {
                new WarningMessageBox("Export Dialog","Error opening export dialog"+ caught.getMessage());
                DashboardUtils.logInfo("Error opening export dialog"+ caught.getMessage());
            }

            @Override
            public void onSuccess(String fileName) {
                exportGrid(fileName, utilGrid);
            }
        }));
    }

    private void exportGrid(String fileName, UtilGrid utilGrid) {
        AutoProgressMessageBox msg = new AutoProgressMessageBox(Constants.GRID_EXPORT_PROGRESS_HEADING, Constants.EXPORTING);
        rpcService.saveCSVtoFile(utilGrid, fileName, new AsyncCallback<ErrorInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                new WarningMessageBox("Grid Export", caught.getLocalizedMessage());
                msg.hide();
            }

            @Override
            public void onSuccess(ErrorInfo result) {
                Window.Location.assign(GWT.getHostPageBaseURL() + "exports/"+fileName+".csv");
                msg.hide();
                Info.display("","File export Completed");
                if(asyncCallback != null)
                    asyncCallback.onSuccess(fileName);

            }
        });
    }

}
