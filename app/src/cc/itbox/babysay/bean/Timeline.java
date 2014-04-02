package cc.itbox.babysay.bean;

import android.provider.BaseColumns;
import cc.itbox.babysay.db.TimelineTable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.Gson;

/**
 * 动态信息数据实体
 * 
 * @author baoyz
 * 
 *         2014-2-23 下午4:44:28
 * 
 */
@Table(name = TimelineTable.TABLE_NAME, id = BaseColumns._ID)
public class Timeline extends Model {
	private static final Gson mGson = new Gson();
	@Column(name = TimelineTable.COLUMN_CREATE_AT)
	private String create_at;// 创建时间
	@Column(name = TimelineTable.COLUMN_TIMELINE_ID)
	private int timeline_id;// id
	@Column(name = TimelineTable.COLUMN_DESCRIPTION)
	private String description;// 描述
	@Column(name = TimelineTable.COLUMN_PIC)
	private String pic;// 图片
	@Column(name = TimelineTable.COLUMN_PRAISED)
	private boolean praised;// 当前用户是否赞过
	@Column(name = TimelineTable.COLUMN_PRAISE_COUNT)
	private int praise_count;// 赞的数量
	@Column(name = TimelineTable.COLUMN_COMMENTS_COUNT)
	private int comments_count;// 评论的数量
	@Column(name = TimelineTable.COLUMN_AUDIO)
	private String audio;// 音频路径
	@Column(name = TimelineTable.COLUMN_AUDIO_DURATION)
	private String audio_duration;// 音频时长

	private User user;
	@Column(name = TimelineTable.COLUMN_USER_JSON)
	private String user_json;

	public String getUser_json() {
		return user_json;
	}

	public void setUser_json(String user_json) {
		this.user_json = user_json;
	}

	public String getCreate_at() {
		return create_at;
	}

	public void setCreate_at(String create_at) {
		this.create_at = create_at;
	}

	public int getTimeline_id() {
		return timeline_id;
	}

	public void setTimeline_id(int timeline_id) {
		this.timeline_id = timeline_id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public boolean isPraised() {
		return praised;
	}

	public void setPraised(boolean praised) {
		this.praised = praised;
	}

	public int getPraise_count() {
		return praise_count;
	}

	public void setPraise_count(int praise_count) {
		this.praise_count = praise_count;
	}

	public int getComments_count() {
		return comments_count;
	}

	public void setComments_count(int comments_count) {
		this.comments_count = comments_count;
	}

	public String getAudio() {
		return audio;
	}

	public void setAudio(String audio) {
		this.audio = audio;
	}

	public String getAudio_duration() {
		return audio_duration;
	}

	public void setAudio_duration(String audio_duration) {
		this.audio_duration = audio_duration;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;

	}

	public static User getUserFromJson(String json) {
		return mGson.fromJson(json, User.class);
	}

}
