package com.application.chatter.feeds.simplefeed;

import java.util.List;
import java.util.Map;

import com.application.chatter.feeds.IFeeds.IFeedData;

public class SimpleFeedBean implements IFeedData {

	private Map<String, List<Feed>> userFeeds = null;

	public SimpleFeedBean() {

	}

	public SimpleFeedBean(Map<String, List<Feed>> userFeeds) {
		this.userFeeds = userFeeds;
	}

	@Override
	public Map<String, List<Feed>> getUserFeeds() {
		return userFeeds;
	}
}
