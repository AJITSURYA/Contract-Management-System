<%@ page language="java" import="java.util.*" pageEncoding="ISO-8859-1"%>
<%@page import="com.model.Contract"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<link href="css/style.css" rel="stylesheet" media="screen"
			type="text/css" />
		<title>Contract Information</title>
		<!-- Use JavaScript script to open a new window display information when preview-->
		<script>
			function preview(url) {
				window.open(url,'Preview','resizable=no,toolbar=no,width=620,height=500,top=50,left=200');
			}
		</script>
	</head>

	<body>
		<div class="mtitle">
			Contract Information
		</div>
		
		
		
		<div class="list">
		  <table>
			<tr>
				<th>
					Contract name
				</th>
				<th class="th1">
					Draft time
				</th>
				<th width="270px">
					User
				</th>
			</tr>
			
			<tr>
				
					
			</tr>
			<%
				List<Contract> contractList=(List<Contract>)request.getAttribute("contractList");
				for (Contract cta : contractList) {
			 %>
			
			<tr>
				<td class="tdname">
					<a href="javascript:preview('contractDetail?conId=<%=cta.getId()%>')"><%=cta.getName() %> </a>
				</td>
				<td>
					<%=cta.getBeginTime() %>
				</td>
				<td>
					<%=cta.getEndTime() %>
				</td>
				<td colspan="3"> </td>
			</tr>
			<%
				}
			 %>
		  </table>
		</div>
	</body>
</html>
