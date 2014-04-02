package cc.itbox.babysay.db;

import java.lang.reflect.Type;

import cc.itbox.babysay.bean.User;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class UserAdapter implements JsonSerializer<User> {

	@Override
	public JsonElement serialize(User user, Type arg1,
			JsonSerializationContext arg2) {
		JsonObject jsonObject = new JsonObject();
		jsonObject.addProperty("uid", user.getUid());
		jsonObject.addProperty("avatar", user.getAvatar());
		jsonObject.addProperty("birthday", user.getBirthday());
		jsonObject.addProperty("description", user.getDescription());
		jsonObject.addProperty("gender", user.getGender());
		jsonObject.addProperty("nickname", user.getNickname());
		jsonObject.addProperty("followers_count", user.getFollowers_count());
		jsonObject.addProperty("friends_count", user.getFriends_count());
		jsonObject.addProperty("bi_followers_count", user.getFollowers_count());
		jsonObject.addProperty("follow_me", user.isFollow_me());
		jsonObject.addProperty("create_at", user.getCreate_at());
		return jsonObject;

	}
}