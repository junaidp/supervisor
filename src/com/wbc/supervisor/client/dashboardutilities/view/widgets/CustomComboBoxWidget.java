package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.shared.dashboardutilities.WbcFileInfo;
import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor.Path;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;

import com.sencha.gxt.cell.core.client.form.ComboBoxCell;
import com.sencha.gxt.cell.core.client.form.ComboBoxCell.TriggerAction;
import com.sencha.gxt.core.client.IdentityValueProvider;
import com.sencha.gxt.data.shared.LabelProvider;
import com.sencha.gxt.data.shared.ListStore;
import com.sencha.gxt.data.shared.ModelKeyProvider;
import com.sencha.gxt.data.shared.PropertyAccess;
import com.sencha.gxt.widget.core.client.ListView;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.ComboBox;

import java.util.ArrayList;

public class CustomComboBoxWidget  {

    private VerticalLayoutContainer container = new VerticalLayoutContainer();

    public interface PlantProperties extends PropertyAccess<WbcFileInfo> {
        @Path("name")
        LabelProvider<WbcFileInfo> nameLabel();
    }


    private static final PlantProperties properties = GWT.create(PlantProperties.class);

    public ComboBox<WbcFileInfo> createComboBox(ArrayList<WbcFileInfo> result) {
        ListStore<WbcFileInfo> store = new ListStore<WbcFileInfo>(new ModelKeyProvider<WbcFileInfo>() {
            @Override
            public String getKey(WbcFileInfo item) {
                return item.getKey();
            }
        });
        ArrayList<WbcFileInfo> updatedList = new ArrayList<WbcFileInfo>();

        for(WbcFileInfo wbcFileInfo : result){
                wbcFileInfo.setShortName(wbcFileInfo.getName().length()> 30?wbcFileInfo.getName().substring(0,30)+"..": wbcFileInfo.getName() );
                updatedList.add(wbcFileInfo);
        }
        store.addAll(updatedList);

        ListView<WbcFileInfo, WbcFileInfo> listView = new ListView<WbcFileInfo, WbcFileInfo>(store, new IdentityValueProvider<WbcFileInfo>(), new ComboCell());
        ComboBoxCell<WbcFileInfo> cell = new ComboBoxCell<WbcFileInfo>(store, properties.nameLabel(), listView);
        ComboBox<WbcFileInfo> comboBox = new ComboBox<WbcFileInfo>(cell);
        comboBox.setTriggerAction(TriggerAction.ALL);
        comboBox.setTypeAhead(true);
        comboBox.setWidth(400);

        comboBox.addSelectionHandler(new SelectionHandler<WbcFileInfo>() {
            @Override
            public void onSelection(SelectionEvent<WbcFileInfo> comboSelectionEvent) {
                DashboardUtils.logInfo("selected Value =" + comboSelectionEvent.getSelectedItem().getName());
            }
        });
        return comboBox;
    }
}
