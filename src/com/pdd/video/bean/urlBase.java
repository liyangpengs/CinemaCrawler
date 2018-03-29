package com.pdd.video.bean;

public class urlBase {
	private String title;//标题
	private String score;//评分
	private String fmzy;//封面主演
	private String cover;//封面
	private String IndexUrl;//详情页面页面
	private String duration;//电影时长
	private int mainType;//主分类
	private String lastUpdateInfo;//当前更新状态
	private String mainTypeStr;//主分类中文
	private String subTypeStr;//子分类中文
	public String getSubTypeStr() {
		return subTypeStr;
	}
	public void setSubTypeStr(String subTypeStr) {
		this.subTypeStr = subTypeStr;
	}
	public String getMainTypeStr() {
		return mainTypeStr;
	}
	public void setMainTypeStr(String mainTypeStr) {
		this.mainTypeStr = mainTypeStr;
	}
	public String getLastUpdateInfo() {
		return lastUpdateInfo;
	}
	public void setLastUpdateInfo(String lastUpdateInfo) {
		this.lastUpdateInfo = lastUpdateInfo;
	}
	public int getMainType() {
		return mainType;
	}
	public void setMainType(int mainType) {
		this.mainType = mainType;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getScore() {
		return score;
	}
	public void setScore(String score) {
		this.score = score;
	}
	public String getFmzy() {
		return fmzy;
	}
	public void setFmzy(String fmzy) {
		this.fmzy = fmzy;
	}
	public String getCover() {
		return cover;
	}
	public void setCover(String cover) {
		this.cover = cover;
	}
	public String getIndexUrl() {
		return IndexUrl;
	}
	public void setIndexUrl(String indexUrl) {
		IndexUrl = indexUrl;
	}
}
