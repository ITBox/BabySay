package cc.itbox.babysay;

public class ErrorInfo {

	/**
	 * 错误代码
	 */
	public int mErrorCode = ErrorCode.ERROR_GENERAL_UNDEFINE;
	
	/**
	 * 错误的文字描述
	 */
	public String mErrorText = "";
	
	/**
	 * 保留字段备用，如果截获异常，用来保存对异常的原始描述
	 */
	public String mErrorReservedText = "";
	
	/**
	 * 保留字段备用，用来保存某些数据
	 */
	public Object mErrorReservedObject = null;
}
