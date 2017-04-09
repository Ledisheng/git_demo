package com.leyu.util;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.leyu.po.TextMessage;
import com.thoughtworks.xstream.XStream;

public class MessageUtil {
	
	public static final String MESSAGE_TEXT = "text";
	public static final String MESSAGE_IMAGE = "image";
	public static final String MESSAGE_VOICE = "voice";
	public static final String MESSAGE_VIDEO = "video";
	public static final String MESSAGE_LINK = "link";
	public static final String MESSAGE_LOCATION = "location";
	public static final String MESSAGE_EVENT = "event";
	public static final String MESSAGE_SUBSCRIBE = "subscribe";
	public static final String MESSAGE_UNSUBSCRIBE = "unsubscribe";
	public static final String MESSAGE_CLICK = "click";
	public static final String MESSAGE_VIEW = "view";

	/**
	 * ���������õĽ�xml ת�� map
	 */
	public static Map<String, String> xmlToMap(InputStream input)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();// ������

		Document doc = reader.read(input);// ������Ӧdocument������
		Element root = doc.getRootElement();// ��ȡxml���ڵ����
		List<Element> list = root.elements();// ��ȡ���ڵ��µ������ӽڵ�
		for (Element element : list) {
			map.put(element.getName(), element.getText());
		}
		input.close();
		return map;
	}

	/** ��ӡmap */
	public static void printInfo(Map<String,String> map) {
		// 1����ȡkeyset
		Set<String> keySet = map.keySet();
		// 2��ʹ��Set���ϵĵ�����
		Iterator<String> iterator = keySet.iterator();
		// 3����ʼ����
		while (iterator.hasNext()) {
			// ������map�е� key,��ӡ��key��Ӧ��valueֵ
			String key = iterator.next();
			System.out.println("key="+key+";	value="+map.get(key));
		}
	}

	/**
	 * ��������ȡrequest���󣬽�xml ת�� map
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request)
			throws Exception {
		InputStream input = request.getInputStream();
		Map<String, String> map = xmlToMap(input);

		return map;
	}

	/**
	 * ��������ȡ�ı����󣬽�xml ת�� map
	 */
	// InputStream is = new FileInputStream(new File("Ҫ��ȡ��XML�ĵ��������ı�"));
	public static void xmlToMap(String pathname) throws Exception {
		File file = new File(pathname);
		InputStream input = null;
		xmlToMap(input);
	}



	/**
	 * ͨ��class���ȡ��Դ�ļ��� ��'/'��ͷ��·��: ʹ�þ���·��Ѱ���ļ�
	 * */
	public static InputStream loadResourceByClazz(Class<?> clazz, String path) {
		System.out.println(path);
		return clazz.getResourceAsStream(path);
	}

	/**
	 * ���������ı���Ϣ(����) ת�� xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		String xml = xstream.toXML(textMessage);
		return xml;
	}
	
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("��ӭ���Ĺ�ע���밴�˵���ʾ���в���:\n\n");
		sb.append("�ظ�1������\n");
		sb.append("�ظ�2������\n");
		sb.append("�ظ�3�������˵���");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("hello ����");
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("hello ����");
		return sb.toString();
	}
	
	public static String initText(String toUser,String fromUser,String content){
		TextMessage textMessage = new TextMessage();
		textMessage.setContent(content);
		textMessage.setFromUserName(fromUser);
		textMessage.setToUserName(toUser);
		textMessage.setCreateTime(dateFormate());
		textMessage.setMsgId(MessageUtil.MESSAGE_TEXT);
		return textMessageToXml(textMessage);
	}
	
	public static String dateFormate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = sdf.format(new Date());
		return format;
	}
	
	
	/**
	 * main����
	 * */
	public static void main(String[] args) throws Exception {
		// // ��ȡ��Ŀ��Դ�ļ� /WxServer0407/src/main/resources/resource/test1.xml
//		InputStream input = loadResourceByClazz(MessageUtil.class,
//				"/resource/test1.xml");
//		Map<String, String> xmlToMap = xmlToMap(input);
//		printInfo(xmlToMap);
		TextMessage textMessage = new TextMessage();
		textMessage.setContent("this is a test");
		textMessage.setCreateTime("1348831860");
		textMessage.setFromUserName("fromUser");
		textMessage.setMsgId("1234567890123456");
		textMessage.setMsgType("text");
		textMessage.setToUserName("toUser");
		String textMessageToXml = textMessageToXml(textMessage);
		System.out.println(textMessageToXml);
	}

}
