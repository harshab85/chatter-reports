package com.application.chatter.feeds;

import java.util.List;
import java.util.Map;

import com.application.chatter.feeds.simplefeed.Feed;


public interface IFeeds {

	interface IFeedData{
		
		public Map<String, List<Feed>> getUserFeeds();
		
	}
	
	public IFeedData getFeedData() throws FeedException;
}
