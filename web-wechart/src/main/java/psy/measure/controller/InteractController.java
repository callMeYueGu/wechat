package psy.measure.controller;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import psy.measure.bo.message.TextMessage;
import psy.utils.LinkCheck;
import psy.utils.MessageDealUtil;

/**
 * 接收处理消息
 * @author yuegu
 *
 */

@Controller
@RequestMapping("/linkwc")
public class InteractController{
	
	/*
	 * 验证signature
	 * 微信平台修改服务器配置，点击提交时，会进到该发法验证
	 * 配置token应与本处验证用的token一致
	 * 该方法不要有返回值，用void
	 */
	@RequestMapping(method={RequestMethod.GET})
	public void validateSignature(HttpServletRequest req,HttpServletResponse res) 
			throws IOException, IllegalArgumentException, IllegalAccessException{
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = res.getWriter();
		if(LinkCheck.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
	}
	
	/*
	 * 接收消息并响应
	 * springMVC通过method={RequestMethod.POST}实现serlvet的doPost
	 */
	@RequestMapping(method={RequestMethod.POST})
	public void getMessage(HttpServletRequest req,HttpServletResponse res) 
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException, UnsupportedEncodingException{
		req.setCharacterEncoding("UTF-8");
		res.setCharacterEncoding("UTF-8");
		
		PrintWriter out = null;
		try {
			out = res.getWriter();
			
			MessageDealUtil mdu = new MessageDealUtil();
			Map<String,String> map = mdu.xmlToText(req);
			String receiveContent = map.get("Content");
			String responseContent = mdu.responseAuto(receiveContent);
			
			String message=null;
			if("text".equals(map.get("MsgType"))){
				TextMessage tm = new TextMessage();
				tm.setContent(responseContent);
				tm.setFromUserName(map.get("ToUserName"));
				tm.setToUserName(map.get("FromUserName"));
				tm.setMsgType(map.get("MsgType"));
				tm.setCreateTime(new Date().getTime());
				message = mdu.textToXml(tm);
			}
			System.out.println(message);
			out.print(message);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(out!=null){
				out.close();
			}
		}
	}
	
}
