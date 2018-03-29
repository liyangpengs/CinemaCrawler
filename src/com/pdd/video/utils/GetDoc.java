package com.pdd.video.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * @author 张超
 * @date 2016年11月16日 13:54:23
 */
public class GetDoc {

	public static Document getdoc(String url, int timeout, int waittime, int retrytime) {
		Document Doc = null;
		for (int i = 0; i < retrytime; i++) {
			try {
				// 如果2次连接间隔不为0,则等待
				if (waittime > 0) {
					Thread.sleep(waittime);
				}
				HttpClientUtils http = new HttpClientUtils();
				String html = http.get(url);
				if("".equals(html)) {
					continue;
				}
				Doc = Jsoup.parse(html);
				if (Doc != null) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return Doc;
	}

	public static Document getdoc4Chrome(String url, int timeout, int waittime, int retrytime) {
		Document Doc = null;

		for (int i = 0; i < retrytime; i++) {
			try {
				// 如果2次连接间隔不为0,则等待
				if (waittime > 0) {
					Thread.sleep(waittime);
				}

				Doc = Jsoup.connect(url)
						      // .userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64)
						      // AppleWebKit/537.36 (KHTML, like Gecko)
						      // Chrome/44.0.2403.155 Safari/537.36")
						      .header("User-Agent",
								      "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.84 Safari/537.36")
						      .header("Upgrade-Insecure-Requests", "1").header("Host", "")
						      // .header("Cookie", "RK=gIPLk6zsbv; pgv_pvi=6193451008;
						      // pt2gguin=o2880626053;
						      // ptcz=2f9c9016f5cf1e5b185aaf2524d52f575887e366fa4933f635042483f0df910b;
						      // dc_vplaying=1; pgv_si=s6308990976;
						      // ts_refer=sh.house.qq.com/a/20150824/008237.htm;
						      // pgv_info=ssid=s4073758766;
						      // ts_last=xw.qq.com/news/20150824002904;
						      // pgv_pvid=4492366404; o_cookie=2880626053;
						      // ts_uid=3282765664;
						      // qv_als=7HvQlOHFckKfd83bA114403807696oq1CQ==")
						      .header("Connection", "keep-alive").header("Cache-Control", "max-age=0")
						      .header("Accept-Language", "zh-CN,zh;q=0.8").header("Accept-Encoding", "gzip, deflate, sdch")
						      .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8")
						      .get();

				// 如果成功取得jsoup doc,跳出循环
				if (Doc != null) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
		return Doc;
	}
	/**
	 *
	 *
	 * @Title:getdocByURL
	 * @Description: TODO(通过Connect.get方法改写过来)
	 * @throws Exception （retrun doc = null）
	 * @filter : ex(描述数据的处理信息)
	 * @return_type rytime(反复执行几次)doc = null
	 */
	public static Document getdocByURL(String url, int timeout, int waittime, int retrytime) {
		Document doc = null;

		URL realurl = null;
		BufferedReader read = null;// 读取访问结果
		String result = "";
		StringBuffer sb = new StringBuffer();
		for(int index = 0 ; index < retrytime ; index++){
			try{
				// 如果2次连接间隔不为0,则等待
				if (waittime > 0) {
					Thread.sleep(waittime);
				}
				realurl = new URL(url);
				// 打开连接
				URLConnection connection = realurl.openConnection();
				// 设置通用的请求属性
				connection.setRequestProperty("accept", "*/*");
				connection.setRequestProperty("connection", "Keep-Alive");
				connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
				String encoding = connection.getContentEncoding();
				// 定义 BufferedReader输入流来读取URL的响应
				read = new BufferedReader(new InputStreamReader(connection.getInputStream(), encoding));
				String line = "";
				while((line = read.readLine())!=null){
					sb.append(line);
				}
				if((result=sb.toString()).isEmpty())
					continue;
				else{
					doc = Jsoup.parse(result);
					break;
				}
			}catch(Exception e){
				break;
			}
		}
		return doc;
	}
}
