package com.application.chatter.feeds;

import java.util.List;
import java.util.Map;

import com.application.chatter.feeds.simplefeed.Feed;

/**
 * An interface to store the user's feeds
 * 
 * @author hbalasubramanian
 *
 */
public interface IFeeds {

	/**
	 * 
	 * Feed data stores as a Map of <CreationDate,Feed>
	 * 
	 * @author hbalasubramanian
	 *
	 */
	interface IFeedData{
		
		public Map<String, List<Feed>> getUserFeeds();
		
	}
	
	/**
	 * 
	 * Returns the current user's feed data
	 * 
	 * @throws FeedException
	 */
	public IFeedData getFeedData() throws FeedException;
}
