 package com.webserver.servlet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class ShowAllUserDemo {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		RandomAccessFile raf = new RandomAccessFile("user.dat", "r");
		
		for(int i=0;i<raf.length()/100;i++) {
			//读取用户名
			//先读取32字节
			byte[] data = new byte[32];
			raf.read(data);
			//将该字节内容还原为字符串
			String username = new String(data,"utf-8").trim();
		
			//读取密码
			raf.read(data);
			String password = new String(data,"utf-8").trim();
			
			//读取昵称
			raf.read(data);
			String nickname = new String(data,"utf-8").trim();
			
			//读取年龄
			int age = raf.readInt();
			
			System.out.println(username+","+password+","+nickname+","+age);
			
		}
	}

}
