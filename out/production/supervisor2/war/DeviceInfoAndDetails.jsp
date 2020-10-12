<%@ page import="com.google.gwt.core.client.GWT" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.google.gwt.user.client.Window" %>
<%@ page import="com.google.gwt.core.client.JsArray"%>
<%@ page import=" com.google.gwt.user.client.Window" %>
<%@ page import=" com.google.gwt.user.client.ui.Panel" %>
<%@ page import=" com.google.gwt.user.client.ui.RootPanel" %>
<%@ page import=" com.google.gwt.visualization.client.AbstractDataTable" %>
<%@ page import=" com.google.gwt.visualization.client.VisualizationUtils" %>
<%@ page import=" com.google.gwt.visualization.client.DataTable" %>
<%@ page import=" com.google.gwt.visualization.client.Selection" %>
<%@ page import=" com.google.gwt.visualization.client.AbstractDataTable.ColumnType" %>
<%@ page import=" com.google.gwt.visualization.client.events.SelectHandler" %>
<%@ page import=" com.google.gwt.visualization.client.visualizations.PieChart" %>
<%@ page import=" com.google.gwt.visualization.client.visualizations.PieChart.Options" %>
<%@ page import="com.wbc.dashboard2dto.browser.DeviceInfoDTO" %>
<%@ page import="com.wbc.dashboard2.client.DashboardConstants" %>
<%@ page import="com.wbc.dashboard2dto.browser.IntravueEventDTO" %>
<%@ page import="com.wbc.dashboard2.client.graphics.gwtchart.WbcGwtTimeseriesChart" %>
<%@ page import="com.wbc.dashboard2.client.graphics.chart.WbcSeriesInfo" %>
<%@ page import="com.wbc.dashboard2.shared.StatsConstants" %>
<%@ page import="com.wbc.dashboard2.shared.dto.MultiSeriesTimebasedChartDTO" %>
<%@ page import="com.google.gwt.user.client.ui.HTMLPanel" %>
<%@ page import="com.google.gwt.user.client.ui.VerticalPanel" %>
<%@ page import="com.google.gwt.visualization.client.visualizations.corechart.LineChart" %>
<%@ page import="com.google.gwt.visualization.client.visualizations.corechart.ColumnChart" %>
<%@ page import="java.util.logging.Level" %>

<%--
  Created by IntelliJ IDEA.
  User: Junaid
  Date: 3/3/2015
  Time: 4:08 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        table {border-collapse:collapse; table-layout:fixed; width:310px;}
        table td {width:100px; word-wrap:break-word;}
    </style>
</head>
<body>
<script src="http://js.nicedit.com/nicEdit-latest.js" type="text/javascript"></script>
<script type="text/javascript">bkLib.onDomLoaded(nicEditors.allTextAreas);</script>






<%!

          // JUST adding some sample graph code to test..........Its giving errror if we call this method.
    public void callGrah()
    {
        Runnable onLoadCallback = new Runnable() {
            public void run() {
                Panel panel = RootPanel.get();

                // Create a pie chart visualization.
                PieChart pie = new PieChart(createTable(), createOptions());

                pie.addSelectHandler(createSelectHandler(pie));
                panel.add(pie);
            }
        };

        // Load the visualization api, passing the onLoadCallback to be called
        // when loading is done.
        VisualizationUtils.loadVisualizationApi(onLoadCallback, PieChart.PACKAGE);
    }

        private Options createOptions() {
            Options options = Options.create();
            options.setWidth(400);
            options.setHeight(240);
            options.set3D(true);
            options.setTitle("My Daily Activities");
            return options;
        }

        private SelectHandler createSelectHandler(final PieChart chart) {
            return new SelectHandler() {
                @Override
                public void onSelect(SelectEvent event) {
                    String message = "";

                    // May be multiple selections.
                    JsArray<Selection> selections = chart.getSelections();

                    for (int i = 0; i < selections.length(); i++) {
                        // add a new line for each selection
                        message += i == 0 ? "" : "\n";

                        Selection selection = selections.get(i);

                        if (selection.isCell()) {
                            // isCell() returns true if a cell has been selected.

                            // getRow() returns the row number of the selected cell.
                            int row = selection.getRow();
                            // getColumn() returns the column number of the selected cell.
                            int column = selection.getColumn();
                            message += "cell " + row + ":" + column + " selected";
                        } else if (selection.isRow()) {
                            // isRow() returns true if an entire row has been selected.

                            // getRow() returns the row number of the selected row.
                            int row = selection.getRow();
                            message += "row " + row + " selected";
                        } else {
                            // unreachable
                            message += "Pie chart selections should be either row selections or cell selections.";
                            message += "  Other visualizations support column selections as well.";
                        }
                    }

                    Window.alert(message);
                }
            };
        }

        private AbstractDataTable createTable() {
            DataTable data = DataTable.create();
            data.addColumn(ColumnType.STRING, "Task");
            data.addColumn(ColumnType.NUMBER, "Hours per Day");
            data.addRows(2);
            data.setValue(0, 0, "Work");
            data.setValue(0, 1, 14);
            data.setValue(1, 0, "Sleep");
            data.setValue(1, 1, 10);
            return data;
        }

%>

