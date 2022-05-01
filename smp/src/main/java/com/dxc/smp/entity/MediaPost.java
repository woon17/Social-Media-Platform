package com.dxc.smp.entity;

import org.springframework.core.io.Resource;

public class MediaPost {
	private int id;
	private String type;
	private String caption;
	private int views;
	private String link;
	private Resource data;
	private String createdBy;
	
	
	public MediaPost(Post post, Resource data) {
		super();
		this.id = post.getId();
		this.type = post.getType();
		this.caption = post.getCaption();
		this.views = post.getViews();
		this.link = post.getLink();
		this.createdBy = post.getCreatedBy();
		this.data = data;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public int getViews() {
		return views;
	}
	public void setViews(int views) {
		this.views = views;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public Resource getData() {
		return data;
	}
	public void setData(Resource data) {
		this.data = data;
	}
	
	
	
}
