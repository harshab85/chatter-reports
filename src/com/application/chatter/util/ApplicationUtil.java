package com.application.chatter.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.application.chatter.reports.MissingInputException;

/**
 * 
 * 
 * @author hbalasubramanian
 *
 */
public final class ApplicationUtil {

	public static final String JSP_PATH = "/WEB-INF/views/ChatterReporter.jsp";
	public static final String PATH = "/ChatterReports/Report";
	public static final String ERROR_PATH = "/WEB-INF/views/Error.jsp";
	
	/**
	 * 
	 * Enum to store keys that will be stored in the request's session
	 * 
	 * @author hbalasubramanian
	 *
	 */		
	public static enum SessionBeanKeys{
		
		AUTHTOKEN("AUTHTOKEN"), FEEDSREPORT("FEEDSREPORT"), DATE("DATE"),
		APPLICATIONINFO("APPLICATIONINFO");
		
		private String key;
		
		SessionBeanKeys(String key){
			this.key = key;
		}
		
		public String getKey(){
			return this.key;
		}
		
	}
	
	/**
	 * Enum to store keys that will be used in the request parameters
	 * 
	 * @author hbalasubramanian
	 *
	 */
	public static enum RequestParams{
		
		LOGOUT("LOGOUT"), ERROR("ERROR");
		
		String paramName = null;
		
		private RequestParams(String paramName) {
			this.paramName = paramName;
		}
		
		public String getParamName(){
			return this.paramName;
		}
	}
	
	/**
	 * Retrieve the full stack trace from an exception without a default message
	 * 
	 * @param throwable	
	 */
	public static String getErrorMessage(Throwable throwable) {
		return getErrorMessage("", throwable);
	}
	
	/**
	 * Retrieve the full stack trace from an exception with a default message
	 * 
	 * @param throwable	
	 */
	public static String getErrorMessage(String msg, Throwable throwable) {
		if (throwable == null) {
			return "Unknown Error";
		}

		String message = msg + throwable.getMessage();

		StringWriter stringWriter = new StringWriter();
		PrintWriter printWriter = new PrintWriter(stringWriter);
		throwable.printStackTrace(printWriter);
		message = stringWriter.toString();

		return message;
	}
	
	/**
	 * Reads proerty files and return a corresponding {@link Properties} instance
	 * 
	 * @throws IOException
	 */
	public static Properties readApplicationProperties(String propertyFileName) throws IOException{
		
		Properties properties = new Properties();
		
		if(propertyFileName == null || propertyFileName.isEmpty()){
			return properties;
		}
		
		ClassLoader loader = Thread.currentThread().getContextClassLoader();           
		InputStream stream = null; 
		try{
			stream = loader.getResourceAsStream(propertyFileName);
			properties.load(stream);
		}
		finally{
			if(stream != null){
				stream.close();
			}
		}
		
		return properties;
	}
	
	/**
	 * Encodes a returna given raw string
	 * 
	 * @throws UnsupportedEncodingException
	 */
	public static String encode(String rawString) throws UnsupportedEncodingException{
		
		if(rawString == null || rawString.isEmpty()){
			return rawString;
		}
		
		return URLEncoder.encode(rawString, "UTF-8");
	}
	
	/**
	 * Return the JSON response for the given connection
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws MissingInputException 
	 */
	public static JSONObject getJSONResponse(URLConnection urlConnection) throws IOException, ParseException, MissingInputException{
				
		if(urlConnection == null){
			throw new MissingInputException("Unable to get the JSON response");
		}
		
		BufferedReader in = null;
		StringBuffer inputLine = new StringBuffer();
		try{
			in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			String s = "";
			while ((s = in.readLine()) != null) 
				inputLine.append(s);
		}
		finally{
			if(in != null)
				in.close();
		}
						
		JSONObject json = null;
		String idServiceResponse = inputLine.toString();
		if(!idServiceResponse.isEmpty()){
			json = (JSONObject)new JSONParser().parse(idServiceResponse);
		}
		
		return json;
	}
	
	
	/**
	 * Return the JSON response for the given URL
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws MissingInputException 
	 */
	public static JSONObject getJSONResponse(String urlString) throws IOException, ParseException, MissingInputException{
		
		if(urlString == null || urlString.isEmpty()){
			throw new MissingInputException("Unable to get the JSON response");
		}
		
		URL url = new URL(urlString);		
		URLConnection conn = url.openConnection();
		return getJSONResponse(conn);
	}
}