<%
//    dashboard2ServiceImpl dashboard2Service = new dashboard2ServiceImpl();
    DeviceInfoDTO deviceInfoDTO = (DeviceInfoDTO) session.getAttribute("deviceinfo");
    ArrayList<String> userDefinedNames = (ArrayList<String>) session.getAttribute("userdefinednames");
    ArrayList<IntravueEventDTO> events = (ArrayList<IntravueEventDTO>) session.getAttribute("events");
    String stats = (String) session.getAttribute("stats");

//    ArrayList<DeviceInfoDTO> deviceList = new ArrayList<DeviceInfoDTO>();
//    deviceList.add(d);
//    callGrah();
    //    ArrayList<DeviceinfoDataDTO> s = dashboard2Service.getNetworkDeviceData(1);
//    System.out.println(s.get(0).getIpAddress()+".................................Here////");
%>

<div style="width:100%;height:100%;background-color:#3399CC">


    <div style="width:100%;float:left;margin-bottom:30px;margin-top:10px">

        <div style="float: left; width: 50%;padding:5px">

            <HTMLPanel styleName="intravueLight">
                <div style="padding:5px; height:120px;background-color:#B0E0E6;height:300px">
                    <div style="padding-left: 600px">
                        <INPUT TYPE="BUTTON" VALUE="Edit" ONCLICK="edit()">
                    </div>

                    <table width='100%'  id ="mainTable">
                        <thead>
                        <tr>
                            <td>Name:</td>
                            <td><%= deviceInfoDTO.getName()%></td>
                         </tr>
                        <tr>
                            <td>IP:</td>
                            <td> <%= deviceInfoDTO.getIpAddress() %> </td>
                        </tr>
                        <%--<tr>--%>
                            <%--<td>Vendor:</td>--%>
                            <%--<td> <%= deviceInfoDTO.getVendorName() %> </td>--%>
                        <%--</tr>--%>
                        <tr>
                            <td>Location:</td>
                            <td> <%= deviceInfoDTO.getLocation() %> </td>
                        </tr>
                        <tr>
                            <td>MAC:</td>
                            <td><%= deviceInfoDTO.getMacAddress() %>  </td>
                        </tr>
                        <%--<tr>--%>
                            <%--<td>Version:</td>--%>
                            <%--<td> <%= deviceInfoDTO.getVendorName() %> </td>--%>
                        <%--</tr>--%>

                        <tr>
                            <td><%= userDefinedNames.get(DashboardConstants.UD1)==null? "Ud1": userDefinedNames.get(DashboardConstants.UD1) %></td>
                            <td><%= deviceInfoDTO.getUd1name() %></td>
                        </tr>

                        <tr>
                            <td><%= userDefinedNames.get(DashboardConstants.UD2)==null? "Ud2": userDefinedNames.get(DashboardConstants.UD2) %></td>
                            <td><%= deviceInfoDTO.getUd2name() %> </td>
                        </tr>

                        <tr>
                            <td><%= userDefinedNames.get(DashboardConstants.UD3)==null? "Ud3": userDefinedNames.get(DashboardConstants.UD3) %></td>
                            <td><%= deviceInfoDTO.getUd3name() %> </td>
                        </tr>

                        <tr>
                            <td><%= userDefinedNames.get(DashboardConstants.UD4)==null? "Ud4": userDefinedNames.get(DashboardConstants.UD4) %></td>
                            <td><%= deviceInfoDTO.getUd4name() %> </td>
                        </tr>

                        <tr>
                            <td><%= userDefinedNames.get(DashboardConstants.UD5)==null? "Ud5": userDefinedNames.get(DashboardConstants.UD5) %></td>
                            <td><%= deviceInfoDTO.getUd5name() %> </td>
                        </tr>

                        <tr>
                            <td><%= userDefinedNames.get(DashboardConstants.UD6)==null? "Ud6": userDefinedNames.get(DashboardConstants.UD6) %></td>
                            <td><%= deviceInfoDTO.getUd6name() %> </td>
                        </tr>

                        <tr>
                            <td>"Notes:"</td>
                            <td><%= deviceInfoDTO.getNotes() %> </td>
                        </tr>

                        </thead>
                    </table>
                </div>
            </HTMLPanel>
        </div>

        <div style="float: left; width: 48.5%;padding:5px;">

            <div style="margin:5px; height:120px;height:150px; background-color:#B0E0E6">
                 <div id="" style="overflow-y: scroll; height:80%; width: 40%; float:left;  padding: 10px;  ">
                   <% for(int i=0; i<events.size(); i++){ %>
                     <HTML><%= events.get(i).getDescription()%></HTML>
                     <%}%>

                 </div>

            </div>

            <div style="margin:5px; height:120px;background-color:#B0E0E6;height:150px">
                <div id="d" style="overflow-y: scroll; height:70%; width: 95%; float:left;  padding: 10px;  ">
                     <HTML><%= stats%></HTML>

                </div>
            </div>

        </div>
    </div>

    <div style="width:99%;float:left;margin:6px;background-color:#B0E0E6;height:40%">
        <div style="width: 95%; margin:15px; height: 40%;background-color: cornsilk  ">

            <HTML>Receive and transmit graph of period</HTML>

            <div id="myd"></div>


        </div>

        <div style="width: 95%; margin:15px; height: 40%;background-color: cornsilk  ">

            <HTML>Ping response and failure graph for period</HTML>
        </div>

    </div>
</div>

<SCRIPT LANGUAGE="JavaScript">
    <!--
    function edit()
    {
        alert("Not imeplemented yet")
    }

    // -->
</SCRIPT>
</body>
</html>
