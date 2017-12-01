package psy.utils;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import psy.measure.bo.message.StaticMessage;
import psy.measure.bo.message.TextMessage;

import com.thoughtworks.xstream.XStream;

/**
 * 对微信接受的消息做处理
 * @author yuegu
 *
 */
public class MessageDealUtil {
	
	/*
	 * 全局变量
	 * 获取可能要提示给用户的信息
	 */
	
	public Map<String,List> getStaticMessage() throws IllegalArgumentException, IllegalAccessException{
		List<String> menus = new ArrayList<String>();
		List<String> psymeasures = new ArrayList<String>();
		List<String> blogs = new ArrayList<String>();
		Map<String,List> maps = new HashMap<String,List>();
		Class clazz = StaticMessage.class;
		Field[] fields = clazz.getDeclaredFields();
		for(Field field : fields){
			if(field.getName().contains("MESSAGE_MENU")){
				 menus.add((String) field.get(clazz));
			}
			if(field.getName().contains("PSY_MEASURE")){
				psymeasures.add((String) field.get(clazz));
			}
			if(field.getName().contains("BLOG_ENTRY")){
				blogs.add((String) field.get(clazz));
			}
		}
		maps.put("menus",menus);
		maps.put("psymeasures",psymeasures);
		maps.put("blogs",blogs);
		return maps;
	}

	/*
	 * 普通文本消息
	 * 自动提示
	 * xml转化为Text
	 */
	public Map<String,String> xmlToText(HttpServletRequest req){
		Map<String,String> map = new HashMap<String,String>();
		InputStream ins = null;
		try {
			ins = req.getInputStream();
			SAXReader sax = new SAXReader();
			Document doc = sax.read(ins);
			Element root = doc.getRootElement();
			
			List<Element> eles = root.elements();
			for(Element ele : eles){
				map.put(ele.getName(), ele.getText());
			}
			
		}catch (DocumentException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(ins!=null){
				try {
					ins.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		return map;
	}
	
	/*
	 * 普通文本消息
	 * xml转化为Text
	 */
	
	public String textToXml(TextMessage tm){
		XStream xs = new XStream();
		xs.alias("xml", tm.getClass());
		return xs.toXML(tm);
	}
	
	/*
	 * 自动回复
	 */
	public String responseAuto(String receiveContent) throws IllegalArgumentException, IllegalAccessException{
		MessageDealUtil mdu = new MessageDealUtil();
		Map<String,List> maps = mdu.getStaticMessage();
		List<String> menus = maps.get("menus");
		List<String> psymeasures = maps.get("psymeasures");
		List<String> blogs = maps.get("blogs");
		String responseContent = "";
		TimeUtil time = new TimeUtil();
		String reply = time.formatYYYYMMDDHHMMSS(new Date())+"\r\n你发了：";
		//如果是?、？、menu、菜单
		if("?".equals(receiveContent.trim()) || "？".equals(receiveContent.trim()) 
				|| "menu".equals(receiveContent.trim().toLowerCase()) || "菜单".equals(receiveContent.trim())){
			for(String menu : menus){
				responseContent += menu+"\r\n";
			}
		}else if("1".equals(receiveContent.trim())){
			for(String psymeasure : psymeasures){
				responseContent += psymeasure+"\r\n";
			}
		}else if("2".equals(receiveContent.trim())){
			for(String blog : blogs){
				responseContent += blog+"\r\n";
			}
		}else{
			responseContent = reply + receiveContent + "\r\n";
		}
		System.out.println(responseContent);
		return responseContent.substring(0,responseContent.length()-2);
	}
}
