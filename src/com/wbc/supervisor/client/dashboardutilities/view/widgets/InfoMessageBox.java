package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.google.gwt.safehtml.shared.SafeHtmlUtils;
import com.sencha.gxt.widget.core.client.box.MessageBox;
import com.sencha.gxt.widget.core.client.event.HideEvent.HideHandler;

public class InfoMessageBox extends MessageBox
{

   public InfoMessageBox(String title, String message)
   {
       super( SafeHtmlUtils.fromTrustedString(title), SafeHtmlUtils.fromTrustedString(message));

       setIcon(ICONS.info());
   }

   public InfoMessageBox(String title, String message, HideHandler hideHandler)
   {
       this(title, message);

       if(hideHandler != null)
           addHideHandler(hideHandler);
   }

}
