package cc.itbox.babysay;
/**
 * 
 * @author youzh
 * 2014-3-9 下午4:23:27
 */
public class ErrorCode {
	public static final int ERROR_GENERAL_UNDEFINE 					= -1000;
	public static final int ERROR_GENERAL_SUCC 						= 1;
	
	public static final int ERROR_SERVER_RESPONSE_NULL 				= 1001;

	public static final int ERROR_RESPONSE_HTTP_1 					= 2001;
	public static final int ERROR_RESPONSE_HTTP_2 					= 2002;
	public static final int ERROR_RESPONSE_HTTP_3 					= 2003;
	public static final int ERROR_RESPONSE_HTTP_4 					= 2004;
	public static final int ERROR_RESPONSE_HTTP_5 					= 2005;
	public static final int ERROR_RESPONSE_HTTP_6 					= 2006;
	public static final int ERROR_RESPONSE_HTTP_7 					= 2007;
	public static final int ERROR_RESPONSE_HTTP_8 					= 2008;
	public static final int ERROR_RESPONSE_HTTP_9 					= 2009;
	public static final int ERROR_RESPONSE_HTTP_10					= 2010;
	public static final int ERROR_RESPONSE_HTTP_11					= 2011;
	
	public static String getErrorDesription(int errCode) {
		switch(errCode) {
		case ERROR_RESPONSE_HTTP_1:
			return "创建失败";
		case ERROR_RESPONSE_HTTP_2:
			return "返回值异常";
		case ERROR_RESPONSE_HTTP_3:
			return "没有返回实体";
		case ERROR_RESPONSE_HTTP_4:
			return "不支持的编码异常";
		case ERROR_RESPONSE_HTTP_5:
			return "请求资源未找到";
		case ERROR_RESPONSE_HTTP_6:
			return "请求超时";
		case ERROR_RESPONSE_HTTP_7:
			return "服务端出现异常";
		case ERROR_RESPONSE_HTTP_8:
			return "服务端相应超时";
		case ERROR_RESPONSE_HTTP_9:
			return "客户端协议异常";
		case ERROR_RESPONSE_HTTP_10:
			return "解析异常";
		case ERROR_RESPONSE_HTTP_11:
			return "连接超时异常";
		default:
			return "";
		}
	}

}
