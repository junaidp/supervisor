package com.wbc.supervisor.client.dashboardutilities.images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface Images extends ClientBundle {


    public Images INSTANCE = GWT.create(Images.class);

    @Source("export.png")
    ImageResource upload();

    @Source("cross.png")
    ImageResource cross();

    @Source("search.png")
    ImageResource search();


}

