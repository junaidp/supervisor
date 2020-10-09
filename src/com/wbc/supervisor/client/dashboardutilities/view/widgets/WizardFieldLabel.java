package com.wbc.supervisor.client.dashboardutilities.view.widgets;

import com.google.gwt.user.client.ui.IsWidget;
import com.sencha.gxt.widget.core.client.form.FieldLabel;

public class WizardFieldLabel extends FieldLabel
{
	public final int DEFAULT_LABEL_WIDTH = 250;

	public WizardFieldLabel(IsWidget widget, String label)
	{
		super( widget, label);
		this.setLabelWidth(DEFAULT_LABEL_WIDTH );
	}

	public WizardFieldLabel(IsWidget widget, String label, String labelSeparator)
	{
		super(widget, label);
		this.setLabelWidth(DEFAULT_LABEL_WIDTH );
		this.setLabelSeparator(labelSeparator);
	}

	public WizardFieldLabel(IsWidget widget, String label, int labelWidth)
	{
		super( widget, label);
		this.setLabelWidth(labelWidth);
	}

	public WizardFieldLabel(IsWidget widget, String label, int labelWidth, String labelSeparator)
	{
		super(widget, label);
		this.setLabelWidth(labelWidth);
		this.setLabelSeparator(labelSeparator);

	}

	public WizardFieldLabel(IsWidget widget, String label, int labelWidth, FieldLabelAppearance appearance)
	{
		super(widget, label, appearance);
		this.setLabelWidth(labelWidth);
	}
}
