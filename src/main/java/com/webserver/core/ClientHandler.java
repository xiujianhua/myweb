package com.webserver.core;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import com.webserver.http.EmptyRequestException;
import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;
import com.webserver.servlet.HttpServlet;
import com.webserver.servlet.LoginServlet;
import com.webserver.servlet.RegServlet;

/**
 * 客户端处理类
 * @author thief
 *
 */
public class ClientHandler implements Runnable {
	private Socket socket;
	
	public ClientHandler(Socket socket) {
		this.socket = socket;
	}
	
	public void run() {
		try {
			//1 准备工作
			System.out.println("ClientHandler: 开始解析请求...");
			HttpRequest request = new HttpRequest(socket);
			System.out.println("ClientHandler: 解析请求完毕！");
			HttpResponse response = new HttpResponse(socket);
			
			//2 处理请求
			System.out.println("ClientHandler: 开始处理请求...");
			//先获取用户请求的资源路径
			String url = request.getRequestURI();
			//首先判断是否为请求注册业务
			String servletName = ServerContext.getServletMap(url);
			if(servletName!=null) {
				Class cls = Class.forName(servletName);
				HttpServlet servlet = (HttpServlet)cls.newInstance();
				servlet.service(request, response);
		
			}else {
				File file = new File("webapps"+url);
				if(file.exists()) {
					System.out.println("资源已经找到");
					
					//将该资源设置到response中
					response.setEntity(file);
					
				}else {
					System.out.println("资源未找到");
					//响应404
					//设置response中的状态代码为404;
					response.setStatusCode(404);
					//设置404页面
					file = new File("webapps/root/404.html");
					response.setEntity(file);
				}
			}
			response.flush();
			System.out.println("ClientHandler: 处理请求完毕！");
			System.out.println("ClientHandler: 开始响应请求...");
			//3 发送响应
			System.out.println("ClientHandler: 响应请求完毕！");
		} catch (EmptyRequestException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//处理与客户端断开连接的操作
			try {
				socket.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	} 
	

	
	
}
