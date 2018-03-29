package com.pdd.video.Crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import com.pdd.video.bean.pageBase;
import com.pdd.video.bean.urlBase;
import com.pdd.video.utils.GetDoc;
import com.pdd.video.utils.HttpClientUtilsForAppV2;
import com.pdd.video.utils.subTypeMatch;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class aiqiyiMovieCrawlerTag {
	
	public static void main(String[] args) {
		List<urlBase> urls=aiqiyiMovieCrawlerLink.getLinks();
		checkAll(urls);
	}
	
	private static void checkAll(List<urlBase> urls){
		for (urlBase u : urls) {
			pageBase page=getPage(u);
			System.out.println("影视ID:"+page.getVid());
			System.out.println("影视名称:"+page.getVideoName());
			System.out.println("播放次数:"+page.getPlayCount());
			System.out.println("封面:"+page.getCover());
			System.out.println("封面主演:"+page.getTostarInfo());
			System.out.println("主分类:"+page.getMtid());
			System.out.println("子分类:"+page.getMtSubid());
			System.out.println("主演:"+page.getPerformerInfo());
			System.out.println("看点:"+page.getWatch());
			System.out.println("影视剧情简介:"+page.getDescription());
			System.out.println("上线时间:"+page.getPublishTime());
			System.out.println("影片介绍:"+page.getMetadescription());
			System.out.println("关键字:"+page.getKeywords());
			System.out.println("源站链接:"+page.getSourceUrl());
			System.out.println();
		}
	}
	
	/**
	 * 取得详情页数据
	 * @param url
	 * @return
	 */
	public static pageBase getPage(urlBase url){
		Document doc=GetDoc.getdoc(url.getIndexUrl(), 3000, 0, 2);
		pageBase page=new pageBase();
//		page.setLastUpdateInfo(url.getLastUpdateInfo());//电影最后更新时间【放影片时长】
		page.setMainTypeStr(url.getMainTypeStr());//主分类字符串  (电影|电视剧|xxx)
		page.setCover(url.getCover());//封面
		page.setVideoName(url.getTitle());//影视名称
		page.setDuration(url.getDuration());//视频时长
		page.setTostarInfo(url.getFmzy());//封面主演
		page.setScore(url.getScore());//评分
		page.setSourceUrl(url.getIndexUrl());//源站url
		page.setMtid(url.getMainType());//主分类
		page.setDirector(getDirector(doc));//导演
		getWatch(doc,page);//看点|影视剧情简介|影视上映时间|播放次数
		page.setPerformerInfo(getperformerInfo(doc));//主演: xxx 饰 xxx
		page.setMtSubid(getSubType(doc));//子分类
//		page.setLastUpdateInfo(lastUpdateInfo);电影没有最后更新信息
		page.setKeywords(getKeyWord(doc));//关键字
		page.setMetadescription(getMetadescription(doc));//影片介绍
		if(page.getVid()==null){
			return null;
		}
		return page;
	}
	/**
	 * 获取导演
	 * @return
	 */
	private static String getDirector(Document doc){
		String Director="";
		if(null!=doc.select("div.progInfo_txt p.progInfo_rtp span.type-con")&&!doc.select("div.progInfo_txt p.progInfo_rtp span.type-con").isEmpty()){
			Director=doc.select("div.progInfo_txt p.progInfo_rtp span.type-con").first().text();
		}
		return Director;
	}
	/**
	 * 返回看点
	 * @param doc
	 * @return
	 */
	private static void getWatch(Document doc,pageBase page){
		String httpUrl="http://mixer.video.iqiyi.com/jp/mixin/videos/";
		Pattern p=Pattern.compile("param\\['tvid'\\] = \"(.*)\";");
		Matcher m=p.matcher(doc.select("script ").toString());
		if(m.find()){
			httpUrl+=m.group(1);
			String content=HttpClientUtilsForAppV2.get(httpUrl).replace("var tvInfoJs=", "");
			JSONObject obj=JSONObject.fromObject(content);
			JSONArray array=obj.getJSONArray("categories");
			String Watch="";
			for (int i = 0; i < array.size(); i++) {
				JSONObject tag=array.getJSONObject(i);
				Watch+=tag.getString("name")+" ";
			}
			page.setWatch(Watch);//看点
			page.setDescription(obj.getString("description"));//影视剧情简介
			page.setPublishTime(new Date(obj.getLong("publishTime")));//影视上映时间
			page.setCrawlerTime(new Date());//爬取时间
			page.setVid(m.group(1)+"_"+obj.getString("vid"));//VID
			page.setPlayCount(obj.getString("playCount"));//播放次数
			page.setPlayerUrl("/page/"+page.getVid()+".html");
		}
	}
	/**
	 * 主演信息
	 */
	private static String getperformerInfo(Document doc){
		String performerInfo="";
		if(null!=doc.select("div.progInfo_txt p.progInfo_rtp span.type-con")&&!doc.select("div.progInfo_txt p.progInfo_rtp span.type-con").isEmpty()){
			performerInfo=doc.select("div.progInfo_txt p.progInfo_rtp span.type-con").last().text();
		}
		return performerInfo;
	}
	/**
	 * 获取子分类
	 */
	private static List<Integer> getSubType(Document doc){
		List<Integer> SubType=new ArrayList<>();
		if(null!=doc.select("div.mod-crumb_bar span.mod-tags_item")&&!doc.select("div.mod-crumb_bar span.mod-tags_item").isEmpty()){
			String [] split=doc.select("div.mod-crumb_bar span.mod-tags_item").text().split(" ");
			for (String s : split) {
				int SubTypeId=subTypeMatch.getSubType(s);
				if(SubTypeId>0){
					SubType.add(SubTypeId);
				}
			}
		}
		return SubType;
	}
	/**
	 * 关键字
	 * @param doc
	 * @return
	 */
	private static String getKeyWord(Document doc){	
		return doc.select("meta[name=keywords]").attr("content");
	}	
	/**
	 * 影片介绍
	 * @param doc
	 * @return
	 */
	private static String getMetadescription(Document doc){
		return doc.select("meta[name=description]").attr("content");
	}
}
