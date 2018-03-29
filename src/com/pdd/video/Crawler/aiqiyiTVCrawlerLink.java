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

public class aiqiyiTVCrawlerLink {
	private static Set<String> titles=new HashSet<String>();
	private static int PageSize=10;//爬取页码控制
	
	private static String CrawlerUrls[][]={
		{"http://list.iqiyi.com/www/2/----------0---11-%d-1-iqiyi--.html","电视剧"},
	};
	/**
	 * 获取链接
	 */
	public static List<urlBase> getLinks(){
		titles.clear();
		List<urlBase> urlBases=new ArrayList<urlBase>();
		for (int i = 0; i < CrawlerUrls.length; i++) {
			for (int j = 2; j <= PageSize; j++) {
				String url=String.format(CrawlerUrls[i][0], j);
				urlBases.addAll(parseHtml(url,CrawlerUrls[i][1]));
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
	public static List<urlBase> parseHtml(String url,String mainType){
		List<urlBase> urlBases=new ArrayList<urlBase>();
		String doc=HttpClientUtilsForAppV2.get(url, new String[]{
				"User-Agent=Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/64.0.3282.186 Safari/537.36",
				"Host=list.iqiyi.com"
		});
		Elements Videos=Jsoup.parse(doc).select("div.wrapper-piclist ul li");
		if(null!=Videos&&!Videos.isEmpty()){
			urlBases.addAll(SetProp(Videos,mainType));
		}
		return urlBases;
	}
	/**
	 * 赋值
	 * @param els
	 * @return
	 */
	public static List<urlBase> SetProp(Elements els,String MainType){
		List<urlBase> urlBases=new ArrayList<urlBase>();
		for (Element e : els) {
			//过滤非电影内容
			if(e.select("div.site-piclist_info div.role_info").text().trim().isEmpty())
				continue;
			//去除重复
			if(titles.contains(e.select("div.site-piclist_info div.mod-listTitle_left p.site-piclist_info_title ").text())){
				continue;
			}else{
				titles.add(e.select("div.site-piclist_info div.mod-listTitle_left p.site-piclist_info_title ").text());
			}
			urlBase base=new urlBase();
			base.setTitle(e.select("div.site-piclist_info div.mod-listTitle_left p.site-piclist_info_title ").text());
			base.setCover("http:"+e.select("div.site-piclist_pic a img").attr("src"));
			if(e.select("div.site-piclist_info div.role_info").text().replace("主演:", "").length()>90){
				base.setFmzy(e.select("div.site-piclist_info div.role_info").text().replace("主演:", "").substring(0, 90)+"...");
			}else{
				base.setFmzy(e.select("div.site-piclist_info div.role_info").text().replace("主演:", ""));
			}
			base.setIndexUrl(e.select("div.site-piclist_pic a").attr("href"));
			base.setScore("0.0");
//			base.setDuration(e.select("div.site-piclist_pic div.wrapper-listTitle p.viedo_rb").text());//电视剧无时长
			base.setMainType(MainTypeMatch.getMainType(MainType));
			base.setMainTypeStr(MainType);
			base.setLastUpdateInfo(e.select("div.site-piclist_pic div.wrapper-listTitle p.viedo_rb").text());
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
			System.out.println("最后更新信息:"+u.getLastUpdateInfo());
			System.out.println("源站链接:"+u.getIndexUrl());
			System.out.println("");
		}
	}
}
