package com.webserver.servlet;

import java.io.File;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * Servlet
 * ��������Servlet�����еĹ���
 * @author thief
 *
 */
public abstract class HttpServlet {
	/**
	 * ��������ķ���
	 * @param request
	 * @param response
	 */
	public abstract void service(HttpRequest request,HttpResponse response);
	
	/**
	 * ����ָ��ҳ��
	 * �÷�����Tomcat��ת�����еķ�����
	 * ͨ��request���Ի�ȡ��ת�����������ڲ���תʹ��
	 * ����ֻ����������Ӧ��Ӧ·����ʾ��ҳ����ͻ���
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
