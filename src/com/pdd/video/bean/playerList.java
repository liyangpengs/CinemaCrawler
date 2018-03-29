package com.pdd.video.bean;

import java.util.Date;

public class playerList {
	private Integer pid; //id
	private String title; //标题   第几集|第几期
	private String cover; //封面
	private Date crawlerTime; //爬取时间
	private String SourceUrl; //源站url
	private String vid;//主表主键
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public Date getCrawlerTime() {
		return crawlerTime;
	}
	public void setCrawlerTime(Date crawlerTime) {
		this.crawlerTime = crawlerTime;
	}
	public String getSourceUrl() {
		return SourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		SourceUrl = sourceUrl;
	}
}

