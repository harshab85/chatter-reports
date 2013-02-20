package com.application.chatter.feeds.simplefeed;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.feeds.FeedException;
import com.application.chatter.feeds.IFeeds;
import com.application.chatter.util.ApplicationUtil;
import com.application.chatter.util.UserInfoUtil;

public class SimpleFeeds implements IFeeds {

	IApplicationInfo applicationInfo = null;
	
	public SimpleFeeds(IApplicationInfo applicationInfo){
		this.applicationInfo = applicationInfo;
	}
	
	@Override
	public IFeedData getFeedData() throws FeedException {
		
		IFeedData feedData = null;
		
		try{
			StringBuilder sb = new StringBuilder();
			sb.append(applicationInfo.getInstanceURL());
			sb.append(UserInfoUtil.FEEDS_URL_PATH);
			
			JSONObject json = UserInfoUtil.getJSONResponse(sb.toString(), applicationInfo.getToken().getValue());
			if(json != null){
				feedData = createFeedData(json);
			}
			else{
				throw new Exception("Unable to read the feed information");
			}
		}
		catch (Exception e) {
			throw new FeedException(ApplicationUtil.getErrorMessage(e));
		}
		
		
		return feedData;		
	}

	protected IFeedData createFeedData(JSONObject json) {

		Map<String, List<Feed>> userFeeds = createUserFeeds(json); 
		
		IFeedData feedData = new SimpleFeedBean(userFeeds);
		return feedData;
	}

	protected Map<String, List<Feed>> createUserFeeds(JSONObject json) {
		
		Map<String, List<Feed>> feeds = new LinkedHashMap<String, List<Feed>>();
		
		JSONArray items = (JSONArray)json.get("items");
		
		Iterator<JSONObject> iter = items.iterator();
		while(iter.hasNext()){
			JSONObject item = iter.next();			
			JSONObject body = (JSONObject)item.get("body");
			JSONArray messageSegments = (JSONArray)body.get("messageSegments");
				
			Iterator<JSONObject> ms = messageSegments.iterator();
			String type = null;
			while(ms.hasNext()){
				JSONObject segment = ms.next();
				type = (String)segment.get("type");
				if(type != null){
					break;
				}
			}
											
			String post = (String)body.get("text");			
			String date = (String)item.get("createdDate");
						
			JSONObject comments = (JSONObject)item.get("comments");
			Long numComments = (Long)comments.get("total");
			
			JSONObject likes = (JSONObject)item.get("likes");
			Long numLikes = (Long)likes.get("total");
			
			Calendar calendar = DatatypeConverter.parseDateTime(date);
			Date createdDate = calendar.getTime();
						
			Feed feed = new Feed();
			feed.setPost(post);
			feed.setType(type);
			feed.setCreationDate(createdDate);
			feed.setNumComments(numComments);
			feed.setLikes(numLikes);
			
			String dateKey = createDateKey(createdDate);
			List<Feed> feedsList = feeds.get(dateKey);
			if(feedsList == null){
				feedsList = new ArrayList<Feed>();
			}
			
			feedsList.add(feed);
			feeds.put(dateKey, feedsList);			
		}
		return feeds;
	}

	protected String createDateKey(Date createdDate) {		
		DateFormat df = new SimpleDateFormat("MMM dd yyyy");
		String dateKey = df.format(createdDate);		
		return dateKey;
	}

}


