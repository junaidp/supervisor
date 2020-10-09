package com.wbc.supervisor.client.dashboardutilities.view.menus;

import com.wbc.supervisor.client.supervisorService;
import com.wbc.supervisor.client.supervisorServiceAsync;
import com.wbc.supervisor.shared.dashboardutilities.Globals;
import com.wbc.supervisor.client.dashboardutilities.utils.DashboardUtils;
import com.wbc.supervisor.client.dashboardutilities.Constants;
import com.wbc.supervisor.client.dashboardutilities.view.header.HeaderPanel;
import com.wbc.supervisor.client.dashboardutilities.view.widgets.WarningMessageBox;
import com.wbc.supervisor.shared.dashboardutilities.ErrorInfo;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHost;
import com.wbc.supervisor.shared.dashboardutilities.IntravueHostDTO;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.logging.client.SimpleRemoteLogHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.Widget;
import com.sencha.gxt.widget.core.client.box.AutoProgressMessageBox;
import com.sencha.gxt.widget.core.client.container.VBoxLayoutContainer;
import com.sencha.gxt.widget.core.client.container.VerticalLayoutContainer;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public class MenuBase implements IsWidget   {
    VBoxLayoutContainer vlc = new VBoxLayoutContainer( VBoxLayoutContainer.VBoxLayoutAlign.STRETCH);

    protected supervisorServiceAsync rpcService = GWT.create( supervisorService.class );
    protected  HTML heading = new HTML();
    protected VerticalLayoutContainer container = new VerticalLayoutContainer();
    private HeaderPanel header = null;
    public SimpleRemoteLogHandler remoteLog = new SimpleRemoteLogHandler();
    protected AsyncCallback<String> asyncCallback;

    public MenuBase() {


    }

    @Override
    public Widget asWidget() {

        return layout();
    }

    private Widget layout() {

        container.getElement().getStyle().setPadding( 10, Style.Unit.PX );
        container.add( heading );
        return container;
    }

    public void logInfo(String message)
    {
        GWT.log(message);
        remoteLog.publish(new LogRecord( Level.INFO, message));

    }

    public void logWarning(String message)
    {
        GWT.log(message);
        remoteLog.publish(new LogRecord( Level.WARNING, message));

    }

    public void logError(String message)
    {
        GWT.log(message);
        remoteLog.publish(new LogRecord( Level.SEVERE, message));

    }

    public void connect(IntravueHost intravueHost, AsyncCallback<String> asyncCallback) {

        String ip = intravueHost.getHostip();
        String email = intravueHost.getHostEmails();
        String hostname = intravueHost.getHostname();
        intravueHost.setPk(Globals.PRODUCT_KEY);
        String getVersionUrl = DashboardUtils.getFullUrl(Constants.URL_VERSION, ip);

        AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( "Connection","Connection to Host: "+hostname+"("+ ip+")" );

        rpcService.validateKeycode(intravueHost, getVersionUrl,  new AsyncCallback<ErrorInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                messageBoxProgress.hide();
                logError("LOGIN FAILED for ip " + ip + " user :" + email + "::" + caught.getMessage());
                new WarningMessageBox(Constants.VALIDATION, "Error in Validating user" + caught.getMessage());
                MenuBase.this.asyncCallback.onFailure(null);
            }

            @Override
            public void onSuccess(ErrorInfo result) {
                messageBoxProgress.hide();
                logInfo("Back from validateKeycode: "+ result.getErrorText()+":"+ result.getResult());
                if(!result.getResult().equalsIgnoreCase("error")) {
                    logInfo(email + " Logged In .. !");
                    Cookies.setCookie("email", email);
                    Cookies.setCookie("ip", ip);
                    Cookies.setCookie("hostName", hostname);
                    logInfo("Cookies updated to ip: " + ip + ", email: " + email);
                    Globals.HOST_IP_ADDRESS = ip;
                    Globals.HOST_NAME = hostname;
                    Globals.HOST_EMAIL = email;
                   if(asyncCallback != null) asyncCallback.onSuccess("loggedIn");
                    if (MenuBase.this.asyncCallback != null)
                        MenuBase.this.asyncCallback.onSuccess("loggedIn");
                }
                else{
                    new WarningMessageBox(Constants.MENU_CONNECT, result.getResult()+":"+result.getErrorText());
                    logWarning(result.getResult()+":"+result.getErrorText());
                }
            }
        });
    }

    protected void getProductKey(IntravueHost intravueHost, AsyncCallback<String> asyncCallback)
    {
        String hostIp = intravueHost.getHostip();
        String email = intravueHost.getHostEmails();
        String hostname = intravueHost.getHostname();
        
        logInfo("Getting Product Key for ip  :"+ hostIp);
        AutoProgressMessageBox messageBoxProgress = DashboardUtils.getProgressMessageBox( "Login","Getting product key from " +hostIp );

        rpcService.getProductKeyAndKeycode(hostIp, new AsyncCallback<ErrorInfo>() {
            @Override
            public void onFailure(Throwable caught) {
                messageBoxProgress.hide();
                if ( caught.getLocalizedMessage().equalsIgnoreCase("404")) {
                    logError( "getProductKeyAndKeycode returned 404 not found, probably blocked by apache, check wbcutil log files for more info ");
                    new WarningMessageBox(Constants.GET_PRODUCT_KEY, "getProductKeyAndKeycode returned 404 not found, probably blocked by apache, check wbcutil log files for more info" );

                } else {
                    logError( "getProductKeyAndKeycode returned some other error code " + caught.getLocalizedMessage() );
                    new WarningMessageBox(Constants.GET_PRODUCT_KEY, "getProductKeyAndKeycode returned some other error code " + caught.getLocalizedMessage() );

                }
                logError("Error in getProductKeyAndKeycode : "+ caught.getLocalizedMessage() + " class > " + caught.getClass() + " cause > " + caught.getCause());
                new WarningMessageBox(Constants.GET_PRODUCT_KEY, "Error in getProductKeyAndKeycode : "+ caught.getLocalizedMessage() + " class > " + caught.getClass() + " cause > " + caught.getCause() );

            }

            @Override
            public void onSuccess(ErrorInfo result) {
                messageBoxProgress.hide();
                logInfo("Back from getProductKeyAndKeycode: "+ result.getErrorText()+":"+ result.getResult());
                if(!result.getResult().equalsIgnoreCase("error")) {
                    String[] tokens = result.getErrorText().split(",");
                    if (tokens.length > 0) {
                        Globals.PRODUCT_KEY = tokens[0];
                        Globals.KEYCODE = tokens[1];
                        intravueHost.setPk(Globals.PRODUCT_KEY);
                        intravueHost.setKeycode(Globals.KEYCODE);
                        connect(intravueHost, asyncCallback);
                    }
                }
                else{
                    new WarningMessageBox(Constants.GET_PRODUCTKEY_AND_KEYCODE, result.getResult()+":"+result.getErrorText());
                    logWarning(result.getResult()+":"+result.getErrorText());
                }
            }
        });

    }
}