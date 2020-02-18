package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * ����ע��ҵ��
 * 
 * @author thief
 *
 */

public class RegServlet extends HttpServlet{
	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("RegServlet:��ʼ����ע��ҵ��");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		int age = Integer.parseInt(request.getParameter("age"));
		System.out.println("username:"+username);
		System.out.println("password:"+password);
		System.out.println("nickname:" +nickname);
		System.out.println("age"+age);
		
		/*
		 * 2�����û���Ϣд���ļ�user.dat��
		 * ÿ���û�ռ��100�ֽڣ������û��������룬
		 * �ǳ�û��Ϊ�ַ�����ռ32�Լ�������intռ4�ֽ�
		 */
		try(RandomAccessFile raf 
				= new RandomAccessFile("user.dat", "rw")){
			raf.seek(raf.length());
			
			byte[] data= username.getBytes("utf-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			data = password.getBytes("utf-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			data = nickname.getBytes("utf-8");
			data = Arrays.copyOf(data, 32);
			raf.write(data);
			
			
			raf.writeInt(age);
			
			System.out.println("�ļ�д�����");
			forward("myweb/reg_success.html", request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		
		System.out.println("RegServlet������ע��ҵ����ϣ�");
	}
	
}
