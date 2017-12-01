package psy.measure.bo.message;
/**
 * 微信接收的普通文本消息
 * @author yuegu
 *
 */
public class TextMessage {
	private String ToUserName;//接收人
	private String FromUserName;//发送人
	private Long CreateTime;//发送时间
	private String MsgType;//消息类型
	private String Content;//消息内容
	private String MsgId;//消息编号id
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public Long getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(Long createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public String getContent() {
		return Content;
	}
	public void setContent(String content) {
		Content = content;
	}
	public String getMsgId() {
		return MsgId;
	}
	public void setMsgId(String msgId) {
		MsgId = msgId;
	}
	
	
	
	
}
