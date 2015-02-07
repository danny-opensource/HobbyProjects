package com.mg.newsreader;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.mg.adapters.RSSFeedAdapter;
import com.mg.model.RssItem;

public class RssReaderActivity extends Activity {

	private List<RssItem> mRssItems;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

		StrictMode.setThreadPolicy(policy);
		setContentView(R.layout.rss_main);
		super.onCreate(savedInstanceState);
	}

	private List<RssItem> getRssItems() {
		List<RssItem> returnList = new ArrayList<RssItem>();
		RssItem item1 = new RssItem("Title1", "http://www.google.com", "Description 1",
				"http://zapt1.staticworld.net/images/article/2013/11/pcworld-idgns-primaryimage-100154539-medium.png");

		RssItem item2 = new RssItem("Title2", "http://www.google.com", "Description 2",
				"http://zapt1.staticworld.net/images/article/2013/11/pcworld-idgns-primaryimage-100154539-medium.png");

		RssItem item3 = new RssItem("Title3", "http://www.google.com", "Description 3",
				"http://zapt1.staticworld.net/images/article/2013/11/pcworld-idgns-primaryimage-100154539-medium.png");

		RssItem item4 = new RssItem("Title4", "http://www.google.com", "Description 4",
				"http://zapt1.staticworld.net/images/article/2013/11/pcworld-idgns-primaryimage-100154539-medium.png");

		returnList.add(item1);
		returnList.add(item2);
		returnList.add(item3);
		returnList.add(item4);
		return returnList;
	}

	@Override
	protected void onResume() {
		mRssItems = getRssItems();
		ListView rssListView = (ListView) findViewById(R.id.rssList);
		ArrayAdapter<RssItem> rssListAdapter = new RSSFeedAdapter(this, R.layout.rss_list_item, mRssItems);
		rssListView.setAdapter(rssListAdapter);
		super.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

}
