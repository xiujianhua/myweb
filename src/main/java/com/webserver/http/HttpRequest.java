package com.webserver.http;

import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * �������
 * �����ÿ��ʵ�����ڱ�ʾ�ͻ��˷��͹�����
 * һ���������������
 * 
 * �������������֣�
 * �����У���Ϣͷ����Ϣ����
 * @author thief
 *
 */

public class HttpRequest {
	/*
	 * 
	 * �����������Ϣ����
	 */
	//����ʽ
	private String method;
	//������Դ�ĳ���·��
	private String url;
	//����ʹ�õ�HttpЭ��汾
	private String protocol;
	//url�е����󲿷�
	private String requestURI;
	//url�еĲ�������
	private String queryString;
	//����ÿһ������Ĳ���
	private Map<String,String> parameters = new HashMap<String, String>();
	

	/*
	 * ��Ϣͷ�����Ϣ����
	 */
	private Map <String,String> headers = new HashMap<String, String>();
	
	
	
	/*
	 * ��Ϣ���������Ϣ����
	 */
	
	
	/*
	 * ��ͻ���������ض���
	 */
	private Socket socket;
	private InputStream in;
	/**
	 * ���췽����������ʼ���������
	 * ��ʼ���������Ĺ��̣����Ƕ�ȡ����ϵ�ͻ���
	 * ���͹�����������̡�
	 * �Դˣ����췽��Ҫ��Socket���룬�Դ˻�ȡ������
	 * ��ȡ�ͻ��˷��͵���������
	 * 
	 *  ����һ�������Ϊ3��
	 *  1����������
	 *  2������Ϣͷ
	 *  3������Ϣ����
	 * @param socket
	 * @throws EmptyRequestException 
	 */
	public HttpRequest(Socket socket) throws EmptyRequestException {
		System.out.println("HttpRequest����ʼ������...");
		try {
			this.socket = socket;
			//ͨ��Socket��ȡ����������ȡ�ͻ��˷��͹�������������
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
		
		System.out.println("HttpRequest����ʼ��������ϣ�");
		
	}
	/**
	 * ����������
	 * @throws EmptyRequestException 
	 */
	private void parseRequestLine() throws EmptyRequestException {
		System.out.println("��ʼ����������...");
		String line = readLine();
		System.out.println("���������ݣ�"+line);
		if("".equals(line)) {
			//������
			throw new EmptyRequestException();
		}
		/*
		 * ���������е�������Ϣ�ֱ�������������õ���Ӧ������
		 * method,url,protocol
		 */
		String[] str = line.split("\\s");
		this.method = str[0];
		this.url = str[1];
		this.protocol = str[2];
				
		System.out.println("method: "+method);
		System.out.println("url: "+url);
		System.out.println("protocol: "+protocol);
		
		//��һ������url����
		parseUrl();
		
		System.out.println("������������ϣ�");
	}
	
	/**
	 * ��һ������url����
	 */
	private void parseUrl() {
		/*
		 * ����url�п��ܺ����û����ݵĲ������֣�
		 * �Դ�����Ҫ��url���н�һ������
		 * 
		 * ����Ҫ�жϵ�ǰurl�Ƿ��в�����
		 * �ж����ݿ��Ը���url���Ƿ��С�������
		 * �������ʾ��url���в���ߓ����û�����ʾ��ǰurl��������
		 * 
		 * ��������������ֱ�ӽ�url��ֵ��ֵ�����ԣ�requestURI����
		 * 
		 * �����в�����
		 * 1���Ƚ�url���ա�������Ϊ�����֣�����������฻ԣrequestURI
		 * 		�Ҳำֵ��queryString
		 * 
		 * 2��ϸ�ֲ������֣����������ְ��ա�&����ֳ���ÿһ������
		 * 		Ȼ��ÿһ�������ٰ��ա�=�����Ϊ������
		 * 		���Ϊkey, �Ҳ�Ϊvalue ���浽parameters��MAP��
		 * 	
		 * 
		 */
		System.out.println("��һ������url");
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
	 * ������Ϣͷ
	 */
	private void parseRequestHeaders() {
		System.out.println("��ʼ������Ϣͷ...");
		/*
		 * ѭ������realLine��������ȡ���ɵ���Ϣͷ
		 * ��readLine����ֵΪһ�����ַ���ʱ��
		 * ��Ӧ���ǵ�����ȡ����CRLF��
		 * ��ʱ�Ϳ���ֹͣ��ȡ��
		 * 
		 * ����ȡ��һ����Ϣͷ�����ǿ��Խ��䰴�ա�����
		 * ��ð�ſո񣩽��в�֣��������Բ�ֳ�����
		 * ��һ�������Ϣͷ�����֣��ڶ���Ϊ��Ϣͷ��ֵ
		 * �����Ǳ��浽map���ɽ�������
		 */
		String line = null;
		while(!(line=readLine()).equals("")) {
			String[] data =line.split(": ");
			headers.put(data[0], data[1]);
		}
		System.out.println("header:"+headers);
		System.out.println("������Ϣͷ��ϣ�");
	}
	/**
	 * ������Ϣ����
	 */
	
	private void parseContent() {
		System.out.println("��ʼ������Ϣ����...");
		
		System.out.println("������Ϣ������ϣ�");
		
	}
	
	/**
	 * ͨ���ͻ��˶�Ӧ����������ȡ�ͻ��˷��͹�����һ���ַ�����
	 * ��CRLFΪһ�н����ı�־�����ص������ַ����в����к����CRLF��
	 */
	private String readLine() {
		try {
			
			StringBuilder builder = new StringBuilder();
			int d = -1;
			//c1��ʾ�ϴζ������ַ���c2��ʾ���ζ������ַ�
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
	 * ���ݲ�������ȡ��Ӧ�Ĳ���ֵ
	 * @param name
	 * @return
	 */
	public String getParameter(String name) {
		return parameters.get(name);
	}
	
	
}
