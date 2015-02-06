package com.mg.newsreader;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class SimpleRSSReaderActivity extends ListActivity {

	private List mHeadlines;
	private List mLinks;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mHeadlines = new ArrayList();
		mLinks = new ArrayList();

		try {
			URL url = new URL("http://feeds.pcworld.com/pcworld/latestnews");
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			factory.setNamespaceAware(false);
			XmlPullParser xpp = factory.newPullParser();

			xpp.setInput(getInputStream(url), "UTF_8");

			boolean insideItem = false;

			int eventType = xpp.getEventType();

			while (eventType != XmlPullParser.END_DOCUMENT) {
				Log.d("Madan", xpp.nextText());
				if (eventType == XmlPullParser.START_TAG) {
					if (xpp.getName().equalsIgnoreCase("item")) {
						insideItem = true;
					} else if (xpp.getName().equalsIgnoreCase("title")) {
						if (insideItem) {
							mHeadlines.add(xpp.nextText());
						}
					} else if (xpp.getName().equalsIgnoreCase("link")) {
						if (insideItem) {
							mLinks.add(xpp.nextText());
						}
					}
				} else if (eventType == XmlPullParser.END_TAG
						&& xpp.getName().equalsIgnoreCase("item")) {
					insideItem = false;
				}
				eventType = xpp.next();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		mHeadlines.add("Sample Text 1");
		mHeadlines.add("Sample Text 2");

		ArrayAdapter adapter = new ArrayAdapter(this,
				android.R.layout.simple_list_item_1, mHeadlines);
		setListAdapter(adapter);

	}

	private InputStream getInputStream(URL url) {
		try {
			return url.openConnection().getInputStream();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		Uri uri = Uri.parse((String) mLinks.get(position));
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		startActivity(intent);
	}

}
