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

public class aiqiyiMusicCrawlerLink {
	private static Set<String> titles=new HashSet<String>();
	private static int PageSize=20;//爬取页码控制
	
	private static String CrawlerUrls[][]={
		{"http://list.iqiyi.com/www/5/------23556----0---11-%d-2-iqiyi--.html","音乐","单曲MV"},
		{"http://list.iqiyi.com/www/5/------23557----0---11-%d-2-iqiyi--.html","音乐","单曲现场"},
		{"http://list.iqiyi.com/www/5/------23558----0---11-%d-2-iqiyi--.html","音乐","演唱会"},
		{"http://list.iqiyi.com/www/5/------23559----0---11-%d-2-iqiyi--.html","音乐","新闻"},
		{"http://list.iqiyi.com/www/5/------23560----0---11-%d-2-iqiyi--.html","音乐","访谈"},
		{"http://list.iqiyi.com/www/5/------23561----0---11-%d-2-iqiyi--.html","音乐","音乐记录"},
		{"http://list.iqiyi.com/www/5/------23562----0---11-%d-2-iqiyi--.html","音乐","音乐周边"},
		{"http://list.iqiyi.com/www/5/------23563----0---11-%d-2-iqiyi--.html","音乐","音乐节目"},
		{"http://list.iqiyi.com/www/5/------23564----0---11-%d-2-iqiyi--.html","音乐","影视原声"},
		{"http://list.iqiyi.com/www/5/------23565----0---11-%d-2-iqiyi--.html","音乐","音乐短片"},
	};
	/**
	 * 获取链接
	 */
	public static List<urlBase> getLinks(){
		titles.clear();
		List<urlBase> urlBases=new ArrayList<urlBase>();
		for (int i = 0; i < CrawlerUrls.length; i++) {
			for (int j = 10; j <= PageSize; j++) {
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
		Elements Videos=Jsoup.parse(doc).select("div.wrapper-piclist ul li");
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
			if(titles.contains(e.select("p.site-piclist_info_title").text())){
				continue;
			}else{
				titles.add(e.select("p.site-piclist_info_title").text());
			}
			urlBase base=new urlBase();
			base.setTitle(e.select("p.site-piclist_info_title").text());
			base.setCover("http:"+e.select("div.site-piclist_pic img").attr("src"));
			base.setFmzy("");
			base.setIndexUrl(e.select("div.site-piclist_pic > a").attr("href"));
			base.setScore("0.0");
			base.setDuration(e.select("p.viedo_rb").text().trim());
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
