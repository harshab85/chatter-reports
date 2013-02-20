package com.application.chatter.feeds;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.feeds.IFeeds.IFeedData;
import com.application.chatter.feeds.simplefeed.SimpleFeeds;
import com.application.chatter.util.UserInfoUtil.FeedType;

/**
 * A factory class to create feed instances using the request's 'feed type' parameter
 * 
 * @author hbalasubramanian
 *
 */
public class FeedsFactory {

	private static final FeedsFactory feedsFactory = new FeedsFactory();
	
	private FeedsFactory(){
	}
	
	/**
	 * Eagerly initialized singleton
	 * 
	 */
	public static FeedsFactory getFactory(){
		return feedsFactory;
	}
	
	/**
	 * Create a new feed bean using the feed type.
	 * 
	 *  <br>
	 *  
	 *  By default the factory creates {@link SimpleFeeds}
	 * 
	 * @throws FeedException
	 * @throws {@link UnsupportedOperationException} when the feed type in the request is not supported
	 */
	public IFeedData getFeeds(IApplicationInfo applicationInfo, String feedType) throws FeedException{
		
		if(feedType == null || feedType.isEmpty() || feedType.equalsIgnoreCase(FeedType.SIMPLE.getFeedType())){
			IFeeds feeds = new SimpleFeeds(applicationInfo);
			return feeds.getFeedData();
		}
		else{
			throw new UnsupportedOperationException("The given feed type is not supported : " + feedType);
		}
	}
}
