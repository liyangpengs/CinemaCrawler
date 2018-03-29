package com.pdd.video.utils;

public class MainTypeMatch {
	/**
	 * 主分类转换
	 * @param Type
	 * @return
	 */
	public static int getMainType(String Type){
		int TypeId=0;
		switch (Type) {
			case "电影":
				TypeId=10000001;
				break;
			case "电视剧":
				TypeId=10000002;
				break;
			case "综艺":
				TypeId=10000003;
				break;
			case "动漫":
				TypeId=10000004;
				break;
			case "搞笑":
				TypeId=10000005;
				break;
			case "音乐":
				TypeId=10000006;
				break;
			case "游戏":
				TypeId=10000007;
				break;
		}
		return TypeId;
	}
	
}

