package com.pdd.video.main;

import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;

import com.pdd.video.Crawler.aiqiyiComicCrawlerLink;
import com.pdd.video.Crawler.aiqiyiComicCrawlerTag;
import com.pdd.video.Crawler.aiqiyiTVCrawlerTag;
import com.pdd.video.bean.pageBase;
import com.pdd.video.bean.urlBase;
import com.pdd.video.db.dbUtils;
import com.pdd.video.utils.FreemakerUtil;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

public class Comic_Main {
	public static void main(String[] args) {
		try {
			List<urlBase> TVUrls=aiqiyiComicCrawlerLink.getLinks();
			for (urlBase urlBase : TVUrls) {
				pageBase page=aiqiyiComicCrawlerTag.getPage(urlBase);
				if(page==null)continue;//过滤空数据
				try {
					JsonConfig jsonConfig=new JsonConfig();
					jsonConfig.setExcludes(new String[]{"mtSubid","plist","lastUpdateInfo","crawlerTime","publishTime","mtid"});
					JSONObject obj=JSONObject.fromObject(page,jsonConfig);
					obj.put("VideoJson", "\""+obj.toString()+"\"");
					ObjectMapper mapper=new ObjectMapper();
					Map map=mapper.readValue(obj.toString(), Map.class);
					map.put("PlayerList", page.getPlist());
					map.put("hotMovie", dbUtils.getRandomMovie("电影"));
					map.put("hotTV", dbUtils.getRandomMovie("电视剧"));
					boolean isok=FreemakerUtil.generatePage(map, "E:/Cinema"+page.getPlayerUrl(),"TV.ftl");
					System.out.println(isok);
					int status=dbUtils.InsertMovie(page);
					if(status>0){
						System.out.println("新增数据成功!");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			System.out.println("程序异常终止....");
			e.printStackTrace();
		}
	}
}
