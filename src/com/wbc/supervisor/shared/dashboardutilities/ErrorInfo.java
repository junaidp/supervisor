package com.wbc.supervisor.shared.dashboardutilities;


import com.google.gwt.user.client.rpc.IsSerializable;

//public class ErrorInfoImpl implements IsSerializable, ErrorInfo
public class ErrorInfo implements IsSerializable
{
	private String result;
	private String errorText;

	public ErrorInfo() {
		result="ok";
		errorText="success";
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	/*
	public String toJson() {
		Gson gson = new Gson();
		return gson.toJson( this );
	}
	*/

	public boolean isOK() {
		if ( result.equals("ok") ) return true;
		return false;
	}
	public boolean isWarning() {
		if ( result.equals("warning") ) return true;
		return false;
	}
	public boolean isError() {
		if ( result.equals("error") ) return true;
		return false;
	}

	public void makeError() {
		result="error";
	}
	public void makeWarning() {
		result="warning";
	}

	public String toString() {
		return ( result + " > ["+ errorText +"]");
	}

}