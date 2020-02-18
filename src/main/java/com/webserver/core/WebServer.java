package com.webserver.core;
/**
 * WebServer����
 * @author thief
 *
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class WebServer {
	private ServerSocket server;
	private ExecutorService threadPool;
	public WebServer() {
		try {
			server = new ServerSocket(8088);
			threadPool = Executors.newFixedThreadPool(30);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		try {
			/*
			 * ��ʱ���ṩ�ͻ��˵Ķ�����ӡ�
			 * �Ƚ������������Ϻ���֧�ָù���
			 */
			while(true) {
				System.out.println("�ȴ��ͻ�������...");
				Socket socket = server.accept();
				System.out.println("һ���ͻ��������ˣ�");
				
				//�����̳߳ش���ͻ�������
				ClientHandler handler = new ClientHandler(socket);
				threadPool.execute(handler);
			}
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		WebServer server = new WebServer();
		server.start();
	}
}
