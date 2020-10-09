package com.wbc.supervisor.client.dashboardutilities.view.login;

import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.menus.MenuBase;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHost;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostDTO;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.core.client.util.DelayedTask;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.FramedPanel;
import com.sencha.gxt.widget.core.client.button.TextButton;
import com.sencha.gxt.widget.core.client.container.BoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.HBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.MarginData;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.form.FieldLabel;
import com.sencha.gxt.widget.core.client.form.IntegerField;
import com.sencha.gxt.widget.core.client.form.PasswordField;
import com.sencha.gxt.widget.core.client.form.TextField;

public class LoginPanel extends MenuBase
{

	protected TextButton btnConnect = new TextButton( "Connect" );
	protected TextButton btnAddIntavueHost = new TextButton(Constants.ADD_REMOTE_INTRAVUE);
	// protected String url = "http://localhost:8080/users";
	protected TextField user = null;
	protected PasswordField password = null;
	protected TextField hostIp = null;
	protected TextField email = null;
	protected IntegerField key;
	private boolean show = false;

	public LoginPanel(boolean show, AsyncCallback<String> asyncCallback)
	{
		this.show = show;
		this.asyncCallback = asyncCallback;
		if(show)
		{
			mainLayout();
		}
		else
		{
			mainLayout();
		}

	}

	public Widget mainLayout()
	{

		BoxLayoutContainer.BoxLayoutData flex = new BoxLayoutContainer.BoxLayoutData( new Margins( 0, 0, 0, 0 ) );
		flex.setFlex( 1 );
		flex.setMaxSize( 400 );

		VBoxLayoutContainer widget = new VBoxLayoutContainer( VBoxLayoutContainer.VBoxLayoutAlign.CENTER );
		widget.add( layout() );
		widget.setHeight( 500 );
		int winsize = Window.getClientWidth() / 2 - 200;
		//widget.getElement().getStyle().setPaddingLeft( winsize, Style.Unit.PX );
		widget.addStyleName( "centered" );
		return widget;
	}

	private Widget layout()
	{
		user = new TextField();
		key = new IntegerField();
		password = new PasswordField();
		hostIp = new TextField();
		email = new TextField();
		key.setWidth( 200 );
		email.setEmptyText( "Enter email address" );
		user.setText( "admin" );
		password.setText( "intravue" );
		hostIp.setText("127.0.0.1");
		key.setEnabled( false );
		user.setAllowBlank( false );
		user.setEmptyText( "Enter username..." );
		hostIp.setAllowBlank( false );

		VBoxLayoutContainer vlc = new VBoxLayoutContainer( VBoxLayoutContainer.VBoxLayoutAlign.STRETCH );
		vlc.add( new FieldLabel( email, "Email" ) );
		vlc.add( new FieldLabel( hostIp, "Host IP" ) );
		vlc.add( new FieldLabel( key, "Host Product Key" ) );
		vlc.add( new FieldLabel( user, "User" ) );
		vlc.add( new FieldLabel( password, "Password" ) );
		HBoxLayoutContainer btnContainer = new HBoxLayoutContainer();
		btnContainer.add(btnConnect);
		btnContainer.add(btnAddIntavueHost);
		vlc.add( btnContainer );


	//	hostIp.addValueChangeHandler( Event -> getProductKey(hostIp.getCurrentValue()) );

		FramedPanel panel = new FramedPanel();
		panel.setHeading( "Login / Connection" );
		panel.add( vlc, new MarginData( 15, 15, 20, 15 ) );

		btnConnect.addStyleName("buttonLoginPanel");
		btnAddIntavueHost.addStyleName("buttonLoginPanel buttonAddIntravue");
		btnContainer.addStyleName("loginButtonsContainer");

	//	btnConnect.addSelectHandler( Event -> 	connect(Globals.HOST_IP_ADDRESS, email.getText(), ""));
	///	btnAddIntavueHost.addSelectHandler( Event -> addIntravueHost() ); We Assume THIS LoginPanel Dialog Will never show up now
		email.setEnabled(true);

		setDataFromCookies();
		if(Cookies.getCookie("ip") != null && Cookies.getCookie("email") != null) {
			IntravueHost intravueHost = new IntravueHost();
			intravueHost.setHostip(Cookies.getCookie("ip"));
			intravueHost.setHostEmails( Cookies.getCookie("email"));
			intravueHost.setHostname(Cookies.getCookie("hostName"));
			//getProductKey(intravueHost, null);
			cookieVerification(intravueHost);

		}
		//asyncCallback.onSuccess("loggedIn");// TEST
		return panel;

	}

	protected void setDataFromCookies()
	{

		if(Cookies.getCookie( "ip" ) != null && !Cookies.getCookie( "ip" ).isEmpty())
		{
			hostIp.setValue(Cookies.getCookie( "ip" ));
			logInfo("Ip updated from cookies: "+ hostIp.getValue());
		}
		else
		{
			//hostIp.setValue("127.0.0.1");
			//logInfo("Nothing found in cookies, host udpated with : "+ hostIp.getValue());
			logInfo("Nothing found in cookies, Not connected !");
			DelayedTask d = new DelayedTask() {
				@Override
				public void onExecute() {
					asyncCallback.onSuccess(Constants.IPNOTFOUND);

				}
			};
			d.delay(300);
			//getProductKey("127.0.0.1", "","");
		}

		if(Cookies.getCookie( "email" ) != null && !Cookies.getCookie( "email" ).isEmpty())
		{
			email.setValue(Cookies.getCookie( "email" ));
			logInfo("Email updated from cookies: "+ email.getValue());
		}
	}

    private void cookieVerification(IntravueHost intravueHost) {
		logInfo("Calling getIntravueHosts to verify If Ip "+intravueHost.getHostip()+ " in Cookies is available in HostFile");
        rpcService.getIntravueHosts(new AsyncCallback<IntravueHostDTO>() {
            @Override
            public void onFailure(Throwable caught) {
                logError("Failure in getting IntraVueHost for Cookie lookUp " + caught.getMessage());
                new WarningMessageBox(Constants.GETTING_REMOTE_INTRAVUE, "Error in getting IntraVueHost for Cookie lookUp" + caught.getMessage());
            }

            @Override
            public void onSuccess(IntravueHostDTO result) {
            boolean ipFoundInHostFile = false;
                for (IntravueHost intravueHostDTO : result.getHostlist()) {
                    if (intravueHostDTO.getHostip().equals(intravueHost.getHostip())) {
                        logInfo("Ip in File "+intravueHostDTO.getHostip() + " is matched with cookie " + intravueHost.getHostip());
                       	ipFoundInHostFile = true;
                        getProductKey(intravueHostDTO, null);
                        break;
                    }
                }
                if(!ipFoundInHostFile) {
					logWarning("Ip:" + intravueHost.getHostip() + "Not Found in Host File");
					asyncCallback.onSuccess(Constants.IPNOTFOUND);
				}
            }
        });
    }
}
