package com.leyu.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.leyu.po.TextMessage;
import com.leyu.util.CheckUtil;
import com.leyu.util.MessageUtil;

public class WeiXinServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public String dateFormate(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String format = sdf.format(new Date());
		return format;
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if (CheckUtil.checkSignature(signature,timestamp,nonce)){
			out.print(echostr);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		PrintWriter out = resp.getWriter();
		String message = null;
		
		try {
			Map<String, String> map =  MessageUtil.xmlToMap(req);
			String toUserName = map.get("ToUserName");
			String fromUserName = map.get("FromUserName");
			String createTime = map.get("CreateTime");
			String msgType = map.get("MsgType");
			String content = map.get("Content");
			String msgId = map.get("MsgId");
			
			// 判断消息类型
			if(MessageUtil.MESSAGE_TEXT.equalsIgnoreCase(msgType)){
				// 文本消息
				if("1".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.firstMenu());
				}else if("2".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.secondMenu());
				}else if("3".equals(content)){
					message = MessageUtil.initText(toUserName, fromUserName, MessageUtil.menuText());
				}
			}else if(MessageUtil.MESSAGE_EVENT.equalsIgnoreCase(msgType)){
				String eventType = map.get("Event");
				if (MessageUtil.MESSAGE_SUBSCRIBE.equalsIgnoreCase(eventType)){
					message = MessageUtil.initText(toUserName, fromUserName, content);
				}
			}
			System.out.println(message);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			out.close();
		}
	}

}
