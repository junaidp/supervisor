package com.wbc.supervisor.client.dashboardutilities.utils;

import com.wbc.supervisor.shared.dashboardutilities.switchprobe.GeneralInfo;
import com.wbc.supervisor.shared.dashboardutilities.switchprobe.SwtichprobeReportInfo;
import com.google.web.bindery.autobean.shared.AutoBean;
import com.google.web.bindery.autobean.shared.AutoBeanFactory;

public interface DashboardFactory extends AutoBeanFactory
{
	// Factory method for a simple AutoBean
	AutoBean<SwtichprobeReportInfo> switchProbeReportInfo();
	AutoBean<GeneralInfo> generalInfo();
}
