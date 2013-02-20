
<%@page import="com.application.chatter.util.ApplicationUtil"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="com.application.chatter.reports.ChatterFeedsReport"%>
<%@page import="com.application.chatter.util.ApplicationUtil.SessionBeanKeys"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Set"%>
<%@page import="com.application.chatter.feeds.simplefeed.Feed"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.application.chatter.feeds.IFeeds.IFeedData"%>
<%@page import="java.net.URL"%>
<%@ page import="com.application.chatter.profile.IProfile.IProfileData" %>
<%@ page import="com.application.chatter.authentication.IAuthenticate.IToken" %>

<%@ page errorPage="/Error.jsp" %>

<html>
	
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Chatter Reports</title>
		
		<script>	
			
			function showFullPost(post){
				var message = 'Full Post \n\n' + post;
				alert(message);
			}
		
		
			function postDate(path, dateKey){
				var selectedDateFields = document.getElementById("select");
				var dateValue = selectedDateFields.options[selectedDateFields.selectedIndex].text;

				var form = document.createElement("form");
			    form.setAttribute("method", "GET");
			    form.setAttribute("action", path);
			    
			    var hiddenField = document.createElement("input");
	            hiddenField.setAttribute("type", "hidden");
			    hiddenField.setAttribute("name", dateKey);
			    hiddenField.setAttribute("value", dateValue);
			    
			    form.appendChild(hiddenField);
			    document.body.appendChild(form);
			    
			    form.submit();
			}
		</script>
				
	</head>

	<%	
		String lastName = null;
		String firstName = null;
		URL photo = null;
		URL thumbnail = null;
		Map<String, List<Feed>> userFeeds = null;
		Set<String> dates = null;
		String selectedDate = null;
		
		ChatterFeedsReport feedsReport = (ChatterFeedsReport)request.getAttribute(SessionBeanKeys.FEEDSREPORT.getKey());
		
		IProfileData profileData = feedsReport.getProfileData();
		IFeedData feeds = feedsReport.getFeedsData();
			
		firstName = profileData.getFirstName();
		lastName = profileData.getLastName();
		photo = profileData.getPhotoURL();
		thumbnail = profileData.getThumbnailURL();
		userFeeds = feeds.getUserFeeds();	
		
		selectedDate = (String)request.getAttribute(SessionBeanKeys.DATE.getKey());
		dates = userFeeds.keySet();
				
		if(selectedDate == null || selectedDate.isEmpty()){
			selectedDate  = dates.iterator().next();			
		}
			
		List<Feed> feedList = userFeeds.get(selectedDate);
		
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
					<select id="select" name="dates" style="width: 115px;" onchange="postDate('/Report','DATE')"> 
						
						<% 							
							Iterator<String> datesItererator = dates.iterator();
							while(datesItererator.hasNext()){
								String date = datesItererator.next();
						%>
								<option id="Date" <%if(date.equals(selectedDate)) out.print("selected=selected"); %>value="Date" title="Date" onclick="reloadReport()"><%out.print(date);%></option>
						<%
							}
						%>
						
					</select>					 
				</td> 

				<td width="10%">
					<form method="GET" action="/Report">
						<INPUT type="submit" name="LOGOUT" value="LOGOUT"></INPUT>						
					</form>
				</td>
			</tr>
		</table>
		
		<br>

		<table width="100%">
			<tr style="background-color:#629edc;font-size:20px">
				<td><b>Date</b></td>
				<td><b>Posts (Total: <%out.print(feedList.size()); %>)</b></td>
				<td><b>Type</b></td>
				<td><b>N Likes </b></td>
				<td><b>N Comments</b></td>				
				<td></td>
			</tr>	
			<% 
				if(feedList != null && !feedList.isEmpty()){
													
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
						i++;
						%>
							
							<td><%out.print(feed.getCreationDate());%></td>
							<td><%out.print(feed.getPost());%></td>
							<td><%out.print(feed.getType());%></td>
							<td><%out.print(feed.getLikes());%></td>
							<td><% out.print(feed.getNumComments()); %></td>
							<td>
								<a href="javascript:showFullPost('<%out.print(feed.getFullPost());%>')">More Details</a>
							</td>
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