package com.wbc.supervisor.client.dashboard2.rootDockLayoutPanels;

import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.wbc.supervisor.client.dashboard2.dashBoardWidgets.PopupPanelWidget;
import com.wbc.supervisor.client.dashboard2.menuPanels.configureIntravuePanels.ConfigureIntravue;

/**
 * Created by Junaid on 9/2/14.
 */
public class MenuPanel extends VerticalPanel {

    public MenuPanel(){
        MenuBar menu = new MenuBar();


//        Command fileCommand = new Command() {
//            private int curPhrase = 0;
//
//            public void execute() {
//                Window.alert("file");
//             }
//        };
//
        MenuBar menuFile = fileMenu();
        MenuBar menuTools = toolsMenu();
        MenuBar menuHelp = helpMenu();

        menu.addItem(new MenuItem("File", menuFile));
        menu.addItem(new MenuItem("Tools", menuTools));
        menu.addItem(new MenuItem("Help", menuHelp));
        menu.setWidth(Window.getClientWidth()-0+"px");
        Window.addResizeHandler(new ResizeHandler() {
            @Override
            public void onResize(ResizeEvent event) {
                setWidth(Window.getClientWidth()-20+"px");
            }
        });
        add(menu);
        menu.setAutoOpen(true);
     }


    private MenuBar helpMenu() {
        MenuBar menuHelp = new MenuBar(true);
        menuHelp.addItem("User Documentation", new Command() {
            @Override
            public void execute() {
                Window.alert("User Documentation");
            }
        });
        menuHelp.addItem("About", new Command() {
            @Override
            public void execute() {
                Window.alert("ivDashboard 2, copyright WebBasedControl.com 2014");
            }
        });
        return menuHelp;
    }

    private MenuBar toolsMenu() {
        MenuBar menuTools = new MenuBar(true);

        menuTools.addItem("Switch Info ...", new Command() {
            @Override
            public void execute() {
                Window.alert("switch Info");
            }
        });
        menuTools.addItem("Vendor Macs ...", new Command() {
            @Override
            public void execute() {
                Window.alert("vendor utility");
            }
        });
        menuTools.addItem("Configure Intravue ...", new Command() {
            @Override
            public void execute() {
                ConfigureIntravue configureIntravue = new ConfigureIntravue();
                new PopupPanelWidget(configureIntravue);
            }
        });
        return menuTools;
    }

    private MenuBar fileMenu() {
        MenuBar menuFile = new MenuBar(true);

        menuFile.addItem("Options ...", new Command() {
            @Override
            public void execute() {
                DialogBox optionsDialog = new DialogBox();
                VerticalPanel vpnlOptions = new VerticalPanel();
                vpnlOptions.setSize("500px", "300px");
                optionsDialog.add(vpnlOptions);
                vpnlOptions.add(new HTML("Here goes the details for Options Menu"));
                optionsDialog.center();
                optionsDialog.setAutoHideEnabled(true);
            }
        });
        menuFile.addItem("Set Timezones ...", new Command() {
            @Override
            public void execute() {
                Window.alert("Sets the client browser timezone and the intravue timezone");
            }
        });
        menuFile.addItem("exit", new Command() {
            @Override
            public void execute() {
                Window.alert("exit");
            }
        });
        return menuFile;
    }
}
