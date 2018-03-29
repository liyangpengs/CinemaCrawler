package com.pdd.video.bean;

import java.util.Date;
import java.util.List;

public class pageBase {
	private String vid;//id
	private String VideoName; //影视名称
	private String cover; //封面
	private String duration; //时长
	private String TostarInfo; //封面主演
	private String score; //评分
	private String SourceUrl; //源站url
	private int mtid; //主分类
	private String director; //导演
	private String Watch; //看点
	private String performerInfo; //演员信息  xxx 饰 xxx
	private String Description; //剧情简介
	private String PlayerUrl; //播放地址
	private Date PublishTime; //影视发布时间
	private Date crawlerTime; //爬取时间
	private List<Integer> mtSubid; //子分类
	private List<playerList> plist; //播放列表 [集|期]
	private String lastUpdateInfo; //最后更新状态
	private String keywords;//关键字
	private String Metadescription;//影片简介
	private String playCount;//播放次数
	private String mainTypeStr;//主分类字符串
	public String getMainTypeStr() {
		return mainTypeStr;
	}
	public void setMainTypeStr(String mainTypeStr) {
		this.mainTypeStr = mainTypeStr;
	}
	public String getPlayCount() {
		return playCount;
	}
	public void setPlayCount(String playCount) {
		this.playCount = playCount;
	}
	public String getVid() {
		return vid;
	}
	public void setVid(String vid) {
		this.vid = vid;
	}
	public String getVideoName() {
		return VideoName;
	}
	public void setVideoName(String videoName) {
		VideoName = videoName;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getTostarInfo() {
		return TostarInfo;
	}
	public void setTostarInfo(String tostarInfo) {
		TostarInfo = tostarInfo;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getDirector() {
		return director;
	}
	public void setDirector(String director) {
		this.director = director;
	}
	public String getWatch() {
		return Watch;
	}
	public void setWatch(String watch) {
		Watch = watch;
	}
	public String getPerformerInfo() {
		return performerInfo;
	}
	public void setPerformerInfo(String performerInfo) {
		this.performerInfo = performerInfo;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPlayerUrl() {
		return PlayerUrl;
	}
	public void setPlayerUrl(String playerUrl) {
		PlayerUrl = playerUrl;
	}
	public Date getPublishTime() {
		return PublishTime;
	}
	public void setPublishTime(Date publishTime) {
		PublishTime = publishTime;
	}
	public Date getCrawlerTime() {
		return crawlerTime;
	}
	public void setCrawlerTime(Date crawlerTime) {
		this.crawlerTime = crawlerTime;
	}
	public int getMtid() {
		return mtid;
	}
	public void setMtid(int mtid) {
		this.mtid = mtid;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public List<Integer> getMtSubid() {
		return mtSubid;
	}
	public void setMtSubid(List<Integer> mtSubid) {
		this.mtSubid = mtSubid;
	}
	public List<playerList> getPlist() {
		return plist;
	}
	public void setPlist(List<playerList> plist) {
		this.plist = plist;
	}
	public String getLastUpdateInfo() {
		return lastUpdateInfo;
	}
	public void setLastUpdateInfo(String lastUpdateInfo) {
		this.lastUpdateInfo = lastUpdateInfo;
	}
	public String getSourceUrl() {
		return SourceUrl;
	}
	public void setSourceUrl(String sourceUrl) {
		SourceUrl = sourceUrl;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public String getMetadescription() {
		return Metadescription;
	}
	public void setMetadescription(String metadescription) {
		Metadescription = metadescription;
	}
}
