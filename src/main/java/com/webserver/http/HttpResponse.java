package com.webserver.http;
/**
 * ��Ӧ����
 * �����ÿһ��ʵ�����ڱ�ʾҪ���ͻ��˷��͵�ʵ����Ӧ����
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
	 * ״̬�������Ϣ����
	 */
	private int statusCode = 200;
	
	/*
	 * ��Ӧͷ�����Ϣ
	 */
	private Map<String,String> headers = new HashMap<String,String>();
	
	
	
	/*
	 * ��Ӧ���������Ϣ����
	 */
	private File entity;
	
	
	
	//�Ϳͻ���������ص�����
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
	 * ����ǰ��Ӧ�����������һ��HTTP��׼��Ӧ��ʽ�����ͻ���
	 */
	public void flush() {
		/*
		 * 1.����״̬��
		 * 2.������Ӧͷ
		 * 3.������Ӧ����
		 */
		System.out.println("��ʼ������Ӧ");
		sendStatusLine();
		sendHeaders();
		sendContent();
		System.out.println("��Ӧ�������");
	}
	/**
	 * ����״̬��
	 */
	private void sendStatusLine() {
		try {
			System.out.println("��ʼ����״̬��...");
			
			String line = "HTTP/1.1"+" "+statusCode+" "+HttpContext.getStatusReson(statusCode);
			out.write(line.getBytes("ISO8859-1"));
			out.write(13);
			out.write(10);
			
			System.out.println("״̬�з������");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ������Ӧͷ
	 */
	private void sendHeaders() {
		System.out.println("��ʼ����״̬��...");
		try {
			Set<Entry<String,String>> entrySet = headers.entrySet();
			for(Entry<String,String> header:entrySet) {
				String line = header.getKey()+": "+header.getValue();
				out.write(line.getBytes("ISO8859-1"));
				out.write(13);
				out.write(10);
			}
			//��������CRLF��ʾ��Ӧͷ���ַ������
			out.write(13);
			out.write(10);
			System.out.println("״̬�з�����ϣ�");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * ������Ӧ����
	 */
	private void sendContent() {
		try (
				FileInputStream is = new FileInputStream(entity);
		){
			System.out.println("��ʼ������Ӧ����");
			
			//������Ӧ����
			int len = -1;
			byte[] data = new byte[1024*10];
			while((len=is.read(data))!=-1) {
				out.write(data,0,len);
			}
			is.close();
			System.out.println("��Ӧ���ķ������");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public File getEntity() {
		return entity;
	}

	/**
	 * ������Ӧ���ĵ�ʵ���ļ��������õ�ͬʱ���Զ���Ӷ�Ӧ��
	 * ���ĵ�������Ӧͷ
	 * @param entity
	 */
	public void setEntity(File entity) {
		this.entity = entity;
		//����ʵ���ʼ���������Ӧͷ
		//����ʵ���ļ���׺��ȡ��Ӧ��������
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
