package com.webserver.http;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * HTTPЭ��������ݶ���
 * @author thief
 *
 */

public class HttpContext {
	/*
	 * ״̬������״̬�����Ķ�Ӧ
	 * key: ״̬����
	 * value:״̬����
	 * 
	 */
	private static Map<Integer,String> statusReasonMapping
		= new HashMap<Integer,String>();
	
	/*
	 * ��Դ�ļ���׺���Ӧ�������Ͷ���Ķ�Ӧ��ϵ
	 * key: ��Դ�ļ��ĺ�׺��
	 * value:Content-Type��Ӧ��ֵ
	 */
	private static Map<String,String> mineMapping 
		= new HashMap<String,String>();
	
	
	
	static {
		//��ʼ��
		initstatusMapping();
		initMineMapping();
	}
	
	private static void initMineMapping() {
		/*
		 * ������ĿĿ¼�е�confĿ¼���web.xml�ļ�
		 * ����Ԫ���е�������Ԫ��<mime-mapping>
		 * �����������������е���Ԫ�أ�
		 * <extension>�е��ı���Ϊkey
		 * <mine-type>�е��ı���Ϊvalue
		 * ���뵽mineMapping���Map
		 * ��ɳ�ʼ������
		 */
		try {
			SAXReader reader = new SAXReader();
			Document doc =  reader.read("conf/web.xml");
			Element root =doc.getRootElement();
			List<Element> list = root.elements("mime-mapping");
			for(Element ele :list) {
				String key = ele.elementText("extension");
				String value = ele.elementText("mime-type");
				mineMapping.put(key,value);
			}
			
			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		
	}
	
	private static void initstatusMapping() {
		statusReasonMapping.put(200,"OK");
		statusReasonMapping.put(201,"Created");
		statusReasonMapping.put(202,"Accepted");
		statusReasonMapping.put(204,"No Content");
		statusReasonMapping.put(301,"Moved Permanently");
		statusReasonMapping.put(302,"Moved Temporarily");
		statusReasonMapping.put(304,"Not Modified");
		statusReasonMapping.put(400,"Bad Request");
		statusReasonMapping.put(401,"Unauthorized");
		statusReasonMapping.put(403,"Forbidden");
		statusReasonMapping.put(404,"Not Found");
		statusReasonMapping.put(500,"Internal Server Error");
		statusReasonMapping.put(501,"Not Implemented");
		statusReasonMapping.put(502,"Bad Gateway");
		statusReasonMapping.put(503,"Service Unavailable");
		
	}
	
	/**
	 * ���ݸ�����״̬���ȡ��Ӧ��״̬����
	 */
	public static String getStatusReson(int code) {
		return statusReasonMapping.get(code);
		
	}
	public static void main(String[] args) {
		String fileName = "sdfa.mp4";
		int index = fileName.lastIndexOf(".");
		String ext = fileName.substring(index+1);
		String str = getContentType(ext);
		System.out.println(str);
	}
	
	/**
	 * ���ݸ����ĺ�׺��ȡ��Ӧ�ĺ�׺
	 */
	public static String getContentType(String name) {
		return mineMapping.get(name);
		
	}
}
