package com.pdd.video.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import com.pdd.video.bean.HotMovie;
import com.pdd.video.bean.pageBase;

public class dbUtils {
	
	private static DBBase db=new DBBase();
	/**
	 * 新增电影
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public static int InsertMovie(pageBase page) throws Exception{
		String sql="insert into movie(vid,cover,videoName,duration,tostarInfo,score,sourceUrl,mtid,director,watch,description,PublishTime,CrawlerTime,performerInfo,keywords,metadescription,playcount,PlayerUrl,lastupdateInfo)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object [] obj={
				page.getVid(),
				page.getCover(),
				page.getVideoName(),
				page.getDuration(),
				page.getTostarInfo(),
				page.getScore(),
				page.getSourceUrl(),
				page.getMtid(),
				page.getDirector(),
				page.getWatch(),
				page.getDescription(),
				page.getPublishTime(),
				page.getCrawlerTime(),
				page.getPerformerInfo(),
				page.getKeywords(),
				page.getMetadescription(),
				page.getPlayCount(),
				page.getPlayerUrl(),
				page.getLastUpdateInfo()
		};
		int index=db.ExecuteUpdate(sql, obj);
		List<Integer> mtsubid=page.getMtSubid();
		String sqls="insert into moviesubtypemiddle(vid,mtSubid)values(?,?)";
		for (Integer i : mtsubid) {
			Object [] objs={page.getVid(),i};
			index+=db.ExecuteUpdate(sqls, objs);
		}
		return index;
	}
	
	public static List<HotMovie> getRandomMovie(String Type){
		List<HotMovie> hotMovie=new ArrayList<>();
		String sql="select cover,videoName,duration,tostarInfo,score,playerurl,publishTime,lastupdateinfo from movie m inner join maintype mt on mt.mtid=m.mtid where mt.mtName=? order by rand() desc limit 0,30 ";
		Object [] obj={Type};
		ResultSet result=db.ExecuteQuery(sql, obj);
		try {
			while(result.next()){
				HotMovie hot=new HotMovie();
				hot.setCover(result.getString(1));
				hot.setVideoName(result.getString(2));
				hot.setDuration(result.getString(3));
				hot.setTostarInfo(result.getString(4));
				hot.setScore(result.getString(5));
				hot.setPlayerUrl(result.getString(6));
				hot.setPublishTime(result.getString(7));
				hot.setLastupdateinfo(result.getString(8));
				hotMovie.add(hot);
			}
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return hotMovie;
	}
	
}
