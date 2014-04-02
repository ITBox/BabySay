package cc.itbox.babysay.bean;

import android.provider.BaseColumns;
import cc.itbox.babysay.db.UserTable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 
 * @author malinkang 2014-3-12
 * 
 */
@Table(name = UserTable.TABLE_NAME, id = BaseColumns._ID)
public class User extends Model {

	@Column(name = UserTable.COLUMN_USER_ID)
	private int uid;// 唯一标识
	@Column(name = UserTable.COLUMN_AVATAR)
	private String avatar;// 头像URL
	@Column(name = UserTable.COLUMN_BIRTHDAY)
	private String birthday;// 生日
	@Column(name = UserTable.COLUMN_DESCRIPTION)
	private String description;// 个人描述
	@Column(name = UserTable.COLUMN_GENDER)
	private String gender;// 性别
	@Column(name = UserTable.COLUMN_NICKNAME)
	private String nickname;// 昵称
	@Column(name = UserTable.COLUMN_FOLLOWERS_COUNT)
	private int followers_count;// 粉丝数
	@Column(name = UserTable.COLUMN_FRIENDS_COUNT)
	private int friends_count;// 关注数
	@Column(name = UserTable.COLUMN_BI_FOLLOWERS_COUNT)
	private int bi_followers_count;// 互相关注数量
	@Column(name = UserTable.COLUMN_FOLLOW_ME)
	private boolean follow_me;// 是否关注当前用户
	@Column(name = UserTable.COLUMN_CREATE_AT)
	private String create_at;// 用户创建时间

	public int getUid() {
		return uid;
	}

	public void setUid(int uid) {
		this.uid = uid;
	}

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public int getBi_followers_count() {
		return bi_followers_count;
	}

	public void setBi_followers_count(int bi_followers_count) {
		this.bi_followers_count = bi_followers_count;
	}

	public boolean isFollow_me() {
		return follow_me;
	}

	public void setFollow_me(boolean follow_me) {
		this.follow_me = follow_me;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	@Override
	public String toString() {
		return "User [uid=" + uid + ", avatar=" + avatar + ", birthday="
				+ birthday + ", description=" + description + ", gender="
				+ gender + ", nickname=" + nickname + ", followers_count="
				+ followers_count + ", friends_count=" + friends_count
				+ ", bi_followers_count=" + bi_followers_count + ", follow_me="
				+ follow_me + ", create_at=" + create_at + "]";
	}

}
