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
	 * 描述：公用的将xml 转换 map
	 */
	public static Map<String, String> xmlToMap(InputStream input)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		SAXReader reader = new SAXReader();// 解析器

		Document doc = reader.read(input);// 创建对应document解析器
		Element root = doc.getRootElement();// 获取xml根节点对象
		List<Element> list = root.elements();// 获取根节点下的所有子节点
		for (Element element : list) {
			map.put(element.getName(), element.getText());
		}
		input.close();
		return map;
	}

	/** 打印map */
	public static void printInfo(Map<String,String> map) {
		// 1、获取keyset
		Set<String> keySet = map.keySet();
		// 2、使用Set集合的迭代器
		Iterator<String> iterator = keySet.iterator();
		// 3、开始遍历
		while (iterator.hasNext()) {
			// 迭代中map中的 key,打印出key对应的value值
			String key = iterator.next();
			System.out.println("key="+key+";	value="+map.get(key));
		}
	}

	/**
	 * 描述：获取request对象，将xml 转换 map
	 */
	public static Map<String, String> xmlToMap(HttpServletRequest request)
			throws Exception {
		InputStream input = request.getInputStream();
		Map<String, String> map = xmlToMap(input);

		return map;
	}

	/**
	 * 描述：获取文本对象，将xml 转换 map
	 */
	// InputStream is = new FileInputStream(new File("要读取的XML文档或者是文本"));
	public static void xmlToMap(String pathname) throws Exception {
		File file = new File(pathname);
		InputStream input = null;
		xmlToMap(input);
	}



	/**
	 * 通过class类读取资源文件： 以'/'开头的路径: 使用绝对路径寻找文件
	 * */
	public static InputStream loadResourceByClazz(Class<?> clazz, String path) {
		System.out.println(path);
		return clazz.getResourceAsStream(path);
	}

	/**
	 * 描述：将文本消息(对象) 转换 xml
	 */
	public static String textMessageToXml(TextMessage textMessage) {
		XStream xstream = new XStream();
		xstream.alias("xml", textMessage.getClass());
		String xml = xstream.toXML(textMessage);
		return xml;
	}
	
	public static String menuText(){
		StringBuffer sb = new StringBuffer();
		sb.append("欢迎您的关注，请按菜单提示进行操作:\n\n");
		sb.append("回复1：篮球\n");
		sb.append("回复2：足球\n");
		sb.append("回复3：调出菜单。");
		return sb.toString();
	}
	
	public static String firstMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("hello 篮球");
		return sb.toString();
	}
	
	public static String secondMenu(){
		StringBuffer sb = new StringBuffer();
		sb.append("hello 足球");
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
	 * main方法
	 * */
	public static void main(String[] args) throws Exception {
		// // 读取项目资源文件 /WxServer0407/src/main/resources/resource/test1.xml
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
