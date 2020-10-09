
package com.wbc.supervisor.client.dashboardutilities.view.login;
        import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
        import com.wbc.supervisor.client.dashboardutilities.view.menus.MenuBase;
        import com.wbc.supervisor.shared.dashboardutilities.UserEntity;
        import com.google.gwt.core.client.GWT;
        import com.google.gwt.user.client.rpc.AsyncCallback;
        import com.google.gwt.user.client.ui.Widget;
        import com.sencha.gxt.cell.core.client.TextButtonCell;
        import com.sencha.gxt.core.client.ValueProvider;
        import com.sencha.gxt.data.shared.ListStore;
        import com.sencha.gxt.data.shared.ModelKeyProvider;
        import com.sencha.gxt.data.shared.PropertyAccess;
        import com.sencha.gxt.widget.core.client.button.TextButton;
        import com.sencha.gxt.widget.core.client.container.*;
        import com.sencha.gxt.widget.core.client.grid.ColumnConfig;
        import com.sencha.gxt.widget.core.client.grid.ColumnModel;
        import com.sencha.gxt.widget.core.client.grid.Grid;
        import java.util.ArrayList;
        import java.util.List;

public class UserPanel extends MenuBase {
    private static final UserPanel.UserProperties props = GWT.create( UserPanel.UserProperties.class);
    private ListStore<UserEntity> store = null;
    protected TextButton btnAddUser = new TextButton("Add User");

    public UserPanel(AsyncCallback<String> asyncCallback)
    {
        this.asyncCallback = asyncCallback;
        container.add( layout());
        populate();
    }

    private Widget layout()
    {
        VerticalLayoutContainer vlc = new   VerticalLayoutContainer();

        vlc.setHeight(300);
        vlc.add( grid());
        vlc.add(btnAddUser);
        btnAddUser.setWidth(100);
        btnAddUser.addSelectHandler(event -> addUser(null) );
        return vlc;
    }



    public interface UserProperties extends PropertyAccess<UserEntity>
    {
        ModelKeyProvider<UserEntity> hostip();
        ValueProvider<UserEntity, String> userName();
        ValueProvider<UserEntity, String> userEmail();
        ValueProvider<UserEntity, String> userPassword();
        ValueProvider<UserEntity, String> userLocation();
        ValueProvider<UserEntity, String> userType();

    }

    public Grid<UserEntity> grid()
    {

        ColumnConfig<UserEntity, String> ipAddressCol = new ColumnConfig<UserEntity, String>(valueProviderIp, 150, "Host");
        ColumnConfig<UserEntity, String> nameCol = new ColumnConfig<UserEntity, String>(props.userName(), 150, "User Name");
        ColumnConfig<UserEntity, String> emailCol = new ColumnConfig<UserEntity, String>(props.userEmail(), 150, "User Email");
        ColumnConfig<UserEntity, String> passwordCol = new ColumnConfig<UserEntity, String>(props.userPassword(), 150, "User Password");
        ColumnConfig<UserEntity, String> locationCol = new ColumnConfig<UserEntity, String>(props.userLocation(), 150, "User Location");
        ColumnConfig<UserEntity, String> typeCol = new ColumnConfig<UserEntity, String>(props.userType(), 150, "User Type");

        ColumnConfig<UserEntity, String> btnRemoveToUser = new ColumnConfig<UserEntity, String>(valueProviderIp, 120, "");
        ColumnConfig<UserEntity, String> btnEditToUser = new ColumnConfig<UserEntity, String>( props.userName(),120, "");
        ColumnConfig<UserEntity, String> btnConnectToUser = new ColumnConfig<UserEntity, String>( props.userName(),120, "");

        TextButtonCell btnRemoveUser = new TextButtonCell();
        TextButtonCell btnConnectUser = new TextButtonCell();
        TextButtonCell btnEditUser = new TextButtonCell();

        btnConnectUser.setText("Connect User");
        btnEditUser.setText("Edit User");
        btnRemoveUser.setText("Remove User");

    //    btnConnectHost.addSelectHandler(event -> connectToHost(event));

        btnConnectToUser.setCell(btnConnectUser);
        btnEditToUser.setCell(btnEditUser);
        btnRemoveToUser.setCell(btnRemoveUser);
        //  btnRemoveConnection.setCell(CustomCell.getDisableRemoveHostButtonCellIP());
        List<ColumnConfig<UserEntity, ?>> columns = new ArrayList<ColumnConfig<UserEntity, ?>>();

        columns.add(ipAddressCol);
        columns.add(nameCol);
        columns.add(emailCol);
        columns.add(passwordCol);
        columns.add(locationCol);
        columns.add(typeCol);

        columns.add(btnConnectToUser);
        columns.add(btnEditToUser);
        columns.add(btnRemoveToUser);

        ColumnModel<UserEntity> cm = new ColumnModel<UserEntity>(columns);

        store = new ListStore<UserEntity>(props.hostip());

        final Grid<UserEntity> grid = new Grid<UserEntity>(store, cm);
        grid.setHeight(400);
        grid.setColumnResize(true);
        emailCol.setWidth(200);

        DashboardUtils.setDefaultGridStyle( grid );
        return grid;
    }

    ValueProvider<UserEntity, String> valueProviderIp = new ValueProvider<UserEntity, String>()
    {
        @Override
        public String getPath()
        {
            return "";
        }

        @Override
        public String getValue(UserEntity taskDTO)
        {
            return taskDTO.getHostip();
        }

        @Override
        public void setValue(UserEntity object, String value)
        {
        }
    };

    private void addUser(Object o) {

        AddUser addUser = new AddUser(new AsyncCallback<String>() {
            @Override
            public void onFailure(Throwable caught) {

            }

            @Override
            public void onSuccess(String result) {
                //getRemoteIntravues();
            }
        });
        addUser.go();
    }

    private void populate() {
        store.clear();
        ArrayList<UserEntity> userList = new ArrayList<>();
        UserEntity user = new UserEntity();
        user.setUserName("Hamza");
        user.setHostip("1");
        user.setUserEmail("Hamza@echoo");
        user.setUserPassword("PK");

        userList.add(user);
        store.addAll( userList );
    }


}


