package com.pdd.video.oss;

import java.io.File;
import java.util.List;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.ObjectListing;

public class ossUtil {
	private static final String endpoint="http://oss-cn-shenzhen.aliyuncs.com";
	private static final String accessKey="LTAItDdw5mBlzPGJ";
	private static final String accessKeySecret="SdVEzs23RrHMKSzd5ldHMRNOlGCHQL";
	/**
	 * 文件上传至oss
	 * @param fileName
	 */
	public static boolean uploadVideo(String fileName){
		System.out.println("---------------开始上传视频---------------");
		OSSClient oss=new OSSClient(endpoint, accessKey, accessKeySecret);
		File file=new File(fileName);
		if(file.exists()&&file.length()>0){
			oss.putObject("pddstatic", "duanzi/video/"+fileName.replace("/data/mycrawler/videoPath/", ""), file);
			oss.shutdown();
			System.out.println("---------------上传完毕---------------");
			file.delete();//上传完毕则删除已上传数据
			return true;
		}else{
			System.out.println("---------------上传失败,文件不存在---------------");
		}
		oss.shutdown();
		return false;
	}
	/**
	 * 文件上传至oss
	 * @param fileName
	 */
	public static boolean uploadcover(String fileName){
		System.out.println("---------------开始上传封面---------------");
		OSSClient oss=new OSSClient(endpoint, accessKey, accessKeySecret);
		File file=new File(fileName);
		if(file.exists()&&file.length()>0){
			oss.putObject("pddstatic", "duanzi/cover/"+fileName.replace("/data/mycrawler/cover/", ""), file);
			oss.shutdown();
			System.out.println("---------------上传完毕---------------");
			file.delete();//上传完毕则删除已上传数据
			return true;
		}else{
			System.out.println("---------------上传失败,文件不存在---------------");
		}
		oss.shutdown();
		return false;
	}
	/**
	 * 上传gif图片至oss
	 * @param fileName
	 */
	public static boolean uploadGIF(String fileName){
		System.out.println("---------------开始上传GIF---------------");
		OSSClient oss=new OSSClient(endpoint, accessKey, accessKeySecret);
		File file=new File(fileName);
		if(file.exists()&&file.length()>0){
			oss.putObject("pddstatic", "duanzi/gif/"+fileName.replace("/data/mycrawler/gif/", ""), file);
			oss.shutdown();
			System.out.println("---------------上传完毕---------------");
			file.delete();//上传完毕则删除已上传数据
			return true;
		}else{
			System.out.println("---------------上传失败,文件不存在---------------");
		}
		oss.shutdown();
		return false;
	}
	/**
	 * 上传html至oss
	 * @param file
	 * @return
	 */
	public static boolean uploadHtml(File file){
		OSSClient oss=new OSSClient(endpoint, accessKey, accessKeySecret);
		oss.putObject("pddstatic", "page/"+file.getName(), file);
		System.out.println("上传完毕:"+file.getName());
		oss.shutdown();
		return true;
	}
	/**
	 * 删除oss文件
	 * @param file
	 * @return
	 */
	public static boolean delFile(){
		OSSClient oss=new OSSClient(endpoint, accessKey, accessKeySecret);
		ObjectListing ols=oss.listObjects("pddstatic", "page/");
		List<OSSObjectSummary> summary=ols.getObjectSummaries();
		if(summary.size()==0)
			return false;
		for (OSSObjectSummary s : summary) {
			if(s.getKey().equals("page/"))
				continue;
			oss.deleteObject("pddstatic", s.getKey());
			System.out.println("删除成功!");
		}
		oss.shutdown();
		return true;
	}
}
