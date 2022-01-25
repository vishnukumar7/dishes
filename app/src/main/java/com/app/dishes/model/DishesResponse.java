package com.app.dishes.model;

import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Entity;

import java.util.List;
import com.google.gson.annotations.SerializedName;
@Entity(tableName = "dish_item")
public class DishesResponse{

	@ColumnInfo(name = "share_link")
	@SerializedName("share_link")
	private String shareLink;

	@SerializedName("more_images")
	private List<String> moreImages;

	@SerializedName("image_url")
	private String imageUrl;

	@SerializedName("name")
	private String name;

	@SerializedName("wiki_link")
	private String wikiLink;

	@SerializedName("id")
	private Integer id;

	@SerializedName("short_desc")
	private String shortDesc;

	public void setShareLink(String shareLink){
		this.shareLink = shareLink;
	}

	public String getShareLink(){
		return shareLink;
	}

	public void setMoreImages(List<String> moreImages){
		this.moreImages = moreImages;
	}

	public List<String> getMoreImages(){
		return moreImages;
	}

	public void setImageUrl(String imageUrl){
		this.imageUrl = imageUrl;
	}

	public String getImageUrl(){
		return imageUrl;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setWikiLink(String wikiLink){
		this.wikiLink = wikiLink;
	}

	public String getWikiLink(){
		return wikiLink;
	}

	public void setId(Integer id){
		this.id = id;
	}

	public Integer getId(){
		return id;
	}

	public void setShortDesc(String shortDesc){
		this.shortDesc = shortDesc;
	}

	public String getShortDesc(){
		return shortDesc;
	}
}