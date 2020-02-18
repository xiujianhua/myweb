package com.webserver.servlet;

import java.io.File;
import java.io.RandomAccessFile;

import com.webserver.http.HttpRequest;
import com.webserver.http.HttpResponse;

/**
 * 处理登录的类
 * @author thief
 *
 */
public class LoginServlet extends HttpServlet {

	public void service(HttpRequest request,HttpResponse response) {
		System.out.println("开始处理登录。。。");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		System.out.println("username: "+username);
		System.out.println("pasword: "+password);
			
		byte[] data = new byte[32];
		
		try (RandomAccessFile raf = new RandomAccessFile("user.dat", "rw");){
			boolean check = false;
			for(int i=0;i<raf.length()/100;i++) {
				raf.seek(i*100);
				raf.read(data);
				String usrName = new String(data,"utf-8").trim();
				if(username.equals(usrName)) {
					raf.read(data);
					String passWord= new String(data,"utf-8").trim();
					if(password.equals(passWord)) {
						check = true;
					}
					break;
				}
			}
			if(check) {
				System.out.println(11111111);
				forward("myweb/login_success.html", request, response);
			}else {
				forward("myweb/login_fail.html", request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("登录处理完成");
		
	}
}
