package com.wbc.supervisor.client.dashboardutilities.view.login;
import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.client.dashboardutilities.DialogBase;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WizardFieldLabel;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.UserEntity;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.*;
import com.sencha.gxt.widget.core.client.form.TextField;

public class AddUser extends DialogBase {
    protected AsyncCallback<String> asyncCallback;
    protected TextField txtFieldHostIp = null;
    protected TextField txtFieldUserName = null;
    protected TextField txtFieldUserPassword = null;
    protected TextField txtFieldUserEmail = null;
    protected TextField txtFieldUserLocation = null;
    protected TextField txtFieldUserType = null;
    protected TextButton btnSaveUser = new TextButton("Save User");
    private supervisorServiceAsync rpcService = GWT.create( supervisorService.class );



    public AddUser(AsyncCallback<String> asyncCallback)
    {
        this.asyncCallback = asyncCallback;
        setWidget( layout());

    }

    public void go()
    {
        setHeading(com.wbc.supervisor.client.dashboardutilities.Constants.ADD_USER);
        setDefault();
        setPredefinedButtons();
        addButton(btnSaveUser);
        show();
    }

    private Widget layout()
    {
        VerticalLayoutContainer addHostContainer = new   VerticalLayoutContainer();
        addHostContainer.addStyleName("margin10");
        txtFieldHostIp = new TextField(  );
        txtFieldHostIp.setWidth( 300 );
        txtFieldHostIp.setEmptyText( "Enter Ip Address" );

        txtFieldUserName = new TextField(  );
        txtFieldUserName.setWidth( 300 );
        txtFieldUserName.setEmptyText( "Enter User Name" );

        txtFieldUserPassword = new TextField(  );
        txtFieldUserPassword.setWidth( 300 );
        txtFieldUserPassword.setEmptyText( "Enter Password" );

        txtFieldUserEmail = new TextField(  );
        txtFieldUserEmail.setWidth( 300 );
        txtFieldUserEmail.setEmptyText( "Enter Email" );

        txtFieldUserLocation = new TextField(  );
        txtFieldUserLocation.setWidth( 300 );
        txtFieldUserLocation.setEmptyText( "Enter Location" );

        txtFieldUserType = new TextField(  );
        txtFieldUserType.setWidth( 300 );
        txtFieldUserType.setEmptyText( "Enter User Type" );

        btnSaveUser.setSize( "100px", "30px" );

        addHostContainer.add( new WizardFieldLabel( txtFieldUserName, "User Name" ) );
        addHostContainer.add( new WizardFieldLabel( txtFieldUserPassword, "Password" ) );
        addHostContainer.add( new WizardFieldLabel( txtFieldUserEmail, "Email" ) );
        addHostContainer.add( new WizardFieldLabel( txtFieldHostIp, "Host IP" ));
        addHostContainer.add( new WizardFieldLabel( txtFieldUserLocation, "Location" ) );
        addHostContainer.add( new WizardFieldLabel( txtFieldUserType, "User Type" ) );

        UserEntity userEntity = new UserEntity();
        btnSaveUser.addSelectHandler(event -> getUserFormData(userEntity, UserAction.SAVE) );

        return addHostContainer;

    }

    protected void getUserFormData(UserEntity user, UserAction userAction)
    {
        user.setUserName(txtFieldUserName.getText());
        user.setUserPassword(txtFieldUserPassword.getText());
        user.setUserEmail(txtFieldUserEmail.getText());
        user.setHostip(txtFieldHostIp.getText());
        user.setUserLocation(txtFieldUserLocation.getText());
        user.setUserType(txtFieldUserType.getText());

        Window.alert(txtFieldUserName.getText() + txtFieldUserPassword.getText() + txtFieldUserType.getText());
        saveUser(user, userAction);

    }



    private void saveUser(UserEntity user, UserAction userAction) {
        AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( "Users","Saving User" );

        rpcService.saveUpdateDeleteUsers(user, userAction, new AsyncCallback<ErrorInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                messageBoxProgress.hide();
                DashboardUtils.logError("Error in "+ userAction + ":" + caught.getLocalizedMessage());
                new WarningMessageBox("User", caught.getLocalizedMessage());
            }

            @Override
            public void onSuccess(ErrorInfo result) {
                messageBoxProgress.hide();
                DashboardUtils.logInfo("Success in "+ userAction+":"+ result.getResult()+"," + result.getErrorText());
                if(!result.getResult().equalsIgnoreCase("error")) {
                    asyncCallback.onSuccess("added");
                }
                else{
                    new WarningMessageBox("Save Intravue Host", result.getResult()+":"+ result.getErrorText()) ;
                }
                hide();
            }
        });


    }


}



