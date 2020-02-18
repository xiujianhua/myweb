package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.Arrays;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * 处理注册业务
 * 
 * @author thief
 *
 */

public class RegServlet extends HttpServlet{
	public void service(HttpRequest request, HttpResponse response) {
		System.out.println("RegServlet:开始处理注册业务");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		int age = Integer.parseInt(request.getParameter("age"));
		System.out.println("username:"+username);
		System.out.println("password:"+password);
		System.out.println("nickname:" +nickname);
		System.out.println("age"+age);
		
		/*
		 * 2：将用户信息写入文件user.dat中
		 * 每个用户占用100字节，其中用户名，密码，
		 * 昵称没字为字符串各占32自己，年龄int占4字节
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
			
			System.out.println("文件写入完毕");
			forward("myweb/reg_success.html", request, response);
			
		}catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		
		
		
		System.out.println("RegServlet：处理注册业务完毕！");
	}
	
}
