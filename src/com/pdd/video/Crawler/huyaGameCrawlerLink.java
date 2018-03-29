package com.pdd.video.Crawler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.pdd.video.bean.urlBase;
import com.pdd.video.utils.HttpClientUtilsForAppV2;
import com.pdd.video.utils.MainTypeMatch;

public class huyaGameCrawlerLink {
	private static Set<String> titles=new HashSet<String>();
	private static int PageSize=10;//爬取页码控制
	
	private static String CrawlerUrls[][]={
//		{"http://ahuya.duowan.com/g/jdqs?tag=全部&order=hot&page=%d","游戏","绝地求生"},
//		{"http://ahuya.duowan.com/g/lol?tag=全部&order=hot&page=%d","游戏","英雄联盟"},
		{"http://ahuya.duowan.com/g/wlyx?tag=地下城与勇士&order=hot&page=%d","游戏","地下城与勇士"},
//		{"http://ahuya.duowan.com/g/wlyx?tag=穿越火线&order=hot&page=%d","游戏","穿越火线"},
//		{"http://ahuya.duowan.com/g/wzry?tag=全部&order=hot&page=%d","游戏","王者荣耀"},
//		{"http://ahuya.duowan.com/g/wlyx?tag=守望先锋&order=hot&page=%d","游戏","守望先锋"},
//		{"http://ahuya.duowan.com/g/wlyx?tag=炉石传说&order=hot&page=%d","游戏","炉石传说"},
//		{"http://ahuya.duowan.com/g/wlyx?tag=魔兽世界&order=hot&page=%d","游戏","魔兽世界"},
//		{"http://ahuya.duowan.com/g/wlyx?tag=坦克世界&order=hot&page=%d","游戏","坦克世界"},
//		{"http://ahuya.duowan.com/g/wlyx?tag=逆战&order=hot&page=%d","游戏","逆战"}
	};
	/**
	 * 获取链接
	 */
	public static List<urlBase> getLinks(){
		titles.clear();
		List<urlBase> urlBases=new ArrayList<urlBase>();
		for (int i = 0; i < CrawlerUrls.length; i++) {
			for (int j = 1; j <= PageSize; j++) {
				String url=String.format(CrawlerUrls[i][0], j);
				System.out.println(url);
				urlBases.addAll(parseHtml(url,CrawlerUrls[i][1],CrawlerUrls[i][2]));
			}
		}
		return urlBases;
	}
	/**
	 * 解析页面
	 * @param url
	 * @param mainType
	 * @param subType
	 */
	public static List<urlBase> parseHtml(String url,String mainType,String subType){
		List<urlBase> urlBases=new ArrayList<urlBase>();
		String doc=HttpClientUtilsForAppV2.get(url, new String[]{
				"User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36",
		});
		Elements Videos=Jsoup.parse(doc).select("ul.vhy-video-list li");
		if(null!=Videos&&!Videos.isEmpty()){
			urlBases.addAll(SetProp(Videos,mainType,subType));
		}
		return urlBases;
	}
	/**
	 * 赋值
	 * @param els
	 * @return
	 */
	public static List<urlBase> SetProp(Elements els,String MainType,String subType){
		List<urlBase> urlBases=new ArrayList<urlBase>();
		for (Element e : els) {
			//去除重复
			if(titles.contains(e.select("p.video-title").text())){
				continue;
			}else{
				titles.add(e.select("p.video-title").text());
			}
			urlBase base=new urlBase();
			base.setTitle(e.select("p.video-title").text());
			base.setCover(e.select("img.video-cover").attr("data-original"));
			base.setFmzy("");
			base.setIndexUrl("http://ahuya.duowan.com"+e.select("> a").attr("href"));
			base.setScore("0.0");
			base.setDuration(e.select("span.video-duration").text());
			base.setMainTypeStr(MainType);
			base.setMainType(MainTypeMatch.getMainType(MainType));
			base.setLastUpdateInfo("");
			base.setSubTypeStr(subType);
			if(base.getTitle()==null||base.getTitle().trim().isEmpty())//过滤空标题
				continue;
			urlBases.add(base);
		}
		return urlBases;
	}
	
	public static void main(String[] args) {
		List<urlBase> AllUrl=getLinks();
		for (urlBase u : AllUrl) {
			System.out.println("标题:"+u.getTitle());
			System.out.println("封面:"+u.getCover());
			System.out.println("主演信息:"+u.getFmzy());
			System.out.println("评分:"+u.getScore());
			System.out.println("时长:"+u.getDuration());
			System.out.println("源站链接:"+u.getIndexUrl());
			System.out.println("");
		}
	}
}
