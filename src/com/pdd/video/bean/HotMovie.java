package com.pdd.video.bean;

public class HotMovie {
	private String cover; //封面
	private String videoName; //电影名称
	private String duration; //时长
	private String tostarInfo; //封面主演
	private String score; //评分
	private String PlayerUrl;//播放页面
	private String publishTime;//发布时间
	private String lastupdateinfo;//最后更新信息
	public String getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(String publishTime) {
		this.publishTime = publishTime;
	}
	public String getLastupdateinfo() {
		return lastupdateinfo;
	}
	public void setLastupdateinfo(String lastupdateinfo) {
		this.lastupdateinfo = lastupdateinfo;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getVideoName() {
		return videoName;
	}
	public void setVideoName(String videoName) {
		this.videoName = videoName;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTostarInfo() {
		return tostarInfo;
	}
	public void setTostarInfo(String tostarInfo) {
		this.tostarInfo = tostarInfo;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getPlayerUrl() {
		return PlayerUrl;
	}
	public void setPlayerUrl(String playerUrl) {
		PlayerUrl = playerUrl;
	}
}
