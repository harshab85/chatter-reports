package com.application.chatter.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.authentication.AuthenticationException;
import com.application.chatter.authentication.IAuthenticate;
import com.application.chatter.authentication.oauth.OAuthUserAuthenticator;
import com.application.chatter.feeds.FeedException;
import com.application.chatter.profile.ProfileReadException;
import com.application.chatter.reports.ChatterFeedsReport;
import com.application.chatter.reports.FeedsReportGenerator;
import com.application.chatter.util.ApplicationUtil;
import com.application.chatter.util.ApplicationUtil.RequestParams;
import com.application.chatter.util.ApplicationUtil.SessionBeanKeys;
import com.application.chatter.util.UserInfoUtil.UserInfoParams;

/**
 * Servlet implementation class TestServlet
 */
@WebServlet(description = "Servlet to get chatter reports", urlPatterns = { "/TestServlet" })
public class ChatterReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChatterReportServlet() {
        super();       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {							
		
		handleLogout(request, response);
		
		IApplicationInfo applicationInfo = null;
		Object appSessionToken = request.getSession().getAttribute(SessionBeanKeys.APPLICATIONINFO.getKey());					
		RequestDispatcher errorDispatcher = getServletConfig().getServletContext().getRequestDispatcher(ApplicationUtil.ERROR_PATH);
		
		
		if(appSessionToken instanceof IApplicationInfo){		
			applicationInfo = (IApplicationInfo)appSessionToken;
		}
		else{
			try{
				IAuthenticate authenticator = OAuthUserAuthenticator.getInstance();
				applicationInfo = authenticator.authenticate(request, response);				
				
				if(applicationInfo == null || applicationInfo.getToken() == null || !applicationInfo.getToken().isAuthenticated()){
					return;
				}				
			}
			catch (AuthenticationException e) {
				request.setAttribute(RequestParams.ERROR.getParamName(), e.getMessage());	
				errorDispatcher.forward(request, response);
			}
			catch (Exception e) {
				request.setAttribute(RequestParams.ERROR.getParamName(), e.getMessage());	
				errorDispatcher.forward(request, response);
			}
		}
		
		ChatterFeedsReport feedsReport = null;
		
		try {
			String feedType = request.getParameter(UserInfoParams.FEEDTYPE.getUserInfoParamName());
			String profileType = request.getParameter(UserInfoParams.PROFILETYPE.getUserInfoParamName());
			feedsReport = FeedsReportGenerator.getReport(applicationInfo, profileType, feedType);
						
			request.getSession().setAttribute(SessionBeanKeys.APPLICATIONINFO.getKey(), applicationInfo);
			request.setAttribute(SessionBeanKeys.FEEDSREPORT.getKey(), feedsReport);			
			
			RequestDispatcher reqDispatcher = getServletConfig().getServletContext().getRequestDispatcher(ApplicationUtil.JSP_PATH);			
			reqDispatcher.forward(request, response);
		} 
		catch (ProfileReadException e) {
			request.setAttribute(RequestParams.ERROR.getParamName(), e.getMessage());	
			errorDispatcher.forward(request, response);
		} 
		catch (FeedException e) {
			request.setAttribute(RequestParams.ERROR.getParamName(), e.getMessage());
			errorDispatcher.forward(request, response);
		}
		catch (Exception e) {
			request.setAttribute(RequestParams.ERROR.getParamName(), e.getMessage());
			errorDispatcher.forward(request, response);
		}
	}

	private void handleLogout(HttpServletRequest request, HttpServletResponse response) {
		
		Object logout = request.getParameter(RequestParams.LOGOUT.getParamName());
		
		if(logout != null){			
			Cookie[] cookies = request.getCookies();
			for(Cookie cookie: cookies){
				if(cookie.getName().equals("sid")){ // TODO Unable to access the cookie thought HTTPS call
					cookie.setMaxAge(0);
					response.addCookie(cookie);
					break;
				}
			}
			
			HttpSession session =  request.getSession();
			if(session != null){
				session.invalidate();
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
	}

}
