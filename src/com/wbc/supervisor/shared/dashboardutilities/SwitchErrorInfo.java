package com.wbc.supervisor.shared.dashboardutilities;

import com.google.gwt.user.client.rpc.IsSerializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class SwitchErrorInfo extends UtilGrid implements IsSerializable
{
	private ErrorInfo errorInfo;
	private HashMap<String,SwitchErrorData> errorMap;
	private ArrayList<Date> dates;

	public SwitchErrorInfo() {
		errorMap = new HashMap<String, SwitchErrorData>();
		dates = new ArrayList<Date>();
		errorInfo = new ErrorInfo();
	}

	public HashMap<String, SwitchErrorData> getErrorMap() {
		return errorMap;
	}

	public void setErrorMap(HashMap<String, SwitchErrorData> errorMap) {
		this.errorMap = errorMap;
	}

	public ArrayList<Date> getHeader() {
		return dates;
	}

	public void setHeader(ArrayList<Date> header) {
		this.dates = header;
	}

	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}

	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}

	public ArrayList<Date> getDates() {
		return dates;
	}

}