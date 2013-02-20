package com.application.chatter.util;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public final class UserInfoUtil {

	//public static final String FEEDS_URL_PATH = "/services/data/v23.0/chatter/feeds/news/me/feed-items";
	public static final String FEEDS_URL_PATH = "/services/data/v23.0/chatter/feeds/user-profile/me/feed-items";
	
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
	
	public enum FeedType{
		SIMPLE("SIMPLE"), ADVANCED("ADVANCED");
		
		String feedType = null;
		
		FeedType(String feedType){
			this.feedType = feedType;
		}
		
		public String getFeedType(){
			return this.feedType;
		}
	}
	
	public enum ProfileType{
		SIMPLE("SIMPLE"), ADVANCED("ADVANCED");
		
		String profileType = null;
		
		ProfileType(String profileType){
			this.profileType = profileType;
		}
		
		public String getProfileType(){
			return this.profileType;
		}
	}
	
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
	
	public static JSONObject getJSONResponse(String urlString, String headerValue) throws IOException, ParseException{
		URL url = new URL(urlString);		
		URLConnection conn = url.openConnection();
		
		StringBuilder sb = new StringBuilder();
		sb.append(AuthorizationUtil.AUTHORIZATION_PREFIX);
		sb.append(" ");
		sb.append(headerValue);
		conn.setRequestProperty(AuthorizationUtil.AUTHORIZATION_HEADER, sb.toString());
		
		return ApplicationUtil.getJSONResponse(conn);
	}
}
