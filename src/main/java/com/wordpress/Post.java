package com.wordpress;

import java.util.ArrayList;


import java.sql.Timestamp;

public class Post {
	@Override
	public String toString() {
		return "Post [pageUrl=" + id + ", title=" + title + "]";
	}

	private String tag = null;
	private String thumbnail = null;
	private String id = null;
	private String category = null;
	private String title = null;
	private String body = null;
	private Timestamp postDate = DbManager.getTodayTimestamp();
	private ArrayList<Image> imgs = new ArrayList<Image>();

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public ArrayList<Image> getImgs() {
		return imgs;
	}

	public void setImgs(ArrayList<Image> imgs) {
		this.imgs = imgs;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Timestamp getPostDate() {
		return postDate;
	}

	public void setPostDate(Timestamp postDate) {
		this.postDate = postDate;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
}
