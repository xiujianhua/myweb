package com.webserver.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * HTTP协议相关内容定义
 * @author thief
 *
 */

public class HttpContext {
	/*
	 * 状态代码与状态描述的对应
	 * key: 状态代码
	 * value:状态描述
	 * 
	 */
	private static Map<Integer,String> statusReasonMapping
		= new HashMap<Integer,String>();
	
	/*
	 * 资源文件后缀与对应介质类型定义的对应关系
	 * key: 资源文件的后缀名
	 * value:Content-Type对应的值
	 */
	private static Map<String,String> mineMapping 
		= new HashMap<String,String>();
	
	
	
	static {
		//初始化
		initstatusMapping();
		initMineMapping();
	}
	
	private static void initMineMapping() {
		/*
		 * 解析项目目录中的conf目录里的web.xml文件
		 * 将根元素中的所有子元素<mime-mapping>
		 * 解析出来，并将其中的子元素：
		 * <extension>中的文本作为key
		 * <mine-type>中的文本作为value
		 * 存入到mineMapping这个Map
		 * 完成初始化操作
		 */
		try {
			SAXReader reader = new SAXReader();
			Document doc =  reader.read("conf/web.xml");
			Element root =doc.getRootElement();
			List<Element> list = root.elements("mime-mapping");
			for(Element ele :list) {
				String key = ele.elementText("extension");
				String value = ele.elementText("mime-type");
				mineMapping.put(key,value);
			}
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void initstatusMapping() {
		statusReasonMapping.put(200,"OK");
		statusReasonMapping.put(201,"Created");
		statusReasonMapping.put(202,"Accepted");
		statusReasonMapping.put(204,"No Content");
		statusReasonMapping.put(301,"Moved Permanently");
		statusReasonMapping.put(302,"Moved Temporarily");
		statusReasonMapping.put(304,"Not Modified");
		statusReasonMapping.put(400,"Bad Request");
		statusReasonMapping.put(401,"Unauthorized");
		statusReasonMapping.put(403,"Forbidden");
		statusReasonMapping.put(404,"Not Found");
		statusReasonMapping.put(500,"Internal Server Error");
		statusReasonMapping.put(501,"Not Implemented");
		statusReasonMapping.put(502,"Bad Gateway");
		statusReasonMapping.put(503,"Service Unavailable");
		
	}
	
	/**
	 * 根据给定的状态码获取对应的状态描述
	 */
	public static String getStatusReson(int code) {
		return statusReasonMapping.get(code);
		
	}
	public static void main(String[] args) {
		String fileName = "sdfa.mp4";
		int index = fileName.lastIndexOf(".");
		String ext = fileName.substring(index+1);
		String str = getContentType(ext);
		System.out.println(str);
	}
	
	/**
	 * 根据给定的后缀获取相应的后缀
	 */
	public static String getContentType(String name) {
		return mineMapping.get(name);
		
	}
}
