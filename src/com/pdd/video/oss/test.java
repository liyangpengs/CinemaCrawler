package com.pdd.video.oss;

import java.io.File;

public class test {
	public static void main(String[] args) throws Exception {
//		Upload(); //上传文件
		delFile();//删除文件
	}
	public static void Upload() throws InterruptedException{
		while(true){
			File file = new File("E:/Cinema/page");
			if (file.isDirectory()) {
				File[] files = file.listFiles();
				for (File f : files) {
					boolean isok = ossUtil.uploadHtml(f);
					if (isok) {
						f.delete();
					}
				}
			}
			Thread.sleep(30000);
		}
	}
	public static void delFile(){
		while(ossUtil.delFile()){
			
		}
	}
}
