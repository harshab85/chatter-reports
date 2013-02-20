package com.application.chatter.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import com.application.chatter.reports.MissingInputException;

public final class UserInfoUtil {

	public static final String DATEKEY_FORMAT = "MMM dd yyyy";
	
	public static final String FEEDS_URL_PATH = "/services/data/v23.0/chatter/feeds/user-profile/me/feed-items";
	
	/**
	 * Enum to store the keys to retrieve user information
	 * 
	 * @author hbalasubramanian
	 *
	 */
	public enum UserInfoParams{
		FEEDTYPE("FEEDTYPE"), PROFILETYPE("PROFILETYPE");
		
		String info = null;
		
		UserInfoParams(String info){
			this.info = info;
		}
		
		public String getUserInfoParamName(){
			return this.info;
		}
	}
	
	/**
	 * Enum to store the supported feed types
	 * 
	 * @author hbalasubramanian
	 *
	 */
	public enum FeedType{
		SIMPLE("SIMPLE");
		
		String feedType = null;
		
		FeedType(String feedType){
			this.feedType = feedType;
		}
		
		public String getFeedType(){
			return this.feedType;
		}
	}
	
	/**
	 * Enum to store the supported profile types
	 * 
	 * @author hbalasubramanian
	 *
	 */
	public enum ProfileType{
		SIMPLE("SIMPLE");
		
		String profileType = null;
		
		ProfileType(String profileType){
			this.profileType = profileType;
		}
		
		public String getProfileType(){
			return this.profileType;
		}
	}
	
	/**
	 * Enum to store the keys used to retrieve the user profile information
	 * 
	 * @author hbalasubramanian
	 *
	 */
	public enum UserProfileKeys{
		
		FIRST_NAME("first_name"), LAST_NAME("last_name"), PHOTOS("photos"),
		THUMBNAIL("thumbnail"), PICTURE("picture");
		
		String key;
		
		UserProfileKeys(String key){
			this.key = key;
		}
		
		public String getKey(){
			return this.key;
		}
		
	}
	
	/**
	 * Enum to store the keys used to retrieve the user feed information
	 * 
	 * @author hbalasubramanian
	 *
	 */
	public enum UserFeedsKeys{
		
		ITEMS("items"), BODY("body"), MESSAGESEGMENTS("messageSegments"),
		TYPE("type"), TEXT("text"), CREATEDDATE("createdDate"), COMMENTS("comments"),
		LIKES("likes"), TOTAL("total");
		
		String key;
		
		UserFeedsKeys(String key){
			this.key = key;
		}
		
		public String getKey(){
			return this.key;
		}
		
	}
	
	/**
	 * Utility method to create a unique data key for the user feeds
	 * 
	 * @return
	 */
	public static String createUserFeedsDateKey(Date createdDate) {		
		DateFormat df = new SimpleDateFormat(UserInfoUtil.DATEKEY_FORMAT);
		String dateKey = df.format(createdDate);		
		return dateKey;
	}
	
	/**
	 * Return the JSON response for the given URL and access token
	 * 
	 * @throws IOException
	 * @throws ParseException
	 * @throws MissingInputException 
	 */
	public static JSONObject getJSONResponse(String urlString, String accessToken) throws IOException, ParseException, MissingInputException{
		
		if(urlString == null || urlString.isEmpty() || accessToken == null || accessToken.isEmpty()){
			throw new MissingInputException("Missing input to retrieve JSON response");
		}
		
		URL url = new URL(urlString);		
		URLConnection conn = url.openConnection();
		
		StringBuilder sb = new StringBuilder();
		sb.append(AuthorizationUtil.AUTHORIZATION_PREFIX);
		sb.append(" ");
		sb.append(accessToken);
		conn.setRequestProperty(AuthorizationUtil.AUTHORIZATION_HEADER, sb.toString());
		
		return ApplicationUtil.getJSONResponse(conn);
	}
}
