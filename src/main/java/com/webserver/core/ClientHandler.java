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
 * �ͻ��˴�����
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
			//1 ׼������
			System.out.println("ClientHandler: ��ʼ��������...");
			HttpRequest request = new HttpRequest(socket);
			System.out.println("ClientHandler: ����������ϣ�");
			HttpResponse response = new HttpResponse(socket);
			
			//2 ��������
			System.out.println("ClientHandler: ��ʼ��������...");
			//�Ȼ�ȡ�û��������Դ·��
			String url = request.getRequestURI();
			//�����ж��Ƿ�Ϊ����ע��ҵ��
			String servletName = ServerContext.getServletMap(url);
			if(servletName!=null) {
				Class cls = Class.forName(servletName);
				HttpServlet servlet = (HttpServlet)cls.newInstance();
				servlet.service(request, response);
		
			}else {
				File file = new File("webapps"+url);
				if(file.exists()) {
					System.out.println("��Դ�Ѿ��ҵ�");
					
					//������Դ���õ�response��
					response.setEntity(file);
					
				}else {
					System.out.println("��Դδ�ҵ�");
					//��Ӧ404
					//����response�е�״̬����Ϊ404;
					response.setStatusCode(404);
					//����404ҳ��
					file = new File("webapps/root/404.html");
					response.setEntity(file);
				}
			}
			response.flush();
			System.out.println("ClientHandler: ����������ϣ�");
			System.out.println("ClientHandler: ��ʼ��Ӧ����...");
			//3 ������Ӧ
			System.out.println("ClientHandler: ��Ӧ������ϣ�");
		} catch (EmptyRequestException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			//������ͻ��˶Ͽ����ӵĲ���
			try {
				socket.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	} 
	

	
	
}
