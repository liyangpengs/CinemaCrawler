package com.pdd.video.utils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreemakerUtil {
	public static boolean generatePage(Map val,String outFilePath,String TemplateName){
		Configuration config=new Configuration();
		config.setClassForTemplateLoading(FreemakerUtil.class, "/com/pdd/video/ftl");
		config.setDefaultEncoding("UTF-8");
		try {
			Template template=config.getTemplate(TemplateName);
			Writer write=new OutputStreamWriter(new FileOutputStream(outFilePath),"UTF-8");
			template.process(val, write);
			write.flush();
			write.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
		return false;
	}
}
