package cc.itbox.babysay.bean;

/**
 * 动态信息数据实体
 * 
 * @author baoyz
 * 
 *         2014-2-23 下午4:44:28
 * 
 */
public class DMBean {

	private String nickname;
	private long time;
	private String timeString;
	private String imageUri;
	private String voiceUri;
	private int praiseCount;
	private boolean praise;

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public String getTimeString() {
		return timeString;
	}

	public void setTimeString(String timeString) {
		this.timeString = timeString;
	}

	public String getImageUri() {
		return imageUri;
	}

	public void setImageUri(String imageUri) {
		this.imageUri = imageUri;
	}

	public String getVoiceUri() {
		return voiceUri;
	}

	public void setVoiceUri(String voiceUri) {
		this.voiceUri = voiceUri;
	}

	public int getPraiseCount() {
		return praiseCount;
	}

	public void setPraiseCount(int praiseCount) {
		this.praiseCount = praiseCount;
	}

	public boolean isPraise() {
		return praise;
	}

	public void setPraise(boolean praise) {
		this.praise = praise;
	}

}
