 package com.webserver.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ShowAllUserDemo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RandomAccessFile raf = new RandomAccessFile("user.dat", "r");
		
		for(int i=0;i<raf.length()/100;i++) {
			//��ȡ�û���
			//�ȶ�ȡ32�ֽ�
			byte[] data = new byte[32];
			raf.read(data);
			//�����ֽ����ݻ�ԭΪ�ַ���
			String username = new String(data,"utf-8").trim();
		
			//��ȡ����
			raf.read(data);
			String password = new String(data,"utf-8").trim();
			
			//��ȡ�ǳ�
			raf.read(data);
			String nickname = new String(data,"utf-8").trim();
			
			//��ȡ����
			int age = raf.readInt();
			
			System.out.println(username+","+password+","+nickname+","+age);
			
		}
	}

}
