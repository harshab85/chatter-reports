<%@page import="com.application.chatter.reports.ChatterFeedsReport"%>
<%@page import="com.application.chatter.util.ApplicationUtil.SessionBeanKeys"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="com.application.chatter.feeds.simplefeed.Feed"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.application.chatter.feeds.IFeeds.IFeedData"%>
<%@page import="java.net.URL"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.application.chatter.profile.IProfile.IProfileData" %>
<%@ page import="com.application.chatter.authentication.IAuthenticate.IToken" %>

<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Chatter Reports</title>
		
				
	</head>

	<%	
		String lastName = null;
		String firstName = null;
		URL photo = null;
		URL thumbnail = null;
		Map<String, List<Feed>> userFeeds = null;
		Set<String> dates = null;
		
		try{
			ChatterFeedsReport feedsReport = (ChatterFeedsReport)request.getAttribute(SessionBeanKeys.FEEDSREPORT.getKey());
		
			IProfileData profileData = feedsReport.getProfileData();
			IFeedData feeds = feedsReport.getFeedsData();
			
			firstName = profileData.getFirstName();
			lastName = profileData.getLastName();
			photo = profileData.getPhotoURL();
			thumbnail = profileData.getThumbnailURL();
			userFeeds = feeds.getUserFeeds();													
		}
		catch(Exception e){
			
		}
	%>

	<body>
		
		<table width="100%">
			<tr height=20>
				<td width="80%">
					<h1>Chatter Reports</h1>
				</td>

				<td width="12.5">
					<h4 style="float:right";>Hi, <%out.print(firstName);%></h4>
				</td>

				<td width="2.5%">
					<img src="<%out.print(thumbnail.toString());%>"></img>
				</td>
			</tr>
		</table>


		<table width=100% style="background-color:#E0E0E0;">
			<tr>
				<td width="90%">View Posts On &nbsp | &nbsp 
					<select id="select" name="" style="width: 115px;"> 
						
						<% 
							dates = userFeeds.keySet();
							Iterator<String> datesItererator = dates.iterator();
							while(datesItererator.hasNext()){
								String date = datesItererator.next();
						%>
								<option id="Date" value="Date" title="Date" onclick="reloadReport()"><%out.print(date);%></option>
						<%
							}
						%>
						
					</select>					 
				</td> 

				<td width="10%">
					<form method="GET" action="/ChatterReports/Report">
						<INPUT type="submit" name="LOGOUT" value="LOGOUT"></INPUT>						
					</form>
				</td>
			</tr>
		</table>
		
		<br>

		<table width="100%">
			<tr style="background-color:#629edc;font-size:20px">
				<td><b>Date</b></td>
				<td><b>Posts (Total: 30)</b></td>
				<td><b>Type</b></td>
				<td><b>Likes </b></td>
				<td><b>Comments</b></td>
			</tr>	
			<% 
				if(dates != null && !dates.isEmpty()){
			
					String selectedDate = (String)request.getAttribute(SessionBeanKeys.DATE.getKey());
					if(selectedDate == null || selectedDate.isEmpty()){
						selectedDate  = dates.iterator().next();			
					}
					
					List<Feed> feedList = userFeeds.get(selectedDate);
					Iterator<Feed> feedsItererator = feedList.iterator();
					int i=0;
					while(feedsItererator.hasNext()){
						Feed feed = feedsItererator.next();
						if(i%2 == 0){
			%>
							<tr style="background-color:#d3e7f1;font-size:20px">
						<% 
						}
						else{
						%>
							<tr style="background-color:#ffffff;font-size:20px">
						<%
						}
						%>
							
							<td><%out.print(feed.getCreationDate());%></td>
							<td><%out.print(feed.getPost());%></td>
							<td><%out.print(feed.getType());%></td>
							<td><%out.print(feed.getLikes());%></td>
							<td><%out.print(feed.getNumComments());%></td>
						</tr>
			<%
					}
				}
				else{
			%>
					<tr style="background-color:#ffffff;font-size:20px">
						<td><%out.print("No Posts found");%></td>
					</tr>
			<%
				}
			%>		
			
	</table>
</body>	
</html>