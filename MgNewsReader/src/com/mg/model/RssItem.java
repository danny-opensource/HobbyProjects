package com.mg.model;

public class RssItem {

	private String mTitle;
	private String mLink;
	private String mDescription;
	private String mThumbnailLink;

	public RssItem(final String title, final String link, final String description, final String thumbnailLink) {
		mTitle = title;
		mLink = link;
		mDescription = description;
		mThumbnailLink = thumbnailLink;
	}

	public String getTitle() {
		return mTitle;
	}

	public String getLink() {
		return mLink;
	}

	public String getDescription() {
		return mDescription;
	}

	public String getThumbnailLink() {
		return mThumbnailLink;
	}

}
