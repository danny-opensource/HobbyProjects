package com.mg.adapters;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Array;
import java.util.List;

import com.mg.model.RssItem;
import com.mg.newsreader.R;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

public class RSSFeedAdapter extends ArrayAdapter<RssItem> {

	public RSSFeedAdapter(Context context, int rssListItem, List<RssItem> mRssItems) {
		super(context, rssListItem, mRssItems);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View v = convertView;

		if (v == null) {
			LayoutInflater inflater;
			inflater = LayoutInflater.from(getContext());

			v = inflater.inflate(R.layout.rss_list_item, null);
		}

		RssItem item = getItem(position);

		ImageView imgView = (ImageView) v.findViewById(R.id.rssThumb);

		try {
			URL thumbUrl = new URL(item.getThumbnailLink());
			Bitmap bmp = BitmapFactory.decodeStream(thumbUrl.openConnection().getInputStream());
			imgView.setImageBitmap(bmp);
		} catch (MalformedURLException mue) {
			mue.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		TextView txtView = (TextView) v.findViewById(R.id.rssText);
		txtView.setText(item.getTitle());

		return v;
	}
}
