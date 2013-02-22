package com.application.chatter.feeds.simplefeed;

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
import com.application.chatter.reports.MissingInputException;
import com.application.chatter.util.ApplicationUtil;
import com.application.chatter.util.UserInfoUtil;
import com.application.chatter.util.UserInfoUtil.UserFeedsKeys;

/**
 * A basic implementation of {@link IFeedData}
 * 
 * <br>
 * 
 * Returns the following information about the current user's feeds
 * <ol>
 * <li> Creation Date
 * <li> Post (Total per day)
 * <li> Type of feed
 * <li> Likes
 * <li> Number of comments for each feed
 * 
 * @author hbalasubramanian
 *
 */
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

	/**
	 * Creates simple feeds as a {@link LinkedHashMap} to maintain order by 
	 * creation date
	 * 
	 */
	protected Map<String, List<Feed>> createUserFeeds(JSONObject json) {
		
		Map<String, List<Feed>> feeds = new LinkedHashMap<String, List<Feed>>();
		
		Object jaItems = json.get(UserFeedsKeys.ITEMS.getKey());
		
		if(jaItems instanceof JSONArray){
			
			JSONArray items = (JSONArray)jaItems;
			
			Iterator<JSONObject> iter = items.iterator();
			while(iter.hasNext()){
				JSONObject item = iter.next();			
				
				Object jBody = item.get(UserFeedsKeys.BODY.getKey());
				if(jBody instanceof JSONObject){
					JSONObject body = (JSONObject)jBody;
					
					JSONArray messageSegments = null;
					Object jMessageSegments = body.get(UserFeedsKeys.MESSAGESEGMENTS.getKey());
					if(jMessageSegments instanceof JSONArray){
						messageSegments = (JSONArray)jMessageSegments;
					}
					
					Object sType = null;
					String type = null;
					if(jMessageSegments != null){
						Iterator<JSONObject> ms = messageSegments.iterator();					
						while(ms.hasNext()){
							JSONObject segment = ms.next();
							sType = segment.get(UserFeedsKeys.TYPE.getKey());
							if(sType instanceof String){
								type = (String)sType;
								break;
							}
						}
					}
											
					Object sText = body.get(UserFeedsKeys.TEXT.getKey());
					String post = "";
					if(sText instanceof String){
						post = (String)sText;
					}
					
					Object sDate = item.get(UserFeedsKeys.CREATEDDATE.getKey());
					String date = null;
					if(sDate instanceof String){
						date = (String)sDate;
					}
					
					Object jComments = item.get(UserFeedsKeys.COMMENTS.getKey());
					JSONObject comments = null;
					if(jComments instanceof JSONObject){
						comments = (JSONObject)jComments;
					}
					
					Object lNumComments = comments.get(UserFeedsKeys.TOTAL.getKey());;
					Long numComments = 0L;
					if(lNumComments instanceof Long){
						numComments = (Long)lNumComments;
					}
					
					List<Feed> commentsList = createComments(comments);
					
					Object jLikes = item.get(UserFeedsKeys.LIKES.getKey());
					JSONObject likes = null;
					if(jLikes instanceof JSONObject){
						likes = (JSONObject)jLikes;
					}
					
					Long numLikes = 0L;
					Object lNumLikes = likes.get(UserFeedsKeys.TOTAL.getKey());;
					if(lNumLikes instanceof Long){
						numLikes = (Long)lNumLikes;
					}
					
					Date createdDate = null;
					try {
						Calendar calendar = DatatypeConverter.parseDateTime(date);
						createdDate = calendar.getTime();
					}
					catch (IllegalArgumentException e) {
						// TODO should log the error
						// Cannot proceed without createdDate
						continue;
					}
								
					Feed feed = new Feed();
					feed.setPost(post);
					feed.setType(type);
					feed.setCreationDate(createdDate);
					feed.setNumComments(numComments);
					feed.setLikes(numLikes);
					feed.setComments(commentsList);										
					createFullPost(feed);
								
					String dateKey = "";
					try {
						dateKey = UserInfoUtil.createUserFeedsDateKey(createdDate);
					} 
					catch (MissingInputException e) {
						// TODO should log the error
						// Cannot proceed without createdDate
						continue;
					}
					
					List<Feed> feedsList = feeds.get(dateKey);
					if(feedsList == null){
						feedsList = new ArrayList<Feed>();
					}					
					
					feedsList.add(feed);
					feeds.put(dateKey, feedsList);			
				}
			}
		}
		
		
		
		return feeds;
	}

	protected void createFullPost(Feed feed){
		String post = feed.getPost();
		List<Feed> comments = feed.getComments();
		
		StringBuilder sb = new StringBuilder();
		sb.append(post);		
		
		for(Feed comment: comments){
			sb.append("\n\r");
			sb.append(comment.getPost());
		}
		
		feed.setFullPost(sb.toString());
	}
	
	protected List<Feed> createComments(JSONObject comments) {
		
		List<Feed> feedsList = new ArrayList<Feed>();
		
		Object jaComments = comments.get("comments");
		JSONArray commentsArray = null;
		if(jaComments instanceof JSONArray){
			commentsArray = (JSONArray)comments.get("comments");
		
			Iterator<JSONObject> iter = commentsArray.iterator();
			
			while(iter.hasNext()){
			
				JSONObject json = iter.next();
				
				Object jBody = json.get(UserFeedsKeys.BODY.getKey());
				if(jBody instanceof JSONObject){
					JSONObject body = (JSONObject)jBody;		
					
					Object sPost = body.get(UserFeedsKeys.TEXT.getKey());
					String post = "";
					if(sPost instanceof String){
						post = (String)sPost;
					}
					
					Object sDate = json.get(UserFeedsKeys.CREATEDDATE.getKey());
					String date = "";
					if(sDate instanceof String){
						date = (String)sDate;
					}
					
					Date createdDate = null;
					try{
						Calendar calendar = DatatypeConverter.parseDateTime(date);
						createdDate = calendar.getTime();
					}				
					catch (IllegalArgumentException e) {
						// TODO should log the error
					}
					
					Feed feed = new Feed();
					feed.setPost(post);
					feed.setType("Comment");
					feed.setCreationDate(createdDate);		
					feed.setLikes(0L);
					feed.setNumComments(0L);
					
					feedsList.add(feed);
				}
			}
		}
		
		return feedsList;
	}
}


