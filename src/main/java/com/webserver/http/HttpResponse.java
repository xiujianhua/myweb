package com.webserver.http;
/**
 * 响应对象
 * 该类的每一个实例用于表示要给客户端发送的实际响应内容
 * @author thief
 *
 */

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class HttpResponse {
	/*
	 * 状态行相关信息定义
	 */
	private int statusCode = 200;
	
	/*
	 * 响应头相关信息
	 */
	private Map<String,String> headers = new HashMap<String,String>();
	
	
	
	/*
	 * 响应正文相关信息定义
	 */
	private File entity;
	
	
	
	//和客户端连接相关的内容
	private Socket socket;
	private OutputStream out;
	
	
	public HttpResponse(Socket socket) {
		this.socket = socket;
		try {
			this.out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 将当前响应对象的内容以一个HTTP标准响应格式发给客户端
	 */
	public void flush() {
		/*
		 * 1.发送状态行
		 * 2.发送响应头
		 * 3.发送响应正文
		 */
		System.out.println("开始发送响应");
		sendStatusLine();
		sendHeaders();
		sendContent();
		System.out.println("响应发送完毕");
	}
	/**
	 * 发送状态行
	 */
	private void sendStatusLine() {
		try {
			System.out.println("开始发送状态行...");
			
			String line = "HTTP/1.1"+" "+statusCode+" "+HttpContext.getStatusReson(statusCode);
			out.write(line.getBytes("ISO8859-1"));
			out.write(13);
			out.write(10);
			
			System.out.println("状态行发送完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送响应头
	 */
	private void sendHeaders() {
		System.out.println("开始发送状态行...");
		try {
			Set<Entry<String,String>> entrySet = headers.entrySet();
			for(Entry<String,String> header:entrySet) {
				String line = header.getKey()+": "+header.getValue();
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);
				out.write(10);
			}
			//单独发送CRLF表示响应头部分发送完毕
			out.write(13);
			out.write(10);
			System.out.println("状态行发送完毕！");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * 发送响应正文
	 */
	private void sendContent() {
		try (
				FileInputStream is = new FileInputStream(entity);
		){
			System.out.println("开始发送响应正文");
			
			//发送响应正文
			int len = -1;
			byte[] data = new byte[1024*10];
			while((len=is.read(data))!=-1) {
				out.write(data,0,len);
			}
			is.close();
			System.out.println("响应正文发送完毕");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public File getEntity() {
		return entity;
	}

	/**
	 * 设置响应正文的实体文件，在设置的同时会自动添加对应的
	 * 正文的两个响应头
	 * @param entity
	 */
	public void setEntity(File entity) {
		this.entity = entity;
		//根据实体问价你设置响应头
		//根据实体文件后缀获取对应介质类型
		String fileName = entity.getName();
		int index = fileName.lastIndexOf(".");
		String ext = fileName.substring(index+1);
		String type = HttpContext.getContentType(ext);
		this.headers.put("Content-Type",type);
		this.headers.put("Content-Length",String.valueOf(entity.length()));
		
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	
	public void putHeader(String name,String value) {
		headers.put(name,value);
	}
	
	public String getHeader(String name) {
		return headers.get(name);
		
	}
	
	
}
