package com.pdd.video.Crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.nodes.Document;

import com.pdd.video.bean.pageBase;
import com.pdd.video.bean.urlBase;
import com.pdd.video.utils.GetDoc;
import com.pdd.video.utils.subTypeMatch;

public class huyaGameCrawlerTag {
	public static void main(String[] args) {
		List<urlBase> urls=huyaGameCrawlerLink.getLinks();
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
		page.setPerformerInfo("");//主演: xxx 饰 xxx
		List<Integer> subType_list=new ArrayList<>();
		subType_list.add(subTypeMatch.getSubType(url.getSubTypeStr()));
		page.setMtSubid(subType_list);//子分类
//		page.setLastUpdateInfo(lastUpdateInfo);电影没有最后更新信息
		page.setKeywords("");//关键字
		page.setMetadescription("");//影片介绍
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
		return Director;
	}
	/**
	 * 返回看点
	 * @param doc
	 * @return
	 */
	private static void getWatch(Document doc,pageBase page){
		String Watch="";
		page.setWatch(Watch);//看点
		page.setDescription(doc.select("p.video-info_des").text().replace("简介：", ""));//影视剧情简介
		try {
			page.setPublishTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(doc.select("div.info").first().select("p").get(1).text().replace("发布于 ", "")));
		} catch (ParseException e) {
			e.printStackTrace();
		}//影视上映时间
		page.setCrawlerTime(new Date());//爬取时间
		page.setVid(doc.select("body").attr("vid")+"_huya");//VID
		Integer play_count=0;
		String play_count_str=doc.select("div.info p.count").text();
		if(play_count_str.indexOf("万")>0){
			play_count_str=play_count_str.replace("万", "");
			play_count=(int)Double.parseDouble(play_count_str)*10000;
		}else if(play_count_str.indexOf(",")>0){
			play_count_str=play_count_str.replace(",", "");
			play_count=Integer.parseInt(play_count_str);
		}else{
			play_count=Integer.parseInt(play_count_str);
		}
		page.setPlayCount(play_count+"");//播放次数
		page.setPlayerUrl("/page/huya_"+doc.select("body").attr("vid")+".html");
	}
}
