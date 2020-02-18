package com.webserver.servlet;

import java.io.File;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * Servlet
 * 定义所有Servlet都具有的共性
 * @author thief
 *
 */
public abstract class HttpServlet {
	/**
	 * 处理请求的方法
	 * @param request
	 * @param response
	 */
	public abstract void service(HttpRequest request,HttpResponse response);
	
	/**
	 * 调到指定页面
	 * 该方法在Tomcat中转发器中的方法。
	 * 通过request可以获取到转发器，用作内部跳转使用
	 * 这里只淡村用于响应对应路径表示的页面给客户端
	 * @param url
	 * @param request
	 */
	public void forward(String url,HttpRequest request,HttpResponse response) {
		System.out.println("==="+url);
		
		File file = new File("webapps/"+url);
		response.setEntity(file);
		System.out.println(response.getEntity().getName());
	}
}
