package com.pdd.video.Crawler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;

import com.pdd.video.bean.pageBase;
import com.pdd.video.bean.playerList;
import com.pdd.video.bean.urlBase;
import com.pdd.video.utils.GetDoc;
import com.pdd.video.utils.HttpClientUtilsForAppV2;
import com.pdd.video.utils.subTypeMatch;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class aiqiyiVarietyCrawlerTag {
	public static void main(String[] args) {
		List<urlBase> urls=aiqiyiVarietyCrawlerLink.getLinks();
		checkAll(urls);
	}
	
	private static void checkAll(List<urlBase> urls){
		for (urlBase u : urls) {
			pageBase page=getPage(u);
			if(page==null)continue;
			System.out.println("影视ID:"+page.getVid());
			System.out.println("影视名称:"+page.getVideoName());
			System.out.println("播放次数:"+page.getPlayCount());
			System.out.println("封面:"+page.getCover());
			System.out.println("封面主演:"+page.getTostarInfo());
			System.out.println("主分类:"+page.getMtid());
			System.out.println("子分类:"+page.getMtSubid());
			System.out.println("最后更新信息:"+page.getLastUpdateInfo());
			System.out.println("看点:"+page.getWatch());
			System.out.println("影视剧情简介:"+page.getDescription());
			System.out.println("上线时间:"+page.getPublishTime());
			System.out.println("影片介绍:"+page.getMetadescription());
			System.out.println("关键字:"+page.getKeywords());
			System.out.println("源站链接:"+page.getSourceUrl());
			System.out.println("播放列表:"+page.getPlist());
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
		page.setLastUpdateInfo(url.getLastUpdateInfo());//影视最后更新时间
		page.setCover(url.getCover());//封面
		page.setVideoName(url.getTitle());//影视名称
//		page.setDuration(url.getDuration());//视频时长 【电视剧无需时长】
		page.setTostarInfo(url.getFmzy());//封面主演
		page.setScore(url.getScore());//评分
		page.setMtid(url.getMainType());//主分类
		page.setDirector(getDirector(doc));//导演
		getWatch(doc,page);//看点|影视剧情简介|影视上映时间|播放次数 |源站url
//		page.setPerformerInfo(getperformerInfo(doc));//主演: xxx 饰 xxx  【电视剧无主演介绍】
		page.setMtSubid(getSubType(doc));//子分类
		page.setKeywords(getKeyWord(doc));//关键字
		page.setMetadescription(getMetadescription(doc));//影片介绍
		page.setMainTypeStr(url.getMainTypeStr());
		page.setPlist(getPlayList(doc,page));//播放列表
		//没获取到视频id
		//播放列表数量小于1 则为没有获取到播放列表
		if(page.getVid()==null||page.getPlist().size()<1){
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
		if(null!=doc.select("div.episodeIntro-line p.episodeIntro-director")&&!doc.select("div.episodeIntro-line p.episodeIntro-director").isEmpty()){
			Director=doc.select("div.episodeIntro-line p.episodeIntro-director").first().text().replace("导演：", "");
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
		Pattern p=Pattern.compile("tvId: (.*),");
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
			page.setSourceUrl(obj.getString("url"));//第一集URL
			page.setWatch(Watch);//看点
			page.setDescription(obj.getString("description").isEmpty()?getDescription(doc):obj.getString("description"));//影视剧情简介
			page.setPublishTime(new Date(obj.getLong("publishTime")));//影视上映时间
			page.setCrawlerTime(new Date());//爬取时间
			page.setVid(m.group(1)+"_"+obj.getString("vid"));//VID
			page.setPlayerUrl("/page/"+page.getVid()+".html");
			page.setPlayCount(obj.getString("playCount"));//播放次数
		}
	}
	/**
	 * 获取子分类
	 */
	private static List<Integer> getSubType(Document doc){
		List<Integer> SubType=new ArrayList<>();
		String []split={};
		if(null!=doc.select("div.episodeIntro-item div.episodeIntro-line p.episodeIntro-type")&&!doc.select("div.episodeIntro-item div.episodeIntro-line p.episodeIntro-type").isEmpty()){
			split=doc.select("div.episodeIntro-item div.episodeIntro-line p.episodeIntro-type").text().replace("类型：", "").split("/");
		}else if(null!=doc.select("div[itemprop=genre]")&&!doc.select("div[itemprop=genre]").isEmpty()){
			split=doc.select("div[itemprop=genre]").first().text().replace("类型：", "").split("/");
		}
		for (String s : split) {
			//电视剧添加子分类
			if(!s.trim().equals("喜剧")||!s.trim().equals("悲剧")||!s.trim().equals("剧情")||!s.trim().equals("影视剧吐槽")){
				s=s.trim().replace("剧", "");
			}
			int SubTypeId=subTypeMatch.getSubType(s.trim());
			if(SubTypeId>0){
				SubType.add(SubTypeId);
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
	/**
	 * 获取影视介绍
	 * @return
	 */	
	private static String getDescription(Document doc){
		return doc.select("span.showMoreText").text().replace("简介：", "");
	}
	/**
	 * 拿到播放列表
	 * @return
	 */
	private static List<playerList> getPlayList(Document doc,pageBase page){
		List<playerList> playerList=new ArrayList<playerList>();
		String PlayListUrl="http://cache.video.iqiyi.com/jp/sdvlst/%d/%d/?categoryId=%d&sourceId=%d";
		Pattern p=Pattern.compile("sourceId: (.*),");
		Matcher m=p.matcher(doc.select("script ").toString());
		Pattern p1=Pattern.compile("cid: (.*),");
		Matcher m1=p1.matcher(doc.select("script ").toString());
		if(m.find()&&m1.find()){
			int albumId=Integer.parseInt(m.group(1));
			int categoryId=Integer.parseInt(m1.group(1));
			String HttpPlayListUrl=String.format(PlayListUrl,categoryId,albumId,categoryId,albumId);
			String Content=HttpClientUtilsForAppV2.get(HttpPlayListUrl).replace("var tvInfoJs=", "");
			JSONObject obj=JSONObject.fromObject(Content);
			//请求失败
			if(obj.getString("code").equals("A00004")){
				return playerList;
			}
			JSONArray array=obj.getJSONArray("data");
			//播放列表为空
			if(array.isEmpty()){
				return playerList;
			}
			for (int j = 0; j < array.size(); j++) {
				JSONObject Player=array.getJSONObject(j);
				playerList list=new playerList();
				if(!Player.getString("tvPicUrl").startsWith("_116_65.jpg")){
					list.setCover(Player.getString("tvPicUrl").replace(".jpg", "_116_65.jpg"));
				}else{
					list.setCover(Player.getString("tvPicUrl"));
				}
				list.setCrawlerTime(new Date());
				list.setSourceUrl(Player.getString("vUrl"));
				list.setTitle(Player.getString("tvYear")+"期");
				list.setVid(page.getVid());
				playerList.add(list);
			}
		}
		return playerList;
	}
}
