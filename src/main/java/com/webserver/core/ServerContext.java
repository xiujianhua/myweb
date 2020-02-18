package com.webserver.core;

import java.util.HashMap;
import java.util.Map;

/**
 * ������������
 * @author thief
 *
 */

public class ServerContext {
	/**
	 * Servletӳ��
	 * key:����·��
	 * value��Servlet����ȫ�޶���
	 */
	private static Map<String,String> servletMap 
	= new HashMap<String,String>();
	static {
		initServletMapping();
	}
	/**
	 * ��ʼ��Servletӳ��
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
