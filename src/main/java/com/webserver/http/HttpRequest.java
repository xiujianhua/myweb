package com.webserver.http;

import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * 请求对象
 * 该类的每个实例用于表示客户端发送过来的
 * 一个具体的请求内容
 * 
 * 请求有三个部分：
 * 请求行，消息头，消息正文
 * @author thief
 *
 */

public class HttpRequest {
	/*
	 * 
	 * 请求行相关信息定义
	 */
	//请求方式
	private String method;
	//请求资源的抽象路径
	private String url;
	//请求使用的Http协议版本
	private String protocol;
	//url中的请求部分
	private String requestURI;
	//url中的参数部分
	private String queryString;
	//保存每一个具体的参数
	private Map<String,String> parameters = new HashMap<String, String>();
	

	/*
	 * 消息头相关信息定义
	 */
	private Map <String,String> headers = new HashMap<String, String>();
	
	
	
	/*
	 * 消息正文相关信息定义
	 */
	
	
	/*
	 * 与客户端连接相关定义
	 */
	private Socket socket;
	private InputStream in;
	/**
	 * 构造方法，用来初始化请求对象
	 * 初始化请求对象的过程，就是读取并解系客户端
	 * 发送过来的请求过程。
	 * 对此，构造方法要求将Socket传入，以此获取输入流
	 * 读取客户端发送的请求内容
	 * 
	 *  解析一个请求分为3步
	 *  1解析请求行
	 *  2解析消息头
	 *  3解析消息正文
	 * @param socket
	 * @throws EmptyRequestException 
	 */
	public HttpRequest(Socket socket) throws EmptyRequestException {
		System.out.println("HttpRequest：初始化请求...");
		try {
			this.socket = socket;
			//通过Socket获取输入流，获取客户端发送过来的请求内容
			this.in = socket.getInputStream();
			
			//1
			parseRequestLine();
			
			//2
			parseRequestHeaders();
			
			//3
			parseContent();
			
			
		} catch (EmptyRequestException e) {
			throw e;
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		
		

//		System.out.println(line);
		
		System.out.println("HttpRequest：初始化请求完毕！");
		
	}
	/**
	 * 解析请求行
	 * @throws EmptyRequestException 
	 */
	private void parseRequestLine() throws EmptyRequestException {
		System.out.println("开始解析请求行...");
		String line = readLine();
		System.out.println("请求行内容："+line);
		if("".equals(line)) {
			//空请求
			throw new EmptyRequestException();
		}
		/*
		 * 将请求行中的三个信息分别解析出来并设置到对应属性上
		 * method,url,protocol
		 */
		String[] str = line.split("\\s");
		this.method = str[0];
		this.url = str[1];
		this.protocol = str[2];
				
		System.out.println("method: "+method);
		System.out.println("url: "+url);
		System.out.println("protocol: "+protocol);
		
		//进一步解析url部分
		parseUrl();
		
		System.out.println("解析请求行完毕！");
	}
	
	/**
	 * 进一步解析url部分
	 */
	private void parseUrl() {
		/*
		 * 由于url中可能含有用户传递的参数部分，
		 * 对此我们要对url进行进一步解析
		 * 
		 * 首先要判断当前url是否含有参数，
		 * 判断依据可以根据url中是否含有“？”。
		 * 若有则表示该url含有参数邠，若没有则表示当前url不含参数
		 * 
		 * 若不含参数，则直接将url的值赋值给属性：requestURI即可
		 * 
		 * 若含有参数：
		 * 1：先将url按照“？”拆为两部分，将“？”左侧富裕requestURI
		 * 		右侧赋值与queryString
		 * 
		 * 2：细分参数部分，将参数部分按照“&”拆分出来每一个参数
		 * 		然后每一个参数再按照“=”拆分为两部分
		 * 		左侧为key, 右侧为value 保存到parameters的MAP里
		 * 	
		 * 
		 */
		System.out.println("进一步解析url");
		String[] data = url.split("\\?");
		requestURI = data[0];
		if(data.length>1) {
			queryString = data[1];
			data = queryString.split("&");
			for(String str:data) {
				String[] line = str.split("=");
				String key = line[0];
				if(line.length>1) {
					parameters.put(line[0],line[1]);
				}else {
					parameters.put(line[0],null);
				}
			}
		}
		System.out.println("requestURI: "+requestURI);
		System.out.println("queryString: "+queryString);
		System.out.println("parameters: "+parameters);
	}
	
	/**
	 * 解析消息头
	 */
	private void parseRequestHeaders() {
		System.out.println("开始解析消息头...");
		/*
		 * 循环调用realLine方法，读取若干的消息头
		 * 当readLine方法值为一个空字符串时，
		 * 那应该是单独读取到了CRLF，
		 * 这时就可以停止读取了
		 * 
		 * 当读取到一个消息头后，我们可以将其按照“：”
		 * （冒号空格）进行拆分，这样可以拆分出两项
		 * 第一项就是消息头的名字，第二项为消息头的值
		 * 将他们保存到map里，完成解析工作
		 */
		String line = null;
		while(!(line=readLine()).equals("")) {
			String[] data =line.split(": ");
			headers.put(data[0], data[1]);
		}
		System.out.println("header:"+headers);
		System.out.println("解析消息头完毕！");
	}
	/**
	 * 解析消息正文
	 */
	
	private void parseContent() {
		System.out.println("开始解析消息正文...");
		
		System.out.println("解析消息正文完毕！");
		
	}
	
	/**
	 * 通过客户端对应的输入流读取客户端发送过来的一行字符串，
	 * 以CRLF为一行结束的标志。返回的这行字符串中部含有后面的CRLF。
	 */
	private String readLine() {
		try {
			
			StringBuilder builder = new StringBuilder();
			int d = -1;
			//c1表示上次读到的字符，c2表示本次读到的字符
			char c1='a',c2='a';
			while((d=in.read())!=-1) {
				c2 = (char)d;
				if(c1==13&&c2==10) {
					break;
				}
				builder.append(c2);
				c1 = c2;
			}
			return builder.toString().trim();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	public String getMethod() {
		return method;
	}
	public String getUrl() {
		return url;
	}
	public String getProtocol() {
		return protocol;
	}
	public String getHeader(String name) {
		return headers.get(name);
		
	}
	public String getRequestURI() {
		return requestURI;
	}
	public String getQueryString() {
		return queryString;
	}
	/**
	 * 根据参数名获取对应的参数值
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	
}
