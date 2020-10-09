package com.wbc.supervisor.client.dashboardutilities.view.menus.kpimanagement;

import com.google.gwt.user.client.ui.HTML;
import com.sencha.gxt.core.client.util.Margins;
import com.sencha.gxt.widget.core.client.container.HorizontalLayoutContainer;
import com.sencha.gxt.widget.core.client.form.CheckBox;


public class KpiEvent extends HorizontalLayoutContainer
{
	protected CheckBox check = new CheckBox(  );
	protected HTML event = null;
	protected boolean other = false;
	public KpiEvent( String key, boolean value, Boolean type )
	{
		check.setValue( value );
		event = new HTML(key);
		event.setWordWrap( false );
		this.other = type;
		add(check, new HorizontalLayoutData( -1,-1, new Margins( 0,10,0,0 ) ));
		add(event, new HorizontalLayoutData( -1,-1, new Margins( 0,10,0,0 ) ));
	}
}
