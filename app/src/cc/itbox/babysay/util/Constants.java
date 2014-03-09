package cc.itbox.babysay.util;

/**
 * 常量
 * 
 * @author huiyh
 * 
 */
public class Constants {

	public static final String WEIBO_KEY = "2730729900";
	public static final String WEIBO_SECRET = "581bf3af8734693b9deeb043b5d73cfc";
	public static final String QQ_ID = "101024856";
	public static final String QQ_KEY = "af5cbdc34de1d600e0721f01c3af825a";
	
	public class Request_Code{

		public static final int TAKE_PICTURE_FROM_CAMERA = 0x10;
		public static final int TAKE_PICTURE_FROM_GALLERY = 0x11;
		public final static int CROP_CAMERA_PICTURE = 0x12;
		public static final int CROP_GALLERY_PICTURE = 0x13;

		
		
	}
	 

	public static final String BASE_URL = "http://115.28.21.177/babysay/index.php/";
	public static final String REGISTER_URL = BASE_URL + "Index/register";
	public static final String LOGIN_URL = BASE_URL + "Index/login";

}
