<%@ page import="java.util.List" %>
<%@ page import="com.wbc.dashboard2dto.browser.DeviceInfoDTO" %>


<html>
<body bgcolor=white>


<br />
<table border="2" cellpadding="10" bordercolor="blue">
<%
List<DeviceInfoDTO> eList = (List)session.getAttribute("testData");%>

 <tr>
   <td>Ip Address</td>
   <td> Name</td>
 </tr>
    <%
for(int i = 0; i < eList.size(); i++) {%>
  <tr >
    <td><%=eList.get(i).getIpAddress()%> </td>
    <td><%=eList.get(i).getName()%> </td>
  </tr>
<%}%>
</table>

</body>
</html>