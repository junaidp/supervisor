package com.wbc.supervisor.client.dashboardutilities.view.widgets;

        import com.google.gwt.event.logical.shared.ValueChangeEvent;
        import com.google.gwt.event.logical.shared.ValueChangeHandler;
        import com.sencha.gxt.widget.core.client.button.ToggleButton;
        import com.sencha.gxt.widget.core.client.tips.ToolTipConfig;

public class ToggleButtonWhite extends ToggleButton
{
public ToggleButtonWhite(String text, int width, ToolTipConfig toolTipConfig)
{
this(text, width);
setToolTipConfig(toolTipConfig);
}

public ToggleButtonWhite(String text, int width)
{
this(text);
setWidth(width);
}
public ToggleButtonWhite(String text)
{
setText(text);
setAllowDepress(false);

addStyleName("ln-desktop-whitebuttonbackground");
addStyleName("ln-desktop-whitebuttonbackgroundWithGreenText");

addValueChangeHandler(new ValueChangeHandler<Boolean>()
{

@Override
public void onValueChange(ValueChangeEvent<Boolean> selected)
{
setStyle(selected.getValue());
}
});
}

public ToggleButtonWhite()
{
super();
addStyleName("ln-desktop-whitebuttonbackground");
addStyleName("ln-desktop-whitebuttonbackgroundWithGreenText");
}

@Override
public void setValue(Boolean value)
{
super.setValue(value);
setStyle(value);
}

protected void setStyle(Boolean value)
{
if (value)
{
addStyleName("ln-desktop-whitebuttonSelected");
removeStyleName("ln-desktop-whitebuttonbackgroundWithGreenText");
}
else
{
removeStyleName("ln-desktop-whitebuttonSelected");
addStyleName("ln-desktop-whitebuttonbackgroundWithGreenText");
}
}
}

