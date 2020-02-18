package com.webserver.core;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务端相关配置
 * @author thief
 *
 */

public class ServerContext {
	/**
	 * Servlet映射
	 * key:请求路径
	 * value：Servlet的完全限定名
	 */
	private static Map<String,String> servletMap 
	= new HashMap<String,String>();
	static {
		initServletMapping();
	}
	/**
	 * 初始化Servlet映射
	 */
	private static void initServletMapping() {
		servletMap.put("/myweb/reg","com.webserver.servlet.RegServlet");
		servletMap.put("/myweb/login","com.webserver.servlet.LoginServlet");
	}
	public static String getServletMap(String name) {
		return servletMap.get(name);
	}
		
	public static void main(String[] args) {
		String name = "/myweb/login1";
		System.out.println(getServletMap(name));
	}
}
