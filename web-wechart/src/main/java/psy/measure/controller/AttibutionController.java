package psy.measure.controller;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import psy.utils.LinkCheck;

/**
 * 归因方式数据处理
 * @author yuegu
 *
 */

@Controller
@RequestMapping("/linkwc")
public class AttibutionController{
	/*
	 * 验证signature
	 * 微信平台修改服务器配置，点击提交时，会进到该发法验证
	 * 配置token应与本处验证用的token一致
	 * 该方法不要有返回值，用void
	 */
	@RequestMapping(method={RequestMethod.GET})
	public void validateSignature(HttpServletRequest req,HttpServletResponse res) throws IOException{
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = res.getWriter();
		if(LinkCheck.checkSignature(signature, timestamp, nonce)){
			out.print(echostr);
		}
		//return "1";
	}
	
	/*
	 * 接受消息
	 * springMVC通过method={RequestMethod.POST}实现serlvet的doPost
	 */
	@RequestMapping(method={RequestMethod.POST})
	public String getMessage(HttpServletRequest req,HttpServletResponse res){
		
		return "";
	}
	
}
