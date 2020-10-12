<%@ page import="java.util.HashMap" %>
<%@ page import="com.wbc.dashboard2.shared.dto.StatDetailsByDeviceDTO" %>
<%@ page import="com.wbc.dashboard2.shared.dto.DeviceStatDetails" %>
<%@ page import="java.util.ArrayList" %>



<html>
<head>
    <script type='text/javascript' src='common.js'></script>
    <script type='text/javascript' src='css.js'></script>
    <script type='text/javascript' src='standardista-table-sorting.js'></script>
</head>
<body bgcolor=white>
<style>

    body
    { padding-left: 3em; background-color: #98CFCC; }
    table
    { /* width: 80%; */ margin: 1em auto; border-collapse: collapse; }
    thead th,
    tfoot th
    { padding: 0.5em; text-align: left; border: 1px solid black; background-color: #AAF; }
    tfoot td
    { padding: 0.5em; text-align: left; border: 1px solid black; background-color: #AAF; }
    tbody td
    { padding: 0.5em; border-left: 1px solid black; border-right: 1px solid black; }
    tbody tr.odd
    { background-color: #DDF; }
    td.numeric,
    th.numeric
    { text-align: right; }
    /*table, td, th {*/
        /*border: 1px solid black;*/
        /*border-collapse: collapse;*/
        /*padding: 1px;*/
        /*text-align: center;*/
        /*font-family: "Trebuchet MS", Arial, Helvetica, sans-serif;*/
        /*border: 1px solid #98bf21;*/
    /*}*/
    tr.alt td {
        color: #000000;
        background-color: #EAF2D3;
    }


    /*th {*/
        /*background-color: green;*/
        /*color: white;*/
        /*font-size: 1em;*/
        /*text-align: left;*/
        /*padding: 5px;*/
        /*background-color: #A7C942;*/
        /*color: #ffffff;*/
    /*}*/

    center{
        font-weight: bold;
        text-align: center;
        font-size: 18px;
        padding: 5px;
    }

</style>

<br />

<%
    StatDetailsByDeviceDTO dto = (StatDetailsByDeviceDTO)session.getAttribute("statDetailByDevice");
%>
<div>
    <HTML styleName="heading">
    <center><%= dto.getChartTitle()%></center>

    </HTML>
</div>
<table width='100%' class='sortable' id ="mainTable">
    <thead>

    <input type="hidden" id="hiddenField"/>
    <tr>
        <th>Ip Address</th>
        <th> Name</th>


        <% for(int i = 0; i < dto.getDates().size(); i++) {
            //---  To Remove '-'  from the date .
            String date = dto.getDates().get(i).toString();
            StringBuffer buf = new StringBuffer(date.length());
            buf.setLength(date.length());
            int current = 0;
            for (int j=0; j<date.length(); j++){
                char cur = date.charAt(j);
                if(cur != '-') buf.setCharAt(current++, cur);
            }
            date = buf.toString();
            //---
        %>
        <th><%=date%> </th>
        <%} %>

    </tr>
    </thead>
    <tbody>
        <% HashMap<Integer, DeviceStatDetails> deviceDataMap = dto.getDeviceDataMap();
                    int rowCount = 0;
                    for (Integer deviceid : deviceDataMap.keySet()) {
                        if(deviceid!=-1){
                            rowCount++;
                       DeviceStatDetails details = deviceDataMap.get(deviceid);
                       if ( deviceDataMap.containsKey(deviceid)) {
                           details.setIpaddress(deviceDataMap.get(deviceid).getIpaddress());
                           details.setName(deviceDataMap.get(deviceid).getName());

                       } else {
                           details.setIpaddress("unknown " + deviceid);
                           details.setName("unknown deviceid " + deviceid);
                       }

    %>
        <%if(rowCount%2 ==0){ %>
    <tr >
            <%}else{%>

    <tr class="alt">
            <%}%>
        <td><%=details.getIpaddress()%> </td>
        <td><%=details.getName()%> </td>
            <%int value =0;%>
            <%for(int i =0; i< details.getValues().size(); i++){
            value = details.getValues().get(i)+value;%>
        <td><%=details.getValues().get(i).toString()%>

                <%}%>
        <td><b><%=value%></b></td>
            <%}}%>

    <tfoot>
    <%

        for (Integer deviceid : deviceDataMap.keySet()) {
            if(deviceid == -1){
                DeviceStatDetails details = deviceDataMap.get(deviceid);
        %>
    <tr>
         <td><b>Total </b> </td>
         <td>   </td>
         <%for(int i =0; i< details.getValues().size(); i++){
            details.getValues().get(i);%>
        <td><b><%=details.getValues().get(i).toString()%></b></td>

                <%}}}%>
    </tr>

    </tfoot>
    </tbody>
</table>
</body>
</html>