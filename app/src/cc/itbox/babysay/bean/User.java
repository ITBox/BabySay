package cc.itbox.babysay.bean;

/**
 * 
 * @author malinkang 2014-3-12
 * 
 */
public class User {
	private String avatar;// 头像URL
	private String birthday;// 生日
	private String login;// 唯一标识
	private String sex;// 性别
	private String username;// 用户名

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
