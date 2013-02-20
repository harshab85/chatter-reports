package com.application.chatter.feeds;

import com.application.chatter.applicationinfo.IApplicationInfo;
import com.application.chatter.feeds.IFeeds.IFeedData;
import com.application.chatter.feeds.simplefeed.SimpleFeeds;
import com.application.chatter.util.UserInfoUtil.FeedType;

public class FeedsFactory {

	private static final FeedsFactory feedsFactory = new FeedsFactory();
	
	private FeedsFactory(){
	}
	
	public static FeedsFactory getFactory(){
		return feedsFactory;
	}
	
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
